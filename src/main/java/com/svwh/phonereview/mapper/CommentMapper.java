package com.svwh.phonereview.mapper;

import com.svwh.phonereview.domain.entity.Comment;
import com.svwh.phonereview.domain.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:46
 */
@Mapper
public interface CommentMapper extends BaseConvertMapper<Comment, CommentVo>{
}
