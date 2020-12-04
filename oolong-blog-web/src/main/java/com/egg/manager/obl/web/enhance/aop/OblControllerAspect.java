package com.egg.manager.obl.web.enhance.aop;


import com.alibaba.fastjson.JSON;
import com.egg.manager.obl.web.enhance.wservices.wservice.aspect.OblControllerAspectService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.enums.basic.AspectNotifyTypeEnum;
import com.egg.manager.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebLoginLog;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebQueryLog;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebLoginLogMgo;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebOperationLogMgo;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebQueryLogMgo;
import com.egg.manager.persistence.obl.log.db.mongo.repository.OblPcWebLoginLogRepository;
import com.egg.manager.persistence.obl.log.db.mongo.repository.OblPcWebOperationLogRepository;
import com.egg.manager.persistence.obl.log.db.mongo.repository.OblPcWebQueryLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author zhoucj
 * @description @Controller层进行日志切面，并记录到mongodb
 * @date 2020/10/20
 */
@Slf4j
@Aspect
@Configuration
public class OblControllerAspect {
    @Autowired
    private OblPcWebQueryLogRepository pcWebQueryLogRepository;

    @Autowired
    private OblPcWebOperationLogRepository pcWebOperationLogRepository;
    @Autowired
    private OblPcWebLoginLogRepository pcWebLoginLogRepository;

    @Autowired
    private OblControllerAspectService oblControllerAspectService;


    @Pointcut("execution(* com.egg.manager.obl.web.controller..*(..)) ")
    public void aspect() {
    }


    @After(value = "aspect()")
    public void afterController(JoinPoint joinPoint) throws Throwable {
    }


