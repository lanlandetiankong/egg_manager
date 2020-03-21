package com.egg.manager.redis.service.common;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Set;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/3/19
 * \* Time: 22:35
 * \* Description:
 * \
 */
public interface MyRedisCommonReqService {

    void dealRedisListCacheRefresh(String key,String hashKey,String userAccountId);

}
