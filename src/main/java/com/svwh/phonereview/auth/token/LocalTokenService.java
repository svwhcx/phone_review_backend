package com.svwh.phonereview.auth.token;



import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;


@Component
public class LocalTokenService extends BasicTokenService {

    // 用来存储token信息，同时防止出现并发安全问题
    private ConcurrentHashMap<String, Long> tokenBucket = new ConcurrentHashMap();

    @Override
    public boolean refreshTokenIsValid(String refreshToken) {
        Long expireTime = tokenBucket.get(REFRESH_TOKEN_PREFIX + refreshToken);
        if (expireTime == null || expireTime < System.currentTimeMillis()){
            if (expireTime != null){
                tokenBucket.remove(REFRESH_TOKEN_PREFIX + refreshToken);
            }
            return false;
        }
        return true;
    }

    @Override
    protected void saveToken(String token, String prefix,int expire) {
        tokenBucket.put(prefix + token, System.currentTimeMillis() + expire);
    }
}
