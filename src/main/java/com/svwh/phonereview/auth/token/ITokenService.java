package com.svwh.phonereview.auth.token;


/**
 * @description Token管理、根据用户信息分发、验证Token是否有效、强制用户下线操作。
 * @Author cxk
 * @Date 2025/2/24 15:50
 */
public interface ITokenService {

    /**
     * RefreshToken的有效时间
     */
    int REFRESH_TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 24 * 15;

    /**
     * AccessToken的有效时间
     */
    int ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 15;

    String ACCESS_TOKEN_PREFIX = "access_token:";

    /**
     * refreshToken的key的前缀
     */
    String REFRESH_TOKEN_PREFIX = "refresh_token:";

    /**
     * 根据用户的信息生成一个accessToken，这里可以不用保存
     * @param aUser
     * @return
     */
    String genAccessToken(AUser aUser);

    /**
     * 生成一个RefreshToken,目前可以不用到用户的任何数据
     * @param aUser
     * @return
     */
    String genRefreshToken();

    /**
     * 已经废弃，应该让业务层来判断并重新生成。
     * 根据refreshToken重新生成一个accessToken
     * @param refreshToken
     * @return
     */
    @Deprecated
    String refreshAccessToken(String refreshToken);

    /**
     * refreshToken是否有效
     * @param refreshToken
     * @return
     */
    boolean refreshTokenIsValid(String refreshToken);

}
