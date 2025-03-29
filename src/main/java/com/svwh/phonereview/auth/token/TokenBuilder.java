package com.svwh.phonereview.auth.token;



import java.util.HashMap;

/**
 * @description 构建Token
 * @Author cxk
 * @Date 2022/4/30 17:39
 */
public class TokenBuilder {


    public static String buildToken(TokenInfo tokenInfo, int millisecond){
        HashMap<String,Object> tokenData = new HashMap<>();
        tokenData.put("userId", tokenInfo.getUserId());
        tokenData.put("username", tokenInfo.getUsername());
        tokenData.put("role", tokenInfo.getRole());
        return TokenUtil.genToken(tokenData,millisecond);
    }

}
