package com.egg.manager.api.service.exception.form;

/**
 * \* note: 登录表单字段缺失异常
 * \* User: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 14:35
 * \* Description:
 * \
 */
public class LoginFormFieldDeficiencyException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public LoginFormFieldDeficiencyException() {
        super("登录表单的字段缺失异常!");
    }

    public LoginFormFieldDeficiencyException(String message) {
        super("登录表单的["+message+"]字段缺失异常!");
        this.message = super.getMessage();
    }

}