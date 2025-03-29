package com.svwh.phonereview.handler;

import org.springframework.stereotype.Component;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/14 16:34
 */
@Component
public class LocalConvetFilter implements ConvertFilter{
    @Override
    public Class<?>[] filterClasses() {
        return new Class[]{};
    }
}
