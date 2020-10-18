package com.egg.manager.web.config.aspect;


import com.alibaba.fastjson.JSON;
import com.egg.manager.common.annotation.log.pc.web.PcWebLoginLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.base.enums.aspect.AspectNotifyTypeEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebLoginLogMgo;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebOperationLogMgo;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMgo;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebLoginLogRepository;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebOperationLogRepository;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebQueryLogRepository;
import com.egg.manager.web.wservices.wservice.aspect.ControllerAspectService;
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


    @Before(value = "aspect()")
    public void beforeController(JoinPoint joinPoint) throws Throwable {
        Method method = controllerAspectService.gainReqMethod(joinPoint);
        //是否需要记录查询日志
        if (method.isAnnotationPresent(PcWebQueryLog.class)) {
            PcWebQueryLog pcWebQueryLog = method.getAnnotation(PcWebQueryLog.class);
            if (pcWebQueryLog.flag() == true) {
                PcWebQueryLogMgo pcWebQueryLogMgo = new PcWebQueryLogMgo();

                //当前log的通知方式是 Before
                pcWebQueryLogMgo.setAspectNotifyType(AspectNotifyTypeEnum.Before.getValue());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToQueryLog(pcWebQueryLogMgo, joinPoint, request);
                //请求成功(至少在before
                pcWebQueryLogMgo.setIsSuccess(SwitchStateEnum.Open.getValue());
                pcWebQueryLogRepository.insert(pcWebQueryLogMgo);
            }
        }

        //是否需要记录[操作]日志
        if (method.isAnnotationPresent(PcWebOperationLog.class)) {
            PcWebOperationLog pcWebOperationLog = method.getAnnotation(PcWebOperationLog.class);
            if (pcWebOperationLog.flag() == true) {
                PcWebOperationLogMgo pcWebOperationLogMgo = new PcWebOperationLogMgo();

                //当前log的通知方式是 Before
                pcWebOperationLogMgo.setAspectNotifyType(AspectNotifyTypeEnum.Before.getValue());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(pcWebOperationLogMgo, joinPoint, request);
                //请求成功(至少在before
                pcWebOperationLogMgo.setIsSuccess(SwitchStateEnum.Open.getValue());
                pcWebOperationLogRepository.insert(pcWebOperationLogMgo);
            }
        }

        //pcWebLoginLogRepository
        //是否需要记录[操作]日志
        if (method.isAnnotationPresent(PcWebLoginLog.class)) {
            PcWebLoginLog pcWebLoginLog = method.getAnnotation(PcWebLoginLog.class);
            if (pcWebLoginLog.flag() == true) {
                PcWebLoginLogMgo pcWebLoginLogMgo = new PcWebLoginLogMgo();
                //当前log的通知方式是 Before
                pcWebLoginLogMgo.setAspectNotifyType(AspectNotifyTypeEnum.Before.getValue());
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToLoginLog(pcWebLoginLogMgo, joinPoint, request);
                //请求成功(至少在before
                pcWebLoginLogMgo.setIsSuccess(SwitchStateEnum.Open.getValue());
                pcWebLoginLogRepository.insert(pcWebLoginLogMgo);
            }
        }
    }


    @After(value = "aspect()")
    public void afterController(JoinPoint joinPoint) throws Throwable {
    }


    @AfterReturning(value = "aspect()", returning = "result")
    public void afterControllerReturn(JoinPoint joinPoint, Object result) throws Throwable {
        Method method = controllerAspectService.gainReqMethod(joinPoint);
        //是否需要记录[查询]日志
        if (method.isAnnotationPresent(PcWebQueryLog.class)) {
            PcWebQueryLog pcWebQueryLog = method.getAnnotation(PcWebQueryLog.class);
            if (pcWebQueryLog.flag() == true) {
                PcWebQueryLogMgo pcWebQueryLogMgo = new PcWebQueryLogMgo();
                //当前log的通知方式是 Before
                pcWebQueryLogMgo.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToQueryLog(pcWebQueryLogMgo, joinPoint, request);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof MyCommonResult) {
                        //如果是封装的结果
                        MyCommonResult commonResult = (MyCommonResult) result;
                        if (commonResult.isHasError() == true) {
                            isSuccess = false;
                            pcWebQueryLogMgo.setException(commonResult.getMsg());
                        }
                    }
                    pcWebQueryLogMgo.setResult(JSON.toJSONString(result));
                }
                pcWebQueryLogMgo.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebQueryLogRepository.insert(pcWebQueryLogMgo);
            }
        }

        //是否需要记录[操作]日志
        if (method.isAnnotationPresent(PcWebOperationLog.class)) {
            PcWebOperationLog pcWebOperationLog = method.getAnnotation(PcWebOperationLog.class);
            if (pcWebOperationLog.flag() == true) {
                PcWebOperationLogMgo pcWebOperationLogMgo = new PcWebOperationLogMgo();
                //当前log的通知方式是 Before
                pcWebOperationLogMgo.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(pcWebOperationLogMgo, joinPoint, request);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof MyCommonResult) {
                        //如果是封装的结果
                        MyCommonResult commonResult = (MyCommonResult) result;
                        if (commonResult.isHasError() == true) {
                            isSuccess = false;
                            pcWebOperationLogMgo.setException(commonResult.getMsg());
                        }
                    }
                    pcWebOperationLogMgo.setResult(JSON.toJSONString(result));
                }
                pcWebOperationLogMgo.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebOperationLogRepository.insert(pcWebOperationLogMgo);
            }
        }

        //是否需要记录[登录]日志
        if (method.isAnnotationPresent(PcWebLoginLog.class)) {
            PcWebLoginLog pcWebLoginLog = method.getAnnotation(PcWebLoginLog.class);
            if (pcWebLoginLog.flag() == true) {
                PcWebLoginLogMgo pcWebLoginLogMgo = new PcWebLoginLogMgo();
                //当前log的通知方式是 Before
                pcWebLoginLogMgo.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToLoginLog(pcWebLoginLogMgo, joinPoint, request);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof MyCommonResult) {
                        //如果是封装的结果
                        MyCommonResult commonResult = (MyCommonResult) result;
                        if (commonResult.isHasError() == true) {
                            isSuccess = false;
                            pcWebLoginLogMgo.setException(commonResult.getMsg());
                        }
                    }
                    pcWebLoginLogMgo.setResult(JSON.toJSONString(result));
                }
                pcWebLoginLogMgo.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebLoginLogRepository.insert(pcWebLoginLogMgo);
            }
        }
    }


    @AfterThrowing(value = "aspect()", throwing = "exception")
    public void afterControllerThrowing(JoinPoint joinPoint, Exception exception) throws Throwable {
        Method method = controllerAspectService.gainReqMethod(joinPoint);
        //是否需要记录[查询]日志
        if (method.isAnnotationPresent(PcWebQueryLog.class)) {
            PcWebQueryLog pcWebQueryLog = method.getAnnotation(PcWebQueryLog.class);
            if (pcWebQueryLog.flag() == true) {
                PcWebQueryLogMgo pcWebQueryLogMgo = new PcWebQueryLogMgo();
                //当前log的通知方式是 Before
                pcWebQueryLogMgo.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToQueryLog(pcWebQueryLogMgo, joinPoint, request);
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
            PcWebOperationLog pcWebOperationLog = method.getAnnotation(PcWebOperationLog.class);
            if (pcWebOperationLog.flag() == true) {
                PcWebOperationLogMgo pcWebOperationLogMgo = new PcWebOperationLogMgo();
                //当前log的通知方式是 Before
                pcWebOperationLogMgo.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(pcWebOperationLogMgo, joinPoint, request);
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
            PcWebLoginLog pcWebLoginLog = method.getAnnotation(PcWebLoginLog.class);
            if (pcWebLoginLog.flag() == true) {
                PcWebLoginLogMgo pcWebLoginLogMgo = new PcWebLoginLogMgo();
                //当前log的通知方式是 Before
                pcWebLoginLogMgo.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToLoginLog(pcWebLoginLogMgo, joinPoint, request);
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
