package com.svwh.phonereview.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.svwh.phonereview.domain.entity.RecommendationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 推荐日志Mapper接口
 */
@Mapper
public interface RecommendationLogMapper extends BaseMapper<RecommendationLog> {

    /**
     * 查询用户的推荐历史记录
     *
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 推荐历史记录列表
     */
    @Select("SELECT * FROM recommendation_logs WHERE user_id = #{userId} ORDER BY recommend_time DESC LIMIT #{limit}")
    List<RecommendationLog> findUserRecommendationHistory(@Param("userId") Long userId, @Param("limit") int limit);
    
    /**
     * 获取用户最近已查看但未互动的推荐记录
     *
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 推荐记录列表
     */
    @Select("SELECT * FROM recommendation_logs WHERE user_id = #{userId} AND clicked = 1 AND (liked = 0 AND favorited = 0) ORDER BY recommend_time DESC LIMIT #{limit}")
    List<RecommendationLog> findViewedButNotInteractedRecommendations(@Param("userId") Long userId, @Param("limit") int limit);
    
    /**
     * 更新推荐记录的点击状态
     *
     * @param id 推荐日志ID
     * @return 更新记录数
     */
    @Update("UPDATE recommendation_logs SET clicked = 1, interaction_time = NOW() WHERE id = #{id}")
    int updateClickStatus(@Param("id") Long id);
    
    /**
     * 更新推荐记录的喜欢状态
     *
     * @param id 推荐日志ID
     * @param liked 喜欢状态
     * @return 更新记录数
     */
    @Update("UPDATE recommendation_logs SET liked = #{liked}, interaction_time = NOW() WHERE id = #{id}")
    int updateLikeStatus(@Param("id") Long id, @Param("liked") int liked);
    
    /**
     * 更新推荐记录的收藏状态
     *
     * @param id 推荐日志ID
     * @param favorited 收藏状态
     * @return 更新记录数
     */
    @Update("UPDATE recommendation_logs SET favorited = #{favorited}, interaction_time = NOW() WHERE id = #{id}")
    int updateFavoriteStatus(@Param("id") Long id, @Param("favorited") int favorited);
    
    /**
     * 查询指定用户和物品的推荐记录
     *
     * @param userId 用户ID
     * @param itemId 物品ID
     * @return 推荐记录
     */
    @Select("SELECT * FROM recommendation_logs WHERE user_id = #{userId} AND item_id = #{itemId} ORDER BY recommend_time DESC LIMIT 1")
    RecommendationLog findByUserIdAndItemId(@Param("userId") Long userId, @Param("itemId") Long itemId);
} 