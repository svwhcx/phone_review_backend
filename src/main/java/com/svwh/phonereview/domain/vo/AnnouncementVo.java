package com.svwh.phonereview.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.svwh.phonereview.domain.entity.Announcement;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 公告
 * @Author cxk
 * @Date 2025/3/24 20:35
 */
@Data
@AutoMapper(target = Announcement.class)
public class AnnouncementVo  {



    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
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
    private LocalDateTime publishTime;

    /**
     * 公告的过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 公告定时发布的时间
     */
    private LocalDateTime scheduleTime;


    /**
     * 创建的时间
     */
    private LocalDateTime createTime;

    /**
     * 更新的时间
     */
    private LocalDateTime updateTime;
}
