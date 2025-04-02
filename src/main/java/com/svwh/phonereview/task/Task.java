package com.svwh.phonereview.task;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Task  {

    // 本次任务的内容
    private String msg;

    /**
     * 本次推送的任务的状态
     * 如果是过期的任务，那么就是expired，如果是定时发布，那么就是published
     */
    private String status;

    // 延迟到什么时候执行（毫秒级别)
    private Long delayTime;

    // 定时任务发布的公告id
    private Long announcementId;
}
