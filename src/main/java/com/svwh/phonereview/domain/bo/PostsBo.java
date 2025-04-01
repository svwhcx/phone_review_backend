package com.svwh.phonereview.domain.bo;

import com.svwh.phonereview.domain.entity.Brand;
import com.svwh.phonereview.domain.entity.Posts;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/26 13:49
 */
@Data
@AutoMapper(target = Posts.class)
public class PostsBo {

    /**
     * 主键id
     */
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
    private BigDecimal rating;

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


    /**
     * 搜索的关键字
     */
    private String keyword;

    /**
     * 排序字段，例如：create_time DESC
     */
    private String sortBy;

    private List<String> fileList;
}
