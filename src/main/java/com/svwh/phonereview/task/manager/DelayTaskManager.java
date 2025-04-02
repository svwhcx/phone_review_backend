package com.svwh.phonereview.task.manager;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.svwh.phonereview.common.constant.AnnouncementConstant;
import com.svwh.phonereview.domain.entity.Announcement;
import com.svwh.phonereview.mapper.AnnouncementMapper;
import com.svwh.phonereview.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


@Component
public class DelayTaskManager implements ITaskManager {

    private final AnnouncementMapper announcementMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(DelayTaskManager.class);

    // 这里应该是一个延迟队列
    DelayQueue<WorkerTask> workQueue = new DelayQueue<>();

    @Autowired
    public DelayTaskManager(AnnouncementMapper announcementMapper) {
        this.announcementMapper = announcementMapper;
        new Thread(new TaskWorker(workQueue)).start();
    }

    @Override
    public void addTask(Task task) {
        workQueue.offer(new WorkerTask(task));
    }


    class WorkerTask implements Delayed {

        private Task task;

        public WorkerTask(Task task) {
            this.task = task;
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)){
                return 1;
            }
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)){
                return -1;
            }
            return 0;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diffTime = task.getDelayTime() - System.currentTimeMillis();
            return unit.convert(diffTime, TimeUnit.MILLISECONDS);
        }
    }


    class TaskWorker implements Runnable {

        private DelayQueue<WorkerTask> workQueue;

        public TaskWorker(DelayQueue<WorkerTask> workQueue) {
            this.workQueue = workQueue;
        }

        /**
         * 专门处理延时任务的线程
         */
        @Override
        public void run() {
            // 这里开一线程循环处理事件
            while (true) {
                try {
                    WorkerTask workerTask = workQueue.take();
                    Task task = workerTask.task;
                    // 这里提前进行查询，判断当前的公告是否已经取消或者取消定时发布
                    Announcement announcement = announcementMapper.selectById(task.getAnnouncementId());
                    if (announcement == null){
                        continue;
                    }
                    LambdaUpdateWrapper<Announcement> aLuw = Wrappers.lambdaUpdate();
                    aLuw.eq(Announcement::getId, task.getAnnouncementId());
                    // 判断是否是过期的任务
                    if (AnnouncementConstant.EXPIRED.equals(task.getStatus()) && AnnouncementConstant.PUBLISHED.equals(announcement.getStatus())){
                        // 过期任务需要修改状态为已过期
                        aLuw.set(Announcement::getStatus, AnnouncementConstant.EXPIRED)
                                .set(Announcement::getUpdateTime, System.currentTimeMillis())
                                .set(Announcement::getExpireTime, LocalDateTime.now());
                    }else if (AnnouncementConstant.SCHEDULED.equals(announcement.getStatus())){
                        aLuw.set(Announcement::getStatus, AnnouncementConstant.PUBLISHED)
                                .set(Announcement::getPublishTime, LocalDateTime.now());
                    }else{
                        continue;
                    }
                    announcementMapper.update(aLuw);
                    LOGGER.info("已经执行一条定时任务："+task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
