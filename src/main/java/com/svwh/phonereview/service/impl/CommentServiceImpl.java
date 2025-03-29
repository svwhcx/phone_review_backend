package com.svwh.phonereview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.common.constant.CommentConstant;
import com.svwh.phonereview.domain.bo.CommentBo;
import com.svwh.phonereview.domain.bo.NotificationBo;
import com.svwh.phonereview.domain.entity.Comment;
import com.svwh.phonereview.domain.entity.Notification;
import com.svwh.phonereview.domain.entity.Posts;
import com.svwh.phonereview.domain.entity.User;
import com.svwh.phonereview.domain.vo.CommentVo;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import com.svwh.phonereview.mapper.CommentMapper;
import com.svwh.phonereview.mapper.PostsMapper;
import com.svwh.phonereview.mapper.UserMapper;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.CommentService;
import com.svwh.phonereview.service.NotificationService;
import com.svwh.phonereview.utils.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

    @Override
    public PageVo<CommentVo> listComments(Long postId, PageQuery pageQuery) {
        LambdaQueryWrapper<Comment> cLqw = Wrappers.lambdaQuery();
        cLqw.eq(Comment::getPostId, postId);
        PageVo<CommentVo> commentVoPageVo = commentMapper.selectVoPage(pageQuery.buildMybatisPage(), cLqw);
        // 配置评论信息
        // TODO
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
        Comment add = MapstructUtils.convert(bo, Comment.class);
        commentMapper.insert(add);
        // 先获取评论的评测信息
        Posts posts = postsMapper.selectById(bo.getPostId());
        // 1. 判断是否是回复别人的评论
        if (bo.getParentId() != null) {
            // 查找评论对应的用户id
            Comment comment = commentMapper.selectById(bo.getParentId());
            // 发送通知
            NotificationBo notificationBo = new NotificationBo();
            notificationBo.setType("comment-reply");
            notificationBo.setTitle("评论回复");
            notificationBo.setContent("用户 <b>" + user.getNickname() + "</b> 回复了您的评论" + comment.getContent().substring(Math.max(10, comment.getContent().length())));
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
        if (commentVoPageVo.getRecords() == null || commentVoPageVo.getRecords().isEmpty()){
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
        if (StringUtils.isNotBlank(bo.getStatus())){
            cLqw.eq("c.status", bo.getStatus());
        }
        if (StringUtils.isNotBlank(bo.getKeyword())){
            cLqw.like("c.content",bo.getKeyword())
                    .or()
                    .like("u.username",bo.getKeyword());
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
        notificationBo.setContent("您的评论 <b>"+ comment.getContent()+ "</b>");
        notificationBo.setUserId(comment.getUserId());
        if (CommentConstant.APPROVED.equals(bo.getStatus())){
            notificationBo.setContent(notificationBo.getContent()+"已通过审核");
            notificationBo.setLink("/review/"+comment.getPostId());
        }
        if (CommentConstant.REJECTED.equals(bo.getStatus())){
            notificationBo.setContent(notificationBo.getContent()+"未通过审核，请重新提交");
        }
        notificationService.add(notificationBo);
        // 管理员更新评论的状态
        commentMapper.updateById(MapstructUtils.convert(bo,Comment.class));
    }
}
