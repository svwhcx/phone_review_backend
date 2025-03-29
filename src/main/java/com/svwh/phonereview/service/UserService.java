package com.svwh.phonereview.service;

import com.svwh.phonereview.domain.bo.UserBo;
import com.svwh.phonereview.domain.vo.LoginVo;
import com.svwh.phonereview.domain.vo.PostsVo;
import com.svwh.phonereview.domain.vo.UserVo;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 17:26
 */
public interface UserService {

    /**
     * 用户登录
     * @param bo 用户的登录信息
     * @return 返回登录成功的 accessToken
     */
    LoginVo login(UserBo bo);

    void register(UserBo bo);

    /**
     * 用户修改自己的密码
     * @param bo 用户的修改密码信息
     */
    void updatePassword(UserBo bo);

    /**
     * 用户修改自己的信息
     * @param bo 用户要修改的信息
     */
    void updateUserInfo(UserBo bo);

    /**
     * 根据用户的id修改用户的状态
     * @param userId
     * @param userBo
     */
    void updateUserStatus(Long userId, UserBo userBo);

    /**
     * 管理员禁言/解禁一个用户
     * @param userId
     * @param userBo
     */
    void updateUserMuted(Long userId, UserBo userBo);

    /**
     * 删除一个用户
     * @param userId
     */
    void deleteUser(Long userId);

    /**
     * 分页获取用户数据
     * @param userBo
     * @param pageQuery
     */
    PageVo<UserVo> getUserPage(UserBo userBo, PageQuery pageQuery);

    /**
     * 管理员修改用户的个人信息
     * @param bo
     */
    void adminUpdateUserInfo(UserBo bo);

    /**
     * 管理员添加一个新的用户
     * @param bo 用户的个人信息
     */
    void adminAddUser(UserBo bo);

    /**
     * 用户分页获取自己收藏的测评
     * @param pageQuery
     * @return
     */
    PageVo<PostsVo> getFavoritesPage(PageQuery pageQuery);

    /**
     * 获取用户个人信息
     * @return
     */
    UserVo getUserInfo();

    /**
     * 获取当前用户的统计数据
     * @return
     */
    UserVo getUserStats();

}
