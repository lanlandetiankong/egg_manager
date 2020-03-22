package com.egg.manager.web.config.shiro;

import lombok.*;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * \* note: shiro jwt token令牌
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 16:46
 * \* Description:
 * \
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class JwtShiroToken implements AuthenticationToken{
    private String token ;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
