package com.svwh.phonereview.domain.bo;

import com.svwh.phonereview.common.validation.AddGroup;
import com.svwh.phonereview.common.validation.ChangePassword;
import com.svwh.phonereview.common.validation.EditGroup;
import com.svwh.phonereview.domain.entity.User;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/24 20:26
 */
@Data
@AutoMapper(target = User.class)
@Setter
@Getter
public class UserBo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(groups = {AddGroup.class})
    private String username;

    /**
     * 邮箱
     */
    @Email(groups = {AddGroup.class, })
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户的个人简介
     */
    private String bio;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空",groups = {AddGroup.class, ChangePassword.class})
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
     * 用户角色
     */
    private String role;

    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空",groups = {ChangePassword.class})
    private String oldPassword;

    /**
     * 用户的状态
     */
    private Integer status;

    /**
     * 搜索关键字（用于管理员根据用户名/邮箱查找用户列表)
     */
    private String keyword;


}
