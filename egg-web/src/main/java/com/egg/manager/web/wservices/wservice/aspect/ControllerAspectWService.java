package com.egg.manager.web.wservices.wservice.aspect;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebLoginLogMO;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebOperationLogMO;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMO;
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
public interface ControllerAspectWService {

    /**
     * 取得 请求的参数
     *
     * @param joinPoint
     * @return JSONObject
     */
    JSONObject dealGetMethodArgsArrayFromJoinPoint(JoinPoint joinPoint);

    /**
     * 设置一些值到 PcWebQueryLogMO
     *
     * @param pcWebQueryLogMO
     * @param joinPoint
     * @param request
     */
    void dealSetValToQueryLog(PcWebQueryLogMO pcWebQueryLogMO, JoinPoint joinPoint, HttpServletRequest request);

    /**
     * 设置一些值到 PcWebOperationLogMO
     *
     * @param pcWebOperationLogMO
     * @param joinPoint
     * @param request
     */
    void dealSetValToOperationLog(PcWebOperationLogMO pcWebOperationLogMO, JoinPoint joinPoint, HttpServletRequest request);


    /**
     * 设置一些值到 PcWebLoginLogMO
     *
     * @param pcWebLoginLogMO
     * @param joinPoint
     * @param request
     */
    void dealSetValToLoginLog(PcWebLoginLogMO pcWebLoginLogMO, JoinPoint joinPoint, HttpServletRequest request);


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
