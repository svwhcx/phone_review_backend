package com.svwh.phonereview.mapper;

import com.svwh.phonereview.domain.entity.Posts;
import com.svwh.phonereview.domain.vo.PostsVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:48
 */
@Mapper
public interface PostsMapper extends BaseConvertMapper<Posts, PostsVo>{
}
