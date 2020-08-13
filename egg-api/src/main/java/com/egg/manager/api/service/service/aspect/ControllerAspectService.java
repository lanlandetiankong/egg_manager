package com.egg.manager.api.service.service.aspect;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.persistence.mongo.mo.log.OperationLogMO;
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
     * @param joinPoint
     * @return JSONObject
     */
    JSONObject dealGetMethodArgsArrayFromJoinPoint(JoinPoint joinPoint);


    /**
     * 设置一些值到OperationLogMO
     * @param operationLogMO
     * @param joinPoint
     * @param request
     */
    void dealSetValToOperationLog(OperationLogMO operationLogMO, JoinPoint joinPoint, HttpServletRequest request);

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
