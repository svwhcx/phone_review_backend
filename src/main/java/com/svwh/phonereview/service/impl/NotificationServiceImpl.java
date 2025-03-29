package com.svwh.phonereview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.domain.bo.NotificationBo;
import com.svwh.phonereview.domain.entity.Notification;
import com.svwh.phonereview.domain.vo.NotificationVo;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import com.svwh.phonereview.mapper.NotificationMapper;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.NotificationService;
import com.svwh.phonereview.utils.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/26 20:30
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;


    @Override
    public void add(NotificationBo bo) {
        // 添加一个通知
        bo.setCreateTime(LocalDateTime.now());
        bo.setIsRead(false);
        notificationMapper.insert(MapstructUtils.convert(bo, Notification.class));
    }

    @Override
    public PageVo<NotificationVo> list(NotificationBo bo,PageQuery pageQuery) {
        Long userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<Notification> nLqw = Wrappers.lambdaQuery();
        nLqw.eq(Notification::getUserId, userId)
                .orderByDesc(Notification::getCreateTime);
        return notificationMapper.selectVoPage(pageQuery.buildMybatisPage(), nLqw);
    }

    @Override
    public void read(Long notificationId) {
        Long userId = UserInfoThreadLocal.get().getUserId();
        valid(notificationId);
        LambdaUpdateWrapper<Notification> nLqw = Wrappers.lambdaUpdate();
        nLqw.eq(Notification::getUserId, userId)
                .eq(Notification::getId, notificationId)
                .set(Notification::getIsRead, true);
        notificationMapper.update(nLqw);
    }

    @Override
    public void delete(Long notificationId) {
        valid(notificationId);
        notificationMapper.deleteById(notificationId);
    }

    @Override
    public void readAll() {
        Long userId = UserInfoThreadLocal.get().getUserId();
        LambdaUpdateWrapper<Notification> nLqw = Wrappers.lambdaUpdate();
        nLqw.eq(Notification::getUserId, userId)
                .set(Notification::getIsRead, true);
        notificationMapper.update(nLqw);
    }

    @Override
    public Long unreadCount() {
        Long userId = UserInfoThreadLocal.get().getUserId();
        LambdaQueryWrapper<Notification> nLqw = Wrappers.lambdaQuery();
        nLqw.eq(Notification::getUserId, userId)
                .eq(Notification::getIsRead, false);
        return notificationMapper.selectCount(nLqw);
    }


    private void valid(Long notificationId){
        Long userId = UserInfoThreadLocal.get().getUserId();
        Notification notification = notificationMapper.selectById(notificationId);
        if (notification == null || !notification.getUserId().equals(userId)){
            throw new BusinessException(DefaultErrorCode.ILLEGAL_OPERATION);
        }
    }
}
