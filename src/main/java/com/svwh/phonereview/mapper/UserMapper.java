package com.svwh.phonereview.mapper;

import com.svwh.phonereview.domain.entity.User;
import com.svwh.phonereview.domain.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 17:27
 */
@Mapper
public interface UserMapper extends BaseConvertMapper<User, UserVo>{
}
