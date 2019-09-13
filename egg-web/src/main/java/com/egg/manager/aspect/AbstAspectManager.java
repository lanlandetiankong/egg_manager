package com.egg.manager.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

public abstract class AbstAspectManager implements MyAspectApi{

    private MyAspectApi aspectApi ;
    public AbstAspectManager(MyAspectApi aspectApi) {
        this.aspectApi = aspectApi ;
    }

    @Override
    public Object handleAspect(ProceedingJoinPoint pjp,Method method) throws Throwable {
        return this.aspectApi.handleAspect(pjp,method) ;
    }

    protected abstract Object execute(ProceedingJoinPoint pjp, Method method)throws Throwable;
}
