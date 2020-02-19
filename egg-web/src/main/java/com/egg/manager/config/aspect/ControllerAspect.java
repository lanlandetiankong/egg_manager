package com.egg.manager.config.aspect;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.aspect.MyAspectApiImpl;
import com.egg.manager.aspect.log.RecordOperationLogAspe;
import com.egg.manager.common.base.beans.request.RequestHeaderBean;
import com.egg.manager.common.base.enums.aspect.AspectNotifyTypeEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.entity.log.OperationLog;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.log.OperationLogMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.aspect.ControllerAspectService;
import com.egg.manager.webvo.session.UserAccountToken;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@Aspect
@Configuration
public class ControllerAspect {
    @Autowired
    private OperationLogMapper operationLogMapper ;
    @Autowired
    private ControllerAspectService controllerAspectService ;
    @Autowired
    private CommonFuncService commonFuncService ;

    @Pointcut("execution(* com.egg.manager.controller..*(..)) ")
    public void aspect() {}


    @Before(value = "aspect()")
    public void beforeController(JoinPoint joinPoint) throws Throwable {
        Method method = controllerAspectService.gainReqMethod(joinPoint);
        //是否需要记录日志
        if(method.isAnnotationPresent(OperLog.class)) {
            OperLog operLog = method.getAnnotation(OperLog.class);
            if(operLog.flag() == true){
                OperationLog operationLog = new OperationLog();
                //当前log的通知方式是 Before
                operationLog.setAspectNotifyType(AspectNotifyTypeEnum.Before.getValue()) ;

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(operationLog,joinPoint,request) ;
                //请求成功(至少在before
                operationLog.setIsSuccess(SwitchStateEnum.Open.getValue());
                operationLogMapper.insert(operationLog);
            }
        }

    }


    @After(value = "aspect()")
    public void afterController(JoinPoint joinPoint) throws Throwable {
        System.out.println("后。。。afterController");
    }


    @AfterReturning(value = "aspect()",returning = "result")
    public void afterControllerReturn(JoinPoint joinPoint,Object result) throws Throwable {
        System.out.println("返回。。。afterControllerReturn");
    }



    @AfterThrowing(value = "aspect()",throwing = "exception")
    public void afterControllerThrowing(JoinPoint joinPoint, Exception exception) throws Throwable {
        System.out.println("异常。。。afterControllerThrowing");
    }


    public Object validationPoint(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕。。。。进入切面");
        Method method = currentMethod(pjp,pjp.getSignature().getName()) ;
        //创建被装饰者
        MyAspectApiImpl aspectApiImpl = new MyAspectApiImpl() ;


        //是否需要记录日志
        if(method.isAnnotationPresent(OperLog.class)) {
            return new RecordOperationLogAspe(aspectApiImpl).handleAspect(pjp,method);
        }
        return pjp.proceed(pjp.getArgs());
    }



    /**
     * 根据方法名取得对应 Method
     * @param joinPoint
     * @param methodName
     * @return
     */
    private Method currentMethod(ProceedingJoinPoint joinPoint , String methodName ) {
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
