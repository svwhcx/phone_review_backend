package com.svwh.phonereview.service;

import com.svwh.phonereview.domain.bo.PostsBo;
import com.svwh.phonereview.domain.vo.PostsVo;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:55
 */
public interface PostsService {

    /**
     * 发布一个评测
     * @param bo
     */
    void add(PostsBo bo);

    /**
     * 删除一个评测
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id获取一个评测的详情
     * @param id
     * @return
     */
    PostsVo get(Long id);

    /**
     * 分页查询评测
     * @param bo
     * @param pageQuery
     * @return
     */
    PageVo<PostsVo> queryPage(PostsBo bo, PageQuery pageQuery);

    /**
     * 组装评测的基本数据
     * @param pageVo
     * @return
     */
    void combinePostData(List<PostsVo> postsVos);

    /**
     * 获取推荐的评测列表
     * @param bo
     * @param pageQuery
     * @return
     */
    PageVo<PostsVo> getRecommendList(PostsBo bo, PageQuery pageQuery);

    /**
     * 更新一个评测
     * @param bo
     */
    void update(PostsBo bo);

    /**
     * 分页获取当前用户的评测列表
     * @param bo
     * @param pageQuery
     * @return
     */
    PageVo<PostsVo> queryPageCurrentUser(PostsBo bo, PageQuery pageQuery);

    /**
     * 用户点赞一个评测
     * @param id
     */
    void like(Long id);

    /**
     * 用户取消点赞一个评测
     * @param id
     */
    void unLike(Long id);

    /**
     * 用户收藏一个评测
     * @param id
     */
    void favorite(Long id);

    /**
     * 用户取消收藏一个评测
     * @param id
     */
    void unFavorite(Long id);

    /**
     * 用户分页获取自己收藏的评测列表
     * @param pageQuery 分页参数
     * @return
     */
    PageVo<PostsVo> getFavoritesPage(PageQuery pageQuery);


    /**
     * 管理员获取评测列表
     * @param bo
     * @param pageQuery
     */
    PageVo<PostsVo> adminList(PostsBo bo, PageQuery pageQuery);

    /**
     * 管理员修改帖子数据
     * @param bo
     */
    void adminUpdate(PostsBo bo);

}
