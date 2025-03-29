package com.svwh.phonereview.controller;

import com.svwh.phonereview.domain.bo.NotificationBo;
import com.svwh.phonereview.domain.vo.NotificationVo;
import com.svwh.phonereview.query.PageQuery;
import com.svwh.phonereview.query.PageVo;
import com.svwh.phonereview.service.NotificationService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/26 20:27
 */
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    /**
     * 用户分页获取自己的通知列表
     * @param pageQuery 分页参数
     * @return 分页后的通知列表
     */
    @GetMapping
    public PageVo<NotificationVo> list(NotificationBo bo,PageQuery pageQuery){
        return notificationService.list(bo,pageQuery);
    }


    /**
     * 标记一个通知为已读
     * @param notificationId 通知id
     */
    @PutMapping("/{notificationId}/read")
    public void read(@NotNull @PathVariable Long notificationId){
        notificationService.read(notificationId);
    }

    /**
     * 用户标记全部通知为已读
     */
    @PutMapping("/read-all")
    public void readAll(){
        notificationService.readAll();
    }

    /**
     * 获取当前用户未读的通知的数量
     * @return
     */
    @GetMapping("/unread-count")
    public Long unreadCount(){
        return notificationService.unreadCount();
    }

    /**
     * 删除一个通知
     * @param notificationId 通知id
     */
    @DeleteMapping("/{notificationId}")
    public void delete(@NotNull @PathVariable Long notificationId){
        notificationService.delete(notificationId);
    }
}
