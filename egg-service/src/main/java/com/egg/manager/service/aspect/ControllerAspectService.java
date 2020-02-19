package com.egg.manager.service.aspect;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.entity.log.OperationLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * 设置一些值到OperationLog
     * @param operationLog
     * @param joinPoint
     * @return
     */
    void dealSetValToOperationLog(OperationLog operationLog, JoinPoint joinPoint,HttpServletRequest request) throws NoSuchMethodException;

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
