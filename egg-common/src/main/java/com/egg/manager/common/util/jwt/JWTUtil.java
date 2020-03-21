package com.egg.manager.common.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author zhouchengjie
 */
public class JWTUtil {

    //TODO 修改为从配置文件 读取
    // 默认过期时间1天
    //@Value("#{egg.conf.jwt.expires.time}:86400000")
    public static Long EXPIRE_TIME =  86400000L ;
    //@Value("#{egg.conf.jwt.secret}:EggPwd")
    public static String DEFAULT_PWD = "EggPwd" ;


    /**
     * 校验token是否正确
     * @param token
     * @return 是否正确
     */
    public static boolean verify(String token, String userAccountId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(DEFAULT_PWD);
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
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(DEFAULT_PWD);
            // 附带username信息
            return JWT.create()
                    .withClaim("userAccountId", userAccountId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
