package com.svwh.phonereview.auth;

import com.svwh.phonereview.auth.token.AUser;

/**
 * @description 系统的认证服务，主要是为了生成Token以及用户登录服务
 * @Author cxk
 * @Date 2025/3/25 16:00
 */
public interface IAuthService {

    /**
     * 内部登录、系统用户登录成功后调用，主要是为了生成对应的Token
     * @param aUser
     * @return
     */
    String internalLogin(AUser aUser);

    /**
     * 刷新用户的accessToken。
     * @return
     */
    String refreshAccessToken();

}
