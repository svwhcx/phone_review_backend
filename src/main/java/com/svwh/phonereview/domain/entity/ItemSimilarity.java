package com.svwh.phonereview.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 物品相似度实体类
 */
@Data
@Accessors(chain = true)
@TableName("item_similarities")
public class ItemSimilarity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 物品1ID（文章1ID）
     */
    @TableId(type = IdType.INPUT)
    private Long post1Id;
    
    /**
     * 物品2ID（文章2ID）
     */
    private Long post2Id;
    
    /**
     * 相似度值，范围0-1
     */
    private Double similarity;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 