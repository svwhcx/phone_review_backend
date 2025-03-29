package com.svwh.phonereview.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.svwh.phonereview.domain.entity.Brand;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/26 13:48
 */
@Data
@AutoMapper(target = Brand.class)
public class BrandVo  {



    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 英文名称
     */
    private String nameEn;

    /**
     * 品牌logo
     */
    private String logo;

    /**
     * 品牌的国家
     */
    private String country;

    /**
     * 品牌的描述
     */
    private String description;

    /**
     * 品牌的状态
     */
    private Integer status;

    /**
     * 品牌的创建时间
     */
    private LocalDateTime createTime;
}
