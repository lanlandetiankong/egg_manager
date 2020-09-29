package com.egg.manager.web.config.aspect;


import com.alibaba.fastjson.JSON;
import com.egg.manager.common.annotation.log.pc.web.PcWebLoginLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.base.enums.aspect.AspectNotifyTypeEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebLoginLogMO;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebOperationLogMO;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMO;
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
                PcWebQueryLogMO pcWebQueryLogMO = new PcWebQueryLogMO();

                //当前log的通知方式是 Before
                pcWebQueryLogMO.setAspectNotifyType(AspectNotifyTypeEnum.Before.getValue());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToQueryLog(pcWebQueryLogMO, joinPoint, request);
                //请求成功(至少在before
                pcWebQueryLogMO.setIsSuccess(SwitchStateEnum.Open.getValue());
                pcWebQueryLogRepository.insert(pcWebQueryLogMO);
            }
        }

        //是否需要记录[操作]日志
        if (method.isAnnotationPresent(PcWebOperationLog.class)) {
            PcWebOperationLog pcWebOperationLog = method.getAnnotation(PcWebOperationLog.class);
            if (pcWebOperationLog.flag() == true) {
                PcWebOperationLogMO pcWebQueryLogMO = new PcWebOperationLogMO();

                //当前log的通知方式是 Before
                pcWebQueryLogMO.setAspectNotifyType(AspectNotifyTypeEnum.Before.getValue());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(pcWebQueryLogMO, joinPoint, request);
                //请求成功(至少在before
                pcWebQueryLogMO.setIsSuccess(SwitchStateEnum.Open.getValue());
                pcWebOperationLogRepository.insert(pcWebQueryLogMO);
            }
        }

        //pcWebLoginLogRepository
        //是否需要记录[操作]日志
        if (method.isAnnotationPresent(PcWebLoginLog.class)) {
            PcWebLoginLog pcWebLoginLog = method.getAnnotation(PcWebLoginLog.class);
            if (pcWebLoginLog.flag() == true) {
                PcWebLoginLogMO pcWebLoginLogMO = new PcWebLoginLogMO();
                //当前log的通知方式是 Before
                pcWebLoginLogMO.setAspectNotifyType(AspectNotifyTypeEnum.Before.getValue());
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToLoginLog(pcWebLoginLogMO, joinPoint, request);
                //请求成功(至少在before
                pcWebLoginLogMO.setIsSuccess(SwitchStateEnum.Open.getValue());
                pcWebLoginLogRepository.insert(pcWebLoginLogMO);
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
        //是否需要记录[查询]日志
        if (method.isAnnotationPresent(PcWebQueryLog.class)) {
            PcWebQueryLog pcWebQueryLog = method.getAnnotation(PcWebQueryLog.class);
            if (pcWebQueryLog.flag() == true) {
                PcWebQueryLogMO pcWebQueryLogMO = new PcWebQueryLogMO();
                //当前log的通知方式是 Before
                pcWebQueryLogMO.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToQueryLog(pcWebQueryLogMO, joinPoint, request);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof MyCommonResult) {
                        //如果是封装的结果
                        MyCommonResult commonResult = (MyCommonResult) result;
                        if (commonResult.isHasError() == true) {
                            isSuccess = false;
                            pcWebQueryLogMO.setException(commonResult.getErrorMsg());
                        }
                    }
                    pcWebQueryLogMO.setResult(JSON.toJSONString(result));
                }
                pcWebQueryLogMO.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebQueryLogRepository.insert(pcWebQueryLogMO);
            }
        }

        //是否需要记录[操作]日志
        if (method.isAnnotationPresent(PcWebOperationLog.class)) {
            PcWebOperationLog pcWebOperationLog = method.getAnnotation(PcWebOperationLog.class);
            if (pcWebOperationLog.flag() == true) {
                PcWebOperationLogMO pcWebOperationLogMO = new PcWebOperationLogMO();
                //当前log的通知方式是 Before
                pcWebOperationLogMO.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(pcWebOperationLogMO, joinPoint, request);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof MyCommonResult) {
                        //如果是封装的结果
                        MyCommonResult commonResult = (MyCommonResult) result;
                        if (commonResult.isHasError() == true) {
                            isSuccess = false;
                            pcWebOperationLogMO.setException(commonResult.getErrorMsg());
                        }
                    }
                    pcWebOperationLogMO.setResult(JSON.toJSONString(result));
                }
                pcWebOperationLogMO.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebOperationLogRepository.insert(pcWebOperationLogMO);
            }
        }

        //是否需要记录[登录]日志
        if (method.isAnnotationPresent(PcWebLoginLog.class)) {
            PcWebLoginLog pcWebLoginLog = method.getAnnotation(PcWebLoginLog.class);
            if (pcWebLoginLog.flag() == true) {
                PcWebLoginLogMO pcWebLoginLogMO = new PcWebLoginLogMO();
                //当前log的通知方式是 Before
                pcWebLoginLogMO.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToLoginLog(pcWebLoginLogMO, joinPoint, request);
                boolean isSuccess = true;
                if (result != null) {
                    if (result instanceof MyCommonResult) {
                        //如果是封装的结果
                        MyCommonResult commonResult = (MyCommonResult) result;
                        if (commonResult.isHasError() == true) {
                            isSuccess = false;
                            pcWebLoginLogMO.setException(commonResult.getErrorMsg());
                        }
                    }
                    pcWebLoginLogMO.setResult(JSON.toJSONString(result));
                }
                pcWebLoginLogMO.setIsSuccess(isSuccess ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue());
                pcWebLoginLogRepository.insert(pcWebLoginLogMO);
            }
        }
        //System.out.println("返回。。。afterControllerReturn");
    }


    @AfterThrowing(value = "aspect()", throwing = "exception")
    public void afterControllerThrowing(JoinPoint joinPoint, Exception exception) throws Throwable {
        Method method = controllerAspectService.gainReqMethod(joinPoint);
        //是否需要记录[查询]日志
        if (method.isAnnotationPresent(PcWebQueryLog.class)) {
            PcWebQueryLog pcWebQueryLog = method.getAnnotation(PcWebQueryLog.class);
            if (pcWebQueryLog.flag() == true) {
                PcWebQueryLogMO pcWebQueryLogMO = new PcWebQueryLogMO();
                //当前log的通知方式是 Before
                pcWebQueryLogMO.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToQueryLog(pcWebQueryLogMO, joinPoint, request);
                //请求失败
                pcWebQueryLogMO.setIsSuccess(SwitchStateEnum.Close.getValue());
                if (exception != null) {
                    pcWebQueryLogMO.setException(JSON.toJSONString(exception));
                }
                pcWebQueryLogRepository.insert(pcWebQueryLogMO);
            }
        }

        //是否需要记录[操作]日志
        if (method.isAnnotationPresent(PcWebOperationLog.class)) {
            PcWebOperationLog pcWebOperationLog = method.getAnnotation(PcWebOperationLog.class);
            if (pcWebOperationLog.flag() == true) {
                PcWebOperationLogMO pcWebOperationLogMO = new PcWebOperationLogMO();
                //当前log的通知方式是 Before
                pcWebOperationLogMO.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToOperationLog(pcWebOperationLogMO, joinPoint, request);
                //请求失败
                pcWebOperationLogMO.setIsSuccess(SwitchStateEnum.Close.getValue());
                if (exception != null) {
                    pcWebOperationLogMO.setException(JSON.toJSONString(exception));
                }
                pcWebOperationLogRepository.insert(pcWebOperationLogMO);
            }
        }
        //是否需要记录[登录]日志
        if (method.isAnnotationPresent(PcWebLoginLog.class)) {
            PcWebLoginLog pcWebLoginLog = method.getAnnotation(PcWebLoginLog.class);
            if (pcWebLoginLog.flag() == true) {
                PcWebLoginLogMO pcWebLoginLogMO = new PcWebLoginLogMO();
                //当前log的通知方式是 Before
                pcWebLoginLogMO.setAspectNotifyType(AspectNotifyTypeEnum.AfterReturning.getValue());

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                //设置一些必要值到log
                controllerAspectService.dealSetValToLoginLog(pcWebLoginLogMO, joinPoint, request);
                //请求失败
                pcWebLoginLogMO.setIsSuccess(SwitchStateEnum.Close.getValue());
                if (exception != null) {
                    pcWebLoginLogMO.setException(JSON.toJSONString(exception));
                }
                pcWebLoginLogRepository.insert(pcWebLoginLogMO);
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
