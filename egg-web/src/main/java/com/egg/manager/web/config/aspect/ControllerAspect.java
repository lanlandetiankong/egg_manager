package com.egg.manager.web.config.aspect;


import com.alibaba.fastjson.JSON;
import com.egg.manager.service.annotation.log.OperLog;
import com.egg.manager.web.aspect.MyAspectApiImpl;
import com.egg.manager.web.aspect.log.RecordOperationLogAspe;
import com.egg.manager.common.base.enums.aspect.AspectNotifyTypeEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.persistence.entity.log.OperationLog;
import com.egg.manager.persistence.mapper.log.OperationLogMapper;
import com.egg.manager.service.service.CommonFuncService;
import com.egg.manager.service.service.aspect.ControllerAspectService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Configuration
public class ControllerAspect {
    @Autowired
    private OperationLogMapper operationLogMapper ;
    @Autowired
    private ControllerAspectService controllerAspectService ;
    @Autowired
    private CommonFuncService commonFuncService ;

    @Pointcut("execution(* com.egg.manager.web.controller..*(..)) ")
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
        //System.out.println("后。。。afterController");
    }


    @AfterReturning(value = "aspect()",returning = "result")
    public void afterControllerReturn(JoinPoint joinPoint,Object result) throws Throwable {
        Method method = controllerAspectService.gainReqMethod(joinPoint);
        //是否需要记录日志
        if(method.isAnnotationPresent(OperLog.class)) {
            OperLog operLog = method.getAnnotation(OperLog.class);
            if(operLog.flag() == true){
                OperationLog operationLog = new OperationLog();
                //当前log的通知方式是 Before
                operationLog.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue()) ;

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(operationLog,joinPoint,request) ;

                boolean isSuccess = true ;
                if(result != null){
                    if(result instanceof MyCommonResult){   //如果是封装的结果
                        MyCommonResult commonResult = (MyCommonResult) result ;
                        if(commonResult.isHasError() == true){
                            isSuccess = false ;
                            operationLog.setException(commonResult.getErrorMsg());
                        }
                    }
                    operationLog.setResult(JSON.toJSONString(result));
                }
                operationLog.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                operationLogMapper.insert(operationLog);
            }
        }
        //System.out.println("返回。。。afterControllerReturn");
    }



    @AfterThrowing(value = "aspect()",throwing = "exception")
    public void afterControllerThrowing(JoinPoint joinPoint, Exception exception) throws Throwable {
        Method method = controllerAspectService.gainReqMethod(joinPoint);
        //是否需要记录日志
        if(method.isAnnotationPresent(OperLog.class)) {
            OperLog operLog = method.getAnnotation(OperLog.class);
            if(operLog.flag() == true){
                OperationLog operationLog = new OperationLog();
                //当前log的通知方式是 Before
                operationLog.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue()) ;

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(operationLog,joinPoint,request) ;
                //请求失败
                operationLog.setIsSuccess(SwitchStateEnum.Close.getValue());
                if(exception != null){
                    operationLog.setException(JSON.toJSONString(exception));
                }
                operationLogMapper.insert(operationLog);
            }
        }
        //System.out.println("异常。。。afterControllerThrowing");
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
