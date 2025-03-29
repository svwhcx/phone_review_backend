package com.svwh.phonereview.service;

import com.svwh.phonereview.domain.bo.CommentBo;
import com.svwh.phonereview.domain.entity.Comment;
import com.svwh.phonereview.domain.vo.CommentVo;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:56
 */
public interface CommentService {

    /**
     * 分页获取一个评测的评论列表
     * @param postId
     * @param pageQuery
     * @return
     */
    PageVo<CommentVo> listComments(Long postId, PageQuery pageQuery);

    /**
     * 用户发表一个评论
     * @param bo 评论
     */
    void add(CommentBo bo);

    /**
     * 用户删除一个评论
     * @param bo 测评id和评论id
     */
    void delete(CommentBo bo);

    /**
     * 获取用户的评论列表
     * @param pageQuery
     * @return
     */
    PageVo<CommentVo> queryUserComments(PageQuery pageQuery);

    /**
     * 管理员分页查看评论列表
     * @param bo
     * @param pageQuery
     * @return
     */
    PageVo<CommentVo> adminList(CommentBo bo, PageQuery pageQuery);

    /**
     * 更新评论的状态。
     * @param bo
     */
    void updateStatus(CommentBo bo);
}
