package com.svwh.phonereview.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.svwh.phonereview.common.validation.AddGroup;
import com.svwh.phonereview.common.validation.EditGroup;
import com.svwh.phonereview.domain.entity.Announcement;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @description 公告
 * @Author cxk
 * @Date 2025/3/24 20:35
 */
@Data
@AutoMapper(target = Announcement.class)
public class AnnouncementBo {

    /**
     * 主键id
     */
    private Long id;


    /**
     * 标题
     */
    private String title;

    /**
     * 公告的内容
     */
    private String content;

    /**
     * 优先级
     */
    private String priority;

    /**
     * 状态
     */
    private String status;

    /**
     * 公告发布的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

    /**
     * 公告的过期时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;

    /**
     * 公告定时发布的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scheduleTime;


    /**
     * 创建的时间
     */
    private LocalDateTime createTime;

    /**
     * 更新的时间
     */
    private LocalDateTime updateTime;

    /**
     * 关键词（用于管理员查看列表时过滤）
     */
    private String keyword;
}
