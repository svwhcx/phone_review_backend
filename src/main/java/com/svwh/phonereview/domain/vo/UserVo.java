package com.svwh.phonereview.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.svwh.phonereview.domain.entity.User;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/24 20:26
 */
@Data
@AutoMapper(target = User.class)
public class UserVo  {



    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户的个人简介
     */
    private String bio;

    /**
     * 用户角色
     */
    private String role;
    /**
     * 头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 是否被禁言
     */
    private Boolean isMuted;

    /**
     * 用户注册的时间
     */
    private LocalDateTime createTime;

    /**
     * 当前用户的状态
     */
    private Integer status;


    /**
     * 发布的帖子数量
     */
    private Long postCount;

    /**
     * 帖子被点赞的数量
     */
    private Long likeCount;

    /**
     * 收藏的帖子数
     */
    private Long favoriteCount;

    /**
     * 所有帖子的阅读量。
     */
    private Long views;

}
