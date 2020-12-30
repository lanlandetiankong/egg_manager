package com.egg.manager.api.exchange.servicesimpl.redis;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.helper.redis.RedisHelper;
import com.egg.manager.api.exchange.services.redis.MyRedisCommonReqService;
import com.egg.manager.api.services.em.define.basic.EmDefineRoleService;
import com.egg.manager.api.services.em.user.basic.EmUserAccountService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
public abstract class BaseRedisCommonReqServiceImpl implements MyRedisCommonReqService {

    @Reference
    private RedisHelper redisHelper;


    @Reference
    public EmUserAccountService emUserAccountService;
    @Reference
    public EmDefineRoleService emDefineRoleService;


    /*protected <T> T dealAutoGetRedisObjectCache(EmUserAccountEntity loginUser, String key, String hashKey, String userAccountId, Class<T> tClass, boolean almostRefresh, Long keyTtl) {
        T t = null;
        boolean retryFlag = false;
        if (almostRefresh == true) {
            //为true的话进来总是取数据库，并刷新到 redis
            dealRedisListCacheRefresh(loginUser, key, hashKey, userAccountId, keyTtl);
            retryFlag = true;
        } else {
            Object obj = redisHelper.hashGet(key, hashKey);
            if (obj != null) {
                String objStr = (String) obj;
                t = JSONObject.parseObject(objStr, tClass);
            } else {
                //从数据库中取得
                dealRedisListCacheRefresh(loginUser, key, hashKey, userAccountId, keyTtl);
                retryFlag = true;
            }
        }
        if (retryFlag) {
            Object obj = redisHelper.hashGet(key, hashKey);
            if (obj != null) {
                String objStr = (String) obj;
                t = JSONObject.parseObject(objStr, tClass);
            }
        }
        return t;
    }


    protected <T> List<T> dealAutoGetRedisListCache(EmUserAccountEntity loginUser, String key, String hashKey, String userAccountId, Class<T> tClass, boolean almostRefresh, Long keyTtl) {
        List<T> tList = new ArrayList<>();
        boolean retryFlag = false;
        if (almostRefresh == true) {
            //为true的话进来总是取数据库，并刷新到 redis
            dealRedisListCacheRefresh(loginUser, key, hashKey, userAccountId, keyTtl);
            retryFlag = true;
        } else {
            Object obj = redisHelper.hashGet(key, hashKey);
            if (obj != null) {
                //String jsonStr = JSONArray.toJSONString(obj) ;
                String objStr = (String) obj;
                if (Constant.JSON_EMPTY_ARRAY_STR.equals(objStr) == false) {
                    tList = JSONArray.parseArray(objStr, tClass);
                }
            } else {
                //从数据库中取得
                dealRedisListCacheRefresh(loginUser, key, hashKey, userAccountId, keyTtl);
                retryFlag = true;
            }
        }
        if (retryFlag) {
            Object obj = redisHelper.hashGet(key, hashKey);
            if (obj != null) {
                //String jsonStr = JSONArray.toJSONString(obj) ;
                String objStr = (String) obj;
                if (Constant.JSON_EMPTY_ARRAY_STR.equals(objStr) == false) {
                    tList = JSONArray.parseArray(objStr, tClass);
                }
            }
        }

        tList = tList != null ? tList : new ArrayList<T>();
        return tList;
    }*/


}
