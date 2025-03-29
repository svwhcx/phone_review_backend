package com.svwh.phonereview.domain.bo;

import com.svwh.phonereview.common.validation.AddGroup;
import com.svwh.phonereview.common.validation.EditGroup;
import com.svwh.phonereview.domain.entity.PhoneModel;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description 手机型号
 * @Author cxk
 * @Date 2025/3/24 20:38
 */
@Data
@AutoMapper(target = PhoneModel.class)
public class PhoneModelBo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 手机型号
     */
    @NotNull(groups = {AddGroup.class, EditGroup.class})
    private String model;

    /**
     * 手机品牌
     */
    @NotNull(groups = {AddGroup.class, EditGroup.class})
    private String brand;

    /**
     * 品牌id
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
     * 手机的发布时间
     */
    private LocalDateTime releaseDate;

    /**
     * 受欢迎的程度
     */
    private Integer popularity;

    /**
     * 手机型号的状态
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 关键词过滤（用于管理员分页获取列表）
     */
    private String keyword;
}
