package com.svwh.phonereview.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.svwh.phonereview.common.validation.AddGroup;
import com.svwh.phonereview.domain.entity.Comment;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description 用户堆评测的评论
 * @Author cxk
 * @Date 2025/3/24 20:40
 */
@Data
@AutoMapper(target = Comment.class)
public class CommentBo {

    private Long id;

    private Long userId;

    @NotNull(message = "帖子不能为空",groups = {AddGroup.class})
    private Long postId;

    private Long parentId;

    private Long rootId;

    private LocalDateTime createTime;

    @NotBlank(message = "内容不能为空",groups = {AddGroup.class})
    private String content;

    private Boolean isDelete;

    @TableField(value = "`status`")
    private String status;


    /**
     * 关键词搜索.
     */
    private String keyword;

    /**
     * 拒绝原因
     */
    private String reason;

    private Boolean notifyUser;
}
