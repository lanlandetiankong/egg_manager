package com.egg.manager.common.exception;

/**
 * 参数异常
 * @author
 */
public class MyParamJsonException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 信息
     */
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public MyParamJsonException() {
    }

    public MyParamJsonException(String message) {
        super(message);
        this.message = message;
    }


}
