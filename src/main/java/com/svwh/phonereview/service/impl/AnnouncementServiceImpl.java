package com.svwh.phonereview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.svwh.phonereview.domain.bo.AnnouncementBo;
import com.svwh.phonereview.domain.entity.Announcement;
import com.svwh.phonereview.domain.vo.AnnouncementVo;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import com.svwh.phonereview.mapper.AnnouncementMapper;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.AnnouncementService;
import com.svwh.phonereview.utils.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public void add(AnnouncementBo bo) {
        // 公告发布的时候，需要判断是否立即发布、定时发布、不发布等等
        if (bo.getStatus() == "published") {
            bo.setCreateTime(LocalDateTime.now());
        }
        if (bo.getStatus() == "scheduled") {
            // 定时发布
            // TODO
        }
        Announcement announcement = MapstructUtils.convert(bo, Announcement.class);
        announcementMapper.insert(announcement);
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
