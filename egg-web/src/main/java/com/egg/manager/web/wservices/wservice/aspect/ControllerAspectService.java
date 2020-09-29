package com.egg.manager.web.wservices.wservice.aspect;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebLoginLogMgo;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebOperationLogMgo;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMgo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/19
 * \* Time: 16:10
 * \* Description:
 * \
 */
public interface ControllerAspectService {

    /**
     * 取得 请求的参数
     *
     * @param joinPoint
     * @return JSONObject
     */
    JSONObject dealGetMethodArgsArrayFromJoinPoint(JoinPoint joinPoint);

    /**
     * 设置一些值到 PcWebQueryLogMgo
     *
     * @param pcWebQueryLogMgo
     * @param joinPoint
     * @param request
     */
    void dealSetValToQueryLog(PcWebQueryLogMgo pcWebQueryLogMgo, JoinPoint joinPoint, HttpServletRequest request);

    /**
     * 设置一些值到 PcWebOperationLogMgo
     *
     * @param pcWebOperationLogMgo
     * @param joinPoint
     * @param request
     */
    void dealSetValToOperationLog(PcWebOperationLogMgo pcWebOperationLogMgo, JoinPoint joinPoint, HttpServletRequest request);


    /**
     * 设置一些值到 PcWebLoginLogMgo
     *
     * @param pcWebLoginLogMgo
     * @param joinPoint
     * @param request
     */
    void dealSetValToLoginLog(PcWebLoginLogMgo pcWebLoginLogMgo, JoinPoint joinPoint, HttpServletRequest request);


    /**
     * 取得当前调用的方法
     *
     * @param signature
     * @return
     */
    Method gainReqMethod(Signature signature);


    /**
     * 取得当前调用的方法
     *
     * @param joinPoint
     * @return
     */
    Method gainReqMethod(JoinPoint joinPoint);
}
