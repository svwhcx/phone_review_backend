package com.svwh.phonereview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.common.constant.CommentConstant;
import com.svwh.phonereview.common.constant.FavoriteConstant;
import com.svwh.phonereview.domain.bo.CommentBo;
import com.svwh.phonereview.domain.bo.NotificationBo;
import com.svwh.phonereview.domain.entity.*;
import com.svwh.phonereview.domain.vo.CommentVo;
import com.svwh.phonereview.domain.vo.UserVo;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import com.svwh.phonereview.mapper.CommentMapper;
import com.svwh.phonereview.mapper.FavoriteMapper;
import com.svwh.phonereview.mapper.PostsMapper;
import com.svwh.phonereview.mapper.UserMapper;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.CommentService;
import com.svwh.phonereview.service.NotificationService;
import com.svwh.phonereview.utils.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:57
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final PostsMapper postsMapper;

    private final NotificationService notificationService;

    private final FavoriteMapper favoriteMapper;

    @Override
    public PageVo<CommentVo> listComments(Long postId, PageQuery pageQuery) {
        LambdaQueryWrapper<Comment> cLqw = Wrappers.lambdaQuery();
        cLqw.eq(Comment::getIsDelete, false)
                .isNull(Comment::getParentId)
                .eq(Comment::getStatus, CommentConstant.APPROVED)
                .orderByDesc(Comment::getCreateTime);
        cLqw.eq(Comment::getPostId, postId);
        PageVo<CommentVo> commentVoPageVo = commentMapper.selectVoPage(pageQuery.buildMybatisPage(), cLqw);
        // 现在要对分页的数据进行处理了
        // 现在要加载一级评论对应的二级评论
        List<CommentVo> records = commentVoPageVo.getRecords();
        if (records == null || records.isEmpty()) {
            return commentVoPageVo;
        }
        List<Long> roodIds = records.stream().map(CommentVo::getId).toList();
        LambdaQueryWrapper<Comment> cLqw1 = Wrappers.lambdaQuery();
        cLqw1.in(Comment::getRootId, roodIds)
                .eq(Comment::getIsDelete,false)
                .eq(Comment::getStatus, CommentConstant.APPROVED)
                .orderByAsc(Comment::getCreateTime);
        List<CommentVo> twoCommentVos = commentMapper.selectVoList(cLqw1);
        // 现在来组装二级评论
        Map<Long, CommentVo> oneCommentMaps = records.stream().collect(Collectors.toMap(CommentVo::getId, Function.identity()));
        Set<Long> userIds = records.stream().map(CommentVo::getUserId).collect(Collectors.toSet());
        Set<Long> commentIds = records.stream().map(CommentVo::getId).collect(Collectors.toSet());
        twoCommentVos.forEach(item -> {
            CommentVo parentComment = oneCommentMaps.get(item.getParentId());
            if (parentComment != null) {
                if (parentComment.getReplies() == null){
                    parentComment.setReplies(new ArrayList<>());
                }
                parentComment.getReplies().add(item);
            }
            userIds.add(item.getUserId());
            commentIds.add(item.getId());
        });
        // 二级评论组装完毕，现在要来组装用户数据。
        // 1. 先获取所有用户的id对应的用户数据。
        List<UserVo> userVos = userMapper.selectVoBatchIds(userIds);
        Map<Long, UserVo> userVoMap = userVos.stream().collect(Collectors.toMap(UserVo::getId, Function.identity()));
        // 2. 用户数据准备完成，现在来准备点赞数据
        LambdaQueryWrapper<Favorite> fLqw = Wrappers.lambdaQuery();
        fLqw.in(Favorite::getTargetId, commentIds)
                .eq(Favorite::getType, FavoriteConstant.LIKE_COMMENT);
        List<Favorite> favorites = favoriteMapper.selectList(fLqw);
        Map<Long, List<Favorite>> favoriteMap = favorites.stream().collect(Collectors.groupingBy(Favorite::getTargetId));
        Map<String, Boolean> userLikeMap = favorites.stream().collect(Collectors.toMap(f -> f.getUserId() + "." + f.getTargetId(), value -> true));
        // 2.2 分组计算count
        // 2. 开始组装用户数据和点赞数据
        records.forEach(item -> {
            // 判断当前用户是否点赞了这个评论
            item.setIsLiked(userLikeMap.getOrDefault(item.getUserId() + "." + item.getId(), false));
            item.setLikes((long) favoriteMap.getOrDefault(item.getId(), new ArrayList<>()).size());

            item.setUserAvatar(userVoMap.get(item.getUserId()).getAvatar());
            item.setNickname(userVoMap.get(item.getUserId()).getNickname());
            item.setUsername(userVoMap.get(item.getUserId()).getUsername());
            if (CollectionUtils.isNotEmpty(item.getReplies())) {
                item.getReplies().forEach(twoComment -> {
                    item.setIsLiked(userLikeMap.getOrDefault(item.getUserId() + "." + item.getId(), false));
                    item.setLikes((long) favoriteMap.getOrDefault(item.getId(), new ArrayList<>()).size());
                    item.setReplyUserAvatar(userVoMap.get(twoComment.getUserId()).getAvatar());
                    item.setReplyUserNickname(userVoMap.get(twoComment.getUserId()).getNickname());
                    item.setReplyUserUsername(userVoMap.get(twoComment.getUserId()).getUsername());
                    twoComment.setReplyToUserId(userVoMap.get(twoComment.getParentId()).getId());
                    twoComment.setReplyTo(userVoMap.get(twoComment.getParentId()).getUsername());
                    twoComment.setReplyUserAvatar(userVoMap.get(twoComment.getUserId()).getAvatar());
                    twoComment.setUsername(userVoMap.get(twoComment.getUserId()).getUsername());
                });
            }
        });
        return commentVoPageVo;
    }

    @Override
    @Transactional
    public void add(CommentBo bo) {
        // 判断状态，是否能评论
        Long userId = UserInfoThreadLocal.get().getUserId();
        User user = userMapper.selectById(userId);
        if (user.getIsMuted()) {
            throw new BusinessException(DefaultErrorCode.USER_IS_MUTED);
        }
        bo.setCreateTime(LocalDateTime.now());
        bo.setUserId(userId);
        bo.setStatus(CommentConstant.APPENDING);
        // 获取parent评论看看有没有rootId
        Comment comment = commentMapper.selectById(bo.getParentId());
        // 如果一个评论的parentId为空，那么这个评论就是一级评论
        if (comment == null) {
            bo.setParentId(null);
            bo.setRootId(null);
        } else if (comment.getParentId() == null) {
            bo.setRootId(bo.getParentId());
        } else {
            // 如果一个评论的parentId不为空，那么说明这个评论是二级评论，并且用户现在在回复二级评论
            bo.setRootId(comment.getRootId());
        }
        Comment add = MapstructUtils.convert(bo, Comment.class);
        commentMapper.insert(add);
        // 这里还要等待管理员审核，审核通过才发送通知。

    }


    @Override
    public void delete(CommentBo bo) {
        LambdaUpdateWrapper<Comment> cLuw = Wrappers.lambdaUpdate();
        cLuw.eq(Comment::getId, bo.getId())
                .eq(Comment::getUserId, bo.getUserId())
                .eq(Comment::getPostId, bo.getPostId())
                .set(Comment::getIsDelete, true);
        commentMapper.update(cLuw);
    }

    @Override
    public PageVo<CommentVo> queryUserComments(PageQuery pageQuery) {
        Long userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<Comment> cLqw = Wrappers.lambdaQuery();
        cLqw.eq(Comment::getUserId, userId)
                .eq(Comment::getIsDelete, false)
                .orderByDesc(Comment::getCreateTime);
        PageVo<CommentVo> commentVoPageVo = commentMapper.selectVoPage(pageQuery.buildMybatisPage(), cLqw);
        // 配置帖子信息
        if (commentVoPageVo.getRecords() == null || commentVoPageVo.getRecords().isEmpty()) {
            return commentVoPageVo;
        }
        // 配置帖子标题
        List<Long> postIds = commentVoPageVo.getRecords().stream().map(CommentVo::getPostId).toList();
        List<Posts> posts = postsMapper.selectByIds(postIds);
        Map<Long, Posts> postsMap = posts.stream().collect(Collectors.toMap(Posts::getId, Function.identity()));
        commentVoPageVo.getRecords().forEach(item -> item.setPostTitle(postsMap.get(item.getPostId()).getTitle()));
        return commentVoPageVo;
    }

    @Override
    public PageVo<CommentVo> adminList(CommentBo bo, PageQuery pageQuery) {
        // 分页联合查询
        QueryWrapper<Comment> cLqw = new QueryWrapper<>();
        if (StringUtils.isNotBlank(bo.getStatus())) {
            cLqw.eq("c.status", bo.getStatus());
        }
        if (StringUtils.isNotBlank(bo.getKeyword())) {
            cLqw.like("c.content", bo.getKeyword())
                    .or()
                    .like("u.username", bo.getKeyword());
        }
        cLqw.orderByDesc("c.create_time");
        IPage<CommentVo> commentVoIPage = commentMapper.adminQueryList(pageQuery.buildMybatisPage(), cLqw);
        return PageVo.build(commentVoIPage);
    }

    @Override
    @Transactional
    public void updateStatus(CommentBo bo) {
        Comment comment = commentMapper.selectById(bo.getId());
        NotificationBo notificationBo = new NotificationBo();
        notificationBo.setType("system");
        notificationBo.setTitle("评论审核");
        notificationBo.setContent("您的评论 <b>" + comment.getContent() + "</b>");
        notificationBo.setUserId(comment.getUserId());
        if (CommentConstant.APPROVED.equals(bo.getStatus())) {
            notificationBo.setContent(notificationBo.getContent() + "已通过审核");
            notificationBo.setLink("/review/" + comment.getPostId());
        }
        if (CommentConstant.REJECTED.equals(bo.getStatus())) {
            notificationBo.setContent(notificationBo.getContent() + " 未通过审核，原因为：" + bo.getReason());
        }
        notificationService.add(notificationBo);
        // 管理员更新评论的状态
        commentMapper.updateById(MapstructUtils.convert(bo, Comment.class));
        // 同时给发布评测的人发送一条通知
        CommentBo convert = MapstructUtils.convert(comment, CommentBo.class);
        convert.setStatus(bo.getStatus());
        notificationComment(convert);
    }

    /**
     * 给发送评测的人和被回复的人发送一个通知
     *
     * @param bo
     */
    private void notificationComment(CommentBo bo) {
        if (CommentConstant.REJECTED.equals(bo.getStatus())) {
            return;
        }
        // 先获取评论的评测信息
        Posts posts = postsMapper.selectById(bo.getPostId());
        User user = userMapper.selectById(posts.getUserId());
        // 1. 判断是否是回复别人的评论
        if (bo.getParentId() != null) {
            // 查找评论对应的用户id
            Comment comment = commentMapper.selectById(bo.getParentId());
            // 发送通知
            NotificationBo notificationBo = new NotificationBo();
            notificationBo.setType("comment-reply");
            notificationBo.setTitle("评论回复");
            notificationBo.setContent("用户 <b>" + user.getNickname() + "</b> 回复了您的评论" + comment.getContent().substring(Math.min(10, comment.getContent().length())));
            notificationBo.setLink("/review/" + posts.getId());
            notificationBo.setUserId(comment.getUserId());
            notificationService.add(notificationBo);
        }
        // 2.2 给发布评测的人一条记录，您的评测被人评论了
        NotificationBo notificationBo = new NotificationBo();
        notificationBo.setType("comment");
        notificationBo.setTitle("新的评论");
        notificationBo.setContent("用户 <b>" + user.getNickname() + "</b> 评论了您的评测 <b>" + posts.getTitle() + "</b>");
        notificationBo.setLink("/review/" + posts.getId());
        notificationBo.setUserId(posts.getUserId());
        notificationService.add(notificationBo);
    }

}
