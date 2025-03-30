package com.svwh.phonereview.verifycode.cache;

import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import com.svwh.phonereview.verifycode.VerifyCodeRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/8 11:37
 */
@Primary
@Component("memoryVerifyCodeCache")
public class MemoryVerifyCodeCache implements IVerifyCodeCache {


    // 直接在这里进行模拟验证码池
    private final ConcurrentHashMap<String, Code> CODE_POOL = new ConcurrentHashMap<>();


    // 验证码有效时长
    private static final int EXPIRE_TIME = 30 * 60 * 1000;

    @Override
    public void saveCode(VerifyCodeRequest verifyCodeRequest) {
        CODE_POOL.put(verifyCodeRequest.getAddr(), new Code(verifyCodeRequest.getCode(), System.currentTimeMillis() + EXPIRE_TIME));
    }


    /**
     * 校验验证码是否正确
     *
     * @param verifyCodeRequest 验证请求
     * @return
     */
    @Override
    public boolean checkCode(VerifyCodeRequest verifyCodeRequest) {
        Code code = CODE_POOL.get(verifyCodeRequest.getAddr());
        if (code == null) {
            return false;
        }
        if (System.currentTimeMillis() > code.getExpireTime()) {
            throw new BusinessException(DefaultErrorCode.VERIFY_CODE_EXPIRED);
        }
        return code.getCode().equals(verifyCodeRequest.getCode());
    }


    /**
     * 验证一个验证码，并设置是否需要移除
     *
     * @param verifyCodeRequest
     * @param isRemoveCode
     * @return
     */

    @Override
    public boolean checkCode(VerifyCodeRequest verifyCodeRequest, boolean isRemoveCode) {
        Code code = CODE_POOL.get(verifyCodeRequest.getAddr());
        if (code == null || System.currentTimeMillis() > code.getExpireTime()) {
            return false;
        }
        if (isRemoveCode) {
            setCodeUsed(verifyCodeRequest);
        }
        return true;
    }


    /**
     * 标记一个验证码失效
     *
     * @param verifyCodeRequest 验证码请求操作
     */
    @Override
    public void setCodeUsed(VerifyCodeRequest verifyCodeRequest) {
        // 标记失效就是直接移除
        CODE_POOL.remove(verifyCodeRequest.getAddr());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class Code {

        private String code;
        private Long expireTime;

        public Code(String code, Long expireTime) {
            this.code = code;
            this.expireTime = expireTime;
        }
    }
}
