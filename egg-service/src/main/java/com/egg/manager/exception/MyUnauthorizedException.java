package com.egg.manager.exception;

/**
 * 身份认证异常
 * @author zhouchengjie
 */
public class MyUnauthorizedException extends RuntimeException {
    public MyUnauthorizedException(String msg) {
        super(msg);
    }

    public MyUnauthorizedException() {
        super();
    }
}
