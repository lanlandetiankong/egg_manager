package com.egg.manager.em.web.enhance.aop;


import com.alibaba.fastjson.JSON;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.enums.basic.AspectNotifyTypeEnum;
import com.egg.manager.facade.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.facade.persistence.em.logs.db.mongo.mo.EmPcWebLoginLogMgo;
import com.egg.manager.facade.persistence.em.logs.db.mongo.mo.EmPcWebOperationLogMgo;
import com.egg.manager.facade.persistence.em.logs.db.mongo.mo.EmPcWebQueryLogMgo;
import com.egg.manager.facade.persistence.em.logs.db.mongo.repository.EmPcWebLoginLogRepository;
import com.egg.manager.facade.persistence.em.logs.db.mongo.repository.EmPcWebOperationLogRepository;
import com.egg.manager.facade.persistence.em.logs.db.mongo.repository.EmPcWebQueryLogRepository;
import com.egg.manager.facade.persistence.enhance.annotation.log.em.EmPcWebLoginLog;
import com.egg.manager.facade.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.facade.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
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
public class EmControllerAspect {
    @Autowired
    private EmPcWebQueryLogRepository pcWebQueryLogRepository;

    @Autowired
    private EmPcWebOperationLogRepository pcWebOperationLogRepository;
    @Autowired
    private EmPcWebLoginLogRepository pcWebLoginLogRepository;

    @Autowired
    private EggAspectTool eggAspectTool;


