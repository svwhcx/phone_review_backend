package com.svwh.phonereview.service;


import com.svwh.phonereview.domain.dto.RecommendationDTO;
import com.svwh.phonereview.domain.vo.PostsVo;
import com.svwh.phonereview.query.PageVo;

import java.util.List;

/**
 * 推荐服务接口
 */
public interface RecommendationService {
    
    /**
     * 获取个性化推荐文章
     *
     * @param userId 用户ID
     * @param limit  返回条数
     * @return 推荐文章列表
     */
    List<RecommendationDTO> getPersonalizedRecommendations(Long userId, Integer limit);
    
    /**
     * 获取热门文章推荐
     *
     * @param limit 返回条数
     * @return 热门文章列表
     */
    List<RecommendationDTO> getPopularRecommendations(Integer limit);
    
    /**
     * 获取相似文章推荐
     *
     * @param postId 文章ID
     * @param limit  返回条数
     * @return 相似文章列表
     */
    List<RecommendationDTO> getSimilarPosts(Long postId, Integer limit);
    
    /**
     * 获取最新文章推荐
     *
     * @param limit 返回条数
     * @return 最新文章列表
     */
    List<RecommendationDTO> getLatestRecommendations(Integer limit);
    
    /**
     * 针对匿名用户的推荐
     *
     * @param limit 返回条数
     * @return 推荐文章列表
     */
    List<RecommendationDTO> getAnonymousRecommendations(Integer limit);
    
    /**
     * 记录用户对推荐的反馈
     *
     * @param userId           用户ID
     * @param recommendationId 推荐记录ID
     * @param feedback         反馈内容
     */
    void recordFeedback(Long userId, Long recommendationId, String feedback);
    
    /**
     * 为用户推荐文章
     *
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 推荐结果
     */
    RecommendationDTO recommendForUser(Long userId, int page, int pageSize);
    
    /**
     * 基于指定文章推荐相似文章
     *
     * @param postId 文章ID
     * @param limit 推荐数量限制
     * @return 推荐结果
     */
    RecommendationDTO recommendSimilarPosts(Long postId, int limit);
    
    /**
     * 使用基于用户的协同过滤算法为用户推荐文章
     *
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 推荐结果
     */
    RecommendationDTO userBasedRecommendation(Long userId, int page, int pageSize);
    
    /**
     * 使用基于物品的协同过滤算法为用户推荐文章
     *
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 推荐结果
     */
    RecommendationDTO itemBasedRecommendation(Long userId, int page, int pageSize);
    
    /**
     * 获取热门文章推荐（用于冷启动）
     *
     * @param page 页码
     * @param pageSize 每页数量
     * @return 推荐结果
     */
    RecommendationDTO getPopularPosts(int page, int pageSize);
    
    /**
     * 记录推荐结果的交互情况
     *
     * @param userId 用户ID
     * @param postId 文章ID
     * @param actionType 交互类型
     * @return 是否记录成功
     */
    boolean recordRecommendationInteraction(Long userId, Long postId, String actionType);
    
    /**
     * 手动触发相似度计算
     *
     * @return 计算结果信息
     */
    String triggerSimilarityCalculation();

    /**
     * 获取个性化推荐文章（带分页）
     *
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页条数
     * @return 带分页的推荐文章列表
     */
    PageVo<PostsVo> getPersonalizedRecommendations(Long userId, int page, int pageSize);

    /**
     * 获取热门文章推荐（带分页）
     *
     * @param page 页码
     * @param pageSize 每页条数
     * @return 带分页的热门文章列表
     */
    PageVo<PostsVo> getPopularRecommendations(int page, int pageSize);

    /**
     * 获取相似文章推荐（带分页）
     *
     * @param userId 用户ID，可为null（匿名用户）
     * @param postId 文章ID
     * @param page 页码
     * @param pageSize 每页条数
     * @return 带分页的相似文章列表
     */
    PageVo<PostsVo> getSimilarPosts(Long userId, Long postId, int page, int pageSize);

    /**
     * 获取最新文章推荐（带分页）
     *
     * @param page 页码
     * @param pageSize 每页条数
     * @return 带分页的最新文章列表
     */
    PageVo<PostsVo> getLatestRecommendations(int page, int pageSize);

    /**
     * 获取匿名用户推荐（带分页）
     *
     * @param page 页码
     * @param pageSize 每页条数
     * @return 带分页的推荐文章列表
     */
    PageVo<PostsVo> getAnonymousRecommendations(int page, int pageSize);
} 