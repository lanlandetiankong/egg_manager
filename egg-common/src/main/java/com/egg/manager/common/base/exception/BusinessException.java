package com.egg.manager.common.base.exception;

/**
 * @author zhouchengjie
 */
public class BusinessException extends Exception{

    private static final long serialVersionUID = 3455708526465670030L;

    public BusinessException(String msg){
        super(msg);
    }
}