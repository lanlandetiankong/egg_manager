package com.egg.manager.service.exception;

/**
 * 参数异常
 * @author zhouchengjie
 */
public class MyParamJsonException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public MyParamJsonException() {}

    public MyParamJsonException(String message) {
        super(message);
        this.message = message;
    }


}
