package com.egg.manager.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

public class MyAspectApiImpl implements  MyAspectApi {

    @Override
    public Object handleAspect(ProceedingJoinPoint pjp, Method method) throws Throwable {
        return null;
    }
}
