package com.egg.manager.api.exchange.servicesimpl.redis;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.api.services.em.define.basic.DefineRoleService;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.api.exchange.helper.redis.RedisHelper;
import com.egg.manager.api.exchange.services.redis.MyRedisCommonReqService;
import com.egg.manager.persistence.commons.base.constant.Constant;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
public abstract class BaseRedisCommonReqServiceImpl implements MyRedisCommonReqService {

    @Reference
    private RedisHelper redisHelper;


    @Autowired
    public UserAccountService userAccountService;
    @Reference
    public DefineRoleService defineRoleService;


    /*protected <T> T dealAutoGetRedisObjectCache(UserAccountEntity loginUser, String key, String hashKey, Long userAccountId, Class<T> tClass, boolean almostRefresh, Long keyTtl) {
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


    protected <T> List<T> dealAutoGetRedisListCache(UserAccountEntity loginUser, String key, String hashKey, Long userAccountId, Class<T> tClass, boolean almostRefresh, Long keyTtl) {
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
