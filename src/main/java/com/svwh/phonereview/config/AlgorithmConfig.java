package com.svwh.phonereview.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 协同过滤算法配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "recommendation.algorithm")
public class AlgorithmConfig {
    
    /**
     * 基于用户的协同过滤权重
     */
    private double userWeight = 0.6;
    
    /**
     * 基于物品的协同过滤权重
     */
    private double itemWeight = 0.4;
    
    /**
     * 相似度阈值，低于该值的相似度将被忽略
     */
    private double similarityThreshold = 0.2;
    
    /**
     * 每个物品最多保存的相似物品数量
     */
    private int maxSimilarItems = 50;
    
    /**
     * 每个用户最多保存的相似用户数量
     */
    private int maxSimilarUsers = 50;
    
    /**
     * 推荐结果数量
     */
    private int recommendationCount = 20;
    
    /**
     * 冷启动阈值，用户交互次数低于该值视为冷启动用户
     */
    private int coldStartThreshold = 5;
} 