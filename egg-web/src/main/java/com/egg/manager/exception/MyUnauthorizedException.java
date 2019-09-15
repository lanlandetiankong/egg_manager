package com.egg.manager.exception;

/**
 * 身份认证异常
 * @author liugh
 * @since 2018-05-06
 */
public class MyUnauthorizedException extends RuntimeException {
    public MyUnauthorizedException(String msg) {
        super(msg);
    }

    public MyUnauthorizedException() {
        super();
    }
}