    @Around(value = "aspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //方法返回值
        Object result = null;
        boolean defaultFlag = true;
        //计时器
        StopWatch watch = new StopWatch();
        watch.start();
        if (defaultFlag) {
            //调用目标方法，如果没调用这个则表示拦截实际的方法调用。
            result = joinPoint.proceed();
        } else {
            return null;
        }
        watch.stop();
        boolean printWatchFlag = false;
        //通知类型
        final String aspectNotifyType = AspectNotifyTypeEnum.Around.getValue();
        Method method = oblControllerAspectService.gainReqMethod(joinPoint);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //是否需要记录[查询]日志
        if (method.isAnnotationPresent(OblPcWebQueryLog.class)) {
            OblPcWebQueryLog queryLogAnno = method.getAnnotation(OblPcWebQueryLog.class);
            //如果为false才接受其他注解的值，只要其中一个@Log为true都可以
            printWatchFlag = ifNullGetOther(printWatchFlag, queryLogAnno.printWatchFlag());
            if (queryLogAnno.flag() == true) {
                OblPcWebQueryLogMgo oblPcWebQueryLogMgo = new OblPcWebQueryLogMgo();
                //当前log的通知方式是 Before
                oblPcWebQueryLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                oblControllerAspectService.dealSetValToQueryLog(oblPcWebQueryLogMgo, joinPoint, request, queryLogAnno);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof WebResult) {
                        //如果是封装的结果
                        WebResult webResult = (WebResult) result;
                        if (webResult.isSuccess() == false) {
                            isSuccess = false;
                            oblPcWebQueryLogMgo.setException(webResult.getMsg());
                        }
                    }
                    oblPcWebQueryLogMgo.setResult(JSON.toJSONString(result));
                }
                oblPcWebQueryLogMgo.setTotalSpendTime(watch.getTotalTimeMillis());
                oblPcWebQueryLogMgo.setStopWatchPrint(watch.prettyPrint());
                oblPcWebQueryLogMgo.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebQueryLogRepository.insert(oblPcWebQueryLogMgo);
            }
        }

        //是否需要记录[操作]日志
        if (method.isAnnotationPresent(OblPcWebOperationLog.class)) {
            OblPcWebOperationLog operationLogAnno = method.getAnnotation(OblPcWebOperationLog.class);
            //如果为false才接受其他注解的值，只要其中一个@Log为true都可以
            printWatchFlag = ifNullGetOther(printWatchFlag, operationLogAnno.printWatchFlag());
            if (operationLogAnno.flag() == true) {
                OblPcWebOperationLogMgo oblPcWebOperationLogMgo = new OblPcWebOperationLogMgo();
                //当前log的通知方式是 Before
                oblPcWebOperationLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                oblControllerAspectService.dealSetValToOperationLog(oblPcWebOperationLogMgo, joinPoint, request, operationLogAnno);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof WebResult) {
                        //如果是封装的结果
                        WebResult webResult = (WebResult) result;
                        if (webResult.isSuccess() == false) {
                            isSuccess = false;
                            oblPcWebOperationLogMgo.setException(webResult.getMsg());
                        }
                    }
                    oblPcWebOperationLogMgo.setResult(JSON.toJSONString(result));
                }
                oblPcWebOperationLogMgo.setTotalSpendTime(watch.getTotalTimeMillis());
                oblPcWebOperationLogMgo.setStopWatchPrint(watch.prettyPrint());
                oblPcWebOperationLogMgo.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebOperationLogRepository.insert(oblPcWebOperationLogMgo);
            }
        }
        //是否需要记录[登录]日志
        if (method.isAnnotationPresent(OblPcWebLoginLog.class)) {
            OblPcWebLoginLog loginLogAnno = method.getAnnotation(OblPcWebLoginLog.class);
            //如果为false才接受其他注解的值，只要其中一个@Log为true都可以
            printWatchFlag = ifNullGetOther(printWatchFlag, loginLogAnno.printWatchFlag());
            if (loginLogAnno.flag() == true) {
                OblPcWebLoginLogMgo oblPcWebLoginLogMgo = new OblPcWebLoginLogMgo();
                //当前log的通知方式是 Before
                oblPcWebLoginLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                oblControllerAspectService.dealSetValToLoginLog(oblPcWebLoginLogMgo, joinPoint, request, loginLogAnno);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof WebResult) {
                        //如果是封装的结果
                        WebResult webResult = (WebResult) result;
                        if (webResult.isSuccess() == false) {
                            isSuccess = false;
                            oblPcWebLoginLogMgo.setException(webResult.getMsg());
                        }
                    }
                    oblPcWebLoginLogMgo.setResult(JSON.toJSONString(result));
                }
                oblPcWebLoginLogMgo.setTotalSpendTime(watch.getTotalTimeMillis());
                oblPcWebLoginLogMgo.setStopWatchPrint(watch.prettyPrint());
                oblPcWebLoginLogMgo.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebLoginLogRepository.insert(oblPcWebLoginLogMgo);
            }
        }
        //上方设置该接口可以打印日志计时器记录时才打印。
        if (printWatchFlag) {
            log.info(watch.prettyPrint());
        }
        return result;
    }


    @AfterThrowing(value = "aspect()", throwing = "exception")
    public void afterControllerThrowing(JoinPoint joinPoint, Exception exception) throws Throwable {
        //通知类型
        final String aspectNotifyType = AspectNotifyTypeEnum.AfterThrowing.getValue();
        Method method = oblControllerAspectService.gainReqMethod(joinPoint);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //是否需要记录[查询]日志
        if (method.isAnnotationPresent(OblPcWebQueryLog.class)) {
            OblPcWebQueryLog queryLogAnno = method.getAnnotation(OblPcWebQueryLog.class);
            if (queryLogAnno.flag() == true) {
                OblPcWebQueryLogMgo oblPcWebQueryLogMgo = new OblPcWebQueryLogMgo();
                //当前log的通知方式是 AfterThrowing
                oblPcWebQueryLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                oblControllerAspectService.dealSetValToQueryLog(oblPcWebQueryLogMgo, joinPoint, request, queryLogAnno);
                //请求失败
                oblPcWebQueryLogMgo.setIsSuccess(SwitchStateEnum.Close.getValue());
                if (exception != null) {
                    oblPcWebQueryLogMgo.setException(JSON.toJSONString(exception));
                }
                pcWebQueryLogRepository.insert(oblPcWebQueryLogMgo);
            }
        }
        //是否需要记录[操作]日志
        if (method.isAnnotationPresent(OblPcWebOperationLog.class)) {
            OblPcWebOperationLog operationLogAnno = method.getAnnotation(OblPcWebOperationLog.class);
            if (operationLogAnno.flag() == true) {
                OblPcWebOperationLogMgo oblPcWebOperationLogMgo = new OblPcWebOperationLogMgo();
                //当前log的通知方式是 AfterThrowing
                oblPcWebOperationLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                oblControllerAspectService.dealSetValToOperationLog(oblPcWebOperationLogMgo, joinPoint, request, operationLogAnno);
                //请求失败
                oblPcWebOperationLogMgo.setIsSuccess(SwitchStateEnum.Close.getValue());
                if (exception != null) {
                    oblPcWebOperationLogMgo.setException(JSON.toJSONString(exception));
                }
                pcWebOperationLogRepository.insert(oblPcWebOperationLogMgo);
            }
        }
        //是否需要记录[登录]日志
        if (method.isAnnotationPresent(OblPcWebLoginLog.class)) {
            OblPcWebLoginLog loginLogAnno = method.getAnnotation(OblPcWebLoginLog.class);
            if (loginLogAnno.flag() == true) {
                OblPcWebLoginLogMgo oblPcWebLoginLogMgo = new OblPcWebLoginLogMgo();
                //当前log的通知方式是 AfterThrowing
                oblPcWebLoginLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                oblControllerAspectService.dealSetValToLoginLog(oblPcWebLoginLogMgo, joinPoint, request, loginLogAnno);
                //请求失败
                oblPcWebLoginLogMgo.setIsSuccess(SwitchStateEnum.Close.getValue());
                if (exception != null) {
                    oblPcWebLoginLogMgo.setException(JSON.toJSONString(exception));
                }
                pcWebLoginLogRepository.insert(oblPcWebLoginLogMgo);
            }
        }
    }


    /**
     * 根据方法名取得对应 Method
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


    private boolean ifNullGetOther(boolean value, boolean other) {
        return value ? value : other;
    }

}
