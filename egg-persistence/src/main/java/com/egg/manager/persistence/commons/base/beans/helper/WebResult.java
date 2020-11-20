package com.egg.manager.persistence.commons.base.beans.helper;

import cn.hutool.http.HttpStatus;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.query.mongo.MongoQueryPageBean;


/**
 * @author zhoucj
 * @description 通用的返回结果模型
 * @date 2020/10/20
 */
public class WebResult extends AbstractResult {

    /**
     * 只允许通过static构造类
     */
    public WebResult() {
    }

    private static WebResult initResult() {
        WebResult result = new WebResult();
        result.putHasError(false);
        result.putHasWarning(false);
        result.putCode(HttpStatus.HTTP_OK);
        result.putMsg(BaseRstMsgConstant.ACTION_SUCCESS_MSG);
        result.putErrorMsg(null);
        return result;
    }

    public static WebResult okQuery() {
        WebResult result = initResult();
        return result;
    }

    public static WebResult okEnums() {
        WebResult result = initResult();
        return result;
    }

    public static WebResult okOperation() {
        WebResult result = initResult();
        return result;
    }

    public static WebResult error(String errorMsg) {
        WebResult result = initResult();
        result.putHasError(true);
        result.putMsg(BaseRstMsgConstant.ACTION_FAIL_MSG);
        result.putErrorMsg(errorMsg);
        result.putCode(HttpStatus.HTTP_INTERNAL_ERROR);
        return result;
    }

    public WebResult toError() {
        return toError(BaseRstMsgConstant.ACTION_FAIL_MSG);
    }

    public WebResult toError(String msg) {
        this.putHasError(true);
        this.putErrorMsg(msg);
        return this;
    }

    public WebResult putPage(MongoQueryPageBean pageBean) {
        this.putResultList(pageBean.getContent());
        this.putCount(pageBean.getTotal());
        return this;
    }


}
