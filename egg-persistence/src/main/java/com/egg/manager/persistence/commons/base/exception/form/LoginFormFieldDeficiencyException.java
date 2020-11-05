package com.egg.manager.persistence.commons.base.exception.form;

/**
 * @author zhoucj
 * @description: 登录表单字段缺失异常
 * @date 2020/10/21
 */
public class LoginFormFieldDeficiencyException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    /**
     * 信息
     */
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public LoginFormFieldDeficiencyException() {
        super("登录表单的字段缺失异常!");
    }

    public LoginFormFieldDeficiencyException(String message) {
        super("登录表单的[" + message + "]字段缺失异常!");
        this.message = super.getMessage();
    }

}
