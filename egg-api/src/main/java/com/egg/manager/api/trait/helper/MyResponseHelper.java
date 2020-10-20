package com.egg.manager.api.trait.helper;

import com.egg.manager.common.base.enums.PublicResultEnum;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpStatus;

/**
 * \* note: 统一返回相应参数
 * @author: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 15:00
 * \* Description:
 * \
 */
public class MyResponseHelper {

    public MyResponseHelper() {
    }

    public static MyCommonResult<Object> handleRequestFailure(Exception e, String msg) {
        return handleRequestFailure(Object.class,e,msg) ;
    }

    public static MyCommonResult<Object> handleRequestFailure(PublicResultEnum resultEnum) {
        return handleRequestFailure(Object.class,resultEnum) ;
    }

    public static <T> MyCommonResult<T> handleRequestFailure(Class<T> clazz,Exception e,String msg) {
        String errorMsg = e.getMessage();
        if(StringUtils.isNotBlank(msg)) {
            errorMsg = msg +" \n " + errorMsg ;
        }
        MyCommonResult<T> result = MyCommonResult.gainErrorResult(clazz,errorMsg) ;
        result.setHasError(true);
        result.setCode(HttpStatus.BAD_REQUEST.value());
        return result ;
    }
    public static <T> MyCommonResult<T> handleRequestFailure(Class<T> clazz,PublicResultEnum resultEnum) {
        MyCommonResult<T> result = MyCommonResult.gainErrorResult(clazz,resultEnum.getLabel()) ;
        result.setHasError(true);
        result.setCode(HttpStatus.BAD_REQUEST.value());
        if(PublicResultEnum.UnauthorizedLoginUser.getValue().equals(resultEnum.getValue())){
            result.setErrorActionType(ErrorActionEnum.AuthenticationExpired.getType());
        }
        return result ;
    }

}
