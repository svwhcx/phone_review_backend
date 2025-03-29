package com.svwh.phonereview.controller;

import com.svwh.phonereview.domain.bo.CommentBo;
import com.svwh.phonereview.domain.vo.CommentVo;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.CommentService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/24 22:02
 */
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {


//    private final CommentService commentService;
//
//
//    /**
//     * 分页获取评论列表
//     * @param bo
//     * @param pageVo
//     * @return
//     */
//    @GetMapping("/list")
//    public PageVo<CommentVo> list(CommentBo bo, PageQuery pageVo){
//        return commentService.list(bo,pageVo);
//    }
//
//    /**
//     * 管理员分页查看评论列表
//     * @param bo
//     * @param pageVo
//     * @return
//     */
//    @GetMapping("/list/admin")
//    public PageVo<CommentVo> listAdmin(CommentBo bo, PageQuery pageVo){
//        return commentService.listAdmin(bo,pageVo);
//    }
//
//    /**
//     * 用户发表一个评论
//     * @param bo 评论内容
//     */
//    @PostMapping()
//    public void add(CommentBo bo){
//         commentService.add(bo);
//    }
//
//    /**
//     * 用户删除自己的评论
//     * @param id
//     */
//    @DeleteMapping("/{id}")
//    public void delete(@NotNull @PathVariable Long id){
//        commentService.delete(id);
//    }
//
//    /**
//     * 用户点赞一个评论
//     * @param id 评论id
//     */
//    @PostMapping("/{id}/favorite")
//    public void favorite(@NotNull @PathVariable Long id){
//        commentService.favorite(id);
//    }
//
//    /**
//     * 用户取消点赞一个评论
//     * @param id
//     */
//    @DeleteMapping("/{id}/favorite")
//    public void unFavorite(@NotNull @PathVariable Long id){
//        commentService.unfavorite(id);
//    }
}
