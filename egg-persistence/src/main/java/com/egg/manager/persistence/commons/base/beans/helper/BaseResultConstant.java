package com.egg.manager.persistence.commons.base.beans.helper;

import java.io.Serializable;

/**
 * @author zhoucj
 * @description result-常量key
 * @date 2020/10/20
 */
public interface BaseResultConstant extends Serializable {
    /**
     * 是否有操作异常
     */
    String HAS_ERROR = "hasError";
    /**
     * 是否有警告信息
     */
    String HAS_WARNING = "hasWarning";
    /**
     * 状态
     */
    String CODE = "code";
    /**
     * 更多参数,配置 com.egg.manager.persistence.commons.base.beans.helper.MyRstMoreAttrKey
     */
    String MORE_ATTRIBUTE = "moreAttribute";
    /**
     * 提示信息、错误信息等，用于展示
     */
    String MSG = "msg";


    /**
     * 授权信息
     */
    String AUTHORIZATION = "authorization";
    /**
     * 操作成功 的数量
     */
    String COUNT = "count";
    /**
     * 表格总数量
     */
    String TOTAL = "total";
    /**
     * 树集合
     */
    String RESULT_LIST = "resultList";
    /**
     * 存储 一些自定义属性的map集合
     */
    String RESULT_MAP = "resultMap";
    /**
     * 枚举 列表
     */
    String ENUM_LIST = "enumList";
    /**
     * 枚举数据默认勾选-List
     */
    String ENUM_DEFAULT_CHECK_LIST = "enumDefaultCheckList";
    /**
     * 前端接收到异常后的操作标识，需与前端一致(axios拦截器设置必须hasError为true才会处理到这个
     */
    String ERROR_ACTION_TYPE = "errorActionType";
    /**
     * 指定类型的bean
     */
    String BEAN = "bean";

    /**
     * 分页bean
     */
    String PAGINATION_BEAN = "paginationBean";




}