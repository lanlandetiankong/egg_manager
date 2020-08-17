package com.egg.manager.common.exception.login;

/**
 * 登录失败
 * @author zhouchengjie
 */
public class MyLoginFailureException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public MyLoginFailureException() {}

    public MyLoginFailureException(String message) {
        super(message);
        this.message = message;
    }


}
