package com.svwh.phonereview.mapper;

import com.svwh.phonereview.domain.entity.Notification;
import com.svwh.phonereview.domain.vo.NotificationVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/26 20:27
 */
@Mapper
public interface NotificationMapper extends BaseConvertMapper<Notification, NotificationVo> {
}
