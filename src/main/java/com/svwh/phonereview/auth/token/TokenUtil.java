package com.svwh.phonereview.auth.token;

import com.svwh.phonereview.exception.BusinessException;
import com.svwh.phonereview.exception.DefaultErrorCode;
import io.jsonwebtoken.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @description Token工具类
 * @Author cxk
 * @Date 2022/4/30 16:42
 */
public class TokenUtil {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String TOKEN_SECRET = "*^%=joh#ads**9";

    /**
     * 解析Token
     */

    public static Claims parse(String jwt, String secretKey) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            throw new BusinessException(DefaultErrorCode.INVALIDE_TOKEN);
        }
        return claims;
    }

    public static Claims parse(String jwt) {
        return parse(jwt, TOKEN_SECRET);
    }

    /**
     * 生成 token
     *
     * @param claims
     * @param millisecond
     * @return
     */
    public static String genToken(Map<String, Object> claims, int millisecond) {
        //获取当前的时间
        Calendar calendar = Calendar.getInstance();
        //获取系统当前时间
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        //失效的时间
        calendar.add(Calendar.MILLISECOND, millisecond);
        //拿到预定过期时间的日期
        Date endTime = calendar.getTime();
        JwtBuilder jwtBuilder = Jwts.builder()
                //签名算法
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                //签发时间
                .setIssuedAt(new Date())
                //到期时间
                .setClaims(claims)
                .setExpiration(endTime)
                //playLoad
                //签发者
                .setIssuer("Phone_Review")
                //接收者
                .setAudience("Phone_Review");
        return TOKEN_PREFIX + jwtBuilder.compact();
    }

    private static <T> T getInfoData(Claims claims, String key, Class<T> clazz) {
        T t;
        try {
            t = claims.get(key, clazz);
        } catch (Exception e) {
            throw new BusinessException(DefaultErrorCode.INVALIDE_TOKEN);
        }
        return t;
    }

    /**
     * 是否是合法的token
     *
     * @param token
     * @return
     */
    private static boolean isValid(String token) {
        return token.contains(TOKEN_PREFIX);
    }

    /**
     * 从Token中获取token信息
     *
     * @param token token字符串
     * @return TokenInfo信息
     * @throws 若token未通过校验，则抛出异常
     */
    public static TokenInfo getTokenInfo(String token) {
        if (!isValid(TOKEN_PREFIX)) {
            throw new BusinessException(DefaultErrorCode.INVALIDE_TOKEN);
        }
        token = token.replaceAll(TOKEN_PREFIX, "");
        Claims parse = parse(token);
        return buildFromClaims(parse);
    }

    /**
     * 从Claims中构建TokenInfo
     * @param claims
     * @return
     */
    private static TokenInfo buildFromClaims(Claims claims) {
        claims.get("", String.class);
        return new TokenInfo.Builder()
                .username(getInfoData(claims, "username", String.class))
                .userId(getInfoData(claims, "userId", Long.class))
                .role(getInfoData(claims, "role", String.class))
                .build();
    }

    /**
     * 判断一个Token是否因过期而失效，而不是Token本身就是一个失效的Token。
     *
     * @param token 待验证Token.
     * @return true表示确实是因为过期而失效，false表示Token不是有效的。
     */
    public static boolean isExpired(String token) {
        try {
            parse(token);
            return true;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从Token中获取用户信息，就算Token失效也返回对应的信息<p>
     * 用于刷新 accessToken
     *
     * @param token 待获取token字串
     * @return 包含用户信息的TokenInfo对象。
     */
    public static TokenInfo getTokenInfoExpire(String token) {
        Claims claims = null;
        try {
            claims = parse(token);
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        } catch (Exception e) {
            return null;
        }
        return buildFromClaims(claims);
    }

}
