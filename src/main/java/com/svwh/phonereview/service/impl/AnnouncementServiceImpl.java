package com.svwh.phonereview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.svwh.phonereview.common.constant.AnnouncementConstant;
import com.svwh.phonereview.domain.bo.AnnouncementBo;
import com.svwh.phonereview.domain.entity.Announcement;
import com.svwh.phonereview.domain.vo.AnnouncementVo;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import com.svwh.phonereview.mapper.AnnouncementMapper;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.AnnouncementService;
import com.svwh.phonereview.task.Task;
import com.svwh.phonereview.task.manager.ITaskManager;
import com.svwh.phonereview.utils.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 22:57
 */
@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementMapper announcementMapper;
    private final ITaskManager iTaskManager;

    @Override
    @Transactional
    public void add(AnnouncementBo bo) {
        if (AnnouncementConstant.LATER.equals(bo.getStatus())){
            bo.setPublishTime(LocalDateTime.now());
            bo.setStatus(AnnouncementConstant.PUBLISHED);
        }

        bo.setCreateTime(LocalDateTime.now());
        Announcement announcement = MapstructUtils.convert(bo, Announcement.class);
        announcementMapper.insert(announcement);

        // 公告发布的时候，需要判断是否立即发布、定时发布、不发布等等
        if (AnnouncementConstant.SCHEDULED.equals(bo.getStatus())){
            bo.setStatus(AnnouncementConstant.SCHEDULED);
            // 这里要进行转换
            Task task = new Task();
            task.setAnnouncementId(announcement.getId());
            task.setStatus(AnnouncementConstant.PUBLISHED);
            // 将localDatetime转换成毫秒值
            task.setDelayTime(bo.getPublishTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
            iTaskManager.addTask(task);
        }
        if (bo.getExpireTime() != null){
            Task task = new Task();
            task.setAnnouncementId(announcement.getId());
            task.setStatus(AnnouncementConstant.EXPIRED);
            task.setDelayTime(bo.getExpireTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
            iTaskManager.addTask(task);
        }

    }

    @Override
    public void update(AnnouncementBo bo) {
        // 判断一个公告是否已经发布，如果已经发布，则不能修改
        Announcement announcement = announcementMapper.selectById(bo.getId());
        if (announcement.getStatus() == "published") {
            throw new BusinessException(DefaultErrorCode.NOTIFICATION_PUBLISHED);
        }
        // TODO 处理定时发布的修改
        Announcement announcement1 = MapstructUtils.convert(bo, Announcement.class);
        announcementMapper.updateById(announcement1);
    }

    @Override
    public void delete(Long notificationId) {
        announcementMapper.deleteById(notificationId);
    }

    @Override
    public PageVo<AnnouncementVo> queryPage(AnnouncementBo announcementBo, PageQuery pageQuery) {
        LambdaQueryWrapper<Announcement> aLqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(announcementBo.getKeyword())) {
            aLqw.like(Announcement::getTitle, announcementBo.getKeyword())
                    .or()
                    .like(Announcement::getContent, announcementBo.getKeyword());
        }
        // 要进行排序
        aLqw.orderByDesc(Announcement::getCreateTime);
        return announcementMapper.selectVoPage(pageQuery.buildMybatisPage(), aLqw);
    }

    @Override
    public List<AnnouncementVo> list(AnnouncementBo announcementBo) {
        LambdaQueryWrapper<Announcement> aLqw = Wrappers.lambdaQuery();
        aLqw.eq(Announcement::getStatus, "published")
                .orderByDesc(Announcement::getPublishTime);
        return announcementMapper.selectVoList(aLqw);
    }
}