    @Pointcut("execution(* com.egg.manager.em.web.controller..*(..)) ")
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
        Method method = eggAspectTool.gainReqMethod(joinPoint);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //是否需要记录[查询]日志
        if (method.isAnnotationPresent(EmPcWebQueryLog.class)) {
            EmPcWebQueryLog queryLogAnno = method.getAnnotation(EmPcWebQueryLog.class);
            //如果为false才接受其他注解的值，只要其中一个@Log为true都可以
            printWatchFlag = ifNullGetOther(printWatchFlag, queryLogAnno.printWatchFlag());
            if (queryLogAnno.flag() == true) {
                EmPcWebQueryLogMgo emPcWebQueryLogMgo = new EmPcWebQueryLogMgo();
                //当前log的通知方式是 Before
                emPcWebQueryLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                eggAspectTool.dealSetValToQueryLog(emPcWebQueryLogMgo, joinPoint, request, queryLogAnno);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof WebResult) {
                        //如果是封装的结果
                        WebResult webResult = (WebResult) result;
                        if (webResult.isSuccess() == false) {
                            isSuccess = false;
                            emPcWebQueryLogMgo.setException(webResult.getMsg());
                        }
                    }
                    emPcWebQueryLogMgo.setResult(JSON.toJSONString(result));
                }
                emPcWebQueryLogMgo.setTotalSpendTime(watch.getTotalTimeMillis());
                emPcWebQueryLogMgo.setStopWatchPrint(watch.prettyPrint());
                emPcWebQueryLogMgo.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebQueryLogRepository.insert(emPcWebQueryLogMgo);
            }
        }

        //是否需要记录[操作]日志
        if (method.isAnnotationPresent(EmPcWebOperationLog.class)) {
            EmPcWebOperationLog operationLogAnno = method.getAnnotation(EmPcWebOperationLog.class);
            //如果为false才接受其他注解的值，只要其中一个@Log为true都可以
            printWatchFlag = ifNullGetOther(printWatchFlag, operationLogAnno.printWatchFlag());
            if (operationLogAnno.flag() == true) {
                EmPcWebOperationLogMgo emPcWebOperationLogMgo = new EmPcWebOperationLogMgo();
                //当前log的通知方式是 Before
                emPcWebOperationLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                eggAspectTool.dealSetValToOperationLog(emPcWebOperationLogMgo, joinPoint, request, operationLogAnno);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof WebResult) {
                        //如果是封装的结果
                        WebResult webResult = (WebResult) result;
                        if (webResult.isSuccess() == false) {
                            isSuccess = false;
                            emPcWebOperationLogMgo.setException(webResult.getMsg());
                        }
                    }
                    emPcWebOperationLogMgo.setResult(JSON.toJSONString(result));
                }
                emPcWebOperationLogMgo.setTotalSpendTime(watch.getTotalTimeMillis());
                emPcWebOperationLogMgo.setStopWatchPrint(watch.prettyPrint());
                emPcWebOperationLogMgo.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebOperationLogRepository.insert(emPcWebOperationLogMgo);
            }
        }
        //是否需要记录[登录]日志
        if (method.isAnnotationPresent(EmPcWebLoginLog.class)) {
            EmPcWebLoginLog loginLogAnno = method.getAnnotation(EmPcWebLoginLog.class);
            //如果为false才接受其他注解的值，只要其中一个@Log为true都可以
            printWatchFlag = ifNullGetOther(printWatchFlag, loginLogAnno.printWatchFlag());
            if (loginLogAnno.flag() == true) {
                EmPcWebLoginLogMgo emPcWebLoginLogMgo = new EmPcWebLoginLogMgo();
                //当前log的通知方式是 Before
                emPcWebLoginLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                eggAspectTool.dealSetValToLoginLog(emPcWebLoginLogMgo, joinPoint, request, loginLogAnno);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof WebResult) {
                        //如果是封装的结果
                        WebResult webResult = (WebResult) result;
                        if (webResult.isSuccess() == false) {
                            isSuccess = false;
                            emPcWebLoginLogMgo.setException(webResult.getMsg());
                        }
                    }
                    emPcWebLoginLogMgo.setResult(JSON.toJSONString(result));
                }
                emPcWebLoginLogMgo.setTotalSpendTime(watch.getTotalTimeMillis());
                emPcWebLoginLogMgo.setStopWatchPrint(watch.prettyPrint());
                emPcWebLoginLogMgo.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebLoginLogRepository.insert(emPcWebLoginLogMgo);
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
        Method method = eggAspectTool.gainReqMethod(joinPoint);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //是否需要记录[查询]日志
        if (method.isAnnotationPresent(EmPcWebQueryLog.class)) {
            EmPcWebQueryLog queryLogAnno = method.getAnnotation(EmPcWebQueryLog.class);
            if (queryLogAnno.flag() == true) {
                EmPcWebQueryLogMgo emPcWebQueryLogMgo = new EmPcWebQueryLogMgo();
                //当前log的通知方式是 AfterThrowing
                emPcWebQueryLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                eggAspectTool.dealSetValToQueryLog(emPcWebQueryLogMgo, joinPoint, request, queryLogAnno);
                //请求失败
                emPcWebQueryLogMgo.setIsSuccess(SwitchStateEnum.Close.getValue());
                if (exception != null) {
                    emPcWebQueryLogMgo.setException(JSON.toJSONString(exception));
                }
                pcWebQueryLogRepository.insert(emPcWebQueryLogMgo);
            }
        }
        //是否需要记录[操作]日志
        if (method.isAnnotationPresent(EmPcWebOperationLog.class)) {
            EmPcWebOperationLog operationLogAnno = method.getAnnotation(EmPcWebOperationLog.class);
            if (operationLogAnno.flag() == true) {
                EmPcWebOperationLogMgo emPcWebOperationLogMgo = new EmPcWebOperationLogMgo();
                //当前log的通知方式是 AfterThrowing
                emPcWebOperationLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                eggAspectTool.dealSetValToOperationLog(emPcWebOperationLogMgo, joinPoint, request, operationLogAnno);
                //请求失败
                emPcWebOperationLogMgo.setIsSuccess(SwitchStateEnum.Close.getValue());
                if (exception != null) {
                    emPcWebOperationLogMgo.setException(JSON.toJSONString(exception));
                }
                pcWebOperationLogRepository.insert(emPcWebOperationLogMgo);
            }
        }
        //是否需要记录[登录]日志
        if (method.isAnnotationPresent(EmPcWebLoginLog.class)) {
            EmPcWebLoginLog loginLogAnno = method.getAnnotation(EmPcWebLoginLog.class);
            if (loginLogAnno.flag() == true) {
                EmPcWebLoginLogMgo emPcWebLoginLogMgo = new EmPcWebLoginLogMgo();
                //当前log的通知方式是 AfterThrowing
                emPcWebLoginLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                eggAspectTool.dealSetValToLoginLog(emPcWebLoginLogMgo, joinPoint, request, loginLogAnno);
                //请求失败
                emPcWebLoginLogMgo.setIsSuccess(SwitchStateEnum.Close.getValue());
                if (exception != null) {
                    emPcWebLoginLogMgo.setException(JSON.toJSONString(exception));
                }
                pcWebLoginLogRepository.insert(emPcWebLoginLogMgo);
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
