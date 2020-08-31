package com.egg.manager.common.base.exception;

/**
 * @author zhouchengjie
 */
public class MyDbException extends Exception{

    private static final long serialVersionUID = 3455708526465670020L;

    public MyDbException(String msg){
        super(msg);
    }
}