package com.svwh.phonereview.service;

import java.util.List;
import java.util.Map;

/**
 * 相似度计算服务接口
 */
public interface SimilarityService {
    
    /**
     * 计算两个用户之间的相似度
     *
     * @param user1Ratings 用户1的评分数据 Map<物品ID, 评分>
     * @param user2Ratings 用户2的评分数据 Map<物品ID, 评分>
     * @return 相似度值，范围0-1
     */
    double calculateUserSimilarity(Map<Long, Double> user1Ratings, Map<Long, Double> user2Ratings);
    
    /**
     * 计算两个物品之间的相似度
     *
     * @param item1Ratings 物品1的评分数据 Map<用户ID, 评分>
     * @param item2Ratings 物品2的评分数据 Map<用户ID, 评分>
     * @return 相似度值，范围0-1
     */
    double calculateItemSimilarity(Map<Long, Double> item1Ratings, Map<Long, Double> item2Ratings);
    
    /**
     * 计算并保存所有用户之间的相似度
     *
     * @param itemType 物品类型
     * @return 计算的相似度对数
     */
    int calculateAllUserSimilarities(String itemType);
    
    /**
     * 计算并保存所有物品之间的相似度
     *
     * @param itemType 物品类型
     * @return 计算的相似度对数
     */
    int calculateAllItemSimilarities(String itemType);
    
    /**
     * 获取与指定用户最相似的用户列表
     *
     * @param userId 用户ID
     * @param limit 返回数量限制
     * @return 相似用户ID列表
     */
    List<Long> getMostSimilarUsers(Long userId, int limit);
    
    /**
     * 获取与指定物品最相似的物品列表
     *
     * @param itemId 物品ID
     * @param limit 返回数量限制
     * @return 相似物品ID列表
     */
    List<Long> getMostSimilarItems(Long itemId, int limit);
} 