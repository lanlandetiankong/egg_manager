package com.egg.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.ErrorActionEnum;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvSortBean;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.exception.login.MyAuthenticationExpiredException;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import com.egg.manager.webvo.session.UserAccountToken;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
public class BaseController {
    public String actionSuccessMsg = "操作成功" ;

    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    private Logger baseLogger = LoggerFactory.getLogger(this.getClass());

    public static boolean checkFieldStrBlank(String... strs) {
        boolean blankFlag = false ;
        if(strs.length > 0){
            for (String str : strs) {
                if(StringUtils.isBlank(str)) {
                    blankFlag = true ;
                    break ;
                }
            }
        }
        return blankFlag ;
    }

    public  void dealCommonErrorCatch(MyCommonResult result,Exception e) {
        dealCommonErrorCatch(baseLogger,result,e,null,true,true) ;
    }


    public  void dealCommonErrorCatch(Logger logger,MyCommonResult result,Exception e) {
        dealCommonErrorCatch(logger,result,e,null,true,true) ;
    }


    public void dealSetTokenToRedis(UserAccountToken userAccountToken) throws InvocationTargetException, IllegalAccessException {   //将用户token分别存入到redis
        if(userAccountToken != null){
            redisHelper.hashTtlPut(redisPropsOfShiroCache.getUserAccountKey(),userAccountToken.getAccount(),userAccountToken,redisPropsOfShiroCache.getUserAccountTtl());
            redisHelper.hashTtlPut(redisPropsOfShiroCache.getUserAccountIdKey(),userAccountToken.getUserAccountId(),userAccountToken,redisPropsOfShiroCache.getUserAccountIdTtl());
            redisHelper.hashTtlPut(redisPropsOfShiroCache.getUserTokenKey(),userAccountToken.getToken(),userAccountToken,redisPropsOfShiroCache.getUserTokenTtl());
        }   else {

        }
    }


    public <T> T getBeanFromRequest(HttpServletRequest request, String paramKey, Class<T> clazz, boolean isRequired) throws BusinessException {
        String queryJson = request.getParameter(paramKey) ;
        T bean = null ;
        if(StringUtils.isNotBlank(queryJson)){
            if(StringUtils.isNotBlank(queryJson) && queryJson != "{}"){
                bean = JSONObject.parseObject(queryJson,clazz);

            }
        }
        if(bean == null && isRequired){
            throw new BusinessException("未取得有效的值："+paramKey);
        }
        return bean ;
    }

    /**
     *
     * @param result
     * @param e
     * @param appendMsg 异常信息
     * @param isAppend appendMsg是否追加到 errorMsg 后面
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

    public  void dealCommonSuccessCatch(MyCommonResult result, String info) {
        result.setInfo(info);
    }


    /**
     * 解析 搜索条件 map
     * @param queryJson
     * @return
     */
    @Deprecated
    public Map<String,Object> parseQueryJsonToMap(String queryJson) {
        Map<String,Object> map = new HashMap<>() ;
        if(StringUtils.isNotBlank(queryJson) && queryJson != "{}"){
            JSONObject jsonObject = JSONObject.parseObject(queryJson);
            if(jsonObject != null && jsonObject.isEmpty() == false){
                for (String jsonKey : jsonObject.keySet()){
                    //驼峰参数 转 下划线 参数 风格
                    String jsonStr = MyStringUtil.camelToUnderline(jsonKey,false);
                    String jsonVal = jsonObject.getString(jsonKey) ;
                    if(StringUtils.isNotBlank(jsonVal)){
                        map.put(jsonStr,jsonVal) ;
                    }
                }
            }
        }
        return map ;
    }


    /**
     * 解析 搜索条件 map
     * @param queryJson
     * @return
     */
    public List<QueryFormFieldBean> parseQueryJsonToBeanList(String queryJson) {
        List<QueryFormFieldBean> fieldBeanList = new ArrayList<>() ;
        if(StringUtils.isNotBlank(queryJson) && "[]".equals(queryJson) == false){
            List<QueryFormFieldBean> fieldBeansTemp = JSONArray.parseArray(queryJson,QueryFormFieldBean.class);
            if(fieldBeansTemp != null && fieldBeansTemp.isEmpty() == false) {
                for (QueryFormFieldBean fieldBean : fieldBeansTemp){
                    //驼峰参数 转 下划线 参数 风格
                    String fieldName = MyStringUtil.camelToUnderline(fieldBean.getFieldName(),false);
                    fieldBean.setFieldName(fieldName);
                    fieldBeanList.add(fieldBean);
                }
            }
        }
        return fieldBeanList ;
    }


    public JSONObject parseQueryJsonToObject(String queryJson) {
        JSONObject jsonObject = null ;
        if(StringUtils.isNotBlank(queryJson) && queryJson != "{}"){
            jsonObject = JSONObject.parseObject(queryJson);
        }
        jsonObject = jsonObject != null ? jsonObject : new JSONObject() ;
        return jsonObject ;
    }

    /**
     * 取得分页 bean
     * @param paginationJson
     * @return
     */
    public AntdvPaginationBean parsePaginationJsonToBean(String paginationJson) {
        AntdvPaginationBean paginationBean = null ;
        if(StringUtils.isNotBlank(paginationJson)){
            paginationBean = JSONObject.parseObject(paginationJson, AntdvPaginationBean.class) ;
        }
        return paginationBean ;
    }


    /**
     * 取得排序 bean
     * @param sortObj
     * @return
     */
    public List<AntdvSortBean> parseSortJsonToBean(String sortObj,boolean addCreateTimeDesc) {
        List<AntdvSortBean> sortBeans = new ArrayList<>() ;
        if(StringUtils.isNotBlank(sortObj) && "{}".equals(sortObj) == false){
            AntdvSortBean antdvSortBean = JSONObject.parseObject(sortObj, AntdvSortBean.class);
            if(antdvSortBean != null){
                String fieldName = MyStringUtil.camelToUnderline(antdvSortBean.getField(),false);
                antdvSortBean.setField(fieldName);
                sortBeans.add(antdvSortBean);
            }
        }
        if(addCreateTimeDesc == true){  //添加日期排序
            sortBeans.add(AntdvSortBean.gainCreateTimeDescBean());
        }
        return sortBeans ;
    }






}
