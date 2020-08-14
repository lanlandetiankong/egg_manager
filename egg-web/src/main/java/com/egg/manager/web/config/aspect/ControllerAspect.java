package com.egg.manager.web.config.aspect;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.egg.manager.api.service.annotation.log.OperLog;
import com.egg.manager.api.service.service.CommonFuncService;
import com.egg.manager.api.service.service.aspect.ControllerAspectService;
import com.egg.manager.common.base.enums.aspect.AspectNotifyTypeEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.persistence.mongo.dao.log.OperationLogRepository;
import com.egg.manager.persistence.mongo.mo.log.OperationLogMO;
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
    private OperationLogRepository operationLogRepository;
    @Reference
    private ControllerAspectService controllerAspectService;
    @Reference
    private CommonFuncService commonFuncService;

    @Pointcut("execution(* com.egg.manager.web.controller..*(..)) ")
    public void aspect() {
    }


    @Before(value = "aspect()")
    public void beforeController(JoinPoint joinPoint) throws Throwable {
        Method method = controllerAspectService.gainReqMethod(joinPoint);
        //是否需要记录日志
        if (method.isAnnotationPresent(OperLog.class)) {
            OperLog operLog = method.getAnnotation(OperLog.class);
            if (operLog.flag() == true) {
                OperationLogMO operationLogMO = new OperationLogMO();
                //当前log的通知方式是 Before
                operationLogMO.setAspectNotifyType(AspectNotifyTypeEnum.Before.getValue());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(operationLogMO, joinPoint, request);
                //请求成功(至少在before
                operationLogMO.setIsSuccess(SwitchStateEnum.Open.getValue());
                operationLogRepository.insert(operationLogMO);
            }
        }

    }


    @After(value = "aspect()")
    public void afterController(JoinPoint joinPoint) throws Throwable {
        //System.out.println("后。。。afterController");
    }


    @AfterReturning(value = "aspect()", returning = "result")
    public void afterControllerReturn(JoinPoint joinPoint, Object result) throws Throwable {
        Method method = controllerAspectService.gainReqMethod(joinPoint);
        //是否需要记录日志
        if (method.isAnnotationPresent(OperLog.class)) {
            OperLog operLog = method.getAnnotation(OperLog.class);
            if (operLog.flag() == true) {
                OperationLogMO operationLogMO = new OperationLogMO();
                //当前log的通知方式是 Before
                operationLogMO.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(operationLogMO, joinPoint, request);

                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof MyCommonResult) {   //如果是封装的结果
                        MyCommonResult commonResult = (MyCommonResult) result;
                        if (commonResult.isHasError() == true) {
                            isSuccess = false;
                            operationLogMO.setException(commonResult.getErrorMsg());
                        }
                    }
                    operationLogMO.setResult(JSON.toJSONString(result));
                }
                operationLogMO.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                operationLogRepository.insert(operationLogMO);
            }
        }
        //System.out.println("返回。。。afterControllerReturn");
    }


    @AfterThrowing(value = "aspect()", throwing = "exception")
    public void afterControllerThrowing(JoinPoint joinPoint, Exception exception) throws Throwable {
        Method method = controllerAspectService.gainReqMethod(joinPoint);
        //是否需要记录日志
        if (method.isAnnotationPresent(OperLog.class)) {
            OperLog operLog = method.getAnnotation(OperLog.class);
            if (operLog.flag() == true) {
                OperationLogMO operationLogMO = new OperationLogMO();
                //当前log的通知方式是 Before
                operationLogMO.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(operationLogMO, joinPoint, request);
                //请求失败
                operationLogMO.setIsSuccess(SwitchStateEnum.Close.getValue());
                if (exception != null) {
                    operationLogMO.setException(JSON.toJSONString(exception));
                }
                operationLogRepository.insert(operationLogMO);
            }
        }
        //System.out.println("异常。。。afterControllerThrowing");
    }


    /**
     * 根据方法名取得对应 Method
     *
     * @param joinPoint
     * @param methodName
     * @return
     */
    private Method currentMethod(ProceedingJoinPoint joinPoint, String methodName) {
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Method resultMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }

}
