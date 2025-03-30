package com.svwh.phonereview.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户交互实体类
 */
@Data
@Accessors(chain = true)
@TableName("user_interactions")
public class UserInteraction implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 交互ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 交互对象ID（文章ID、评论ID等）
     */
    private Long itemId;
    
    /**
     * 交互对象类型：post-文章，comment-评论
     */
    private String itemType;
    
    /**
     * 交互类型：like-点赞，favorite-收藏，view-浏览
     */
    private String actionType;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 