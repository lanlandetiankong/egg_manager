package com.egg.manager.common.base.constant.web.api;

/**
 * @author zhoucj
 * @description: api 常量
 * @date 2020/10/20
 */
public interface WebApiConstant {
    String QUERY_OBJ_LABEL = "字段查询配置(json格式)";
    String PAGINATION_OBJ_LABEL = "分页配置(json格式)";
    String SORT_OBJ_LABEL = "排序对象(json格式)";

    String DELETE_ID_ARRAY_LABEL = "要删除的id数组";
    String DELETE_ID_LABEL = "指定要删除的id";


    /**
     * 分页查询相关字段名
     */
    String FIELDNAME_QUERY_OBJ = "queryObj";
    String FIELDNAME_PAGINATION_OBJ = "paginationObj";
    String FIELDNAME_SORT_OBJ = "sortObj";
}
