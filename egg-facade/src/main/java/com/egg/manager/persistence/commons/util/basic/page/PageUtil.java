package com.egg.manager.persistence.commons.util.basic.page;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.Constant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.exception.MyRuntimeBusinessException;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryField;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.commons.util.data.str.MyStringUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhoucj
 * @description: 分页工具
 * @date 2020/11/23
 */
public class PageUtil {
    public static <T> T getBeanFromRequest(HttpServletRequest request, String paramKey, Class<T> clazz, boolean isRequired) throws MyRuntimeBusinessException {
        String queryJson = request.getParameter(paramKey);
        T bean = null;
        if (StringUtils.isNotBlank(queryJson)) {
            if (StringUtils.isNotBlank(queryJson) && (Constant.JSON_EMPTY_OBJECT.equals(queryJson) == false)) {
                bean = JSONObject.parseObject(queryJson, clazz);
            }
        }
        if (bean == null && isRequired) {
            throw new MyRuntimeBusinessException(BaseRstMsgConstant.ErrorMsg.paramCannotEmpty(paramKey));
        }
        return bean;
    }

    /**
     * 解析 搜索条件 map
     * @param request
     * @return
     */
    public static QueryFieldArr parseQueryJsonToBeanList(HttpServletRequest request) {
        return parseQueryJsonToBeanList(request.getParameter(WebApiConstant.FIELDNAME_QUERY_OBJ));
    }

    /**
     * 解析 搜索条件 map
     * @param queryJson
     * @return
     */
    public static QueryFieldArr parseQueryJsonToBeanList(String queryJson) {
        QueryFieldArr fieldBeanList = new QueryFieldArr();
        if (StringUtils.isNotBlank(queryJson) && (Constant.JSON_EMPTY_ARRAY.equals(queryJson) == false)) {
            QueryFieldArr fieldBeansTemp = QueryFieldArr.parseFromJson(queryJson);
            if (CollectionUtil.isEmpty(fieldBeansTemp)) {
                return fieldBeanList;
            }
            for (QueryField fieldBean : fieldBeansTemp) {
                if (checkIsQueryField(fieldBean) == false) {
                    continue;
                }
                //驼峰参数 转 下划线 参数 风格
                String fieldName = MyStringUtil.camelToUnderline(fieldBean.getFieldName(), false);
                fieldBean.setFieldName(fieldName);
                fieldBeanList.add(fieldBean);
            }
        }
        return fieldBeanList;
    }

    public static <T> AntdvPage<T> parsePaginationJsonToBean(HttpServletRequest request, Class<T> clazz) {
        return parsePaginationJsonToBean(request.getParameter(WebApiConstant.FIELDNAME_PAGINATION_OBJ), clazz);
    }

    /**
     * 取得分页 bean
     * @param paginationJson
     * @return
     */
    public static <T> AntdvPage<T> parsePaginationJsonToBean(String paginationJson, Class<T> clazz) {
        AntdvPage<T> vpage = null;
        if (StringUtils.isNotBlank(paginationJson)) {
            vpage = JSONObject.parseObject(paginationJson, AntdvPage.class);
        } else {
            vpage = AntdvPage.gainDefault(clazz);
        }
        return vpage;
    }

    public static AntdvSortMap parseSortJsonToBean(HttpServletRequest request, boolean addCreateTimeDesc) {
        return parseSortJsonToBean(request.getParameter(WebApiConstant.FIELDNAME_SORT_OBJ), addCreateTimeDesc);
    }

    /**
     * 取得排序 bean
     * @param sortObj
     * @return
     */
    public static AntdvSortMap parseSortJsonToBean(String sortObj, boolean addCreateTimeDesc) {
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

    /**
     * 解析 搜索条件 map
     * @param queryJson
     * @return
     */
    @Deprecated
    public static Map<String, Object> parseQueryJsonToMap(String queryJson) {
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
     * 判断是否可以添加为 查询字段
     * @param fieldBean
     * @return
     */
    public static boolean checkIsQueryField(QueryField fieldBean) {
        if (fieldBean == null) {
            return false;
        }
        if (fieldBean.getValue() == null || StringUtils.isBlank(fieldBean.getFieldName())) {
            return false;
        }
        return !(fieldBean.getValue() instanceof String) || !StringUtils.isBlank(String.valueOf(fieldBean.getValue()));
    }

}