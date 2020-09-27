package com.egg.manager.api.trait.helper;

import com.egg.manager.common.base.enums.PublicResultEnum;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import org.apache.commons.lang3.StringUtils;
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

    public static <T> MyCommonResult<T> handleRequestFailure(Class<T> clazz,Exception e,String msg) {
        String errorMsg = e.getMessage();
        if(StringUtils.isNotBlank(msg)) {
            errorMsg = msg +" \n " + errorMsg ;
        }
        MyCommonResult<T> result = MyCommonResult.gainErrorResult(clazz,errorMsg) ;
        result.setActionFlag(false);
        result.setHasError(true);
        result.setStatus(HttpStatus.BAD_REQUEST.value());
        result.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
        return result ;
    }
    public static <T> MyCommonResult<T> handleRequestFailure(Class<T> clazz,PublicResultEnum resultEnum) {
        MyCommonResult<T> result = MyCommonResult.gainErrorResult(clazz,resultEnum.getLabel()) ;
        result.setActionFlag(false);
        result.setHasError(true);
        result.setStatus(HttpStatus.BAD_REQUEST.value());
        result.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
        if(PublicResultEnum.UnauthorizedLoginUser.getValue().equals(resultEnum.getValue())){
            result.setErrorActionType(ErrorActionEnum.AuthenticationExpired.getType());
        }
        return result ;
    }

}
