package com.svwh.phonereview.domain.bo;

import com.svwh.phonereview.domain.entity.Notification;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/26 20:23
 */
@Data
@AutoMapper(target = Notification.class)
public class NotificationBo {


    /**
     * 主键id
     */
    private Long id;

    private String title;

    /**
     * 通知的类型
     */
    private String type;


    /**
     * 本次通知的类型
     */
    private String content;

    /**
     * 通知的创建时间
     */
    private LocalDateTime createTime;

    /**
     * 当前通知是否已经阅读
     */
    private Boolean isRead;

    /**
     * 用户的id
     */
    private Long userId;

    /**
     * 用户点击查看详情的跳转地址
     */
    private String link;
}
