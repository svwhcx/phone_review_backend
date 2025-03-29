package com.svwh.phonereview.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.svwh.phonereview.domain.vo.UserVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/24 20:26
 */
@Data
@TableName("user")
@AutoMapper(target = UserVo.class)
public class User {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
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
     * 当前永不的状态
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 用户注册的时间
     */
    private LocalDateTime createTime;

    /**
     * 角色名称
     */
    private String role;



}
