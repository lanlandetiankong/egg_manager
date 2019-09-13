package com.egg.manager.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

public interface MyAspectApi {
    Object handleAspect(ProceedingJoinPoint pjp,Method method) throws Throwable ;

}
