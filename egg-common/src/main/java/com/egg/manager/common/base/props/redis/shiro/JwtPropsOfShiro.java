package com.egg.manager.common.base.props.redis.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/3/20
 * \* Time: 23:16
 * \* Description:
 * \
 */
@Component
@ConfigurationProperties(prefix = "props.jwt")
@PropertySource("classpath:props-redis.properties")
public class JwtPropsOfShiro {


}
