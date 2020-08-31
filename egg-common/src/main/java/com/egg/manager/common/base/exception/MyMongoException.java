package com.egg.manager.common.base.exception;

/**
 * @author zhouchengjie
 */
public class MyMongoException extends RuntimeException{

    private static final long serialVersionUID = 3455708526465670030L;

    public MyMongoException(String msg){
        super(msg);
    }
}