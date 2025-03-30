package com.svwh.phonereview.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.svwh.phonereview.domain.entity.UserInteraction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户交互Mapper接口
 */
@Mapper
public interface UserInteractionMapper extends BaseMapper<UserInteraction> {

    /**
     * 查询用户对指定类型物品的所有交互
     *
     * @param userId 用户ID
     * @param itemType 物品类型
     * @return 用户交互列表
     */
    @Select("SELECT * FROM user_interactions WHERE user_id = #{userId} AND item_type = #{itemType}")
    List<UserInteraction> findByUserIdAndItemType(@Param("userId") Long userId, @Param("itemType") String itemType);
    
    /**
     * 查询对指定物品的所有交互
     *
     * @param itemId 物品ID
     * @param itemType 物品类型
     * @return 用户交互列表
     */
    @Select("SELECT * FROM user_interactions WHERE item_id = #{itemId} AND item_type = #{itemType}")
    List<UserInteraction> findByItemIdAndItemType(@Param("itemId") Long itemId, @Param("itemType") String itemType);
    
    /**
     * 查询指定用户对指定物品的交互
     *
     * @param userId 用户ID
     * @param itemId 物品ID
     * @param itemType 物品类型
     * @return 用户交互列表
     */
    @Select("SELECT * FROM user_interactions WHERE user_id = #{userId} AND item_id = #{itemId} AND item_type = #{itemType}")
    List<UserInteraction> findByUserIdAndItemIdAndItemType(
            @Param("userId") Long userId, 
            @Param("itemId") Long itemId, 
            @Param("itemType") String itemType);
    
    /**
     * 查询用户的指定类型交互
     *
     * @param userId 用户ID
     * @param actionType 交互类型
     * @return 用户交互列表
     */
    @Select("SELECT * FROM user_interactions WHERE user_id = #{userId} AND action_type = #{actionType}")
    List<UserInteraction> findByUserIdAndActionType(@Param("userId") Long userId, @Param("actionType") String actionType);
    
    /**
     * 查询与用户有交互的所有用户ID
     *
     * @param itemType 物品类型
     * @return 用户ID列表
     */
    @Select("SELECT DISTINCT user_id FROM user_interactions WHERE item_type = #{itemType}")
    List<Long> findAllUserIds(@Param("itemType") String itemType);
    
    /**
     * 查询所有有交互记录的物品ID
     *
     * @param itemType 物品类型
     * @return 物品ID列表
     */
    @Select("SELECT DISTINCT item_id FROM user_interactions WHERE item_type = #{itemType}")
    List<Long> findAllItemIds(@Param("itemType") String itemType);
} 