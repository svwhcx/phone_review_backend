package com.svwh.phonereview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.svwh.phonereview.auth.IAuthService;
import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.auth.token.AUser;
import com.svwh.phonereview.common.constant.FavoriteConstant;
import com.svwh.phonereview.domain.bo.NotificationBo;
import com.svwh.phonereview.domain.bo.UserBo;
import com.svwh.phonereview.domain.entity.Favorite;
import com.svwh.phonereview.domain.entity.User;
import com.svwh.phonereview.domain.vo.LoginVo;
import com.svwh.phonereview.domain.vo.PostsVo;
import com.svwh.phonereview.domain.vo.UserVo;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import com.svwh.phonereview.mapper.FavoriteMapper;
import com.svwh.phonereview.mapper.PostsMapper;
import com.svwh.phonereview.mapper.UserMapper;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.NotificationService;
import com.svwh.phonereview.service.UserService;
import com.svwh.phonereview.utils.MapstructUtils;
import com.svwh.phonereview.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 17:26
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final IAuthService authService;
    private final FavoriteMapper favoriteMapper;
    private final PostsMapper postsMapper;
    private final NotificationService notificationService;


    @Override
    public LoginVo login(UserBo bo) {
        LambdaQueryWrapper<User> uLqw = Wrappers.lambdaQuery();
        uLqw.eq(User::getUsername, bo.getUsername())
                .eq(User::getPassword, PasswordUtils.encryption(bo.getPassword()))
                .select(User::getId,User::getUsername,User::getNickname,User::getRole,User::getStatus);
        UserVo vo = userMapper.selectVoOne(uLqw);
        if (vo == null) {
            throw new BusinessException(DefaultErrorCode.LOGIN_FAIL);
        }
        if (vo.getStatus() == 0) {
            throw new BusinessException(DefaultErrorCode.USER_DISABLED);
        }
        String token =  authService.internalLogin(new AUser(vo.getId(), bo.getUsername(), vo.getRole()));
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setUser(vo);

        return loginVo;
    }

    @Override
    @Transactional
    public void register(UserBo bo) {
        // 判断用户名是否已经被注册
        LambdaQueryWrapper<User> uLqw = Wrappers.lambdaQuery();
        uLqw.eq(User::getUsername, bo.getUsername());
        Long count = userMapper.selectCount(uLqw);
        if (count != null && count > 0) {
            throw new BusinessException(DefaultErrorCode.USERNAME_EXIST);
        }
        LambdaQueryWrapper<User> uLqw2 = Wrappers.lambdaQuery();
        uLqw2.eq(User::getEmail, bo.getEmail());
        count = userMapper.selectCount(uLqw2);
        if (count != null && count > 0) {
            throw new BusinessException(DefaultErrorCode.EMAIL_EXITS);
        }
        // 加密密码后保存到数据库中
        bo.setRole("user");
        bo.setCreateTime(LocalDateTime.now());
        bo.setPassword(PasswordUtils.encryption(bo.getPassword()));
        User user = MapstructUtils.convert(bo, User.class);
        userMapper.insert(user);
        // 发送一条系统通知
        NotificationBo notificationBo = new NotificationBo();
        notificationBo.setType("system");
        notificationBo.setContent("欢迎使用只能手机评测论坛，请完善您的个人资料");
        notificationBo.setLink("/user-center");
        notificationBo.setTitle("系统通知");
        notificationBo.setUserId(user.getId());
        notificationService.add(notificationBo);
    }

    @Override
    public void updatePassword(UserBo bo) {
        Long userId = UserInfoThreadLocal.get().getUserId();
        User user = userMapper.selectById(userId);
        bo.setOldPassword(PasswordUtils.encryption(bo.getOldPassword()));
        if (user.getPassword().equals(bo.getOldPassword())) {
            throw new BusinessException(DefaultErrorCode.OLD_PASSWORD_ERROR);
        }
        LambdaUpdateWrapper<User> uLuw = Wrappers.lambdaUpdate();
        uLuw.eq(User::getId, userId)
                .set(User::getPassword, PasswordUtils.encryption(bo.getPassword()));
        userMapper.update(uLuw);
    }

    @Override
    public void updateUserInfo(UserBo bo) {
        Long userId = UserInfoThreadLocal.get().getUserId();
        LambdaUpdateWrapper<User> uLuw = Wrappers.lambdaUpdate();
        uLuw.eq(User::getId, userId);
        if (StringUtils.isNotBlank(bo.getNickname())) {
            uLuw.set(User::getNickname, bo.getNickname());
        }
        uLuw.set(StringUtils.isNotBlank(bo.getAvatar()), User::getAvatar, bo.getAvatar());
        uLuw.set(StringUtils.isBlank(bo.getEmail()), User::getEmail, bo.getEmail());
        uLuw.set(StringUtils.isBlank(bo.getBio()), User::getBio, bo.getBio());
        userMapper.update(uLuw);
    }

    @Override
    public void updateUserStatus(Long userId, UserBo userBo) {
        User user = MapstructUtils.convert(userBo, User.class);
        user.setId(userId);
        userMapper.updateById(user);
    }

    @Override
    public void updateUserMuted(Long userId, UserBo userBo) {
        this.updateUserStatus(userId, userBo);
    }

    @Override
    public void deleteUser(Long userId) {
        userMapper.deleteById(userId);
    }

    @Override
    public PageVo<UserVo> getUserPage(UserBo userBo, PageQuery pageQuery) {
        LambdaQueryWrapper<User> uLqw = Wrappers.lambdaQuery();
        uLqw.orderByDesc(User::getCreateTime);
        if (StringUtils.isNotBlank(userBo.getKeyword())) {
            uLqw.like(User::getUsername, userBo.getKeyword())
                    .or()
                    .like(User::getEmail, userBo.getKeyword());
        }
        return userMapper.selectVoPage(pageQuery.buildMybatisPage(), uLqw);
    }

    @Override
    public void adminUpdateUserInfo(UserBo bo) {
        User user = MapstructUtils.convert(bo, User.class);
        userMapper.updateById(user);
    }

    @Override
    public void adminAddUser(UserBo bo) {
        LambdaQueryWrapper<User> uLqw = Wrappers.lambdaQuery();
        uLqw.eq(User::getUsername, bo.getUsername())
                .or()
                .eq(User::getEmail, bo.getEmail());
        Long count = userMapper.selectCount(uLqw);
        if (count != null && count > 0) {
            throw new BusinessException(DefaultErrorCode.USERNAME_EXIST);
        }
        User user = MapstructUtils.convert(bo, User.class);
        user.setPassword(PasswordUtils.encryption(user.getPassword()));
        userMapper.insert(user);
    }

    @Override
    public PageVo<PostsVo> getFavoritesPage(PageQuery pageQuery) {
        Long userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<Favorite> fLqw = Wrappers.lambdaQuery();
        fLqw.eq(Favorite::getType, FavoriteConstant.FAVORITE_POST)
                .eq(Favorite::getUserId, userId)
                .orderByDesc(Favorite::getCreateTime);
        Page<Favorite> favoritePage = favoriteMapper.selectPage(pageQuery.buildMybatisPage(), fLqw);
        // 然后从posts中获取数据
        List<Favorite> records = favoritePage.getRecords();
        PageVo<PostsVo> pageVo = new PageVo<>();
        pageVo.setTotal(favoritePage.getTotal());
        pageVo.setPageNum(favoritePage.getCurrent());
        if (records == null || records.isEmpty()){
            return pageVo;
        }
        // 获取对应的测评的数据
        List<Long> postIds = records.stream().map(Favorite::getTargetId).toList();
        List<PostsVo> posts = postsMapper.selectVoBatchIds(postIds);
        Map<Long, PostsVo> postsMap = posts.stream().collect(Collectors.toMap(PostsVo::getId, Function.identity()));
        List<PostsVo> postsVos = new ArrayList<>();
        records.forEach(record -> postsVos.add(postsMap.get(record.getTargetId())));
        pageVo.setRecords(postsVos);
        return pageVo;
    }

    @Override
    public UserVo getUserInfo() {
        Long userId = UserInfoThreadLocal.get().getUserId();
        UserVo vo = userMapper.selectVoById(userId);
        vo.setPassword(null);
        vo.setIsDelete(null);
        vo.setIsMuted(null);
        vo.setCreateTime(null);
        vo.setStatus(null);
        return vo;
    }

    @Override
    public UserVo getUserStats() {
        return null;
    }
}
