package com.egg.manager.persistence.commons.base.exception;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
public class MyRuntimeBusinessException extends RuntimeException {

    private static final long serialVersionUID = 3455708526465670030L;

    public MyRuntimeBusinessException(String msg) {
        super(msg);
    }
}
