package com.egg.manager.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.constant.Constant;
import com.egg.manager.persistence.commons.base.exception.BusinessException;
import com.egg.manager.persistence.commons.base.exception.login.MyAuthenticationExpiredException;
import com.egg.manager.persistence.commons.base.helper.ErrorActionEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.query.mongo.MyMongoQueryPageBean;
import com.egg.manager.persistence.commons.util.str.MyStringUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
public class BaseController {
    public final String actionSuccessMsg = "操作成功！";
    public final String actionFailMsg = "操作失败！";








    public <T> T getBeanFromRequest(HttpServletRequest request, String paramKey, Class<T> clazz, boolean isRequired) throws BusinessException {
        String queryJson = request.getParameter(paramKey);
        T bean = null;
        if (StringUtils.isNotBlank(queryJson)) {
            if (StringUtils.isNotBlank(queryJson) && (Constant.JSON_EMPTY_OBJECT.equals(queryJson) == false)) {
                bean = JSONObject.parseObject(queryJson, clazz);

            }
        }
        if (bean == null && isRequired) {
            throw new BusinessException("未取得有效的值：" + paramKey);
        }
        return bean;
    }


    public <T> void respResultJsonToFront(Logger logger, HttpServletResponse response, MyCommonResult<T> result) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JSONObject.toJSONString(result));
        } catch (IOException e) {
            logger.error(e.getCause().getMessage());
        }
    }

    public void dealCommonErrorCatch(Logger logger, MyCommonResult result, Exception e) {
        dealCommonErrorCatch(logger, result, e, this.actionFailMsg, true, true);
    }
    private void dealCommonErrorCatch(Logger logger, MyCommonResult result, Exception e, String errorMsg) {
        dealCommonErrorCatch(logger, result, e, errorMsg, true, true);
    }

    /**
     * @param result
     * @param e
     * @param errorMsg 异常信息
     * @param isAppend appendMsg是否追加到 errorMsg 后面
     */
    public void dealCommonErrorCatch(Logger logger, MyCommonResult result, Exception e, String errorMsg, boolean isAppend, boolean isPrintStackTrace) {
        result.setHasError(true);
        String errmsg = null;
        if (isAppend) {
            errmsg = (StringUtils.isBlank(errorMsg) ? "" : errorMsg) + e.getMessage();
        } else {
            errmsg = errorMsg;
        }
        if (isPrintStackTrace) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        //清空信息
        result.setMsg(errmsg);
        if (e instanceof MyAuthenticationExpiredException) {
            result.setErrorActionType(ErrorActionEnum.AuthenticationExpired.getType());
        }
    }


    public void dealSetMongoPageResult(MyCommonResult result, MyMongoQueryPageBean pageBean) {
        result.setResultList(pageBean.getContent());
        result.setCount(pageBean.getTotal());
    }


    /**
     * 解析 搜索条件 map
     * @param queryJson
     * @return
     */
    @Deprecated
    public Map<String, Object> parseQueryJsonToMap(String queryJson) {
        Map<String, Object> map = Maps.newHashMap();
        if (StringUtils.isNotBlank(queryJson) && (Constant.JSON_EMPTY_OBJECT.equals(queryJson) == false)) {
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
     * @param queryJson
     * @return
     */
    public List<QueryFormFieldBean> parseQueryJsonToBeanList(String queryJson) {
        List<QueryFormFieldBean> fieldBeanList = new ArrayList<>();
        if (StringUtils.isNotBlank(queryJson) && (Constant.JSON_EMPTY_ARRAY.equals(queryJson) == false)) {
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


    /**
     * 取得分页 bean
     * @param paginationJson
     * @return
     */
    public <T> AntdvPaginationBean<T> parsePaginationJsonToBean(String paginationJson, Class<T> clazz) {
        AntdvPaginationBean<T> paginationBean = null;
        if (StringUtils.isNotBlank(paginationJson)) {
            paginationBean = JSONObject.parseObject(paginationJson, AntdvPaginationBean.class);
        } else {
            paginationBean = AntdvPaginationBean.gainDefaultPaginationBean(clazz);
        }
        return paginationBean;
    }


    /**
     * 取得排序 bean
     * @param sortObj
     * @return
     */
    public List<AntdvSortBean> parseSortJsonToBean(String sortObj, boolean addCreateTimeDesc) {
        List<AntdvSortBean> sortBeans = new ArrayList<>();
        if (StringUtils.isNotBlank(sortObj) && Constant.JSON_EMPTY_OBJECT.equals(sortObj) == false) {
            AntdvSortBean antdvSortBean = JSONObject.parseObject(sortObj, AntdvSortBean.class);
            if (antdvSortBean != null) {
                String fieldName = MyStringUtil.camelToUnderline(antdvSortBean.getField(), false);
                antdvSortBean.setField(fieldName);
                sortBeans.add(antdvSortBean);
            }
        }
        if (addCreateTimeDesc == true) {
            //添加日期排序
            sortBeans.add(AntdvSortBean.gainCreateTimeDescBean());
        }
        return sortBeans;
    }
}
