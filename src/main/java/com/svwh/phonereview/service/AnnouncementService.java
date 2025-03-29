package com.svwh.phonereview.service;

import com.svwh.phonereview.domain.bo.AnnouncementBo;
import com.svwh.phonereview.domain.vo.AnnouncementVo;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:56
 */
public interface AnnouncementService {

    /**
     * 管理员添加一个公告
     * @param bo
     */
    void add(AnnouncementBo bo);

    /**
     * 管理员更新一个公告数据
     * @param bo
     */
    void update(AnnouncementBo bo);

    void delete(Long notificationId);

    /**
     * 管理员分页查询
     * @param announcementBo
     * @param pageQuery
     * @return
     */
    PageVo<AnnouncementVo> queryPage(AnnouncementBo announcementBo, PageQuery pageQuery);

    /**
     * 用户获取全部的公告列表
     * @param announcementBo
     * @return
     */
    List<AnnouncementVo> list(AnnouncementBo announcementBo);
}
