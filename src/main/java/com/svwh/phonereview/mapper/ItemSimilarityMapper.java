package com.svwh.phonereview.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.svwh.phonereview.domain.entity.ItemSimilarity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 物品相似度Mapper接口
 */
@Mapper
public interface ItemSimilarityMapper extends BaseMapper<ItemSimilarity> {

    /**
     * 查询与指定物品最相似的N个物品
     *
     * @param itemId 物品ID
     * @param limit 限制数量
     * @return 相似物品列表
     */
    @Select("SELECT * FROM item_similarities WHERE post1_id = #{itemId} ORDER BY similarity DESC LIMIT #{limit}")
    List<ItemSimilarity> findMostSimilarItems(@Param("itemId") Long itemId, @Param("limit") int limit);
    
    /**
     * 批量插入物品相似度
     *
     * @param similarities 物品相似度列表
     * @return 插入数量
     */
    int batchInsert(@Param("similarities") List<ItemSimilarity> similarities);
    
    /**
     * 删除所有物品相似度数据（用于重新计算）
     *
     * @return 删除数量
     */
    @Select("TRUNCATE TABLE item_similarities")
    int deleteAll();
} 