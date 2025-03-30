package com.svwh.phonereview.service.impl;


import com.svwh.phonereview.config.AlgorithmConfig;
import com.svwh.phonereview.domain.entity.ItemSimilarity;
import com.svwh.phonereview.domain.entity.UserSimilarity;
import com.svwh.phonereview.mapper.ItemSimilarityMapper;
import com.svwh.phonereview.mapper.UserSimilarityMapper;
import com.svwh.phonereview.service.SimilarityService;
import com.svwh.phonereview.service.UserInteractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 相似度计算服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SimilarityServiceImpl implements SimilarityService {

    private final UserInteractionService userInteractionService;
    private final UserSimilarityMapper userSimilarityMapper;
    private final ItemSimilarityMapper itemSimilarityMapper;
    private final AlgorithmConfig algorithmConfig;

    @Override
    public double calculateUserSimilarity(Map<Long, Double> user1Ratings, Map<Long, Double> user2Ratings) {
        // 使用余弦相似度计算用户相似性
        if (user1Ratings.isEmpty() || user2Ratings.isEmpty()) {
            return 0.0;
        }
        
        // 找出两个用户共同评分的物品
        Set<Long> commonItems = new HashSet<>(user1Ratings.keySet());
        commonItems.retainAll(user2Ratings.keySet());
        
        if (commonItems.isEmpty()) {
            return 0.0;
        }
        
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        
        // 计算公共物品的点积和范数
        for (Long itemId : commonItems) {
            double r1 = user1Ratings.get(itemId);
            double r2 = user2Ratings.get(itemId);
            
            dotProduct += r1 * r2;
        }
        
        // 计算两个用户评分向量的范数
        for (double rating : user1Ratings.values()) {
            norm1 += Math.pow(rating, 2);
        }
        
        for (double rating : user2Ratings.values()) {
            norm2 += Math.pow(rating, 2);
        }
        
        norm1 = Math.sqrt(norm1);
        norm2 = Math.sqrt(norm2);
        
        // 避免除以零
        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }
        
        // 计算余弦相似度
        double similarity = dotProduct / (norm1 * norm2);
        
        // 确保相似度在0-1范围内
        return Math.max(0.0, Math.min(1.0, similarity));
    }

    @Override
    public double calculateItemSimilarity(Map<Long, Double> item1Ratings, Map<Long, Double> item2Ratings) {
        // 使用余弦相似度计算物品相似性
        if (item1Ratings.isEmpty() || item2Ratings.isEmpty()) {
            return 0.0;
        }
        
        // 找出两个物品共同被评分的用户
        Set<Long> commonUsers = new HashSet<>(item1Ratings.keySet());
        commonUsers.retainAll(item2Ratings.keySet());
        
        if (commonUsers.isEmpty()) {
            return 0.0;
        }
        
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        
        // 计算公共用户的点积和范数
        for (Long userId : commonUsers) {
            double r1 = item1Ratings.get(userId);
            double r2 = item2Ratings.get(userId);
            
            dotProduct += r1 * r2;
        }
        
        // 计算两个物品评分向量的范数
        for (double rating : item1Ratings.values()) {
            norm1 += Math.pow(rating, 2);
        }
        
        for (double rating : item2Ratings.values()) {
            norm2 += Math.pow(rating, 2);
        }
        
        norm1 = Math.sqrt(norm1);
        norm2 = Math.sqrt(norm2);
        
        // 避免除以零
        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }
        
        // 计算余弦相似度
        double similarity = dotProduct / (norm1 * norm2);
        
        // 确保相似度在0-1范围内
        return Math.max(0.0, Math.min(1.0, similarity));
    }

    @Override
    @Transactional
    public int calculateAllUserSimilarities(String itemType) {
        log.info("Starting calculation of all user similarities for itemType: {}", itemType);
        long startTime = System.currentTimeMillis();
        
        // 获取所有用户的物品评分矩阵
        Map<Long, Map<Long, Double>> userItemMatrix = userInteractionService.getUserItemMatrix(itemType);
        List<Long> userIds = new ArrayList<>(userItemMatrix.keySet());
        
        // 清空之前的相似度数据
        userSimilarityMapper.deleteAll();
        log.info("Cleared existing user similarities");
        
        List<UserSimilarity> similarities = new ArrayList<>();
        int count = 0;
        double threshold = algorithmConfig.getSimilarityThreshold();
        
        // 计算所有用户对之间的相似度
        for (int i = 0; i < userIds.size(); i++) {
            Long user1Id = userIds.get(i);
            Map<Long, Double> user1Ratings = userItemMatrix.get(user1Id);
            
            for (int j = i + 1; j < userIds.size(); j++) {
                Long user2Id = userIds.get(j);
                Map<Long, Double> user2Ratings = userItemMatrix.get(user2Id);
                
                // 计算相似度
                double similarity = calculateUserSimilarity(user1Ratings, user2Ratings);
                
                // 只保存超过阈值的相似度值
                if (similarity > threshold) {
                    UserSimilarity userSimilarity = new UserSimilarity()
                            .setUser1Id(user1Id)
                            .setUser2Id(user2Id)
                            .setSimilarity(similarity)
                            .setCreateTime(LocalDateTime.now())
                            .setUpdateTime(LocalDateTime.now());
                    
                    similarities.add(userSimilarity);
                    
                    // 相似度是对称的，同时保存反向关系
                    UserSimilarity reverseSimilarity = new UserSimilarity()
                            .setUser1Id(user2Id)
                            .setUser2Id(user1Id)
                            .setSimilarity(similarity)
                            .setCreateTime(LocalDateTime.now())
                            .setUpdateTime(LocalDateTime.now());
                    
                    similarities.add(reverseSimilarity);
                    count += 2;
                }
            }
        }
        
        // 批量保存相似度数据
        if (!similarities.isEmpty()) {
            for (UserSimilarity similarity : similarities) {
                userSimilarityMapper.insert(similarity);
            }
        }
        
        long endTime = System.currentTimeMillis();
        log.info("Completed calculation of {} user similarities in {} ms", count, (endTime - startTime));
        
        return count;
    }

    @Override
    @Transactional
    public int calculateAllItemSimilarities(String itemType) {
        log.info("Starting calculation of all item similarities for itemType: {}", itemType);
        long startTime = System.currentTimeMillis();
        
        // 获取用户物品评分矩阵
        Map<Long, Map<Long, Double>> userItemMatrix = userInteractionService.getUserItemMatrix(itemType);
        
        // 转置矩阵，获取物品-用户评分矩阵
        Map<Long, Map<Long, Double>> itemUserMatrix = transposeMatrix(userItemMatrix);
        List<Long> itemIds = new ArrayList<>(itemUserMatrix.keySet());
        
        // 清空之前的相似度数据
        itemSimilarityMapper.deleteAll();
        log.info("Cleared existing item similarities");
        
        List<ItemSimilarity> similarities = new ArrayList<>();
        int count = 0;
        double threshold = algorithmConfig.getSimilarityThreshold();
        
        // 计算所有物品对之间的相似度
        for (int i = 0; i < itemIds.size(); i++) {
            Long item1Id = itemIds.get(i);
            Map<Long, Double> item1Ratings = itemUserMatrix.get(item1Id);
            
            for (int j = i + 1; j < itemIds.size(); j++) {
                Long item2Id = itemIds.get(j);
                Map<Long, Double> item2Ratings = itemUserMatrix.get(item2Id);
                
                // 计算相似度
                double similarity = calculateItemSimilarity(item1Ratings, item2Ratings);
                
                // 只保存超过阈值的相似度值
                if (similarity > threshold) {
                    ItemSimilarity itemSimilarity = new ItemSimilarity()
                            .setPost1Id(item1Id)
                            .setPost2Id(item2Id)
                            .setSimilarity(similarity)
                            .setCreateTime(LocalDateTime.now())
                            .setUpdateTime(LocalDateTime.now());
                    
                    similarities.add(itemSimilarity);
                    
                    // 相似度是对称的，同时保存反向关系
                    ItemSimilarity reverseSimilarity = new ItemSimilarity()
                            .setPost1Id(item2Id)
                            .setPost2Id(item1Id)
                            .setSimilarity(similarity)
                            .setCreateTime(LocalDateTime.now())
                            .setUpdateTime(LocalDateTime.now());
                    
                    similarities.add(reverseSimilarity);
                    count += 2;
                }
            }
        }
        
        // 批量保存相似度数据
        if (!similarities.isEmpty()) {
            for (ItemSimilarity similarity : similarities) {
                itemSimilarityMapper.insert(similarity);
            }
        }
        
        long endTime = System.currentTimeMillis();
        log.info("Completed calculation of {} item similarities in {} ms", count, (endTime - startTime));
        
        return count;
    }

    @Override
    @Cacheable(value = "userSimilaritiesCache", key = "#userId + '-' + #limit")
    public List<Long> getMostSimilarUsers(Long userId, int limit) {
        List<UserSimilarity> similarities = userSimilarityMapper.findMostSimilarUsers(userId, limit);
        return similarities.stream()
                .map(UserSimilarity::getUser2Id)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "itemSimilaritiesCache", key = "#itemId + '-' + #limit")
    public List<Long> getMostSimilarItems(Long itemId, int limit) {
        List<ItemSimilarity> similarities = itemSimilarityMapper.findMostSimilarItems(itemId, limit);
        return similarities.stream()
                .map(ItemSimilarity::getPost2Id)
                .collect(Collectors.toList());
    }
    
    /**
     * 转置用户-物品矩阵为物品-用户矩阵
     *
     * @param userItemMatrix 用户-物品评分矩阵
     * @return 物品-用户评分矩阵
     */
    private Map<Long, Map<Long, Double>> transposeMatrix(Map<Long, Map<Long, Double>> userItemMatrix) {
        Map<Long, Map<Long, Double>> itemUserMatrix = new HashMap<>();
        
        for (Map.Entry<Long, Map<Long, Double>> userEntry : userItemMatrix.entrySet()) {
            Long userId = userEntry.getKey();
            Map<Long, Double> itemRatings = userEntry.getValue();
            
            for (Map.Entry<Long, Double> itemEntry : itemRatings.entrySet()) {
                Long itemId = itemEntry.getKey();
                Double rating = itemEntry.getValue();
                
                // 确保物品在矩阵中有对应的映射
                itemUserMatrix.computeIfAbsent(itemId, k -> new HashMap<>())
                        .put(userId, rating);
            }
        }
        
        return itemUserMatrix;
    }
} 