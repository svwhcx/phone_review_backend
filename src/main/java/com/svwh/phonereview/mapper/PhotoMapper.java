package com.svwh.phonereview.mapper;

import com.svwh.phonereview.domain.entity.Photo;
import com.svwh.phonereview.domain.vo.PhotoVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/29 17:57
 */
@Mapper
public interface PhotoMapper extends BaseConvertMapper<Photo, PhotoVo>{
}
