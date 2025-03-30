package com.svwh.phonereview.controller;

import com.svwh.phonereview.annotation.RecordInteraction;
import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.auth.annotation.IgnoreAuth;
import com.svwh.phonereview.auth.token.TokenInfo;
import com.svwh.phonereview.common.constant.CommentConstant;
import com.svwh.phonereview.common.constant.FavoriteConstant;
import com.svwh.phonereview.common.constant.RecommendConstant;
import com.svwh.phonereview.common.validation.AddGroup;
import com.svwh.phonereview.domain.bo.CommentBo;
import com.svwh.phonereview.domain.bo.FavoriteBo;
import com.svwh.phonereview.domain.bo.PostsBo;
import com.svwh.phonereview.domain.entity.Comment;
import com.svwh.phonereview.domain.vo.CommentVo;
import com.svwh.phonereview.domain.vo.PostsVo;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.query.RecommendQuery;
import com.svwh.phonereview.service.CommentService;
import com.svwh.phonereview.service.FavoriteService;
import com.svwh.phonereview.service.PostsService;
import com.svwh.phonereview.service.RecommendationService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/24 21:46
 */
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;
    private final CommentService commentService;
    private final FavoriteService favoriteService;
    private final RecommendationService recommendationService;

    /**
     * 查询所有的评测列表
     * @param bo
     * @param pageQuery
     * @return
     */
    @GetMapping()
    @IgnoreAuth
    public PageVo<PostsVo> list(PostsBo bo, PageQuery pageQuery){
        return postsService.queryPage(bo,pageQuery);
    }

    /**
     * 获取推荐列表(旧方法，已弃用，保留向后兼容)
     * @param pageQuery
     * @return
     */
  /*  @GetMapping("/recommend")
    @IgnoreAuth
    public PageVo<PostsVo> getRecommendList(PageQuery pageQuery){
        return postsService.getRecommendList(pageQuery);
    }*/
    
    /**
     * 获取个性化推荐文章
     * 根据用户历史行为生成个性化推荐
     * @param pageQuery 分页参数
     * @return 个性化推荐的文章列表
     */
    @GetMapping("/recommend")
    public PageVo<PostsVo> getPersonalizedRecommendations(RecommendQuery pageQuery) {
        TokenInfo tokenInfo = UserInfoThreadLocal.get();
        Long userId = tokenInfo.getUserId();
        return recommendationService.getPersonalizedRecommendations(userId, pageQuery.getPage(), pageQuery.getLimit());
    }

    
    /**
     * 获取与指定文章相似的文章推荐
     * @param postId 文章ID
     * @param pageQuery 分页参数
     * @return 相似文章列表
     */
    @GetMapping("/{postId}/similar")
    @IgnoreAuth
    public PageVo<PostsVo> getSimilarPosts(@PathVariable Long postId, RecommendQuery pageQuery) {
        Long userId = null;
        try {
            userId = UserInfoThreadLocal.get().getUserId();
        } catch (Exception e) {
            // 用户未登录，使用匿名推荐
        }
        return recommendationService.getSimilarPosts(userId, postId, pageQuery.getPage(), pageQuery.getLimit());
    }

    
    /**
     * 获取匿名用户的推荐文章
     * 用于未登录用户的通用推荐
     * @param pageQuery 分页参数
     * @return 推荐文章列表
     */
    @GetMapping("/anonymous-recommend")
    @IgnoreAuth
    public PageVo<PostsVo> getAnonymousRecommendations(RecommendQuery pageQuery) {
        return recommendationService.getAnonymousRecommendations(pageQuery.getPage(), pageQuery.getLimit());
    }



    /**
     * 发布一篇帖子
     * @param bo 帖子参数
     */
    @PostMapping
    public void add( @Validated(AddGroup.class) @RequestBody PostsBo bo){
        postsService.add(bo);
    }

    /**
     * 获取一个评测的详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @IgnoreAuth
    @RecordInteraction(actionType = RecommendConstant.interactionType.VIEW
                     , itemType = RecommendConstant.itemType.POST,
                      itemIdParam = "id")
    public PostsVo get(@NotNull @PathVariable Long id){
        return postsService.get(id);
    }

    /**
     * 更新一个评测
     * @param id
     * @param bo
     */
    @PutMapping("/{id}")
    public void update(@PathVariable Long id,@RequestBody PostsBo bo){
        bo.setId(id);
        postsService.update(bo);
    }

    /**
     * 删除一个评测
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@NotNull @PathVariable Long id){
        postsService.delete(id);
    }

    /**
     * 用户点赞一个评测
     * @param id
     */
    @PostMapping("/{id}/like")
    @RecordInteraction(actionType = RecommendConstant.interactionType.LIKE
                     , itemType = RecommendConstant.itemType.POST,
                      itemIdParam = "id")
    public void like(@NotNull @PathVariable Long id){
        postsService.like(id);
    }

    /**
     * 用户取消一个评测的点赞
     * @param id
     */
    @DeleteMapping("/{id}/like")
    public void unLike(@NotNull @PathVariable Long id){
        postsService.unLike(id);
    }

    /**
     * 用户收藏一个评测
     * @param id
     */
    @PostMapping("/{id}/favorite")
    @RecordInteraction(actionType = RecommendConstant.interactionType.FAVORITE
                     , itemType = RecommendConstant.itemType.POST,
                      itemIdParam = "id")
    public void favorite(@NotNull @PathVariable Long id){
        postsService.favorite(id);
    }

    /**
     * 用户取消一个评测的收藏
     * @param id
     */
    @DeleteMapping("/{id}/favorite")
    public void unFavorite(@NotNull @PathVariable Long id){
        postsService.unFavorite(id);
    }


    /**
     * 获取当前用户发布的评测列表（分页）
     * @param bo
     * @param pageQuery
     * @return
     */
    @GetMapping("/user")
    public PageVo<PostsVo> listCurrentUser(PostsBo bo, PageQuery pageQuery){
        return postsService.queryPageCurrentUser(bo,pageQuery);
    }

    /**
     * 获取当前用户收藏的评测列表
     * @param pageQuery 分页参数
     * @return
     */
    @GetMapping("/user/favorites")
    public PageVo<PostsVo> listFavorites(PageQuery pageQuery){
        return postsService.getFavoritesPage(pageQuery);
    }

    /**
     * ----------------------------------------
     * 评论部分
     * --------------------------------------------
     */


    /**
     * 分页获取评测的评论列表
     * @param postId
     * @param pageQuery
     * @return
     */
    @GetMapping("/{postId}/comments")
    @IgnoreAuth
    public PageVo<CommentVo> listComments(@PathVariable Long postId, PageQuery pageQuery){
        return commentService.listComments(postId,pageQuery);
    }


    /**
     * 用户删除一个评论
     * @param postId
     * @param commentId
     */
    @DeleteMapping("/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long postId,@PathVariable Long commentId){
        CommentBo bo = new CommentBo();
        bo.setId(commentId);
        bo.setPostId(postId);
        commentService.delete(bo);
    }


    /**
     * 用户发布一个评论
     * @param postId 评测id
     * @param bo 评论的内容
     */
    @PostMapping("/{postId}/comments")
    @RecordInteraction(actionType = RecommendConstant.interactionType.COMMENT
                     , itemType = RecommendConstant.itemType.POST,
                      itemIdParam="postId"
    )
    public void addComment(@PathVariable Long postId,@RequestBody CommentBo bo){
        bo.setPostId(postId);
        commentService.add(bo);
    }


    /**
     * 用户点赞一个评论
     * @param postId 评测id
     * @param commentId 评论id
     */
    @PostMapping("/{postId}/comments/{commentId}/like")
    public  void likeComment(@PathVariable Long postId,@PathVariable Long commentId){
        FavoriteBo bo = new FavoriteBo();
        bo.setTargetId(commentId);
        favoriteService.addCommentLike(bo);
    }

    /**
     * 取消点赞一个评论
     * @param postId 评测id
     * @param commentId 评论id
     */
    @DeleteMapping("/{postId}/comments/{commentId}/like")
    public void unLikeComment(@PathVariable Long postId,@PathVariable Long commentId){
        FavoriteBo bo = new FavoriteBo();
        bo.setTargetId(commentId);
        bo.setUserId(UserInfoThreadLocal.get().getUserId());
        bo.setType(FavoriteConstant.LIKE_COMMENT);
        favoriteService.delete(bo);
    }


    /***
     *
     * ------------------------------------------
     *                 管理员部分
     * ------------------------------------------
     */

    /**
     * 管理员获取评测列表（分页）
     * @param bo
     * @param pageQuery
     * @return
     */
    @GetMapping("/admin/list")
    public PageVo<PostsVo> adminListPosts(PostsBo bo,PageQuery pageQuery){
        return postsService.adminList(bo,pageQuery);
    }

    /**
     * 管理员修改帖子的状态
     * @param postId
     * @param bo
     */
    @PutMapping("/admin/{postId}/status")
    public void updateStatus(@PathVariable Long postId,@RequestBody PostsBo bo){
        bo.setId(postId);
        postsService.adminUpdate(bo);
    }

    /**
     * 管理员分页查看一个评测的评论列表
     * @param postId
     * @param pageQuery
     * @return
     */
    @GetMapping("/admin/{postId}/comments")
    public PageVo<CommentVo> adminListComments(@PathVariable Long postId,PageQuery pageQuery){
        CommentBo bo = new CommentBo();
        bo.setPostId(postId);
        return commentService.listComments(postId,pageQuery);
    }
}
