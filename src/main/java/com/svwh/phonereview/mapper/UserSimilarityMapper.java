package com.svwh.phonereview.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.svwh.phonereview.domain.entity.UserSimilarity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户相似度Mapper接口
 */
@Mapper
public interface UserSimilarityMapper extends BaseMapper<UserSimilarity> {

    /**
     * 查询与指定用户最相似的N个用户
     *
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 相似用户列表
     */
    @Select("SELECT * FROM user_similarities WHERE user1_id = #{userId} ORDER BY similarity DESC LIMIT #{limit}")
    List<UserSimilarity> findMostSimilarUsers(@Param("userId") Long userId, @Param("limit") int limit);
    
    /**
     * 批量插入用户相似度
     *
     * @param similarities 用户相似度列表
     * @return 插入数量
     */
    int batchInsert(@Param("similarities") List<UserSimilarity> similarities);
    
    /**
     * 删除所有用户相似度数据（用于重新计算）
     *
     * @return 删除数量
     */
    @Select("TRUNCATE TABLE user_similarities")
    int deleteAll();
} 