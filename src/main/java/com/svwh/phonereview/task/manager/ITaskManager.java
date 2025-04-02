package com.svwh.phonereview.task.manager;


import com.svwh.phonereview.task.Task;


public interface ITaskManager {

    /**
     * 向任务管理器中添加一个任务
     * @param task
     */
    void addTask(Task task);

}
