package com.egg.manager.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.service.exception.login.MyAuthenticationExpiredException;
import com.egg.manager.service.helper.ErrorActionEnum;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.service.redis.service.RedisHelper;
import com.egg.manager.service.redis.service.user.UserAccountRedisService;
import com.egg.manager.service.webvo.session.UserAccountToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 14:28
 * \* Description:
 * \
 */
@Slf4j
public class BaseController {
    public String actionSuccessMsg = "操作成功！";
    public String actionFailMsg = "操作失败！";

    @Value("${egg.conf.jwt.sso:true}")
    private boolean jwtSsoFlag;

    @Autowired
    private RedisHelper redisHelper;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache;

    @Autowired
    private UserAccountRedisService userAccountRedisService;


    public static boolean checkFieldStrBlank(String... strs) {
        boolean blankFlag = false;
        if (strs.length > 0) {
            for (String str : strs) {
                if (StringUtils.isBlank(str)) {
                    blankFlag = true;
                    break;
                }
            }
        }
        return blankFlag;
    }



    /**
     * 设置/刷新 用户信息缓存到redis
     *
     * @param userAccountToken
     * @param result
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void dealSetTokenToRedis(UserAccountToken userAccountToken, MyCommonResult result) throws InvocationTargetException, IllegalAccessException {   //将用户 token 分别存入到redis
        if (userAccountToken != null && StringUtils.isNotBlank(userAccountToken.getUserAccountId()) && StringUtils.isNotBlank(userAccountToken.getAuthorization())) {
            //通过当前用户id 取得原先的 authorization(如果在ttl期间重新登录的话
            Object oldAuthorization = redisHelper.hashGet(redisPropsOfShiroCache.getUserAuthorizationKey(), userAccountToken.getUserAccountId());
            if (oldAuthorization != null && jwtSsoFlag) {   //根据用户id取得 当前用户的 Authorization 值，清理之前的缓存，删除后就类似于[单点登录] ,jwtSsoFlag由application.properties 配置取得
                String userAuthorization = (String) oldAuthorization;
                redisHelper.hashRemove(redisPropsOfShiroCache.getUserAuthorizationKey(), userAuthorization);
                //清除 authorization 缓存
                redisHelper.hashRemove(redisPropsOfShiroCache.getAuthorizationKey(), userAuthorization);

                redisHelper.hashRemove(redisPropsOfShiroCache.getUserAccountKey(), userAuthorization);
                redisHelper.hashRemove(redisPropsOfShiroCache.getUserPermissionsKey(), userAuthorization);
                redisHelper.hashRemove(redisPropsOfShiroCache.getUserRolesKey(), userAuthorization);
                redisHelper.hashRemove(redisPropsOfShiroCache.getUserFrontButtonsKey(), userAuthorization);
                redisHelper.hashRemove(redisPropsOfShiroCache.getUserFrontRouterUrlKey(), userAuthorization);
            }
            String authorization = userAccountToken.getAuthorization();
            //设置 用户id指向当前 的 authorization
            redisHelper.hashTtlPut(redisPropsOfShiroCache.getUserAuthorizationKey(), userAccountToken.getUserAccountId(), authorization, redisPropsOfShiroCache.getUserAuthorizationTtl());
            //设置 authorization 缓存 当前用户的token
            redisHelper.hashTtlPut(redisPropsOfShiroCache.getAuthorizationKey(), authorization, userAccountToken, redisPropsOfShiroCache.getAuthorizationTtl());

            //设置到缓存,hashKey 都是 authorization
            userAccountRedisService.dealGetCurrentUserEntity(authorization, userAccountToken.getUserAccountId(), true);
            Set<String> permissionSet = userAccountRedisService.dealGetCurrentUserAllPermissionSet(authorization, userAccountToken.getUserAccountId(), true);
            userAccountRedisService.dealGetCurrentUserAllRoleSet(authorization, userAccountToken.getUserAccountId(), true);
            userAccountRedisService.dealGetCurrentUserFrontButtons(authorization, userAccountToken.getUserAccountId(), true);
            Set<String> routerUrlSet = userAccountRedisService.dealGetCurrentUserFrontRouterUrls(authorization, userAccountToken.getUserAccountId(), true);
            if (result != null) {
                result.setRouterUrlSet(routerUrlSet);
                result.setPermissionSet(permissionSet);
            }
        } else {
            log.error("未能成功缓存用户信息到Redis");
        }
    }


    public <T> T getBeanFromRequest(HttpServletRequest request, String paramKey, Class<T> clazz, boolean isRequired) throws BusinessException {
        String queryJson = request.getParameter(paramKey);
        T bean = null;
        if (StringUtils.isNotBlank(queryJson)) {
            if (StringUtils.isNotBlank(queryJson) && queryJson != "{}") {
                bean = JSONObject.parseObject(queryJson, clazz);

            }
        }
        if (bean == null && isRequired) {
            throw new BusinessException("未取得有效的值：" + paramKey);
        }
        return bean;
    }




    public void dealCommonErrorCatch(Logger logger, MyCommonResult result, Exception e) {
        dealCommonErrorCatch(logger, result, e, null, true, true);
    }

    /**
     *
     * @param result
     * @param e
     * @param appendMsg 异常信息
     * @param isAppend  appendMsg是否追加到 errorMsg 后面
     */
    public  void dealCommonErrorCatch(Logger logger,MyCommonResult result,Exception e,String appendMsg,boolean isAppend,boolean isPrintStackTrace) {
        result.setHasError(true);
        String errmsg = null ;
        if(isAppend){
            errmsg = e.getMessage() + (StringUtils.isBlank(appendMsg) ? "" : appendMsg) ;
        }   else {
            errmsg = appendMsg ;
        }
        if(isPrintStackTrace){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        result.setErrorMsg(errmsg);
        if(e instanceof MyAuthenticationExpiredException) {
            result.setErrorActionType(ErrorActionEnum.AuthenticationExpired.getType());
        }
    }

    public void dealCommonSuccessCatch(MyCommonResult result, String info) {
        result.setInfo(info);
    }


    /**
     * 解析 搜索条件 map
     *
     * @param queryJson
     * @return
     */
    @Deprecated
    public Map<String, Object> parseQueryJsonToMap(String queryJson) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(queryJson) && queryJson != "{}") {
            JSONObject jsonObject = JSONObject.parseObject(queryJson);
            if (jsonObject != null && jsonObject.isEmpty() == false) {
                for (String jsonKey : jsonObject.keySet()) {
                    //驼峰参数 转 下划线 参数 风格
                    String jsonStr = MyStringUtil.camelToUnderline(jsonKey, false);
                    String jsonVal = jsonObject.getString(jsonKey);
                    if (StringUtils.isNotBlank(jsonVal)) {
                        map.put(jsonStr, jsonVal);
                    }
                }
            }
        }
        return map;
    }


    /**
     * 解析 搜索条件 map
     *
     * @param queryJson
     * @return
     */
    public List<QueryFormFieldBean> parseQueryJsonToBeanList(String queryJson) {
        List<QueryFormFieldBean> fieldBeanList = new ArrayList<>();
        if (StringUtils.isNotBlank(queryJson) && "[]".equals(queryJson) == false) {
            List<QueryFormFieldBean> fieldBeansTemp = JSONArray.parseArray(queryJson, QueryFormFieldBean.class);
            if (fieldBeansTemp != null && fieldBeansTemp.isEmpty() == false) {
                for (QueryFormFieldBean fieldBean : fieldBeansTemp) {
                    //驼峰参数 转 下划线 参数 风格
                    String fieldName = MyStringUtil.camelToUnderline(fieldBean.getFieldName(), false);
                    fieldBean.setFieldName(fieldName);
                    fieldBeanList.add(fieldBean);
                }
            }
        }
        return fieldBeanList;
    }


    public JSONObject parseQueryJsonToObject(String queryJson) {
        JSONObject jsonObject = null;
        if (StringUtils.isNotBlank(queryJson) && queryJson != "{}") {
            jsonObject = JSONObject.parseObject(queryJson);
        }
        jsonObject = jsonObject != null ? jsonObject : new JSONObject();
        return jsonObject;
    }

    /**
     * 取得分页 bean
     *
     * @param paginationJson
     * @return
     */
    public AntdvPaginationBean parsePaginationJsonToBean(String paginationJson) {
        AntdvPaginationBean paginationBean = null;
        if (StringUtils.isNotBlank(paginationJson)) {
            paginationBean = JSONObject.parseObject(paginationJson, AntdvPaginationBean.class);
        } else {
            paginationBean = AntdvPaginationBean.gainDefaultPaginationBean();
        }
        return paginationBean;
    }


    /**
     * 取得排序 bean
     *
     * @param sortObj
     * @return
     */
    public List<AntdvSortBean> parseSortJsonToBean(String sortObj, boolean addCreateTimeDesc) {
        List<AntdvSortBean> sortBeans = new ArrayList<>();
        if (StringUtils.isNotBlank(sortObj) && "{}".equals(sortObj) == false) {
            AntdvSortBean antdvSortBean = JSONObject.parseObject(sortObj, AntdvSortBean.class);
            if (antdvSortBean != null) {
                String fieldName = MyStringUtil.camelToUnderline(antdvSortBean.getField(), false);
                antdvSortBean.setField(fieldName);
                sortBeans.add(antdvSortBean);
            }
        }
        if (addCreateTimeDesc == true) {  //添加日期排序
            sortBeans.add(AntdvSortBean.gainCreateTimeDescBean());
        }
        return sortBeans;
    }


}
