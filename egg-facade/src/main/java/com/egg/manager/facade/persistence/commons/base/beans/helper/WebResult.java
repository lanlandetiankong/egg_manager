package com.egg.manager.facade.persistence.commons.base.beans.helper;

import cn.hutool.http.HttpStatus;
import com.egg.manager.facade.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.AntdvPage;


/**
 * @author zhoucj
 * @description 通用的返回结果模型
 * @date 2020/10/20
 */
public class WebResult extends AbstractResult {

    private static final long serialVersionUID = 4626736174900245688L;

    /**
     * 只允许通过static构造类
     */
    public WebResult() {
    }

    private static WebResult initResult() {
        WebResult result = new WebResult();
        result.putSuccess(true);
        result.putHasWarning(false);
        result.putCode(HttpStatus.HTTP_OK);
        result.putMsg(BaseRstMsgConstant.SuccessMsg.actionSuccess());
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
        result.putSuccess(false);
        result.putMsg(BaseRstMsgConstant.ErrorMsg.actionFail());
        result.putErrorMsg(errorMsg);
        result.putCode(HttpStatus.HTTP_INTERNAL_ERROR);
        return result;
    }

    public WebResult toError() {
        return toError(BaseRstMsgConstant.ErrorMsg.actionFail());
    }

    public WebResult toError(String msg) {
        this.putSuccess(false);
        this.putErrorMsg(msg);
        return this;
    }

    public WebResult putPage(AntdvPage pageBean) {
        this.putGridList(pageBean.getContent());
        this.putCount(pageBean.getTotal());
        return this;
    }


}
