package com.svwh.phonereview.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.svwh.phonereview.domain.vo.NotificationVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description 通知的
 * @Author cxk
 * @Date 2025/3/26 20:14
 */
@Data
@TableName("notification")
@AutoMapper(target = NotificationVo.class)
public class Notification {

    @TableId
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
     * 前端路由跳转地址
     */
    private String link;

}
