package com.egg.manager.persistence.commons.base.helper;

import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.enums.basic.PublicResultEnum;
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

    public static <T> WebResult handleRequestFailure(PublicResultEnum resultEnum) {
        WebResult result = WebResult.error(resultEnum.getLabel());
        if (PublicResultEnum.UnauthorizedLoginUser.getValue().equals(resultEnum.getValue())) {
            result.putErrorActionType(ErrorActionEnum.AuthenticationExpired.getType());
        }
        return result;
    }

}
