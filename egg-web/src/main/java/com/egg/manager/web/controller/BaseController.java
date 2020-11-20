package com.egg.manager.web.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.Constant;
import com.egg.manager.persistence.commons.base.exception.MyRuntimeBusinessException;
import com.egg.manager.persistence.commons.base.pagination.ISortAble;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.form.QueryField;
import com.egg.manager.persistence.commons.base.query.form.QueryFieldArr;
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
import java.util.Map;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
public class BaseController {

    public <T> T getBeanFromRequest(HttpServletRequest request, String paramKey, Class<T> clazz, boolean isRequired) throws MyRuntimeBusinessException {
        String queryJson = request.getParameter(paramKey);
        T bean = null;
        if (StringUtils.isNotBlank(queryJson)) {
            if (StringUtils.isNotBlank(queryJson) && (Constant.JSON_EMPTY_OBJECT.equals(queryJson) == false)) {
                bean = JSONObject.parseObject(queryJson, clazz);
            }
        }
        if (bean == null && isRequired) {
            throw new MyRuntimeBusinessException("未取得有效的值：" + paramKey);
        }
        return bean;
    }

    public <T> void handleRespJsonToFront(Logger logger, HttpServletResponse response, WebResult result) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JSONObject.toJSONString(result));
        } catch (IOException e) {
            logger.error("", e);
        }
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
    public QueryFieldArr parseQueryJsonToBeanList(String queryJson) {
        QueryFieldArr fieldBeanList = new QueryFieldArr();
        if (StringUtils.isNotBlank(queryJson) && (Constant.JSON_EMPTY_ARRAY.equals(queryJson) == false)) {
            QueryFieldArr fieldBeansTemp = QueryFieldArr.parseFromJson(queryJson);
            if (CollectionUtil.isNotEmpty(fieldBeansTemp)) {
                for (QueryField fieldBean : fieldBeansTemp) {
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
    public <T> AntdvPage<T> parsePaginationJsonToBean(String paginationJson, Class<T> clazz) {
        AntdvPage<T> vpage = null;
        if (StringUtils.isNotBlank(paginationJson)) {
            vpage = JSONObject.parseObject(paginationJson, AntdvPage.class);
        } else {
            vpage = AntdvPage.gainDefault(clazz);
        }
        return vpage;
    }

    /**
     * 取得排序 bean
     * @param sortObj
     * @return
     */
    public AntdvSortMap parseSortJsonToBean(String sortObj, boolean addCreateTimeDesc) {
        AntdvSortMap sortMap = new AntdvSortMap();
        if (StringUtils.isNotBlank(sortObj) && Constant.JSON_EMPTY_OBJECT.equals(sortObj) == false) {
            Map<String, Boolean> map = JSON.parseObject(sortObj, Map.class);
            if (CollectionUtil.isNotEmpty(map)) {
                sortMap.putAll(map);
            }
        }
        if (addCreateTimeDesc == true) {
            //添加日期排序
            sortMap.putDesc(FieldConst.COL_CREATE_TIME);
        }
        return sortMap;
    }
}
