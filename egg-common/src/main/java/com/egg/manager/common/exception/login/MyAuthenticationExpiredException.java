package com.egg.manager.common.exception.login;

/**
 * 用户信息过期 异常
 * @author zhouchengjie
 */
public class MyAuthenticationExpiredException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String defaultMsg = "用户信息已过期，请重新登录！" ;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public MyAuthenticationExpiredException() {
        this.message = defaultMsg ;
    }

    public MyAuthenticationExpiredException(String message) {
        super(message);
        this.message = message;
    }


}
