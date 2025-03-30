package com.svwh.phonereview.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 推荐日志实体类
 * 用于记录系统向用户推荐的内容及互动情况
 */
@Data
@Accessors(chain = true)
@TableName("recommendation_logs")
public class RecommendationLog implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 推荐日志ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 推荐项目ID（文章ID）
     */
    private Long postId;
    
    /**
     * 项目类型，如post等
     */
    private String itemType;
    
    /**
     * 推荐算法类型：USER_BASED-基于用户的协同过滤，ITEM_BASED-基于物品的协同过滤，MIXED-混合推荐
     */
    private String algorithm;
    
    /**
     * 推荐得分（值越高表示推荐度越高）
     */
    private Double score;
    
    /**
     * 是否被点击（0-未点击，1-已点击）
     */
    private Integer clicked;
    
    /**
     * 是否被喜欢（0-未喜欢，1-已喜欢）
     */
    private Integer liked;
    
    /**
     * 是否被收藏（0-未收藏，1-已收藏）
     */
    private Integer favorited;
    
    /**
     * 推荐时间
     */
    private LocalDateTime recommendTime;
    
    /**
     * 互动时间
     */
    private LocalDateTime interactionTime;
    
    /**
     * 用户反馈内容
     */
    private String feedback;
    
    /**
     * 反馈时间
     */
    private LocalDateTime feedbackTime;
} 