package com.egg.manager.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

public interface MyAspectApi {
    Object handleAspect(ProceedingJoinPoint pjp,Method method) throws Throwable ;

}
