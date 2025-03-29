package com.svwh.phonereview.mapper;

import com.svwh.phonereview.domain.entity.Favorite;
import com.svwh.phonereview.domain.vo.FavoriteVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:49
 */
@Mapper
public interface FavoriteMapper extends BaseConvertMapper<Favorite, FavoriteVo>{
}
