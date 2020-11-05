package com.egg.manager.api.exchange.services.redis;

import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
public interface MyRedisCommonReqService {

    /**
     * redis缓存刷新
     * @param loginUser
     * @param key
     * @param hashKey
     * @param userAccountId
     * @param keyTtl
     */
    void dealRedisListCacheRefresh(UserAccount loginUser, String key, String hashKey, Long userAccountId, Long keyTtl);

}
