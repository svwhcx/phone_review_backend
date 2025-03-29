package com.svwh.phonereview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.common.constant.FavoriteConstant;
import com.svwh.phonereview.domain.bo.FavoriteBo;
import com.svwh.phonereview.domain.bo.PostsBo;
import com.svwh.phonereview.domain.entity.Favorite;
import com.svwh.phonereview.domain.entity.Posts;
import com.svwh.phonereview.domain.vo.PostsVo;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import com.svwh.phonereview.mapper.FavoriteMapper;
import com.svwh.phonereview.mapper.PostsMapper;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.FavoriteService;
import com.svwh.phonereview.service.PostsService;
import com.svwh.phonereview.utils.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Override
    public void add(PostsBo bo) {
        Posts posts = MapstructUtils.convert(bo, Posts.class);
        posts.setCreateTime(LocalDateTime.now());
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
        return postsMapper.selectVoById(id);
    }

    @Override
    public PageVo<PostsVo> queryPage(PostsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Posts> pLqw = Wrappers.lambdaQuery();
        return postsMapper.selectVoPage(pageQuery.buildMybatisPage(), pLqw);
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
