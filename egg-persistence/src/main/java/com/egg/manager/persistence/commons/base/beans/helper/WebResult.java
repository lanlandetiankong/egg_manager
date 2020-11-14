package com.egg.manager.persistence.commons.base.beans.helper;

import cn.hutool.http.HttpStatus;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;

import java.util.HashMap;

/**
 * @author zhoucj
 * @description 通用的返回结果模型
 * @date 2020/10/20
 */
public class WebResult extends AbstractResult{

    /**
     * 只允许通过static构造类
     */
    private WebResult() {
        this.initResult();
    }

    private static WebResult initResult() {
        WebResult result = new WebResult();
        result.put(HAS_ERROR,false);
        result.put(HAS_WARNING,false);
        result.put(CODE,HttpStatus.HTTP_OK);
        result.put(MORE_ATTRIBUTE,new HashMap<String,Object>());
        result.put(MSG,BaseRstMsgConstant.ACTION_SUCCESS_MSG);
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
        result.put(HAS_ERROR,true);
        result.put(MSG,errorMsg);
        return result;
    }






}
