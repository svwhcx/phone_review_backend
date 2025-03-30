package com.svwh.phonereview.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户交互记录注解
 * 用于标记需要记录用户交互行为的方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RecordInteraction {
    
    /**
     * 交互类型
     * 可选值: view, like, unlike, favorite, unfavorite, comment
     */
    String actionType();
    
    /**
     * 物品类型
     * 可选值: post, comment, phone
     * 默认为post
     */
    String itemType() default "post";
    
    /**
     * 用户ID参数名
     * 方法中表示用户ID的参数名
     */
    String userIdParam() default "userId";
    
    /**
     * 物品ID参数名
     * 方法中表示物品ID的参数名
     */
    String itemIdParam() default "itemId";
    
    /**
     * 描述信息
     */
    String description() default "";
} 