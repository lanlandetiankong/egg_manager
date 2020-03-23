package com.egg.manager.service.helper;

import com.egg.manager.common.base.enums.PublicResultEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

/**
 * \* note: 统一返回相应参数
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 15:00
 * \* Description:
 * \
 */
public class MyResponseHelper {

    public MyResponseHelper() {
    }

    public static <T> MyCommonResult<T> handleRequestFailure(Exception e,String msg) {
        MyCommonResult<T> result = new MyCommonResult<T>() ;
        String errorMsg = e.getMessage();
        if(StringUtils.isNotBlank(msg)) {
            errorMsg = msg +" \n " + errorMsg ;
        }
        result.setActionFlag(false);
        result.setHasError(true);
        result.setStatus(HttpStatus.BAD_REQUEST.value());
        result.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
        result.setErrorMsg(errorMsg);
        return result ;
    }
    public static <T> MyCommonResult<T> handleRequestFailure(PublicResultEnum resultEnum) {
        MyCommonResult<T> result = new MyCommonResult<T>() ;
        String errorMsg = resultEnum.getLabel();
        result.setActionFlag(false);
        result.setHasError(true);
        result.setStatus(HttpStatus.BAD_REQUEST.value());
        result.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
        result.setErrorMsg(errorMsg);
        if(PublicResultEnum.UnauthorizedLoginUser.getValue().equals(resultEnum.getValue())){
            result.setErrorActionType(ErrorActionEnum.AuthenticationExpired.getType());
        }
        return result ;
    }

}
