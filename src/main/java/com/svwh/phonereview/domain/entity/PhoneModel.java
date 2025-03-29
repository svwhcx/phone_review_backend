package com.svwh.phonereview.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.svwh.phonereview.domain.vo.PhoneModelVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description 手机型号
 * @Author cxk
 * @Date 2025/3/24 20:38
 */
@Data
@TableName("phone_model")
@AutoMapper(target = PhoneModelVo.class)
public class PhoneModel {

    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 手机型号
     */
    private String name;

    /**
     * 手机品牌
     */
    private Long brandId;

    /**
     * 手机型号的图片（暂时不会用到）
     */
    private String image;

    /**
     * 描述
     */
    private String description;

    /**
     * 手机价格
     */
    private BigDecimal price;

    /**
     * 受欢迎的程度
     */
    private Integer popularity;

    /**
     * 手机型号的状态
     */
    private Boolean enable;

    /**
     * 手机的发布时间
     */
    private LocalDateTime releaseDate;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
