package com.svwh.phonereview.service;

import com.svwh.phonereview.domain.bo.NotificationBo;
import com.svwh.phonereview.domain.vo.NotificationVo;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/26 20:30
 */
public interface NotificationService {

    /**
     * 添加一个通知
     * @param bo
     */
    void add(NotificationBo bo);

    /**
     * 分页获取通知列表
     * @param pageQuery 分页参数
     * @return
     */
    PageVo<NotificationVo> list(NotificationBo bo,PageQuery pageQuery);

    /**
     * 用户标记一个通知为已读
     * @param notificationId 通知id
     */
    void read(Long notificationId);

    /**
     * 删除一个通知
     * @param notificationId 通知id
     */
    void delete(Long notificationId);

    void readAll();


    /**
     * 用户获取未读的通知数量
     * @return
     */
    Long unreadCount();


}
