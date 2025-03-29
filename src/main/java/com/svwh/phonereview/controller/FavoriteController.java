package com.svwh.phonereview.controller;

import com.svwh.phonereview.domain.bo.FavoriteBo;
import com.svwh.phonereview.domain.entity.Favorite;
import com.svwh.phonereview.domain.vo.FavoriteVo;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.FavoriteService;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/24 22:02
 */
@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    /**
     * 用户分页查看自己的点赞列表
     * @param bo
     * @param pageQuery
     * @return
     */
//    @GetMapping("/list")
//    public PageVo<FavoriteVo> list(FavoriteBo bo, PageQuery pageQuery){
//        return favoriteService.queryPage(bo,pageQuery);
//    }
//
//    /**
//     * 用户点赞一个帖子
//     * @param bo
//     */
//    @PostMapping("/article")
//    public void favoriteArticle(FavoriteBo bo){
//        favoriteService.favoriteArticle(bo);
//    }
//
//    /**
//     * 用户取消点赞一个帖子
//     * @param id
//     */
//    @DeleteMapping("/{id}/article")
//    public void unFavoriteArticle(@NonNull @PathVariable Long id){
//        favoriteService.unFavoriteArticle(id);
//    }
//
//    /**
//     * 用户点赞一个评论
//     * @param bo
//     */
//    @PostMapping("/comment")
//    public void favoriteComment(FavoriteBo bo){
//        favoriteService.favoriteComment(bo);
//    }
//
//    /**
//     * 用户取消点赞一个评论
//     * @param id
//     */
//    @DeleteMapping("/{id}/comment")
//    public void unFavoriteComment(@NotNull @PathVariable Long id){
//        favoriteService.unFavoriteComment(id);
//    }
}
