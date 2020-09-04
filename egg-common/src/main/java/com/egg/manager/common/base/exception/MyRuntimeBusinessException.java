package com.egg.manager.common.base.exception;

/**
 * @Description:
 * @ClassName: MyRuntimeBusinessException
 * @Author: zhoucj
 * @Date: 2020/9/4 10:07
 */
public class MyRuntimeBusinessException extends RuntimeException{

    private static final long serialVersionUID = 3455708526465670030L;

    public MyRuntimeBusinessException(String msg){
        super(msg);
    }
}
