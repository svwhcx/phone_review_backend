package com.svwh.phonereview.mapper;

import com.svwh.phonereview.domain.entity.Favorite;
import com.svwh.phonereview.domain.vo.FavoriteVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:49
 */
@Mapper
public interface FavoriteMapper extends BaseConvertMapper<Favorite, FavoriteVo>{


    /**
     * 获取评论的点赞量
     * @param userId
     * @return
     */
    @Select("select count(1) from favorite as f join comment as c on f.target_id = c.id where c.user_id = #{userId} and f.type = 3")
    long countCommentLike(Long userId);
}
