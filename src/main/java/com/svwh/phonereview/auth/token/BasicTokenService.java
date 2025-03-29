package com.svwh.phonereview.auth.token;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/24 16:26
 */
public abstract class BasicTokenService implements ITokenService {


    @Override
    public String genAccessToken(AUser aUser) {
        // 先对基本的信息进行构建，然后添加到tokenBucket中
        TokenInfo tokenInfo = new TokenInfo.Builder()
                .username(aUser.getUsername())
                .userId(aUser.getId())
                .role(aUser.getRole())
                .build();
        String token = TokenBuilder.buildToken(tokenInfo,ACCESS_TOKEN_EXPIRED_TIME);
        // 目前不保存accessToken。
//        saveToken(token,ACCESS_TOKEN_PREFIX);
        return token;
    }

    @Override
    public String genRefreshToken() {
        TokenInfo tokenInfo = new TokenInfo.Builder()
                .build();
        String token = TokenBuilder.buildToken(tokenInfo,REFRESH_TOKEN_EXPIRED_TIME);
        saveToken(token,REFRESH_TOKEN_PREFIX,REFRESH_TOKEN_EXPIRED_TIME);
        return token;
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        // 1. 先验证refreshToken是否有效
        if (refreshTokenIsValid(refreshToken)) {
            // 2. 验证通过，生成新的accessToken
//            TokenInfo tokenInfo = TokenUtil.getTokenInfo(refreshToken);
//            String accessToken = TokenBuilder.buildToken(tokenInfo,ACCESS_TOKEN_EXPIRED_TIME);
//            // 3. 刷新token
//            refreshToken(refreshToken);
            return null;
        }
        return null;
    }

    protected abstract void saveToken(String token, String prefix, int expire);
}
