package com.svwh.phonereview.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.svwh.phonereview.domain.vo.BrandVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/26 13:47
 */
@Data
@TableName("brand")
@AutoMapper(target = BrandVo.class)
public class Brand {

    /**
     * 主键id
     */
    @TableId
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
