package com.svwh.phonereview.task.manager;


import com.svwh.phonereview.task.Task;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/30 16:34
 */
public interface ITaskManager {

    /**
     * 向任务管理器中添加一个任务
     * @param task
     */
    void addTask(Task task);

}
