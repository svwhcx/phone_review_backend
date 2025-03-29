package com.svwh.phonereview.permission.aop;

import com.svwh.phonereview.auth.token.TokenInfo;
import com.svwh.phonereview.auth.UserInfoThreadLocal;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import com.svwh.phonereview.permission.RoleEnum;
import com.svwh.phonereview.permission.annotation.CheckPermission;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 13:42
 */
@Aspect
@Component
@Order(2)
public class CheckPermissionAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(CheckPermissionAspect.class);

    @Pointcut("@annotation(com.svwh.phonereview.permission.annotation.CheckPermission)")
    private void pointcut(){

    }

    @Before(value = "pointcut() && @annotation(checkPermission)")
    public void checkPermission(CheckPermission checkPermission){
        TokenInfo tokenInfo = UserInfoThreadLocal.get();
        LOGGER.info("token is {}", tokenInfo);
        // 鉴权
        checkRole(tokenInfo,checkPermission);
    }


    private void checkRole(TokenInfo tokenInfo, CheckPermission checkPermission){
        // 判断。
        for (RoleEnum roleEnum : checkPermission.value()) {
            if (roleEnum.getDesc().equals(tokenInfo.getRole())){
                return;
            }
        }
        throw new BusinessException(DefaultErrorCode.NO_PERMISSION);
    }
}
