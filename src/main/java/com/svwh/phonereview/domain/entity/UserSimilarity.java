package com.svwh.phonereview.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户相似度实体类
 */
@Data
@Accessors(chain = true)
@TableName("user_similarities")
public class UserSimilarity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户1ID
     */
    @TableId(type = IdType.INPUT)
    private Long user1Id;
    
    /**
     * 用户2ID
     */
    private Long user2Id;
    
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