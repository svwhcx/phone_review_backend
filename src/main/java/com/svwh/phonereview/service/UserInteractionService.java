package com.svwh.phonereview.service;



import com.svwh.phonereview.domain.entity.UserInteraction;

import java.util.List;
import java.util.Map;

/**
 * 用户交互服务接口
 */
public interface UserInteractionService {
    
    /**
     * 记录用户交互行为
     *
     * @param userId 用户ID
     * @param itemId 物品ID
     * @param itemType 物品类型
     * @param actionType 行为类型
     * @return 操作是否成功
     */
    boolean recordInteraction(Long userId, Long itemId, String itemType, String actionType);
    
    /**
     * 获取用户的所有交互行为
     *
     * @param userId 用户ID
     * @param itemType 物品类型
     * @return 交互行为列表
     */
    List<UserInteraction> getUserInteractions(Long userId, String itemType);
    
    /**
     * 获取物品的所有交互行为
     *
     * @param itemId 物品ID
     * @param itemType 物品类型
     * @return 交互行为列表
     */
    List<UserInteraction> getItemInteractions(Long itemId, String itemType);
    
    /**
     * 获取用户对所有物品的评分矩阵
     *
     * @param itemType 物品类型
     * @return 用户对物品的评分矩阵，格式为 Map<用户ID, Map<物品ID, 评分>>
     */
    Map<Long, Map<Long, Double>> getUserItemMatrix(String itemType);
    
    /**
     * 查询所有有交互行为的用户ID
     *
     * @param itemType 物品类型
     * @return 用户ID列表
     */
    List<Long> getAllUserIds(String itemType);
    
    /**
     * 查询所有有交互行为的物品ID
     *
     * @param itemType 物品类型
     * @return 物品ID列表
     */
    List<Long> getAllItemIds(String itemType);
    
    /**
     * 计算物品的隐式评分
     * 基于用户的不同行为（浏览、点赞、收藏）计算综合评分
     *
     * @param interactions 交互列表
     * @return 评分值
     */
    double calculateImplicitRating(List<UserInteraction> interactions);
} 