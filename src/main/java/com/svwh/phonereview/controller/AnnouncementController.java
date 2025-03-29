package com.svwh.phonereview.controller;

import com.svwh.phonereview.auth.annotation.IgnoreAuth;
import com.svwh.phonereview.domain.bo.AnnouncementBo;
import com.svwh.phonereview.domain.vo.AnnouncementVo;
import com.svwh.phonereview.permission.annotation.CheckPermission;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description 公告管理服务
 * @Author cxk
 * @Date 2025/3/24 21:46
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    /**
     * 用户获取全部的公告列表
     * @param announcementBo
     * @return
     */
    @GetMapping("/list")
    @IgnoreAuth
    public List<AnnouncementVo> list(AnnouncementBo announcementBo){
        return announcementService.list(announcementBo);
    }

    /**
     * 管理员添加一个公告
     * @param bo
     */
    @PostMapping("/admin")
    @CheckPermission
    public void add(@RequestBody AnnouncementBo bo){
        announcementService.add(bo);
    }

    /**
     * 管理员分页查询公告，
     * @param announcementBo
     * @param pageQuery
     * @return
     */
    @GetMapping("/admin/list")
    public PageVo<AnnouncementVo> getAnnouncementPage(AnnouncementBo announcementBo, PageQuery pageQuery){
        return announcementService.queryPage(announcementBo,pageQuery);
    }

    /**
     * 管理员编辑一个公告
     * @param notificationId
     * @param bo
     */
    @PutMapping("/admin/{notificationId}")
    public void update(@PathVariable Long notificationId,@RequestBody AnnouncementBo bo){
        bo.setId(notificationId);
        announcementService.update(bo);
    }

    /**
     * 管理员删除一个公告
     * @param notificationId
     */
    @DeleteMapping("/admin/{notificationId}")
    @CheckPermission
    public void delete(@PathVariable Long notificationId){
        announcementService.delete(notificationId);
    }
}
