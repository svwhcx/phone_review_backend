package com.svwh.phonereview.auth;

import com.svwh.phonereview.auth.token.TokenInfo;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 15:15
 */
public class UserInfoThreadLocal {


    private static final ThreadLocal<TokenInfo> USER_INFO = new ThreadLocal<>();

    public static void set(TokenInfo tokenInfo){
        USER_INFO.set(tokenInfo);
    }

    public static TokenInfo get(){
        return USER_INFO.get();
    }

    public static void remove(){
        USER_INFO.remove();
    }

}
