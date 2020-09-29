package com.egg.manager.baseService.services.redis.serviceimpl.common;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.api.services.basic.define.DefineRoleService;
import com.egg.manager.api.services.basic.user.UserAccountService;
import com.egg.manager.api.services.redis.service.RedisHelper;
import com.egg.manager.api.services.redis.service.common.MyRedisCommonReqService;
import com.egg.manager.common.base.constant.Constant;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/3/19
 * \* Time: 22:36
 * \* Description:
 * \
 */
@Slf4j
public abstract class BaseRedisCommonReqServiceImpl implements MyRedisCommonReqService {

    @Reference
    private RedisHelper redisHelper;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache;

    @Autowired
    public UserAccountService userAccountService;
    @Reference
    public DefineRoleService defineRoleService;


    protected <T> T dealAutoGetRedisObjectCache(UserAccount loginUser, String key, String hashKey, String userAccountId, Class<T> tClass, boolean almostRefresh, Long keyTtl) {
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


    protected <T> List<T> dealAutoGetRedisListCache(UserAccount loginUser, String key, String hashKey, String userAccountId, Class<T> tClass, boolean almostRefresh, Long keyTtl) {
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
    }


}
