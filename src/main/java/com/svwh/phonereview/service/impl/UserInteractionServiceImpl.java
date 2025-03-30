package com.svwh.phonereview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.svwh.phonereview.domain.entity.UserInteraction;
import com.svwh.phonereview.mapper.UserInteractionMapper;
import com.svwh.phonereview.service.UserInteractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户交互服务实现类
 */
@Slf4j
@Service

public class UserInteractionServiceImpl implements UserInteractionService {
    @Autowired
    private  UserInteractionMapper userInteractionMapper;
    
    /**
     * 不同交互类型的权重
     */
    private static final Map<String, Double> ACTION_WEIGHTS = Map.of(
            "view", 1.0,    // 浏览权重
            "like", 3.0,    // 点赞权重
            "favorite", 5.0  // 收藏权重
    );

    @Override
    public boolean recordInteraction(Long userId, Long itemId, String itemType, String actionType) {
        if (userId == null || itemId == null || itemType == null || actionType == null) {
            log.warn("Record interaction failed: invalid parameters");
            return false;
        }
        
        try {
            // 检查是否已存在相同交互记录
            LambdaQueryWrapper<UserInteraction> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserInteraction::getUserId, userId)
                   .eq(UserInteraction::getItemId, itemId)
                   .eq(UserInteraction::getItemType, itemType)
                   .eq(UserInteraction::getActionType, actionType);
            
            UserInteraction existingInteraction = userInteractionMapper.selectOne(wrapper);
            
            if (existingInteraction != null) {
                // 已存在则更新时间
                existingInteraction.setUpdateTime(LocalDateTime.now());
                userInteractionMapper.updateById(existingInteraction);
                log.debug("Updated existing interaction: userId={}, itemId={}, type={}, action={}", 
                        userId, itemId, itemType, actionType);
            } else {
                // 不存在则创建新记录
                UserInteraction interaction = new UserInteraction()
                        .setUserId(userId)
                        .setItemId(itemId)
                        .setItemType(itemType)
                        .setActionType(actionType)
                        .setCreateTime(LocalDateTime.now())
                        .setUpdateTime(LocalDateTime.now());
                
                userInteractionMapper.insert(interaction);
                log.debug("Recorded new interaction: userId={}, itemId={}, type={}, action={}", 
                        userId, itemId, itemType, actionType);
            }
            
            return true;
        } catch (Exception e) {
            log.error("Failed to record interaction", e);
            return false;
        }
    }

    @Override
    public List<UserInteraction> getUserInteractions(Long userId, String itemType) {
        return userInteractionMapper.findByUserIdAndItemType(userId, itemType);
    }

    @Override
    public List<UserInteraction> getItemInteractions(Long itemId, String itemType) {
        return userInteractionMapper.findByItemIdAndItemType(itemId, itemType);
    }

    @Override
    public Map<Long, Map<Long, Double>> getUserItemMatrix(String itemType) {
        // 获取所有用户交互记录
        LambdaQueryWrapper<UserInteraction> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInteraction::getItemType, itemType);
        List<UserInteraction> allInteractions = userInteractionMapper.selectList(wrapper);
        
        // 按用户ID分组
        Map<Long, List<UserInteraction>> userInteractions = allInteractions.stream()
                .collect(Collectors.groupingBy(UserInteraction::getUserId));
        
        // 构建用户-物品评分矩阵
        Map<Long, Map<Long, Double>> userItemMatrix = new HashMap<>();
        
        for (Map.Entry<Long, List<UserInteraction>> entry : userInteractions.entrySet()) {
            Long userId = entry.getKey();
            List<UserInteraction> interactions = entry.getValue();
            
            // 按物品ID分组计算评分
            Map<Long, List<UserInteraction>> itemInteractions = interactions.stream()
                    .collect(Collectors.groupingBy(UserInteraction::getItemId));
            
            Map<Long, Double> itemRatings = new HashMap<>();
            for (Map.Entry<Long, List<UserInteraction>> itemEntry : itemInteractions.entrySet()) {
                Long itemId = itemEntry.getKey();
                List<UserInteraction> itemInteractionList = itemEntry.getValue();
                
                // 计算该物品的隐式评分
                double rating = calculateImplicitRating(itemInteractionList);
                itemRatings.put(itemId, rating);
            }
            
            userItemMatrix.put(userId, itemRatings);
        }
        
        return userItemMatrix;
    }

    @Override
    public List<Long> getAllUserIds(String itemType) {
        return userInteractionMapper.findAllUserIds(itemType);
    }

    @Override
    public List<Long> getAllItemIds(String itemType) {
        return userInteractionMapper.findAllItemIds(itemType);
    }

    @Override
    public double calculateImplicitRating(List<UserInteraction> interactions) {
        if (interactions == null || interactions.isEmpty()) {
            return 0.0;
        }
        
        double totalWeight = 0.0;
        
        // 遍历所有交互记录，根据交互类型计算总评分
        for (UserInteraction interaction : interactions) {
            String actionType = interaction.getActionType();
            Double weight = ACTION_WEIGHTS.getOrDefault(actionType, 0.0);
            totalWeight += weight;
        }
        
        // 归一化评分到0-5范围，5分为最高
        return Math.min(5.0, totalWeight);
    }
} 