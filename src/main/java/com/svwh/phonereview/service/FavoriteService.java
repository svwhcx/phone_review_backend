package com.svwh.phonereview.service;

import com.svwh.phonereview.domain.bo.FavoriteBo;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:56
 */
public interface FavoriteService {

    /**
     * 添加一个点赞/收藏
     * @param bo
     */
    void add(FavoriteBo bo);

    /**
     * 点赞一个评论
     */
    void addCommentLike(FavoriteBo bo);

    /**
     * 点赞一个评测
     * @param bo
     */
    void addPostLike(FavoriteBo bo);

    /**
     * 收藏一个评测
     * @param bo
     */
    void addPostFavorite(FavoriteBo bo);

    /**
     * 取消一个点赞/ 收藏
     * @param bo
     */
    void delete(FavoriteBo bo);
}
