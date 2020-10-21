package com.egg.manager.common.base.constant.redis;


/**
 * @author zhoucj
 * @version V1.0
 * @description:
 * @date 2019/10/5
 */
public interface RedisShiroKeyConstant {
    String SHIRO_CACHE_PREFIX = "shiro:cache";
    String SHIRO_CACHE_TOKEN = SHIRO_CACHE_PREFIX + ":token";
    String SHIRO_CACHE_ACCOUNT = SHIRO_CACHE_PREFIX + ":account";

    /**
     * 过期时间
     */
    Long SHIRO_CACHE_TOKEN_TTL = 30 * 60 * 1000L;

}
