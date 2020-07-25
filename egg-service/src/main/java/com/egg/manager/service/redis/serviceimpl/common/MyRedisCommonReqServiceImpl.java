package com.egg.manager.service.redis.serviceimpl.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.service.redis.service.RedisHelper;
import com.egg.manager.service.redis.service.common.MyRedisCommonReqService;
import com.egg.manager.service.service.define.DefineRoleService;
import com.egg.manager.service.service.user.UserAccountService;
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
public abstract class MyRedisCommonReqServiceImpl implements MyRedisCommonReqService {

    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    @Autowired
    public UserAccountService userAccountService ;
    @Autowired
    public DefineRoleService defineRoleService ;


    protected  <T> T dealAutoGetRedisObjectCache(String key,String hashKey,String userAccountId,Class<T> tClass,boolean almostRefresh,Long keyTtl){
        T t = null;
        boolean retryFlag = false ;
        if(almostRefresh == true){    //为true的话进来总是取数据库，并刷新到 redis
            dealRedisListCacheRefresh(key,hashKey,userAccountId,keyTtl);
            retryFlag = true;
        }   else {
            Object obj = redisHelper.hashGet(key,hashKey);
            if(obj != null){
                String objStr = (String) obj ;
                t = JSONObject.parseObject(objStr,tClass);
            }   else {
                //从数据库中取得
                dealRedisListCacheRefresh(key,hashKey,userAccountId,keyTtl);
                retryFlag = true;
            }
        }
        if(retryFlag){
            Object obj =  redisHelper.hashGet(key,hashKey);
            if(obj != null){
                String objStr = (String) obj ;
                t = JSONObject.parseObject(objStr,tClass);
            }
        }
        return t ;
    }


    protected  <T> List<T> dealAutoGetRedisListCache(String key,String hashKey,String userAccountId,Class<T> tClass,boolean almostRefresh,Long keyTtl){
        List<T> tList = new ArrayList<>();
        boolean retryFlag = false ;
        if(almostRefresh == true){    //为true的话进来总是取数据库，并刷新到 redis
            dealRedisListCacheRefresh(key,hashKey,userAccountId,keyTtl);
            retryFlag = true;
        }   else {
            Object obj = redisHelper.hashGet(key,hashKey);
            if(obj != null){
                //String jsonStr = JSONArray.toJSONString(obj) ;
                String objStr = (String) obj ;
                if("\"[]\"".equals(objStr) ==false){
                    tList = JSONArray.parseArray(objStr,tClass);
                }
            }   else {
                //从数据库中取得
                dealRedisListCacheRefresh(key,hashKey,userAccountId,keyTtl);
                retryFlag = true;
            }
        }
        if(retryFlag){
            Object obj =  redisHelper.hashGet(key,hashKey);
            if(obj != null){
                //String jsonStr = JSONArray.toJSONString(obj) ;
                String objStr = (String) obj ;
                if("\"[]\"".equals(objStr) ==false){
                    tList = JSONArray.parseArray(objStr,tClass);
                }
            }
        }

        tList = tList != null ? tList : new ArrayList<T>() ;
        return tList ;
    }











}