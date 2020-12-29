package com.egg.manager.facade.persistence.commons.base.helper;

import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhoucj
 * @description统一返回相应参数
 * @date 2020/10/21
 */
public class MyResponseHelper {

    public MyResponseHelper() {
    }

    public static <T> WebResult handleRequestFailure(Exception e, String msg) {
        String errorMsg = e.getMessage();
        if (StringUtils.isNotBlank(msg)) {
            errorMsg = msg + " \n " + errorMsg;
        }
        WebResult result = WebResult.error(errorMsg);
        return result;
    }

    public static WebResult handleRequestFailure(String label) {
        WebResult result = WebResult.error(StringUtils.isNotBlank(label) ? label : BaseRstMsgConstant.ErrorMsg.actionFail());
        return result;
    }

    public static WebResult handleAuthenticationExpired() {
        WebResult result = WebResult.error(BaseRstMsgConstant.ErrorMsg.shiroUnauthorized());
            result.putErrorActionType(ErrorActionEnum.AuthenticationExpired.getType());
        return result;
    }

}
