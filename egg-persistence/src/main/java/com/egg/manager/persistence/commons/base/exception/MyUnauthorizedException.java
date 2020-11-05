package com.egg.manager.persistence.commons.base.exception;

/**
 * 身份认证异常
 * @author
 */
public class MyUnauthorizedException extends RuntimeException {
    public MyUnauthorizedException(String msg) {
        super(msg);
    }

    public MyUnauthorizedException() {
        super();
    }
}
