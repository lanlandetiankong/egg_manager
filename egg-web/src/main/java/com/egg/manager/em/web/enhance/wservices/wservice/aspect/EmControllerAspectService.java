package com.egg.manager.em.web.enhance.wservices.wservice.aspect;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebLoginLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebOperationLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebQueryLogMgo;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebLoginLog;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
public interface EmControllerAspectService {

    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    JSONObject dealGetMethodArgsArrayFromJoinPoint(JoinPoint joinPoint, HttpServletRequest request);

    /**
     * 设置一些值到 EmPcWebQueryLogMgo
     * @param emPcWebQueryLogMgo
     * @param joinPoint
     * @param request
     */
    void dealSetValToQueryLog(EmPcWebQueryLogMgo emPcWebQueryLogMgo, JoinPoint joinPoint, HttpServletRequest request, EmPcWebQueryLog queryLogAnno);

    /**
     * 设置一些值到 EmPcWebOperationLogMgo
     * @param emPcWebOperationLogMgo
     * @param joinPoint
     * @param request
     */
    void dealSetValToOperationLog(EmPcWebOperationLogMgo emPcWebOperationLogMgo, JoinPoint joinPoint, HttpServletRequest request, EmPcWebOperationLog operationLogAnno);


    /**
     * 设置一些值到 EmPcWebLoginLogMgo
     * @param emPcWebLoginLogMgo
     * @param joinPoint
     * @param request
     */
    void dealSetValToLoginLog(EmPcWebLoginLogMgo emPcWebLoginLogMgo, JoinPoint joinPoint, HttpServletRequest request, EmPcWebLoginLog loginLogAnno);


    /**
     * 取得当前调用的方法
     * @param signature
     * @return
     */
    Method gainReqMethod(Signature signature);


    /**
     * 取得当前调用的方法
     * @param joinPoint
     * @return
     */
    Method gainReqMethod(JoinPoint joinPoint);
}
