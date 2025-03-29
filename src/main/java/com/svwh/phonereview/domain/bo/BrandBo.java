package com.svwh.phonereview.domain.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.svwh.phonereview.domain.entity.Brand;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/27 13:49
 */
@Data
@AutoMapper(target = Brand.class)
public class BrandBo {


    /**
     * 主键id
     */

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
