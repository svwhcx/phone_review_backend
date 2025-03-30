package com.svwh.phonereview.controller;

import com.svwh.phonereview.auth.annotation.IgnoreAuth;
import com.svwh.phonereview.common.validation.AddGroup;
import com.svwh.phonereview.common.validation.ChangePassword;
import com.svwh.phonereview.domain.bo.PostsBo;
import com.svwh.phonereview.domain.bo.UserBo;
import com.svwh.phonereview.domain.vo.CommentVo;
import com.svwh.phonereview.domain.vo.LoginVo;
import com.svwh.phonereview.domain.vo.PostsVo;
import com.svwh.phonereview.domain.vo.UserVo;
import com.svwh.phonereview.permission.RoleEnum;
import com.svwh.phonereview.permission.annotation.CheckPermission;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.CommentService;
import com.svwh.phonereview.service.PostsService;
import com.svwh.phonereview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/24 21:45
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostsService postsService;
    private final CommentService commentService;

    /**
     * 用户注册
     *
     * @param ob
     * @return
     */
    @PostMapping("/register")
    @IgnoreAuth
    public LoginVo register(@Validated(AddGroup.class) @RequestBody UserBo bo){
        return userService.register(bo);
    }


    /**
     * 用户登录
     * @param ob
     * @return
     */
    @PostMapping("/login")
    @IgnoreAuth
    public LoginVo login(@RequestBody UserBo ob){
        return userService.login(ob);
    }

    @GetMapping("/info")
    public UserVo userInf(){
        return userService.getUserInfo();
    }

    /**
     * 用户修改密码
     * @param bo 需要的密码数据
     */
    @PutMapping("/password")
    public void updatePassword(@Validated(ChangePassword.class)@RequestBody UserBo bo){
        userService.updatePassword(bo);
    }

    /**
     * 用户修改自己的个人信息<p>
     * 这里只能修改用户的nickname、avatar、邮箱以及bio个人简介<p>
     * 密码用专用的密码接口
     * @param bo 需要修改的信息
     */
    @PutMapping("/info")
    public void updateUserInfo(@RequestBody UserBo bo){
        userService.updateUserInfo(bo);
    }


    /**
     * 管理员添加一个用户到系统中
     * @param bo
     */
    @PostMapping("/admin")
    @CheckPermission
    public void adminAddUser(@RequestBody UserBo bo){
        userService.adminAddUser(bo);
    }

    /**
     * 管理员修改用户的个人信息
     * @param userId
     * @param bo
     */
    @PutMapping("/admin/{userId}")
    @CheckPermission
    public void adminUpdateUserInfo(@PathVariable Long userId,@RequestBody UserBo bo){
        bo.setId(userId);
        userService.adminUpdateUserInfo(bo);
    }

    /**
     * 管理员分页获取用户的列表
     * @param userBo
     * @param pageQuery
     * @return
     */
    @GetMapping("/admin/list")
    @CheckPermission(RoleEnum.ADMIN)
    public PageVo<UserVo> getUserPage(UserBo userBo, PageQuery pageQuery){
        return userService.getUserPage(userBo,pageQuery);
    }

    /**
     * 管理员删除一个用户（可能不会用到）
     * @param userId
     */
    @DeleteMapping("/admin/{userId}")
    @CheckPermission(RoleEnum.ADMIN)
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    /**
     * 管理员修改用户的状态
     */
    @PutMapping("/admin/{userId}/status")
    @CheckPermission(RoleEnum.ADMIN)
    public void updateUserStatus(@PathVariable Long userId,UserBo userBo){
        userService.updateUserStatus(userId,userBo);
    }

    /**
     * 管理员操作用户禁言
     * @param userId
     * @param userBo
     */
    @PutMapping("/admin/{userId}/mute")
    @CheckPermission(RoleEnum.ADMIN)
    public void updateUserMute(@PathVariable Long userId,@RequestBody UserBo userBo){
        userService.updateUserMuted(userId,userBo);
    }


    /**
     * 用户分页获取自己的收藏
     * @param pageQuery 分页参数
     * @return 分页的收藏评测列表
     */
    @GetMapping("/favorites")
    public PageVo<PostsVo> getFavorites(PageQuery pageQuery){
        return userService.getFavoritesPage(pageQuery);
    }
    /**
     * 用户获取自己的评测
     * @param pageQuery 分页参数
     * @return 评测的分页列表
     */
    @GetMapping("/posts")
    public PageVo<PostsVo> getPosts(PageQuery pageQuery){
        return postsService.queryPageCurrentUser(new PostsBo(),pageQuery);
    }

    @GetMapping("/comments")
    public PageVo<CommentVo> queryUserComments(PageQuery pageQuery){
        return commentService.queryUserComments(pageQuery);
    }

    /**
     * 获取当前用户的统计数据
     * @return
     */
    @GetMapping("/stats")
    public UserVo getUserStats(){
        return userService.getUserStats();
    }
}
