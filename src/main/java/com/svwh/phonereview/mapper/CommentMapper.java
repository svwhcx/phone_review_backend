package com.svwh.phonereview.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.svwh.phonereview.domain.entity.Comment;
import com.svwh.phonereview.domain.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:46
 */
@Mapper
public interface CommentMapper extends BaseConvertMapper<Comment, CommentVo>{


    /**
     * 管理员分页查询 用户的评论，并且根据条件进行过滤
     * @return
     */
    @Select("<script>select c.id as id,c.create_time as creatTime,c.content as content,c.prent_id as parentId,p.name as postTitle,c.status as status from comment as c join posts as p on c.post_id = p.id join user as u on u.id = c.user_id ${ew.customSqlSegment}</script>")
    IPage<CommentVo> adminQueryList(Page<Comment> page, @Param(Constants.WRAPPER)QueryWrapper<Comment> queryWrapper);
}



