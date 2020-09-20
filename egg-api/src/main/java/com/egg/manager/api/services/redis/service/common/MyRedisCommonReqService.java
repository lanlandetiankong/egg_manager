package com.egg.manager.api.services.redis.service.common;

import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/3/19
 * \* Time: 22:35
 * \* Description:
 * \
 */
public interface MyRedisCommonReqService {

    void dealRedisListCacheRefresh(UserAccount loginUser,String key, String hashKey, String userAccountId, Long keyTtl);

}
