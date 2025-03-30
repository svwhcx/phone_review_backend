package com.svwh.phonereview.aspect;


import com.svwh.phonereview.annotation.RecordInteraction;
import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.service.UserInteractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户交互切面
 * 用于拦截带有@RecordInteraction注解的方法，记录用户交互行为
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class UserInteractionAspect {

    private final UserInteractionService userInteractionService;

    /**
     * 定义切点：拦截所有使用@RecordInteraction注解的方法
     */
    @Pointcut("@annotation(com.svwh.phonereview.annotation.RecordInteraction)")
    public void recordInteractionPointcut() {
    }

    /**
     * 方法成功返回后记录用户交互
     *
     * @param joinPoint 连接点
     * @param result 方法返回值
     */
    @AfterReturning(pointcut = "recordInteractionPointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        try {
            if(UserInfoThreadLocal.get().getUserId()==null){
                return;
            }
            // 获取方法签名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            
            // 获取注解信息
            RecordInteraction annotation = method.getAnnotation(RecordInteraction.class);
            
            // 获取方法参数和参数值的映射
            Map<String, Object> paramValues = getMethodParamValueMap(method, joinPoint.getArgs());
            
            // 从参数映射中获取用户ID和物品ID
            Long userId = UserInfoThreadLocal.get().getUserId();
            Long itemId = getParameterValue(paramValues, annotation.itemIdParam(), Long.class);
            
            // 检查ID值有效性
            if (userId == null || userId <= 0 || itemId == null || itemId <= 0) {
                log.warn("Invalid user or item ID found in method {}: userId={}, itemId={}",
                        method.getName(), userId, itemId);
                return;
            }
            
            // 获取交互类型和物品类型
            String actionType = annotation.actionType();
            String itemType = annotation.itemType();
            
            log.debug("Recording interaction via aspect: userId={}, itemId={}, itemType={}, actionType={}",
                    userId, itemId, itemType, actionType);
            
            // 调用服务记录交互
            boolean recorded = userInteractionService.recordInteraction(userId, itemId, itemType, actionType);
            
            if (!recorded) {
                log.warn("Failed to record interaction: userId={}, itemId={}, itemType={}, actionType={}",
                        userId, itemId, itemType, actionType);
            }
        } catch (Exception e) {
            log.error("Error in interaction recording aspect: {}", e.getMessage());
            // 不抛出异常，避免影响业务逻辑
        }
    }
    
    /**
     * 获取方法参数名和参数值的映射
     *
     * @param method 方法
     * @param args 参数值数组
     * @return 参数名和参数值的映射
     */
    private Map<String, Object> getMethodParamValueMap(Method method, Object[] args) {
        Map<String, Object> paramValueMap = new HashMap<>();
        
        // 使用Spring的DefaultParameterNameDiscoverer获取参数名
        ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                if (i < args.length && parameterNames[i] != null) {
                    paramValueMap.put(parameterNames[i], args[i]);
                }
            }
        } else {
            // 降级处理：如果无法获取参数名，则使用索引作为键
            log.warn("Unable to discover parameter names for method: {}", method.getName());
            for (int i = 0; i < args.length; i++) {
                paramValueMap.put("arg" + i, args[i]);
            }
        }
        
        return paramValueMap;
    }
    
    /**
     * 从参数映射中获取指定名称的参数值，并转换为指定类型
     *
     * @param paramValues 参数名和参数值的映射
     * @param paramName 参数名
     * @param targetType 目标类型
     * @param <T> 泛型类型
     * @return 参数值
     */
    @SuppressWarnings("unchecked")
    private <T> T getParameterValue(Map<String, Object> paramValues, String paramName, Class<T> targetType) {
        Object value = paramValues.get(paramName);
        
        if (value == null) {
            return null;
        }
        
        if (targetType.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        
        // 尝试类型转换
        if (targetType == Long.class && value instanceof Number) {
            return (T) Long.valueOf(((Number) value).longValue());
        }
        
        if (targetType == Long.class && value instanceof String) {
            try {
                return (T) Long.valueOf((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        
        log.warn("Cannot convert parameter value to required type: param={}, value={}, targetType={}",
                paramName, value, targetType.getName());
        
        return null;
    }
}