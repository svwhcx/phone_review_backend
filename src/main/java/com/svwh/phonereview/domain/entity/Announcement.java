package com.svwh.phonereview.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.svwh.phonereview.domain.vo.AnnouncementVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description 公告
 * @Author cxk
 * @Date 2025/3/24 20:35
 */
@Data
@TableName("announcement")
@AutoMapper(target = AnnouncementVo.class)
public class Announcement {

    /**
     * 主键id
     */
    @TableId
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

//    /**
//     * 是否开启
//     */
//    private Boolean enable;

//    /**
//     * 排序
//     */
//    private Integer order;
}
