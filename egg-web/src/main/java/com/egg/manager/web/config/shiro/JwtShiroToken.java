package com.egg.manager.web.config.shiro;

import lombok.*;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author zhoucj
 * @description:shiro jwt token令牌
 * @date 2019/9/14
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class JwtShiroToken implements AuthenticationToken {
    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
