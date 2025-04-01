package com.svwh.phonereview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.svwh.phonereview.config.AlgorithmConfig;
import com.svwh.phonereview.domain.bo.PostsBo;
import com.svwh.phonereview.domain.dto.RecommendationDTO;
import com.svwh.phonereview.domain.entity.ItemSimilarity;
import com.svwh.phonereview.domain.entity.RecommendationLog;
import com.svwh.phonereview.domain.entity.UserInteraction;
import com.svwh.phonereview.domain.vo.PostsVo;
import com.svwh.phonereview.mapper.ItemSimilarityMapper;
import com.svwh.phonereview.mapper.RecommendationLogMapper;
import com.svwh.phonereview.mapper.UserInteractionMapper;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.PostsService;
import com.svwh.phonereview.service.RecommendationService;
import com.svwh.phonereview.service.SimilarityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 推荐服务实现类 - 基于协同过滤的混合推荐
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final SimilarityService similarityService;
    private final UserInteractionMapper userInteractionMapper;
    private final RecommendationLogMapper recommendationLogMapper;
    private final AlgorithmConfig algorithmConfig;
    private final ItemSimilarityMapper itemSimilarityMapper;
    private final PostsService postsService;
    
    @Value("${recommendation.result.default-limit:10}")
    private Integer defaultLimit;
    
    @Value("${recommendation.result.min-interaction-count:5}")
    private Integer minInteractionCount;
    
    /**
     * 物品类型常量
     */
    private static final String POST_ITEM_TYPE = "post";
    
    /**
     * 算法类型常量
     */
    private static final String USER_BASED = "USER_BASED";
    private static final String ITEM_BASED = "ITEM_BASED";
    private static final String MIXED = "MIXED";
    private static final String POPULAR = "POPULAR";

    @Override
    @Cacheable(value = "userRecommendationsCache", key = "#userId + '-' + #page + '-' + #pageSize")
    public RecommendationDTO recommendForUser(Long userId, int page, int pageSize) {
        log.info("Generating recommendations for user: {}, page: {}, size: {}", userId, page, pageSize);
        
        // 检查参数有效性
        if (userId == null || userId <= 0 || page <= 0 || pageSize <= 0) {
            log.warn("Invalid parameters for recommendation: userId={}, page={}, pageSize={}", userId, page, pageSize);
            return RecommendationDTO.empty(page, pageSize);
        }
        
        // 获取用户交互次数，判断是否为冷启动用户
        Long interactionCount = getUserInteractionCount(userId);
        log.info("User {} has {} interactions", userId, interactionCount);
        
        // 如果用户交互次数少于冷启动阈值，则返回热门推荐
        if (interactionCount < algorithmConfig.getColdStartThreshold()) {
            log.info("Cold start user detected (interactions: {} < threshold: {}), using popular posts", 
                    interactionCount, algorithmConfig.getColdStartThreshold());
            RecommendationDTO popularResult = getPopularPosts(page, pageSize);
            log.info("Returning {} popular posts for cold start user", 
                    popularResult.getPosts() != null ? popularResult.getPosts().size() : 0);
            return popularResult;
        }
        
        // 使用混合推荐算法
        log.info("Using hybrid recommendation algorithm for user: {}", userId);
        RecommendationDTO userBasedResult = userBasedRecommendation(userId, page, pageSize);
        log.info("User-based recommendation returned {} posts", 
                userBasedResult.getPosts() != null ? userBasedResult.getPosts().size() : 0);
        
        RecommendationDTO itemBasedResult = itemBasedRecommendation(userId, page, pageSize);
        log.info("Item-based recommendation returned {} posts", 
                itemBasedResult.getPosts() != null ? itemBasedResult.getPosts().size() : 0);
        
        // 合并用户和物品两种推荐结果
        RecommendationDTO result = combineRecommendations(userBasedResult, itemBasedResult, userId, page, pageSize);
        log.info("Combined recommendation returned {} posts", 
                result.getPosts() != null ? result.getPosts().size() : 0);
        
        // 如果合并后的结果为空，则返回热门推荐和最新评测的混合结果作为后备
        if (result.getPosts() == null || result.getPosts().isEmpty()) {
            log.warn("Combined recommendation is empty, falling back to mixed popular and latest posts");
            return getMixedPopularAndLatestPosts(page, pageSize);
        }
        
        // 记录推荐日志
//        if (result.getPosts() != null && !result.getPosts().isEmpty()) {
//            recordRecommendations(userId, result.getPosts(), MIXED);
//        }
        
        return result;
    }

    @Override
    @Cacheable(value = "itemRecommendationsCache", key = "#postId + '-' + #limit")
    public RecommendationDTO recommendSimilarPosts(Long postId, int limit) {
        log.info("Finding similar posts for post: {}, limit: {}", postId, limit);
        
        if (postId == null || postId <= 0 || limit <= 0) {
            log.info("Invalid parameters: postId={}, limit={}", postId, limit);
            return RecommendationDTO.empty(1, limit);
        }
        
        // 获取相似文章ID列表
        List<Long> similarPostIds = similarityService.getMostSimilarItems(postId, limit);
        
        // 获取相似文章详情
        List<PostsVo> similarPosts = fetchPostsByIds(similarPostIds);
        
        return new RecommendationDTO()
                .setPage(1)
                .setPageSize(limit)
                .setTotal((long) similarPosts.size())
                .setTotalPages(1)
                .setPosts(similarPosts)
                .setAlgorithmType(ITEM_BASED);
    }

    @Override
    public RecommendationDTO userBasedRecommendation(Long userId, int page, int pageSize) {
        log.info("Generating user-based recommendations for user: {}", userId);
        
        // 获取与当前用户最相似的用户
        List<Long> similarUserIds = similarityService.getMostSimilarUsers(userId, algorithmConfig.getMaxSimilarUsers());
        
        if (similarUserIds.isEmpty()) {
            log.info("No similar users found for user: {}", userId);
            return RecommendationDTO.empty(page, pageSize);
        }
        
        // 获取用户已交互的文章ID集合（用于排除已交互文章）
        Set<Long> interactedItemIds = getUserInteractedItemIds(userId);
        
        // 收集推荐候选文章及其得分
        Map<Long, Double> candidateItems = new HashMap<>();
        
        // 为每个相似用户获取其交互文章
        for (Long similarUserId : similarUserIds) {
            // 获取用户相似度
            double userSimilarity = getUserSimilarity(userId, similarUserId);
            
            // 获取相似用户的交互记录
            List<UserInteraction> interactions = userInteractionMapper.selectList(
                    new LambdaQueryWrapper<UserInteraction>()
                            .eq(UserInteraction::getUserId, similarUserId)
                            .eq(UserInteraction::getItemType, POST_ITEM_TYPE)
            );
            
            // 为每个交互记录计算推荐分数
            for (UserInteraction interaction : interactions) {
                Long itemId = interaction.getItemId();
                
                // 跳过用户已交互的文章
                if (interactedItemIds.contains(itemId)) {
                    continue;
                }
                
                // 基于用户相似度和交互类型计算得分
                double score = userSimilarity * getInteractionScore(interaction.getActionType());
                
                // 更新候选文章得分
                candidateItems.merge(itemId, score, Double::sum);
            }
        }
        
        // 根据得分创建分页推荐结果
        return createPagedRecommendations(candidateItems, page, pageSize, USER_BASED);
    }

    @Override
    public RecommendationDTO itemBasedRecommendation(Long userId, int page, int pageSize) {
        log.info("Generating item-based recommendations for user: {}", userId);
        
        // 获取用户交互过的所有文章
        List<UserInteraction> userInteractions = userInteractionMapper.selectList(
                new LambdaQueryWrapper<UserInteraction>()
                        .eq(UserInteraction::getUserId, userId)
                        .eq(UserInteraction::getItemType, POST_ITEM_TYPE)
        );
        
        if (userInteractions.isEmpty()) {
            log.info("No interactions found for user: {}", userId);
            return RecommendationDTO.empty(page, pageSize);
        }
        
        // 获取用户已交互的文章ID集合（用于排除已交互文章）
        Set<Long> interactedItemIds = userInteractions.stream()
                .map(UserInteraction::getItemId)
                .collect(Collectors.toSet());
        
        // 收集推荐候选文章及其得分
        Map<Long, Double> candidateItems = new HashMap<>();
        
        // 基于用户交互过的每篇文章，找出相似文章
        for (UserInteraction interaction : userInteractions) {
            Long itemId = interaction.getItemId();
            double interactionScore = getInteractionScore(interaction.getActionType());
            
            // 获取与当前文章相似的文章列表
            List<ItemSimilarity> similarItems = itemSimilarityMapper.findMostSimilarItems(
                    itemId, 
                    algorithmConfig.getMaxSimilarItems()
            );
            
            // 计算相似文章的推荐得分
            for (ItemSimilarity similarItem : similarItems) {
                Long similarItemId = similarItem.getPost2Id();
                
                // 跳过用户已交互的文章
                if (interactedItemIds.contains(similarItemId)) {
                    continue;
                }
                
                // 基于物品相似度和用户交互类型计算得分
                double score = similarItem.getSimilarity() * interactionScore;
                
                // 更新候选文章得分
                candidateItems.merge(similarItemId, score, Double::sum);
            }
        }
        
        // 根据得分创建分页推荐结果
        return createPagedRecommendations(candidateItems, page, pageSize, ITEM_BASED);
    }

    @Override
    public RecommendationDTO getPopularPosts(int page, int pageSize) {
        log.info("Getting popular posts, page: {}, size: {}", page, pageSize);
        
        // 获取热门文章列表
        List<PostsVo> popularPosts = getPopularPostsFromService(page, pageSize);
        
        // 获取实际总数，而不是估算值
        // 由于PostsService没有直接提供count方法，我们通过查询一次大页码的数据来获取总数
        PostsBo countBo = new PostsBo();
        countBo.setSortBy("view_count DESC, like_count DESC");
        PageQuery countQuery = new PageQuery();
        countQuery.setPageNum(1);
        countQuery.setPageSize(1); // 只查询一条记录，减少开销
        PageVo<PostsVo> countResult = postsService.queryPage(countBo, countQuery);
        long actualTotal = countResult.getTotal(); // PageVo中的total字段包含了总记录数
        log.info("Actual total count of popular posts: {}", actualTotal);
        
        // 构建推荐结果
        RecommendationDTO result = new RecommendationDTO();
        result.setPage(page);
        result.setPageSize(pageSize);
        result.setTotal(actualTotal); // 使用实际总数
        result.setTotalPages((int) Math.ceil((double) result.getTotal() / pageSize));
        result.setPosts(popularPosts);
        result.setAlgorithmType(POPULAR);
        
        return result;
    }

    @Override
    public boolean recordRecommendationInteraction(Long userId, Long postId, String actionType) {
        try {
            if (userId == null || userId <= 0 || postId == null || postId <= 0) {
                log.warn("Invalid parameters for recording interaction: userId={}, postId={}", userId, postId);
                return false;
            }
            
            // 查找最近的推荐记录
            RecommendationLog log = recommendationLogMapper.findByUserIdAndItemId(userId, postId);
            
            if (log != null) {
                // 更新推荐记录的交互情况
                switch (actionType.toLowerCase()) {
                    case "click", "view" -> {
                        log.setClicked(1);
                        log.setInteractionTime(LocalDateTime.now());
                    }
                    case "like" -> {
                        log.setLiked(1);
                        log.setInteractionTime(LocalDateTime.now());
                    }
                    case "favorite" -> {
                        log.setFavorited(1);
                        log.setInteractionTime(LocalDateTime.now());
                    }
                }
                
                // 更新记录
                recommendationLogMapper.updateById(log);
            }
            
            // 记录用户交互
            UserInteraction interaction = new UserInteraction();
            interaction.setUserId(userId);
            interaction.setItemId(postId);
            interaction.setItemType(POST_ITEM_TYPE);
            interaction.setActionType(actionType);
            interaction.setCreateTime(LocalDateTime.now());
            
            userInteractionMapper.insert(interaction);
            
            return true;
        } catch (Exception e) {
            log.error("Failed to record recommendation interaction", e);
            return false;
        }
    }

    @Override
    public String triggerSimilarityCalculation() {
        try {
            log.info("Manually triggering similarity calculation");
            
            // 计算物品相似度
            int itemCount = similarityService.calculateAllItemSimilarities(POST_ITEM_TYPE);
            
            // 计算用户相似度
            int userCount = similarityService.calculateAllUserSimilarities(POST_ITEM_TYPE);
            
            return String.format("计算完成：更新了 %d 对物品相似度和 %d 对用户相似度", itemCount, userCount);
        } catch (Exception e) {
            log.error("Failed to calculate similarities", e);
            return "计算失败：" + e.getMessage();
        }
    }
    
    @Async
    @Scheduled(cron = "0 0 2 * * ?")
    public void scheduledSimilarityCalculation() {
        log.info("Starting scheduled similarity calculation");
        try {
        triggerSimilarityCalculation();
        } catch (Exception e) {
            log.error("Scheduled similarity calculation failed", e);
        }
    }
    
    private RecommendationDTO combineRecommendations(
            RecommendationDTO userBasedResult,
            RecommendationDTO itemBasedResult,
            Long userId,
            int page,
            int pageSize) {
        
        // 如果其中一个结果为空，直接返回另一个
        if (userBasedResult.getPosts() == null || userBasedResult.getPosts().isEmpty()) {
            return itemBasedResult;
        }
        
        if (itemBasedResult.getPosts() == null || itemBasedResult.getPosts().isEmpty()) {
            return userBasedResult;
        }
        
        // 合并两种推荐结果，应用权重
        double userWeight = algorithmConfig.getUserWeight();
        double itemWeight = algorithmConfig.getItemWeight();
        
        // 分别获取两种推荐的结果，转为 Map<文章ID, 文章信息>
        Map<Long, PostsVo> userBasedPosts = userBasedResult.getPosts().stream()
                .collect(Collectors.toMap(PostsVo::getId, post -> post, (p1, p2) -> p1));
        
        Map<Long, PostsVo> itemBasedPosts = itemBasedResult.getPosts().stream()
                .collect(Collectors.toMap(PostsVo::getId, post -> post, (p1, p2) -> p1));
        
        // 合并所有候选文章ID
        Set<Long> allPostIds = new HashSet<>(userBasedPosts.keySet());
        allPostIds.addAll(itemBasedPosts.keySet());
        
        // 加权计算最终得分
        Map<Long, Double> combinedScores = new HashMap<>();
        
        for (Long postId : allPostIds) {
            double userScore = 0.0;
            double itemScore = 0.0;
            
            if (userBasedPosts.containsKey(postId)) {
                // 注意：这里假设 PostsVo 有 recommendScore 的获取方法
                // 实际中可能需要使用其他属性或自行添加
                userScore = Optional.ofNullable(userBasedPosts.get(postId).getRating())
                        .map(BigDecimal::doubleValue)
                        .orElse(0.0);
            }
            
            if (itemBasedPosts.containsKey(postId)) {
                itemScore = Optional.ofNullable(itemBasedPosts.get(postId).getRating())
                        .map(BigDecimal::doubleValue)
                        .orElse(0.0);
            }
            
            // 计算加权分数
            double finalScore = userScore * userWeight + itemScore * itemWeight;
            combinedScores.put(postId, finalScore);
        }
        
        // 对所有候选文章按加权得分排序
        List<Map.Entry<Long, Double>> sortedEntries = combinedScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .collect(Collectors.toList());
        
        // 分页获取当前页的推荐结果
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, sortedEntries.size());
        
        if (fromIndex >= sortedEntries.size()) {
            log.warn("Page index out of range for {} algorithm (page: {}, total entries: {}), falling back to mixed popular and latest posts", 
                    MIXED, page, sortedEntries.size());
            return getMixedPopularAndLatestPosts(page, pageSize);
        }
        
        List<Map.Entry<Long, Double>> pagedEntries = sortedEntries.subList(fromIndex, toIndex);
        
        // 构建结果
        List<PostsVo> combinedPosts = new ArrayList<>();
        for (Map.Entry<Long, Double> entry : pagedEntries) {
            Long postId = entry.getKey();
            
            // 获取文章信息，优先使用基于物品的推荐结果
            PostsVo post = itemBasedPosts.getOrDefault(postId, userBasedPosts.get(postId));
            
            if (post != null) {
                // 设置推荐得分 (这里需要PostsVo有设置方法)
                // post.setRecommendScore(entry.getValue());
                combinedPosts.add(post);
            }
        }
        
        // 创建最终的推荐结果
        return new RecommendationDTO()
                .setPage(page)
                .setPageSize(pageSize)
                .setTotal((long) sortedEntries.size())
                .setTotalPages((int) Math.ceil((double) sortedEntries.size() / pageSize))
                .setPosts(combinedPosts)
                .setAlgorithmType(MIXED);
    }

    private RecommendationDTO createPagedRecommendations(
            Map<Long, Double> recommendedItems, 
            int page, 
            int pageSize,
            String algorithmType) {
        
        // 如果没有推荐项，返回热门推荐和最新评测的混合结果作为后备
        if (recommendedItems.isEmpty()) {
            log.warn("No recommended items found for {} algorithm, falling back to mixed popular and latest posts", algorithmType);
            return getMixedPopularAndLatestPosts(page, pageSize);
        }
        
        // 按得分排序并分页
        List<Map.Entry<Long, Double>> sortedEntries = recommendedItems.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .collect(Collectors.toList());
        
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, sortedEntries.size());
        
        if (fromIndex >= sortedEntries.size()) {
            log.warn("Page index out of range for {} algorithm (page: {}, total entries: {}), falling back to mixed popular and latest posts", 
                    algorithmType, page, sortedEntries.size());
            return getMixedPopularAndLatestPosts(page, pageSize);
        }
        
        List<Map.Entry<Long, Double>> pagedEntries = sortedEntries.subList(fromIndex, toIndex);
        
        // 获取文章详情
        List<Long> postIds = pagedEntries.stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        List<PostsVo> posts = fetchPostsByIds(postIds);
        
        // 如果获取的文章列表为空，返回热门推荐和最新评测的混合结果作为后备
        if (posts == null || posts.isEmpty()) {
            log.warn("Failed to fetch any posts for {} algorithm, falling back to mixed popular and latest posts", algorithmType);
            return getMixedPopularAndLatestPosts(page, pageSize);
        }
        
        // 构建推荐结果
        return new RecommendationDTO()
                .setPage(page)
                .setPageSize(pageSize)
                .setTotal((long) sortedEntries.size())
                .setTotalPages((int) Math.ceil((double) sortedEntries.size() / pageSize))
                .setPosts(posts)
                .setAlgorithmType(algorithmType);
    }
    
    private void recordRecommendations(Long userId, List<PostsVo> posts, String algorithmType) {
        if (posts == null || posts.isEmpty()) {
            return;
        }
        
        for (int i = 0; i < posts.size(); i++) {
            PostsVo post = posts.get(i);
            
            // 创建推荐日志
            RecommendationLog log = new RecommendationLog();
            log.setUserId(userId);
            log.setPostId(post.getId());
            log.setAlgorithm(algorithmType);
            log.setScore(Optional.ofNullable(post.getRating())
                    .map(BigDecimal::doubleValue)
                    .orElse(0.0));
            log.setClicked(0);
            log.setLiked(0);
            log.setFavorited(0);
            log.setRecommendTime(LocalDateTime.now());
            
            // 保存日志
            recommendationLogMapper.insert(log);
            
            // 如果需要，可以设置推荐ID到结果中
            // post.setRecommendationId(log.getId());
        }
    }

    private Long getUserInteractionCount(Long userId) {
        return userInteractionMapper.selectCount(
                new LambdaQueryWrapper<UserInteraction>()
                        .eq(UserInteraction::getUserId, userId)
                        .eq(UserInteraction::getItemType, POST_ITEM_TYPE)
        );
    }
    
    private Set<Long> getUserInteractedItemIds(Long userId) {
        List<UserInteraction> interactions = userInteractionMapper.selectList(
                new LambdaQueryWrapper<UserInteraction>()
                        .eq(UserInteraction::getUserId, userId)
                        .eq(UserInteraction::getItemType, POST_ITEM_TYPE)
                        .select(UserInteraction::getItemId)
        );
        
        return interactions.stream()
                .map(UserInteraction::getItemId)
                .collect(Collectors.toSet());
    }
    
    private List<PostsVo> fetchPostsByIds(List<Long> postIds) {
        if (postIds == null || postIds.isEmpty()) {
            log.warn("Empty post IDs list provided to fetchPostsByIds");
            return Collections.emptyList();
        }
        
        // 调用辅助方法获取文章
        List<PostsVo> result = getPostsByIds(postIds);
        
        // 如果没有成功获取任何文章，记录警告
        if (result.isEmpty()) {
            log.warn("Failed to fetch any valid posts from IDs: {}", postIds);
        }
        
        return result;
    }

    private double getUserSimilarity(Long user1Id, Long user2Id) {
        // 调用辅助方法获取用户相似度
        return getUserSimilarityValue(user1Id, user2Id);
    }

    private double getInteractionScore(String actionType) {
        // 不同交互类型的权重
        switch (actionType.toLowerCase()) {
            case "view":
                return 1.0;
            case "like":
                return 3.0;
            case "favorite":
                return 5.0;
            case "comment":
                return 4.0;
            default:
                return 0.5;
        }
    }

    // 新增的接口实现方法，转为调用上面已有的逻辑

    @Override
    public PageVo<PostsVo> getPersonalizedRecommendations(Long userId, int page, int pageSize) {
        log.info("Getting personalized recommendations with pagination for user: {}, page: {}, size: {}", userId, page, pageSize);
        RecommendationDTO recommendationDTO = recommendForUser(userId, page, pageSize);
        
        // 确保返回的分页信息正确
        if (recommendationDTO.getPosts() == null || recommendationDTO.getPosts().isEmpty()) {
            log.warn("No personalized recommendations found for user: {}", userId);
            return new PageVo<>((long)page, 0L, Collections.emptyList());
        }
        
        return new PageVo<>((long)page, recommendationDTO.getTotal(), recommendationDTO.getPosts());
    }

    @Override
    public PageVo<PostsVo> getPopularRecommendations(int page, int pageSize) {
        log.info("Getting popular recommendations with pagination, page: {}, size: {}", page, pageSize);
        RecommendationDTO recommendationDTO = getPopularPosts(page, pageSize);
        
        // 确保返回的分页信息正确
        if (recommendationDTO.getPosts() == null || recommendationDTO.getPosts().isEmpty()) {
            log.warn("No popular recommendations found");
            return new PageVo<>((long)page, 0L, Collections.emptyList());
        }
        
        // 使用实际的总数，而不是估算值
        long total = recommendationDTO.getTotal();
        log.info("Returning {} popular recommendations, total: {}", recommendationDTO.getPosts().size(), total);
        return new PageVo<>((long)page, total, recommendationDTO.getPosts());
    }

    @Override
    public PageVo<PostsVo> getSimilarPosts(Long userId, Long postId, int page, int pageSize) {
        log.info("Getting similar posts with pagination for postId: {}, userId: {}, page: {}, size: {}", postId, userId, page, pageSize);
        RecommendationDTO recommendationDTO = recommendSimilarPosts(postId, pageSize);
        
        // 记录交互
        if (userId != null && userId > 0) {
            recordRecommendationInteraction(userId, postId, "view_similar");
        }
        
        // 确保返回的分页信息正确
        if (recommendationDTO.getPosts() == null || recommendationDTO.getPosts().isEmpty()) {
            log.warn("No similar posts found for postId: {}", postId);
            return new PageVo<>((long)page, 0L, Collections.emptyList());
        }
        
        log.info("Returning {} similar posts for postId: {}", recommendationDTO.getPosts().size(), postId);
        return new PageVo<>((long)page, recommendationDTO.getTotal(), recommendationDTO.getPosts());
    }

    @Override
    public PageVo<PostsVo> getLatestRecommendations(int page, int pageSize) {
        log.info("Getting latest recommendations with pagination, page: {}, size: {}", page, pageSize);
        // 获取最新文章
        List<PostsVo> latestPosts = getLatestPostsFromService(page, pageSize);
        
        // 确保返回的分页信息正确
        if (latestPosts == null || latestPosts.isEmpty()) {
            log.warn("No latest recommendations found");
            return new PageVo<>((long)page, 0L, Collections.emptyList());
        }
        
        // 获取实际总数，而不是估算值
        PageQuery countQuery = new PageQuery();
        countQuery.setPageNum(1);
        countQuery.setPageSize(1); // 只查询一条记录，减少开销
        PostsBo countBo = new PostsBo();
        countBo.setSortBy("create_time DESC");
        PageVo<PostsVo> countResult = postsService.queryPage(countBo, countQuery);
        long actualTotal = countResult.getTotal(); // PageVo中的total字段包含了总记录数
        
        log.info("Returning {} latest recommendations, total: {}", latestPosts.size(), actualTotal);
        return new PageVo<>((long)page, actualTotal, latestPosts);
    }

    @Override
    public PageVo<PostsVo> getAnonymousRecommendations(int page, int pageSize) {
        log.info("Getting anonymous recommendations with pagination, page: {}, size: {}", page, pageSize);
        // 匿名用户就给热门推荐
        PageVo<PostsVo> result = getPopularRecommendations(page, pageSize);
        
        // 确保返回的分页信息正确
        if (result.getRecords() == null || result.getRecords().isEmpty()) {
            log.warn("No anonymous recommendations found");
            return new PageVo<>((long)page, 0L, Collections.emptyList());
        }
        
        log.info("Returning {} anonymous recommendations", result.getRecords().size());
        return result;
    }

    @Override
    public List<RecommendationDTO> getPersonalizedRecommendations(Long userId, Integer limit) {
        RecommendationDTO dto = recommendForUser(userId, 1, limit);
        return Collections.singletonList(dto);
    }

    @Override
    public List<RecommendationDTO> getPopularRecommendations(Integer limit) {
        RecommendationDTO dto = getPopularPosts(1, limit);
        return Collections.singletonList(dto);
    }

    @Override
    public List<RecommendationDTO> getSimilarPosts(Long postId, Integer limit) {
        RecommendationDTO dto = recommendSimilarPosts(postId, limit);
        return Collections.singletonList(dto);
    }

    @Override
    public List<RecommendationDTO> getLatestRecommendations(Integer limit) {
        PageVo<PostsVo> pageVo = getLatestRecommendations(1, limit);
        RecommendationDTO dto = new RecommendationDTO()
                .setPage(1)
                .setPageSize(limit)
                .setTotal(pageVo.getTotal())
                .setTotalPages(1)
                .setPosts(pageVo.getRecords())
                .setAlgorithmType("LATEST");
        return Collections.singletonList(dto);
    }

    @Override
    public List<RecommendationDTO> getAnonymousRecommendations(Integer limit) {
        PageVo<PostsVo> pageVo = getAnonymousRecommendations(1, limit);
        RecommendationDTO dto = new RecommendationDTO()
                .setPage(1)
                .setPageSize(limit)
                .setTotal(pageVo.getTotal())
                .setTotalPages(1)
                .setPosts(pageVo.getRecords())
                .setAlgorithmType("POPULAR");
        return Collections.singletonList(dto);
    }

    @Override
    public void recordFeedback(Long userId, Long recommendationId, String feedback) {
        try {
            // 查找推荐记录
            RecommendationLog log = recommendationLogMapper.selectById(recommendationId);
            if (log != null && log.getUserId().equals(userId)) {
                // 设置反馈
            log.setFeedback(feedback);
            log.setFeedbackTime(LocalDateTime.now());
                
                // 更新记录
            recommendationLogMapper.updateById(log);
            }
        } catch (Exception e) {
            log.error("Failed to record feedback", e);
        }
    }

    /**
     * 从PostsService获取热门文章列表
     */
    private List<PostsVo> getPopularPostsFromService(int page, int pageSize) {
        // 这里直接调用PostsService的queryPage方法，按照热度排序
        PostsBo bo = new PostsBo();
        bo.setSortBy("view_count DESC, like_count DESC"); // 现在PostsBo有该方法了
        
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNum(page);
        pageQuery.setPageSize(pageSize);
        PageVo<PostsVo> pageVo = postsService.queryPage(bo, pageQuery);
        
        return pageVo.getRecords();
    }

    /**
     * 根据ID列表获取文章列表
     */
    private List<PostsVo> getPostsByIds(List<Long> postIds) {
        if (postIds == null || postIds.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<PostsVo> result = new ArrayList<>();
        // 逐个查询文章
        for (Long postId : postIds) {
            try {
                PostsVo post = postsService.get(postId);
                if (post != null) {
                    result.add(post);
                }
            } catch (Exception e) {
                log.error("获取文章失败: postId={}", postId, e);
            }
        }
        return result;
    }

    /**
     * 从PostsService获取最新文章列表
     */
    private List<PostsVo> getLatestPostsFromService(int page, int pageSize) {
        // 这里直接调用PostsService的queryPage方法，按照创建时间排序
        PostsBo bo = new PostsBo();
        bo.setSortBy("create_time DESC"); // 现在PostsBo有该方法了
        
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNum(page);
        pageQuery.setPageSize(pageSize);
        PageVo<PostsVo> pageVo = postsService.queryPage(bo, pageQuery);
        
        return pageVo.getRecords();
    }

    /**
     * 获取用户相似度
     */
    private double getUserSimilarityValue(Long user1Id, Long user2Id) {
        // 由于SimilarityService中可能没有实现该方法，这里使用固定值模拟
        // 实际项目中应根据实际情况实现
        return 0.8;
    }
    
    /**
     * 获取热门推荐和最新评测的混合结果
     * 
     * @param page 页码
     * @param pageSize 每页数量
     * @return 混合推荐结果
     */
    private RecommendationDTO getMixedPopularAndLatestPosts(int page, int pageSize) {
        log.info("Getting mixed popular and latest posts, page: {}, size: {}", page, pageSize);
        
        // 计算每种类型应获取的数量（各占一半）
        int halfSize = pageSize / 2;
        int popularSize = halfSize;
        int latestSize = pageSize - popularSize; // 处理奇数情况
        
        // 获取热门文章
        List<PostsVo> popularPosts = getPopularPostsFromService(page, popularSize);
        log.info("Retrieved {} popular posts", popularPosts.size());
        
        // 获取最新文章
        List<PostsVo> latestPosts = getLatestPostsFromService(page, latestSize);
        log.info("Retrieved {} latest posts", latestPosts.size());
        
        // 合并两种结果，并去重
        Set<Long> addedPostIds = new HashSet<>();
        List<PostsVo> mixedPosts = new ArrayList<>();
        
        // 交替添加热门和最新文章
        int popularIndex = 0;
        int latestIndex = 0;
        
        while (mixedPosts.size() < pageSize && 
               (popularIndex < popularPosts.size() || latestIndex < latestPosts.size())) {
            
            // 添加热门文章
            if (popularIndex < popularPosts.size()) {
                PostsVo popularPost = popularPosts.get(popularIndex++);
                if (!addedPostIds.contains(popularPost.getId())) {
                    mixedPosts.add(popularPost);
                    addedPostIds.add(popularPost.getId());
                }
            }
            
            // 如果已经达到页面大小，退出循环
            if (mixedPosts.size() >= pageSize) {
                break;
            }
            
            // 添加最新文章
            if (latestIndex < latestPosts.size()) {
                PostsVo latestPost = latestPosts.get(latestIndex++);
                if (!addedPostIds.contains(latestPost.getId())) {
                    mixedPosts.add(latestPost);
                    addedPostIds.add(latestPost.getId());
                }
            }
        }
        
        // 如果混合结果不足，尝试添加更多热门或最新文章
        if (mixedPosts.size() < pageSize) {
            // 继续添加热门文章
            while (popularIndex < popularPosts.size() && mixedPosts.size() < pageSize) {
                PostsVo popularPost = popularPosts.get(popularIndex++);
                if (!addedPostIds.contains(popularPost.getId())) {
                    mixedPosts.add(popularPost);
                    addedPostIds.add(popularPost.getId());
                }
            }
            
            // 继续添加最新文章
            while (latestIndex < latestPosts.size() && mixedPosts.size() < pageSize) {
                PostsVo latestPost = latestPosts.get(latestIndex++);
                if (!addedPostIds.contains(latestPost.getId())) {
                    mixedPosts.add(latestPost);
                    addedPostIds.add(latestPost.getId());
                }
            }
        }
        
        // 获取实际总数（热门+最新的总数，去重后）
        PostsBo popularCountBo = new PostsBo();
        popularCountBo.setSortBy("view_count DESC, like_count DESC");
        PageQuery popularCountQuery = new PageQuery();
        popularCountQuery.setPageNum(1);
        popularCountQuery.setPageSize(1);
        PageVo<PostsVo> popularCountResult = postsService.queryPage(popularCountBo, popularCountQuery);
        
        PostsBo latestCountBo = new PostsBo();
        latestCountBo.setSortBy("create_time DESC");
        PageQuery latestCountQuery = new PageQuery();
        latestCountQuery.setPageNum(1);
        latestCountQuery.setPageSize(1);
        PageVo<PostsVo> latestCountResult = postsService.queryPage(latestCountBo, latestCountQuery);
        
        // 估算总数（实际上可能有重复，但这里简化处理）
        long estimatedTotal = popularCountResult.getTotal() + latestCountResult.getTotal();
        log.info("Estimated total count of mixed posts: {}", estimatedTotal);
        
        // 构建推荐结果
        RecommendationDTO result = new RecommendationDTO();
        result.setPage(page);
        result.setPageSize(pageSize);
        result.setTotal(estimatedTotal); // 使用估算总数
        result.setTotalPages((int) Math.ceil((double) result.getTotal() / pageSize));
        result.setPosts(mixedPosts);
        result.setAlgorithmType("MIXED_POPULAR_LATEST");
        
        return result;
    }
}