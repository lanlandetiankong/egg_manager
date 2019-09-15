package com.egg.manager.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * \* note: shiro jwt token令牌
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 16:46
 * \* Description:
 * \
 */
public class JwtShiroToken implements AuthenticationToken{
    private String token ;

    public JwtShiroToken(String token) {
        this.token = token ;
    }


    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
