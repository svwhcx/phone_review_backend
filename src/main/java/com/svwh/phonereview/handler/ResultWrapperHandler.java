package com.svwh.phonereview.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svwh.phonereview.common.ResultVo;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.utils.SpringContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * @description Http请求结果返回统一处理
 * @Author cxk
 * @Date 2025/3/14 14:30
 */
@RestControllerAdvice
public class ResultWrapperHandler implements ResponseBodyAdvice<Object> {

    private ConvertFilter convertFilter;

    private static final Class<?>[] INTERNAL_FILTER_CLASS = new Class[]{ResultVo.class};

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        String typeClass = returnType.getGenericParameterType().getTypeName();
        // 先过滤内部的
        for (Class<?> internalFilterClass : INTERNAL_FILTER_CLASS) {
            if (internalFilterClass.getName().equals(typeClass)) {
                return false;
            }
        }
        // 再过滤外部的。
        ConvertFilter filters = SpringContextHolder.getBean(ConvertFilter.class);
        if (filters == null) {
            return true;
        }
        Class<?>[] classes = filters.filterClasses();
        if (classes == null || classes.length == 0) {
            return true;
        }
        for (Class<?> aClass : classes) {
            if (aClass.getName().equals(typeClass)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return null;
        }
        if (body instanceof ResultVo) {
            return body;
        }
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在Result里后，再转换为json字符串响应给前端
                return objectMapper.writeValueAsString(new ResultVo<>(body));
            } catch (JsonProcessingException e) {
                throw new BusinessException(500);
            }
        }
        return new ResultVo<>(body);
    }
}
