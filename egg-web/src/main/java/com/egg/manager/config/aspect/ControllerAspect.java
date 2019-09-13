package com.egg.manager.config.aspect;


import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.aspect.MyAspectApiImpl;
import com.egg.manager.aspect.log.RecordOperationLogAspe;
import com.egg.manager.common.util.str.ComUtil;
import com.egg.manager.common.util.str.StringUtil;
import com.egg.manager.entity.log.OperationLog;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Aspect
@Configuration
public class ControllerAspect {

    @Pointcut("execution(* com.egg.manager.controller..*(..)) ")
    public void aspect() {}

    @Around(value = "aspect()")
    public Object validationPoint(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("进入切面");
        Method method = currentMethod(pjp,pjp.getSignature().getName()) ;
        //创建被装饰者
        MyAspectApiImpl aspectApiImpl = new MyAspectApiImpl() ;
        //是否需要记录日志
        if(method.isAnnotationPresent(OperLog.class)) {
            return new RecordOperationLogAspe(aspectApiImpl).handleAspect(pjp,method);
        }
        return pjp.proceed(pjp.getArgs());



    }



    private Method currentMethod ( ProceedingJoinPoint joinPoint , String methodName ) {
        Method[] methods = joinPoint.getTarget().getClass().getMethods() ;
        Method resultMethod =  null ;
        for (Method method : methods){
            if(method.getName().equals(methodName)){
                resultMethod = method ;
                break;
            }
        }
        return resultMethod ;
    }

}
