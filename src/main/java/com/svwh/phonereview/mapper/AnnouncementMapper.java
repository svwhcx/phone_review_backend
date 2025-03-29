package com.svwh.phonereview.mapper;

import com.svwh.phonereview.domain.entity.Announcement;
import com.svwh.phonereview.domain.vo.AnnouncementVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:48
 */
@Mapper
public interface AnnouncementMapper extends BaseConvertMapper<Announcement, AnnouncementVo>{
}
