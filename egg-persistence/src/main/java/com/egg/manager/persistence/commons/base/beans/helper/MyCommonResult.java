package com.egg.manager.persistence.commons.base.beans.helper;

import cn.hutool.http.HttpStatus;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.enums.PublicResultEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhoucj
 * @description 通用的返回结果模型
 * @date 2020/10/20
 */
@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MyCommonResult<T> implements Serializable {


    /**
     * 授权信息
     */
    private String authorization;
    /**
     * 提示信息、错误信息等，用于展示
     */
    private String msg;
    /**
     * 是否有操作异常
     */
    @Builder.Default
    private boolean hasError = false;
    /**
     * 是否有警告信息
     */
    @Builder.Default
    private boolean hasWarning = false;
    /**
     * 操作成功 的数量
     */
    private long count;
    /**
     * 表格总数量
     */
    private long total;
    /**
     * 树集合
     */
    private List<T> resultList;
    /**
     * 存储 一些自定义属性的map集合
     */
    private Map resultMap;
    /**
     * 枚举 列表
     */
    private List enumList;
    /**
     * 枚举数据默认勾选-List
     */
    private List enumDefaultCheckList;
    /**
     * 前端接收到异常后的操作标识，需与前端一致(axios拦截器设置必须hasError为true才会处理到这个
     */
    private String errorActionType;
    /**
     * 指定类型的bean
     */
    private T bean;
    /**
     * 状态
     */
    @Builder.Default
    private Integer code = HttpStatus.HTTP_OK;

    /**
     * 分页bean
     */
    private AntdvPaginationBean paginationBean;
    /**
     * 更多参数,配置 com.egg.manager.persistence.commons.base.beans.helper.MyRstMoreAttrKey
     */
    @Builder.Default
    private Map<String, Object> moreAttribute = Maps.newHashMap();


    /**
     * 只允许通过static构造类
     */
    private MyCommonResult() {
    }

    private static <T> MyCommonResult<T> gainInitQueryBean(Class<T> tClass) {
        MyCommonResult<T> result = new MyCommonResult<>();
        result.setMsg(BaseRstMsgConstant.ACTION_SUCCESS_MSG);
        result.setHasError(false);
        result.setHasWarning(false);
        return result;
    }

    public static <T> MyCommonResult<T> gainQueryResult(Class<T> tClass) {
        MyCommonResult<T> result = gainInitQueryBean(tClass);
        return result;
    }

    public static <E> MyCommonResult<Object> gainEnumResult() {
        MyCommonResult<Object> result = gainInitQueryBean(Object.class);
        result.setMsg(BaseRstMsgConstant.ACTION_SUCCESS_MSG);
        return result;
    }

    public static MyCommonResult gainOperationResult() {
        MyCommonResult result = gainInitQueryBean(Object.class);
        result.setMsg(PublicResultEnum.Success.getLabel());
        return result;
    }

    public static MyCommonResult gainErrorResult(String errorMsg) {
        MyCommonResult result = gainInitQueryBean(Object.class);
        result.setHasError(true);
        result.setMsg(errorMsg);
        return result;
    }

    public static <T> MyCommonResult<T> gainErrorResult(Class<T> tClass, String errorMsg) {
        MyCommonResult<T> result = gainInitQueryBean(tClass);
        result.setHasError(true);
        result.setMsg(errorMsg);
        return result;
    }

    /**
     * 设置分页信息
     * @param paginationBean
     * @param total
     * @param <T>
     */
    public <T> void myAntdvPaginationBeanSet(AntdvPaginationBean<T> paginationBean, Long total) {
        if (paginationBean != null) {
            paginationBean.setTotal(total);
        }
        this.paginationBean = paginationBean;
    }


    /**
     * 添加 更多参数
     * @param key
     * @param value
     */
    public void addMoreAttribute(String key, Object value) {
        this.moreAttribute = (this.moreAttribute == null) ? Maps.newHashMap() : this.moreAttribute;
        this.moreAttribute.put(key, value);
    }
}
