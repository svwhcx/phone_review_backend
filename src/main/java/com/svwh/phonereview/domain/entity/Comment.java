package com.svwh.phonereview.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.svwh.phonereview.domain.vo.CommentVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description 用户堆评测的评论
 * @Author cxk
 * @Date 2025/3/24 20:40
 */
@Data
@TableName("comment")
@AutoMapper(target = CommentVo.class)
public class Comment {

    @TableId
    private Long id;

    private Long userId;

    private Long postId;

    private Long parentId;

    private LocalDateTime createTime;

    private String content;

    /**
     * 评论的点赞量
     */
    private Integer favorite;

    private Boolean isDelete;

    @TableField("`status`")
    private String status;

}
