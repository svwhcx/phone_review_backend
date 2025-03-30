package com.svwh.phonereview.auth.aop;

import com.svwh.phonereview.auth.token.TokenInfo;
import com.svwh.phonereview.auth.token.TokenUtil;
import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.auth.annotation.IgnoreAuth;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 16:21
 */
@Aspect
@Component
@Order(1)
public class AuthAspect {



    @Pointcut("within(com.svwh.phonereview..*) && (@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.GetMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PutMapping))")
    public void pointCut() {}

    @Before(value = "pointCut()")
    public void authentication(JoinPoint joinpoint){
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        Method method = signature.getMethod();
        // 如果一个接口需要忽略用户认证，则不做验证

        // 从request中获取token
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = Objects.requireNonNull(requestAttributes).getRequest();
        String accessToken = servletRequest.getHeader("Authorization");
        // 解析token并验证时间
        try {
            TokenInfo tokenInfo = TokenUtil.getTokenInfo(accessToken);
            // 防止拿refreshToken来充当accessToken.
            if (tokenInfo.getUserId() == null){
                throw new BusinessException(DefaultErrorCode.AUTH_FAIL);
            }
            UserInfoThreadLocal.set(tokenInfo);
        }catch (Exception e){
            if(method.isAnnotationPresent(IgnoreAuth.class)){
                return;
            }
            throw new BusinessException(DefaultErrorCode.AUTH_FAIL);
        }
    }

    @After(value = "pointCut()")
    public void after(){
        // 清除ThreadLocal，防止OOM
        UserInfoThreadLocal.remove();
    }
}
