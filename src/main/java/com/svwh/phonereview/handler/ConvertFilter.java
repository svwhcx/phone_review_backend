package com.svwh.phonereview.handler;

import org.springframework.stereotype.Component;

/**
 * @description 结果转化的屏蔽器，有的时候无需将结果转化为<p>
 *      {code:1,data:null,msg:null}的形式，那么用户可以直接实现这个接口
 * @Author cxk
 * @Date 2025/3/14 15:06
 */
@Component
public interface ConvertFilter {

    /**
     * 获取哪些类要进行过滤的操作
     * @return 目标过滤的Class数组
     */
    Class<?>[] filterClasses();

}
