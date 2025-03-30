package com.svwh.phonereview.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 缓存配置类
 */
@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${recommendation.cache.item-similarities-size:1000}")
    private int itemSimilaritiesCacheSize;

    @Value("${recommendation.cache.user-similarities-size:2000}")
    private int userSimilaritiesCacheSize;

    @Value("${recommendation.cache.recommendation-ttl:3600}")
    private int recommendationTtl;

    /**
     * 缓存名称常量
     */
    public static final String ITEM_SIMILARITIES_CACHE = "itemSimilaritiesCache";
    public static final String USER_SIMILARITIES_CACHE = "userSimilaritiesCache";
    public static final String USER_RECOMMENDATIONS_CACHE = "userRecommendationsCache";
    public static final String ITEM_RECOMMENDATIONS_CACHE = "itemRecommendationsCache";

    /**
     * 主缓存管理器
     */
    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheNames(Arrays.asList(
                ITEM_SIMILARITIES_CACHE,
                USER_SIMILARITIES_CACHE,
                USER_RECOMMENDATIONS_CACHE,
                ITEM_RECOMMENDATIONS_CACHE
        ));
        
        // 默认配置
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .recordStats());
        
        log.info("Initialized default cache manager with TTL: 10 minutes");
        return cacheManager;
    }
    
    /**
     * 物品相似度缓存管理器
     */
    @Bean
    public CacheManager itemSimilaritiesCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(ITEM_SIMILARITIES_CACHE);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(itemSimilaritiesCacheSize)
                .expireAfterWrite(24, TimeUnit.HOURS) // 物品相似度缓存24小时
                .recordStats());
        
        log.info("Initialized item similarities cache with size: {}", itemSimilaritiesCacheSize);
        return cacheManager;
    }
    
    /**
     * 用户相似度缓存管理器
     */
    @Bean
    public CacheManager userSimilaritiesCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(USER_SIMILARITIES_CACHE);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(userSimilaritiesCacheSize)
                .expireAfterWrite(12, TimeUnit.HOURS) // 用户相似度缓存12小时
                .recordStats());
        
        log.info("Initialized user similarities cache with size: {}", userSimilaritiesCacheSize);
        return cacheManager;
    }
    
    /**
     * 推荐结果缓存管理器
     */
    @Bean
    public CacheManager recommendationsCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                USER_RECOMMENDATIONS_CACHE,
                ITEM_RECOMMENDATIONS_CACHE
        );
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(recommendationTtl, TimeUnit.SECONDS)
                .recordStats());
        
        log.info("Initialized recommendations cache with TTL: {} seconds", recommendationTtl);
        return cacheManager;
    }
} 