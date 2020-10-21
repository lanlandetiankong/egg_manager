package com.egg.manager.common.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Component
public class JwtUtil {

    /**
     * 默认过期时间1
     */
    public static Long EXPIRE_TIME;
    public static String DEFAULT_SECRET;


    /**
     * 校验token是否正确
     * @param token
     * @return 是否正确
     */
    public static boolean verify(String token, String userAccountId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(DEFAULT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userAccountId", userAccountId)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得 token 中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUserAccountId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userAccountId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,指定时间后过期,一经生成不可修改，令牌在指定时间内一直有效
     * @param userAccountId 用户id
     * @return 加密的token
     */
    public static String sign(String userAccountId) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(DEFAULT_SECRET);
            // 附带username信息
            return JWT.create()
                    .withClaim("userAccountId", userAccountId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }


    @Value("${egg.conf.jwt.expires.time}")
    public void setExpireTime(String expireTime) {
        expireTime = StringUtils.isBlank(expireTime) ? "86400000" : expireTime;
        EXPIRE_TIME = Long.valueOf(expireTime);
    }

    @Value("${egg.conf.jwt.secret}")
    public void setDefaultPwd(String defaultSecret) {
        DEFAULT_SECRET = StringUtils.isBlank(defaultSecret) ? "EggPwd" : defaultSecret;
    }
}
