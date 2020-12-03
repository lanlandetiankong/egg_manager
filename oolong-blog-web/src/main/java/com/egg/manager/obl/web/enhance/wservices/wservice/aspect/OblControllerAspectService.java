package com.egg.manager.obl.web.enhance.wservices.wservice.aspect;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebLoginLog;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebQueryLog;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebLoginLogMgo;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebOperationLogMgo;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebQueryLogMgo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
public interface OblControllerAspectService {

    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    JSONObject dealGetMethodArgsArrayFromJoinPoint(JoinPoint joinPoint, HttpServletRequest request);

    /**
     * 设置一些值到 OblPcWebQueryLogMgo
     * @param oblPcWebQueryLogMgo
     * @param joinPoint
     * @param request
     */
    void dealSetValToQueryLog(OblPcWebQueryLogMgo oblPcWebQueryLogMgo, JoinPoint joinPoint, HttpServletRequest request, OblPcWebQueryLog queryLogAnno);

    /**
     * 设置一些值到 OblPcWebOperationLogMgo
     * @param oblPcWebOperationLogMgo
     * @param joinPoint
     * @param request
     */
    void dealSetValToOperationLog(OblPcWebOperationLogMgo oblPcWebOperationLogMgo, JoinPoint joinPoint, HttpServletRequest request, OblPcWebOperationLog operationLogAnno);


    /**
     * 设置一些值到 OblPcWebLoginLogMgo
     * @param oblPcWebLoginLogMgo
     * @param joinPoint
     * @param request
     */
    void dealSetValToLoginLog(OblPcWebLoginLogMgo oblPcWebLoginLogMgo, JoinPoint joinPoint, HttpServletRequest request, OblPcWebLoginLog loginLogAnno);


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
