package com.svwh.phonereview.mapper;

import com.svwh.phonereview.domain.entity.Brand;
import com.svwh.phonereview.domain.vo.BrandVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/26 13:49
 */
@Mapper
public interface BrandMapper extends BaseConvertMapper<Brand, BrandVo> {
}
