package com.svwh.phonereview.mapper;

import com.svwh.phonereview.domain.entity.PhoneModel;
import com.svwh.phonereview.domain.vo.PhoneModelVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:50
 */
@Mapper
public interface PhoneModelMapper extends BaseConvertMapper<PhoneModel, PhoneModelVo>{
}
