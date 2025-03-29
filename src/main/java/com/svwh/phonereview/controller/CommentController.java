package com.svwh.phonereview.controller;

import com.svwh.phonereview.domain.bo.CommentBo;
import com.svwh.phonereview.domain.vo.CommentVo;
import com.svwh.phonereview.permission.annotation.CheckPermission;
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
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {


    private final CommentService commentService;


    /**
     * 管理员分页查看评论列表
     * @param bo
     * @return
     */
    @GetMapping("/admin/list")
    @CheckPermission
    public PageVo<CommentVo> listAdmin(CommentBo bo, PageQuery pageQuery){
        return commentService.adminList(bo,pageQuery);
    }


    /**
     * 管理员删除评论
     * @param commentId
     */
    @DeleteMapping("/admin/{commentId}")
    @CheckPermission
    public void delete(@PathVariable Long commentId){
        CommentBo bo = new CommentBo();
        bo.setId(commentId);
        commentService.delete(bo);
    }

    /**
     * 管理员更新评论的状态，例如审核是否通过
     * @param commentId
     * @param bo
     */
    @PutMapping("/admin/{commentId}/status")
    @CheckPermission
    public void updateStatus(@PathVariable Long commentId,@RequestBody CommentBo bo){
        bo.setId(commentId);
        commentService.updateStatus(bo);
    }

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
