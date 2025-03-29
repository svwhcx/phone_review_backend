package com.svwh.phonereview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.domain.bo.FavoriteBo;
import com.svwh.phonereview.domain.bo.NotificationBo;
import com.svwh.phonereview.domain.entity.Comment;
import com.svwh.phonereview.domain.entity.Favorite;
import com.svwh.phonereview.domain.entity.Posts;
import com.svwh.phonereview.domain.entity.User;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import com.svwh.phonereview.mapper.CommentMapper;
import com.svwh.phonereview.mapper.FavoriteMapper;
import com.svwh.phonereview.mapper.PostsMapper;
import com.svwh.phonereview.mapper.UserMapper;
import com.svwh.phonereview.service.FavoriteService;
import com.svwh.phonereview.service.NotificationService;
import com.svwh.phonereview.utils.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jmx.export.NotificationListenerBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:57
 */
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final NotificationService notificationService;
    private final PostsMapper postsMapper;

    @Override
    public void add(FavoriteBo bo) {
        bo.setUserId(UserInfoThreadLocal.get().getUserId());
        // 判断是否已经存在
        LambdaQueryWrapper<Favorite> fLqw = buildQuery(bo);
        Long count = favoriteMapper.selectCount(fLqw);
        if (count != null && count > 0){
            throw new BusinessException(bo.getIErrorCode());
        }
        bo.setCreateTime(LocalDateTime.now());
        Favorite favorite = MapstructUtils.convert(bo, Favorite.class);
        favoriteMapper.insert(favorite);
        // 点赞的时候判断是哪一个点赞
    }

    @Override
    @Transactional
    public void addCommentLike(FavoriteBo bo) {
        bo.buildLikeComment();
        add(bo);
        // 构建通知
        Long userId = UserInfoThreadLocal.get().getUserId();
        NotificationBo notificationBo = new NotificationBo();
        // 获取评论
        Comment comment = commentMapper.selectById(bo.getTargetId());
        User user = userMapper.selectById(userId);
        notificationBo.setUserId(comment.getUserId());
        notificationBo.setType("comment_like");
        notificationBo.setTitle("您的评论被点赞了");
        notificationBo.setContent("用户 <b>" + user.getNickname() + "</b> 点赞了您的评论"+ "<b>" + comment.getContent().substring(Math.min(10,comment.getContent().length())) + "</b>");
        notificationBo.setLink("/review/"+comment.getPostId());
        notificationService.add(notificationBo);
    }

    @Override
    public void addPostLike(FavoriteBo bo) {
        // 用户点赞一个评测
        bo.buildLikePost();
        add(bo);
        // 构建通知
        Long userId = UserInfoThreadLocal.get().getUserId();
        Posts posts = postsMapper.selectById(bo.getTargetId());
        User user = userMapper.selectById(userId);
        NotificationBo notificationBo = new NotificationBo();
        notificationBo.setTitle("您的评测被点赞了");
        notificationBo.setLink("/review/"+posts.getId());
        notificationBo.setUserId(posts.getUserId());
        notificationBo.setContent("用户 <b>" + user.getNickname()+"</b> 点赞了您的评测 " + posts.getTitle());
        notificationBo.setType("post_like");
        notificationService.add(notificationBo);
    }

    @Override
    public void addPostFavorite(FavoriteBo bo) {
        // 用户收藏一个评测
        bo.buildFavoritePost();
        add(bo);
    }

    @Override
    public void delete(FavoriteBo bo) {
        LambdaQueryWrapper<Favorite> fLqw = buildQuery(bo);
        favoriteMapper.delete(fLqw);
    }


    private LambdaQueryWrapper<Favorite> buildQuery(FavoriteBo bo){
        LambdaQueryWrapper<Favorite> fLqw = Wrappers.lambdaQuery();
        fLqw.eq(Favorite::getUserId, bo.getUserId())
                .eq(Favorite::getTargetId, bo.getTargetId())
                .eq(Favorite::getType, bo.getType());
        return fLqw;
    }
}
