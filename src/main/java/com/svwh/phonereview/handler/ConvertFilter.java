package com.svwh.phonereview.handler;

import org.springframework.stereotype.Component;


@Component
public interface ConvertFilter {

    /**
     * 获取哪些类要进行过滤的操作
     * @return 目标过滤的Class数组
     */
    Class<?>[] filterClasses();

}
