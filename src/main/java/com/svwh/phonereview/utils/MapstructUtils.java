package com.svwh.phonereview.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import io.github.linpeilie.Converter;
import lombok.Generated;

import java.util.List;
import java.util.Map;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/24 20:55
 */
public class MapstructUtils {
    private static final Converter CONVERTER = (Converter)SpringContextHolder.getBean(Converter.class);

    public static <T, V> V convert(T source, Class<V> desc) {
        if (ObjectUtil.isNull(source)) {
            return null;
        } else {
            return ObjectUtil.isNull(desc) ? null : CONVERTER.convert(source, desc);
        }
    }

    public static <T, V> V convert(T source, V desc) {
        if (ObjectUtil.isNull(source)) {
            return null;
        } else {
            return ObjectUtil.isNull(desc) ? null : CONVERTER.convert(source, desc);
        }
    }

    public static <T, V> List<V> convert(List<T> sourceList, Class<V> desc) {
        if (ObjectUtil.isNull(sourceList)) {
            return null;
        } else {
            return (List)(CollUtil.isEmpty(sourceList) ? CollUtil.newArrayList(new Object[0]) : CONVERTER.convert(sourceList, desc));
        }
    }

    public static <T> T convert(Map<String, Object> map, Class<T> beanClass) {
        if (MapUtil.isEmpty(map)) {
            return null;
        } else {
            return ObjectUtil.isNull(beanClass) ? null : CONVERTER.convert(map, beanClass);
        }
    }

    @Generated
    private MapstructUtils() {
    }
}

