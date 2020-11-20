package com.egg.manager.web.enhance.aop;


import com.alibaba.fastjson.JSON;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.enums.aspect.AspectNotifyTypeEnum;
import com.egg.manager.persistence.commons.base.enums.base.SwitchStateEnum;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebLoginLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebOperationLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebQueryLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.repository.pc.web.PcWebLoginLogRepository;
import com.egg.manager.persistence.em.logs.db.mongo.repository.pc.web.PcWebOperationLogRepository;
import com.egg.manager.persistence.em.logs.db.mongo.repository.pc.web.PcWebQueryLogRepository;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebLoginLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.web.enhance.wservices.wservice.aspect.web.ControllerAspectService;
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
public class ControllerAspect {
    @Autowired
    private PcWebQueryLogRepository pcWebQueryLogRepository;

    @Autowired
    private PcWebOperationLogRepository pcWebOperationLogRepository;
    @Autowired
    private PcWebLoginLogRepository pcWebLoginLogRepository;

    @Autowired
    private ControllerAspectService controllerAspectService;


    @Pointcut("execution(* com.egg.manager.web.controller..*(..)) ")
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
        Method method = controllerAspectService.gainReqMethod(joinPoint);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //是否需要记录[查询]日志
        if (method.isAnnotationPresent(PcWebQueryLog.class)) {
            PcWebQueryLog queryLogAnno = method.getAnnotation(PcWebQueryLog.class);
            //如果为false才接受其他注解的值，只要其中一个@Log为true都可以
            printWatchFlag = ifNullGetOther(printWatchFlag, queryLogAnno.printWatchFlag());
            if (queryLogAnno.flag() == true) {
                PcWebQueryLogMgo pcWebQueryLogMgo = new PcWebQueryLogMgo();
                //当前log的通知方式是 Before
                pcWebQueryLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                controllerAspectService.dealSetValToQueryLog(pcWebQueryLogMgo, joinPoint, request, queryLogAnno);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof WebResult) {
                        //如果是封装的结果
                        WebResult webResult = (WebResult) result;
                        if (webResult.isHasError() == true) {
                            isSuccess = false;
                            pcWebQueryLogMgo.setException(webResult.getMsg());
                        }
                    }
                    pcWebQueryLogMgo.setResult(JSON.toJSONString(result));
                }
                pcWebQueryLogMgo.setTotalSpendTime(watch.getTotalTimeMillis());
                pcWebQueryLogMgo.setStopWatchPrint(watch.prettyPrint());
                pcWebQueryLogMgo.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebQueryLogRepository.insert(pcWebQueryLogMgo);
            }
        }

        //是否需要记录[操作]日志
        if (method.isAnnotationPresent(PcWebOperationLog.class)) {
            PcWebOperationLog operationLogAnno = method.getAnnotation(PcWebOperationLog.class);
            //如果为false才接受其他注解的值，只要其中一个@Log为true都可以
            printWatchFlag = ifNullGetOther(printWatchFlag, operationLogAnno.printWatchFlag());
            if (operationLogAnno.flag() == true) {
                PcWebOperationLogMgo pcWebOperationLogMgo = new PcWebOperationLogMgo();
                //当前log的通知方式是 Before
                pcWebOperationLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(pcWebOperationLogMgo, joinPoint, request, operationLogAnno);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof WebResult) {
                        //如果是封装的结果
                        WebResult webResult = (WebResult) result;
                        if (webResult.isHasError() == true) {
                            isSuccess = false;
                            pcWebOperationLogMgo.setException(webResult.getMsg());
                        }
                    }
                    pcWebOperationLogMgo.setResult(JSON.toJSONString(result));
                }
                pcWebOperationLogMgo.setTotalSpendTime(watch.getTotalTimeMillis());
                pcWebOperationLogMgo.setStopWatchPrint(watch.prettyPrint());
                pcWebOperationLogMgo.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebOperationLogRepository.insert(pcWebOperationLogMgo);
            }
        }
        //是否需要记录[登录]日志
        if (method.isAnnotationPresent(PcWebLoginLog.class)) {
            PcWebLoginLog loginLogAnno = method.getAnnotation(PcWebLoginLog.class);
            //如果为false才接受其他注解的值，只要其中一个@Log为true都可以
            printWatchFlag = ifNullGetOther(printWatchFlag, loginLogAnno.printWatchFlag());
            if (loginLogAnno.flag() == true) {
                PcWebLoginLogMgo pcWebLoginLogMgo = new PcWebLoginLogMgo();
                //当前log的通知方式是 Before
                pcWebLoginLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                controllerAspectService.dealSetValToLoginLog(pcWebLoginLogMgo, joinPoint, request, loginLogAnno);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof WebResult) {
                        //如果是封装的结果
                        WebResult webResult = (WebResult) result;
                        if (webResult.isHasError() == true) {
                            isSuccess = false;
                            pcWebLoginLogMgo.setException(webResult.getMsg());
                        }
                    }
                    pcWebLoginLogMgo.setResult(JSON.toJSONString(result));
                }
                pcWebLoginLogMgo.setTotalSpendTime(watch.getTotalTimeMillis());
                pcWebLoginLogMgo.setStopWatchPrint(watch.prettyPrint());
                pcWebLoginLogMgo.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebLoginLogRepository.insert(pcWebLoginLogMgo);
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
        Method method = controllerAspectService.gainReqMethod(joinPoint);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //是否需要记录[查询]日志
        if (method.isAnnotationPresent(PcWebQueryLog.class)) {
            PcWebQueryLog queryLogAnno = method.getAnnotation(PcWebQueryLog.class);
            if (queryLogAnno.flag() == true) {
                PcWebQueryLogMgo pcWebQueryLogMgo = new PcWebQueryLogMgo();
                //当前log的通知方式是 AfterThrowing
                pcWebQueryLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                controllerAspectService.dealSetValToQueryLog(pcWebQueryLogMgo, joinPoint, request, queryLogAnno);
                //请求失败
                pcWebQueryLogMgo.setIsSuccess(SwitchStateEnum.Close.getValue());
                if (exception != null) {
                    pcWebQueryLogMgo.setException(JSON.toJSONString(exception));
                }
                pcWebQueryLogRepository.insert(pcWebQueryLogMgo);
            }
        }
        //是否需要记录[操作]日志
        if (method.isAnnotationPresent(PcWebOperationLog.class)) {
            PcWebOperationLog operationLogAnno = method.getAnnotation(PcWebOperationLog.class);
            if (operationLogAnno.flag() == true) {
                PcWebOperationLogMgo pcWebOperationLogMgo = new PcWebOperationLogMgo();
                //当前log的通知方式是 AfterThrowing
                pcWebOperationLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(pcWebOperationLogMgo, joinPoint, request, operationLogAnno);
                //请求失败
                pcWebOperationLogMgo.setIsSuccess(SwitchStateEnum.Close.getValue());
                if (exception != null) {
                    pcWebOperationLogMgo.setException(JSON.toJSONString(exception));
                }
                pcWebOperationLogRepository.insert(pcWebOperationLogMgo);
            }
        }
        //是否需要记录[登录]日志
        if (method.isAnnotationPresent(PcWebLoginLog.class)) {
            PcWebLoginLog loginLogAnno = method.getAnnotation(PcWebLoginLog.class);
            if (loginLogAnno.flag() == true) {
                PcWebLoginLogMgo pcWebLoginLogMgo = new PcWebLoginLogMgo();
                //当前log的通知方式是 AfterThrowing
                pcWebLoginLogMgo.setAspectNotifyType(aspectNotifyType);
                //设置一些必要值到log
                controllerAspectService.dealSetValToLoginLog(pcWebLoginLogMgo, joinPoint, request, loginLogAnno);
                //请求失败
                pcWebLoginLogMgo.setIsSuccess(SwitchStateEnum.Close.getValue());
                if (exception != null) {
                    pcWebLoginLogMgo.setException(JSON.toJSONString(exception));
                }
                pcWebLoginLogRepository.insert(pcWebLoginLogMgo);
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
