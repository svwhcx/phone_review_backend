package com.svwh.phonereview.domain.dto;

import com.svwh.phonereview.domain.vo.PostsVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 推荐结果数据传输对象
 * 用于封装推荐结果及分页信息
 */
@Data
@Accessors(chain = true)
public class RecommendationDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 当前页码
     */
    private Integer page;
    
    /**
     * 每页大小
     */
    private Integer pageSize;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 总页数
     */
    private Integer totalPages;
    
    /**
     * 推荐的文章列表
     */
    private List<PostsVo> posts;
    
    /**
     * 推荐算法类型
     */
    private String algorithmType;
    
    /**
     * 文章ID (单篇文章的情况)
     */
    private Long id;
    
    /**
     * 标题 (单篇文章的情况)
     */
    private String title;
    
    /**
     * 作者ID (单篇文章的情况)
     */
    private Long authorId;
    
    /**
     * 作者名称 (单篇文章的情况)
     */
    private String authorName;
    
    /**
     * 封面图片 (单篇文章的情况)
     */
    private String coverImage;
    
    /**
     * 摘要 (单篇文章的情况)
     */
    private String summary;
    
    /**
     * 推荐评分 (单篇文章的情况)
     */
    private Double score;
    
    /**
     * 浏览数 (单篇文章的情况)
     */
    private Integer viewCount;
    
    /**
     * 点赞数 (单篇文章的情况)
     */
    private Integer likeCount;
    
    /**
     * 评论数 (单篇文章的情况)
     */
    private Integer commentCount;
    
    /**
     * 创建时间 (单篇文章的情况)
     */
    private LocalDateTime createTime;
    
    /**
     * 推荐ID (用于反馈)
     */
    private Long recommendationId;
    
    /**
     * 相似度原因描述
     */
    private String similarityReason;
    
    /**
     * 构建空的推荐结果
     * @return 空的推荐结果
     */
    public static RecommendationDTO empty(Integer page, Integer pageSize) {
        return new RecommendationDTO()
                .setPage(page)
                .setPageSize(pageSize)
                .setTotal(0L)
                .setTotalPages(0)
                .setPosts(List.of())
                .setAlgorithmType("NONE");
    }
} 