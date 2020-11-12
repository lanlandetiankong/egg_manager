package com.egg.manager.persistence.commons.base.helper;

import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.enums.PublicResultEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

/**
 * @author zhoucj
 * @description统一返回相应参数
 * @date 2020/10/21
 */
public class MyResponseHelper {

    public MyResponseHelper() {
    }

    public static WebResult handleRequestFailure(Exception e, String msg) {
        return handleRequestFailure(Object.class, e, msg);
    }

    public static WebResult handleRequestFailure(PublicResultEnum resultEnum) {
        return handleRequestFailure(Object.class, resultEnum);
    }

    public static <T> WebResult handleRequestFailure(Class<T> clazz, Exception e, String msg) {
        String errorMsg = e.getMessage();
        if (StringUtils.isNotBlank(msg)) {
            errorMsg = msg + " \n " + errorMsg;
        }
        WebResult result = WebResult.gainErrorResult(clazz, errorMsg);
        result.putHasError(true);
        result.putCode(HttpStatus.BAD_REQUEST.value());
        return result;
    }

    public static <T> WebResult handleRequestFailure(Class<T> clazz, PublicResultEnum resultEnum) {
        WebResult result = WebResult.gainErrorResult(clazz, resultEnum.getLabel());
        result.putHasError(true);
        result.putCode(HttpStatus.BAD_REQUEST.value());
        if (PublicResultEnum.UnauthorizedLoginUser.getValue().equals(resultEnum.getValue())) {
            result.putErrorActionType(ErrorActionEnum.AuthenticationExpired.getType());
        }
        return result;
    }

}
