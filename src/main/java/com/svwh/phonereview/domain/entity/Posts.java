package com.svwh.phonereview.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.svwh.phonereview.domain.vo.PostsVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description 系统的帖子，（文章）
 * @Author cxk
 * @Date 2025/3/24 20:47
 */
@Data
@TableName("posts")
@AutoMapper(target = PostsVo.class)
public class Posts {

    @TableId
    private Long id;

    /**
     * 用户的主键id
     */
    private Long userId;

    /**
     * 评测的标题
     */
    private String title;

    /**
     * 评测的内容
     */
    private String content;

    /**
     * 评测的手机型号对应的id
     */
    private Long phoneModelId;

    /**
     * 手机品牌id
     */
    private Long brandId;

    /**
     * 对应的图片列表(,分割)
     */
    private String images;

    /**
     * 外观评分
     */
    private Integer appearanceRating;
    /**
     * 屏幕评分
     */
    private Integer screenRating;

    /**
     * 性能评分
     */
    private Integer performanceRating;

    /**
     * 相机评分
     */
    private Integer cameraRating;

    /**
     * 电池评分
     */
    private Integer batteryRating;

    /**
     * 系统评分
     */
    private Integer systemRating;

    /**
     * 综合评分
     */
    private Integer rating;

    /**
     * 帖子的浏览量
     */
    private Long views;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;


    /**
     * 文章是否启用
     */
    private Boolean enable;

    private Integer status;
}
