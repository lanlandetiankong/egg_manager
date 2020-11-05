package com.egg.manager.persistence.commons.base.exception;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = 3455708526465670030L;

    public BusinessException(String msg) {
        super(msg);
    }
}