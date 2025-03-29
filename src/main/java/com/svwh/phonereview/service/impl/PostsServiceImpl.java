package com.svwh.phonereview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.auth.token.TokenInfo;
import com.svwh.phonereview.common.constant.CommentConstant;
import com.svwh.phonereview.common.constant.FavoriteConstant;
import com.svwh.phonereview.common.constant.PostsConstant;
import com.svwh.phonereview.domain.bo.FavoriteBo;
import com.svwh.phonereview.domain.bo.NotificationBo;
import com.svwh.phonereview.domain.bo.PostsBo;
import com.svwh.phonereview.domain.entity.Comment;
import com.svwh.phonereview.domain.entity.Favorite;
import com.svwh.phonereview.domain.entity.Posts;
import com.svwh.phonereview.domain.entity.User;
import com.svwh.phonereview.domain.vo.*;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import com.svwh.phonereview.mapper.*;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.FavoriteService;
import com.svwh.phonereview.service.NotificationService;
import com.svwh.phonereview.service.PostsService;
import com.svwh.phonereview.utils.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:56
 */
@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {

    private final PostsMapper postsMapper;
    private final FavoriteService favoriteService;
    private final FavoriteMapper favoriteMapper;
    private final UserMapper userMapper;
    private final PhoneModelMapper phoneModelMapper;
    private final BrandMapper brandMapper;
    private final NotificationService notificationService;
    private final CommentMapper commentMapper;

    @Override
    public void add(PostsBo bo) {
        bo.setCreateTime(LocalDateTime.now());
        // 对图片进行拼接
        if (bo.getFileList() != null && !bo.getFileList().isEmpty()){
            bo.setImages(String.join(",", bo.getFileList()));
        }
        bo.setUserId(UserInfoThreadLocal.get().getUserId());
        bo.setStatus(PostsConstant.APPENDING);
        Posts posts = MapstructUtils.convert(bo, Posts.class);
        postsMapper.insert(posts);
    }

    @Override
    public void delete(Long id) {
        // 验证是否是当前的用户
        valid(id);
        // TODO 联动删除对应的评论和收藏
        postsMapper.deleteById(id);
    }

    @Override
    public PostsVo get(Long id) {
        PostsVo vo = postsMapper.selectVoById(id);
        if (!PostsConstant.APPROVED.equals(vo.getStatus())){
            throw new BusinessException(DefaultErrorCode.POSTS_REJECTED);
        }
        // 组装用户数据
        User user = userMapper.selectById(vo.getUserId());
        vo.setUsername(user.getUsername());
        vo.setUserAvatar(user.getAvatar());
        vo.setNickname(user.getNickname());
        // 拆分图片
        if (StringUtils.isNotBlank(vo.getImages())){
            vo.setFileList(new ArrayList<>(List.of(vo.getImages().split(","))));
        }
        // 组装点赞数和收藏数，以及评论数


        LambdaQueryWrapper<Favorite> favoriteLqw = Wrappers.lambdaQuery();
        favoriteLqw.eq(Favorite::getTargetId, id)
                .eq(Favorite::getType, FavoriteConstant.LIKE_POST)
                .or()
                .eq(Favorite::getType,FavoriteConstant.FAVORITE_POST);
        List<FavoriteVo> vos = favoriteMapper.selectVoList(favoriteLqw);
        // 评论数
        LambdaQueryWrapper<Comment> cLqw = Wrappers.lambdaQuery();
        cLqw.eq(Comment::getPostId, id)
                .eq(Comment::getIsDelete, false)
                .eq(Comment::getStatus, CommentConstant.APPROVED);
        Long count = commentMapper.selectCount(cLqw);
        // 组装数据,同时验证用户当前是否点赞或者是否收藏
        boolean isLiked = false;
        boolean isFavorited = false;
        TokenInfo tokenInfo = UserInfoThreadLocal.get();
        long likeCount = 0;
        long favoriteCount = 0;
        for (FavoriteVo fVo : vos) {
            if (FavoriteConstant.LIKE_POST.equals(fVo.getType())){
                likeCount++;
                if (tokenInfo != null && tokenInfo.getUserId().equals(vo.getUserId())){
                    isLiked = true;
                }
            }else if(FavoriteConstant.FAVORITE_POST.equals(fVo.getType())){
                favoriteCount++;
                if (tokenInfo != null && tokenInfo.getUserId().equals(vo.getUserId())){
                    isFavorited = true;
                }
            }
        }
        vo.setComments(Math.max(count,0));
        vo.setLikeCount(likeCount);
        vo.setFavoriteCount(favoriteCount);
        vo.setIsFavorited(isFavorited);
        vo.setIsLiked(isLiked);
        return vo;
    }

    @Override
    public PageVo<PostsVo> queryPage(PostsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Posts> pLqw = Wrappers.lambdaQuery();
        pLqw.eq(Posts::getStatus,PostsConstant.APPROVED);
        PageVo<PostsVo> pagevo = postsMapper.selectVoPage(pageQuery.buildMybatisPage(), pLqw);
        // 配置用户的头像
        if (pagevo.getRecords() == null || pagevo.getRecords().isEmpty()){
            return pagevo;
        }

        List<Long> userIds = pagevo.getRecords().stream().map(PostsVo::getUserId).toList();
        LambdaQueryWrapper<User> uLqw = Wrappers.lambdaQuery();
        uLqw.in(User::getId, userIds)
                .select(User::getId, User::getAvatar,User::getUsername,User::getNickname);
        Map<Long, UserVo> userMap = userMapper.selectVoList(uLqw).stream().collect(Collectors.toMap(UserVo::getId, Function.identity()));
        pagevo.getRecords().forEach(vo -> {
            // 分割图片
            if (StringUtils.isNotBlank(vo.getImages())){
                vo.setFileList(new ArrayList<>(List.of(vo.getImages().split(","))));
            }
            vo.setUsername(userMap.get(vo.getUserId()).getUsername());
            vo.setNickname(userMap.get(vo.getUserId()).getNickname());
            vo.setUserAvatar(userMap.get(vo.getUserId()).getAvatar());
        });
        return pagevo;
    }

    @Override
    public PageVo<PostsVo> getRecommendList(PostsBo bo, PageQuery pageQuery) {
        PageVo<PostsVo> pageVo = new PageVo<>();
        pageVo.setTotal(0L);
        return pageVo;
    }

    @Override
    public void update(PostsBo bo) {
        valid(bo.getId());
        postsMapper.updateById(MapstructUtils.convert(bo, Posts.class));
    }

    @Override
    public PageVo<PostsVo> queryPageCurrentUser(PostsBo bo, PageQuery pageQuery) {
        Long userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<Posts> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.orderByDesc(Posts::getCreateTime);
        lambdaQueryWrapper.eq(Posts::getUserId, userId);
        // 条件过滤
        return postsMapper.selectVoPage(pageQuery.buildMybatisPage(), lambdaQueryWrapper);
    }

    @Override
    public void like(Long id) {
        // 点赞
        FavoriteBo favoriteBo = new FavoriteBo();
        favoriteBo.setTargetId(id);
        favoriteService.addPostLike(favoriteBo);
    }

    @Override
    public void unLike(Long id) {
        Long userId = UserInfoThreadLocal.get().getUserId();
        FavoriteBo favoriteBo = new FavoriteBo();
        favoriteBo.setUserId(userId);
        favoriteBo.setTargetId(id);
        favoriteBo.buildLikePost();
        favoriteService.delete(favoriteBo);
    }

    @Override
    public void favorite(Long id) {
        FavoriteBo favoriteBo = new FavoriteBo();
        favoriteBo.setTargetId(id);
        favoriteService.addPostFavorite(favoriteBo);
    }

    @Override
    public void unFavorite(Long id) {
        Long userId = UserInfoThreadLocal.get().getUserId();
        FavoriteBo favoriteBo = new FavoriteBo();
        favoriteBo.setUserId(userId);
        favoriteBo.setTargetId(id);
        favoriteBo.buildFavoritePost();
        favoriteService.delete(favoriteBo);
    }

    @Override
    public PageVo<PostsVo> getFavoritesPage(PageQuery pageQuery) {
        Long userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<Favorite> fLqw = Wrappers.lambdaQuery();
        fLqw.eq(Favorite::getUserId, userId)
                .eq(Favorite::getType, FavoriteConstant.FAVORITE_POST)
                .orderByDesc(Favorite::getCreateTime);
        Page<Favorite> favoritePage = favoriteMapper.selectPage(pageQuery.buildMybatisPage(), fLqw);
        PageVo<PostsVo> pageVo = new PageVo<>();
        pageVo.setTotal(favoritePage.getTotal());
        pageVo.setPageNum(favoritePage.getCurrent());
        if (favoritePage.getRecords() == null || favoritePage.getRecords().isEmpty()){
            return pageVo;
        }
        List<PostsVo> postsVoList = new ArrayList<>();
        List<Long> postIds = favoritePage.getRecords().stream().map(Favorite::getTargetId).toList();
        List<PostsVo> posts = postsMapper.selectVoBatchIds(postIds);
        Map<Long, PostsVo> postsVoMap = posts.stream().collect(Collectors.toMap(PostsVo::getId, Function.identity()));
        favoritePage.getRecords().forEach(item -> postsVoList.add(postsVoMap.get(item.getTargetId())));
        pageVo.setRecords(postsVoList);
        return pageVo;
    }


    /**
     * ---------------------------------
     *           管理员部分
     *
     *-----------------------------------
     */

    @Override
    public PageVo<PostsVo> adminList(PostsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Posts> pLqw = Wrappers.lambdaQuery();
        if (bo.getStatus() != null){
            pLqw.eq(Posts::getStatus, bo.getStatus());
        }
        if (StringUtils.isNotBlank(bo.getKeyword())){
            pLqw.like(Posts::getTitle, bo.getKeyword())
                    .or()
                    .like(Posts::getContent, bo.getKeyword());
        }
        pLqw.orderByDesc(Posts::getCreateTime);
        PageVo<PostsVo> pageVo = postsMapper.selectVoPage(pageQuery.buildMybatisPage(), pLqw);
        if (pageVo.getRecords() == null || pageVo.getRecords().isEmpty()){
            return pageVo;
        }
        // 组装用户数据
        List<Long> userIds = pageVo.getRecords().stream().map(PostsVo::getUserId).toList();
        List<UserVo> userVos = userMapper.selectVoBatchIds(userIds);
        Map<Long, UserVo> userVoMap = userVos.stream().collect(Collectors.toMap(UserVo::getId, Function.identity()));
        // 组装品牌和型号数据
        List<Long> phoneModelIds = pageVo.getRecords().stream().map(PostsVo::getPhoneModelId).toList();
        List<PhoneModelVo> phoneModelVos = phoneModelMapper.selectVoBatchIds(phoneModelIds);
        Map<Long, PhoneModelVo> phoneModelVoMap = phoneModelVos.stream().collect(Collectors.toMap(PhoneModelVo::getId, Function.identity()));
        // 组装品牌数据
        List<Long> brandIds = phoneModelVos.stream().map(PhoneModelVo::getBrandId).toList();
        List<BrandVo> brandVos = brandMapper.selectVoBatchIds(brandIds);
        Map<Long, BrandVo> brandVoMap = brandVos.stream().collect(Collectors.toMap(BrandVo::getId, Function.identity()));
        pageVo.getRecords().forEach(item -> {
            // 分割评测图片
            item.setFileList(StringUtils.isNotBlank(item.getImages()) ? Arrays.asList(item.getImages().split(",")) : new ArrayList<>());
            item.setUserAvatar(userVoMap.get(item.getUserId()).getAvatar());
            item.setUsername(userVoMap.get(item.getUserId()).getUsername());
            item.setBrand(brandVoMap.get(item.getBrandId()).getName());
            item.setPhoneModel(phoneModelVoMap.get(item.getPhoneModelId()).getName());
        });
        return pageVo;
    }

    @Override
    @Transactional
    public void adminUpdate(PostsBo bo) {
        // 获取这个帖子对应的用户的id
        Posts posts = postsMapper.selectById(bo.getId());
        // 更新的时候应该会发送系统通知。
        NotificationBo notificationBo = new NotificationBo();
        notificationBo.setType("system");
        notificationBo.setLink("/user-center");
        notificationBo.setTitle("评测审核");
        notificationBo.setContent("您的评测 <b>"+ posts.getTitle()+ "</b>");
        notificationBo.setUserId(posts.getUserId());
        if (PostsConstant.REJECTED.equals(bo.getStatus())){
            notificationBo.setContent(notificationBo.getContent()+"未通过审核，请重新提交评测");
        }else {
            notificationBo.setContent(notificationBo.getContent()+"已通过审核，请查看");
            notificationBo.setLink("/review/" + bo.getId());
        }
        notificationService.add(notificationBo);
        postsMapper.updateById(MapstructUtils.convert(bo,Posts.class));
    }

    /**
     * 校验一个评测的操作合合法性
     *
     * @param postsId
     */
    private void valid(Long postsId) {
        Long userId = UserInfoThreadLocal.get().getUserId();
        Posts posts = postsMapper.selectById(postsId);
        if (posts == null || !posts.getUserId().equals(userId)) {
            throw new BusinessException(DefaultErrorCode.ILLEGAL_OPERATION);
        }
    }
}
