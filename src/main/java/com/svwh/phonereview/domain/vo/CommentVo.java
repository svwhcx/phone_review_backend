package com.svwh.phonereview.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.svwh.phonereview.domain.entity.Comment;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description 用户堆评测的评论
 * @Author cxk
 * @Date 2025/3/24 20:40
 */
@Data
@AutoMapper(target = Comment.class)
public class CommentVo  {




    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long userId;

    private Long postId;

    private Long parentId;

    private LocalDateTime createTime;

    private String content;

    /**
     * 评论的点赞量
     */
    private Integer favorite;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 帖子的标题
     */
    private String postTitle;

    private String status;

    private String username;

    private String nickname;

    private String userAvatar;

    private Long rootId;


    /**
     * 二级评论列表
     */
    private List<CommentVo> replies;

    /**
     * 回复的用户的头像
     */
    private String replyUserAvatar;

    private String replyUserNickname;

    private String replyUserUsername;

    private String replyTo;

    private Long replyToUserId;

    /**
     * 当前评论的点赞量
     */
    private Long likes;

    /**
     * 当前用户是否点赞了
     */
    private Boolean isLiked;
}
