package com.egg.manager.service.redis.service.common;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/3/19
 * \* Time: 22:35
 * \* Description:
 * \
 */
public interface MyRedisCommonReqService {

    void dealRedisListCacheRefresh(String key,String hashKey,String userAccountId,Long keyTtl);

}
