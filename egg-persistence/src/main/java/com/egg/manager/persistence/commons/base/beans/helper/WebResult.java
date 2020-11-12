package com.egg.manager.persistence.commons.base.beans.helper;

import cn.hutool.http.HttpStatus;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;

import java.util.HashMap;

/**
 * @author zhoucj
 * @description 通用的返回结果模型
 * @date 2020/10/20
 */
public class WebResult extends AbstractResult{

    /**
     * 分页bean
     */
    private AntdvPaginationBean paginationBean;


    private void initResult(){
        this.put(HAS_ERROR,false);
        this.put(HAS_WARNING,false);
        this.put(CODE,HttpStatus.HTTP_OK);
        this.put(MORE_ATTRIBUTE,new HashMap<String,Object>());
        this.put(MSG,BaseRstMsgConstant.ACTION_SUCCESS_MSG);
    }
    /**
     * 只允许通过static构造类
     */
    private WebResult() {
        this.initResult();
    }

    private static WebResult gainInitQueryBean(Class tClass) {
        WebResult result = new WebResult();
        return result;
    }

    public static WebResult gainQueryResult(Class tClass) {
        WebResult result = gainInitQueryBean(tClass);
        return result;
    }

    public static WebResult gainEnumResult() {
        WebResult result = gainInitQueryBean(Object.class);
        return result;
    }

    public static WebResult gainOperationResult() {
        WebResult result = gainInitQueryBean(Object.class);
        return result;
    }

    public static WebResult gainErrorResult(String errorMsg) {
        WebResult result = gainInitQueryBean(Object.class);
        result.put(HAS_ERROR,true);
        result.put(MSG,errorMsg);
        return result;
    }

    public static WebResult gainErrorResult(Class tClass, String errorMsg) {
        WebResult result = gainInitQueryBean(tClass);
        result.put(HAS_ERROR,true);
        result.put(MSG,errorMsg);
        return result;
    }

    /**
     * 设置分页信息
     * @param paginationBean
     * @param total
     * @param 
     */
    public  void myAntdvPaginationBeanSet(AntdvPaginationBean paginationBean, Long total) {
        if (paginationBean != null) {
            paginationBean.setTotal(total);
        }
        this.paginationBean = paginationBean;
    }



}
