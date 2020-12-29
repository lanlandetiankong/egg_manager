package com.egg.manager.facade.persistence.commons.base.exception.login;

/**
 * 登录失败
 * @author
 */
public class MyLoginFailureException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    /**
     * 信息
     */
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public MyLoginFailureException() {
    }

    public MyLoginFailureException(Exception e) {
        super(e);
    }

    public MyLoginFailureException(String message) {
        super(message);
        this.message = message;
    }


}
