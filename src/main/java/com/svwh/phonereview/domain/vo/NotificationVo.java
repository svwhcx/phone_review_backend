package com.svwh.phonereview.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.svwh.phonereview.domain.entity.Notification;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/26 20:25
 */
@Data
@AutoMapper(target = Notification.class)
public class NotificationVo  {


    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 通知的类型
     */
    private String type;

    private String title;


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
     * 用户点击查看详情后的跳转地址
     */
    private String link;
}
