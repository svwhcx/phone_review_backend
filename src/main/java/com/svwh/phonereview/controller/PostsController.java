package com.svwh.phonereview.controller;

import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.auth.annotation.IgnoreAuth;
import com.svwh.phonereview.common.validation.AddGroup;
import com.svwh.phonereview.domain.bo.CommentBo;
import com.svwh.phonereview.domain.bo.FavoriteBo;
import com.svwh.phonereview.domain.bo.PostsBo;
import com.svwh.phonereview.domain.entity.Comment;
import com.svwh.phonereview.domain.vo.CommentVo;
import com.svwh.phonereview.domain.vo.PostsVo;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.CommentService;
import com.svwh.phonereview.service.FavoriteService;
import com.svwh.phonereview.service.PostsService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 获取推荐的评测列表（分页）
     * @param bo
     * @param pageQuery
     * @return
     */
    @GetMapping("/recommend")
    public PageVo<PostsVo> getRecommendList(PostsBo bo, PageQuery pageQuery){
        return postsService.getRecommendList(bo,pageQuery);
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
        favoriteService.delete(bo);
    }
}
