package com.egg.manager.redis.serviceimpl.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.service.define.DefineRoleService;
import com.egg.manager.redis.service.RedisHelper;
import com.egg.manager.redis.service.common.MyRedisCommonReqService;
import com.egg.manager.service.user.UserAccountService;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/3/19
 * \* Time: 22:36
 * \* Description:
 * \
 */
public abstract class MyRedisCommonReqServiceImpl implements MyRedisCommonReqService {

    private Logger baseLogger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    @Autowired
    public UserAccountService userAccountService ;

    @Autowired
    public DefineRoleService defineRoleService ;




    protected  <T> List<T> dealAutoGetRedisListCache(String key,String hashKey,String userAccountId,Class<T> tClass,boolean almostRefresh){
        List<T> tList = new ArrayList<>();
        boolean retryFlag = false ;
        if(almostRefresh == true){    //为true的话进来总是取数据库，并刷新到 redis
            dealRedisListCacheRefresh(key,hashKey,userAccountId);
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
                dealRedisListCacheRefresh(key,hashKey,userAccountId);
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
