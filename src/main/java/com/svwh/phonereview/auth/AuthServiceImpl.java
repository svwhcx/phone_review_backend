package com.svwh.phonereview.auth;

import com.svwh.phonereview.auth.token.AUser;
import com.svwh.phonereview.auth.token.ITokenService;
import com.svwh.phonereview.auth.token.TokenInfo;
import com.svwh.phonereview.auth.token.TokenUtil;
import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Objects;

/**
 * @description
 * @Author cxk
 * @Date 2025/3/25 16:43
 */
@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService{

   private final ITokenService tokenService;

    @Override
    public String internalLogin(AUser aUser) {
        // 内部登录成功，生成一个accessToken，同时生成一个RefreshToken。
        String accessToken = tokenService.genAccessToken(aUser);
        String refreshToken = tokenService.genRefreshToken();
        // 将accessToken返回给用户。
        // 将freshToken保存到Http-Only-Cookie中。
        addCookie(refreshToken);
        return accessToken;
    }

    @Override
    public String refreshAccessToken() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = Objects.requireNonNull(requestAttributes).getRequest();
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies == null){
            throw new BusinessException(DefaultErrorCode.ACCESS_TOKEN_REFRESH_FAIL);
        }
        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse("");
        if (refreshToken.contains("Bearer")){
            throw new BusinessException(DefaultErrorCode.ACCESS_TOKEN_REFRESH_FAIL);
        }
        refreshToken = refreshToken.replace("Bearer ","");
        // 判断用户的refreshToken是否有效
        if (tokenService.refreshTokenIsValid(refreshToken)){
            // 如果refreshToken有效，判断accessToken是否因为到期而认证失败的。
            String authorization = servletRequest.getHeader("Authorization");
            // 验证一下
            TokenInfo tokenInfo = TokenUtil.getTokenInfoExpire(authorization);
            if (tokenInfo == null){
                throw new BusinessException(DefaultErrorCode.ACCESS_TOKEN_REFRESH_FAIL);
            }
            // 确实是否因为accessToken过期而认证失败的。
            // 重新生成accessToken
            return tokenService.genAccessToken(new AUser(tokenInfo.getUserId(), tokenInfo.getUsername(), tokenInfo.getRole()));
        }
        return null;
    }

    /**
     * 添加refreshToken到HTTP-Only-Cookie中。
     * @param refreshToken 刷新token字符串。
     */
    private void addCookie(String refreshToken){
        refreshToken = refreshToken.replace("Bearer ","");
        Cookie refreshTokenCookie = new Cookie("refreshToken",refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");
        // Cookie的有效期：秒
        refreshTokenCookie.setMaxAge(ITokenService.REFRESH_TOKEN_EXPIRED_TIME/1000);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse servletResponse = Objects.requireNonNull(requestAttributes).getResponse();
        servletResponse.addCookie(refreshTokenCookie);
    }
}
