package com.svwh.phonereview.scheduler;


import com.svwh.phonereview.service.SimilarityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 相似度计算定时任务
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SimilarityCalculationScheduler {
    
    private final SimilarityService similarityService;
    
    private static final String POST_ITEM_TYPE = "post";
    
    /**
     * 定时计算物品相似度（每天凌晨2点执行）
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void calculateItemSimilarities() {
        log.info("Starting scheduled item similarity calculation");
        try {
            int count = similarityService.calculateAllItemSimilarities(POST_ITEM_TYPE);
            log.info("Successfully calculated similarities for {} item pairs", count);
        } catch (Exception e) {
            log.error("Failed to calculate item similarities", e);
        }
    }
    
    /**
     * 定时计算用户相似度（每天凌晨3点执行）
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void calculateUserSimilarities() {
        log.info("Starting scheduled user similarity calculation");
        try {
            int count = similarityService.calculateAllUserSimilarities(POST_ITEM_TYPE);
            log.info("Successfully calculated similarities for {} user pairs", count);
        } catch (Exception e) {
            log.error("Failed to calculate user similarities", e);
        }
    }
} 