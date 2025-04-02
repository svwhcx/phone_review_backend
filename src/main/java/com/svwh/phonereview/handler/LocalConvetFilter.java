package com.svwh.phonereview.handler;

import org.springframework.stereotype.Component;


@Component
public class LocalConvetFilter implements ConvertFilter{
    @Override
    public Class<?>[] filterClasses() {
        return new Class[]{};
    }
}
