package com.egg.manager.web.aspect.log;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.service.annotation.log.OperLog;
import com.egg.manager.web.aspect.AbstAspectManager;
import com.egg.manager.web.aspect.MyAspectApi;
import com.egg.manager.common.util.jwt.JWTUtil;
import com.egg.manager.persistence.entity.log.OperationLog;
import com.egg.manager.service.spring.SpringContextBeanService;
import com.egg.manager.service.service.log.OperationLogService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * 操作日志 记录 切面
 */
public class RecordOperationLogAspe extends AbstAspectManager {

    private Logger logger = LoggerFactory.getLogger(RecordOperationLogAspe.class);

    public RecordOperationLogAspe(MyAspectApi aspectApi) {
        super(aspectApi);
    }

    @Override
    public Object handleAspect(ProceedingJoinPoint pjp, Method method) throws Throwable {
        super.handleAspect(pjp, method);
        return execute(pjp, method);
    }


    @Override
    @Async
    protected Object execute(ProceedingJoinPoint pjp, Method method) throws Throwable {
        OperLog operationLog = method.getAnnotation(OperLog.class);
        String actionLog = null;
        StackTraceElement[] stackTraceElements = null;
        // 是否执行异常
        boolean isException = false;
        // 请求接收到的时间
        long receiveTime;
        // 开始时间戳
        long operationTime = System.currentTimeMillis();
        try {
            return pjp.proceed(pjp.getArgs());
        } catch (Throwable throwable) {
            isException = true;
            actionLog = throwable.getLocalizedMessage();
            stackTraceElements = throwable.getStackTrace();
            throw throwable;
        } finally {
            operLogHandle(pjp, method, operationLog, actionLog, operationTime, isException, stackTraceElements);
        }

    }

    private void operLogHandle(ProceedingJoinPoint joinPoint,
                               Method method,
                               OperLog operLog,
                               String actionLog,
                               long startTime,
                               boolean isException,
                               StackTraceElement[] stackTraceElements) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        OperationLogService operationLogService = SpringContextBeanService.getBean(OperationLogService.class);
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = sra.getRequest();
        String authorization = request.getHeader("authorization");
        OperationLog operationLog = new OperationLog();
        if (StringUtils.isNotBlank(authorization)) {
            String userAccountId = JWTUtil.getUserAccountId(authorization);
            operationLog.setUserAccountId(userAccountId);
        }
        operationLog.setIpAddr(getIpAddress(request));
        operationLog.setClassName(joinPoint.getTarget().getClass().getName());
        operationLog.setCreateTime(new Date(startTime));
        operationLog.setLogDescription(operLog.description());
        operationLog.setModelName(operLog.modelName());
        operationLog.setAction(operLog.action());
        if (isException == true) {
            StringBuilder exceptionMsgBuilder = new StringBuilder();
            exceptionMsgBuilder.append(actionLog + " &#10; ");
            for (StackTraceElement element : stackTraceElements) {
                exceptionMsgBuilder.append(element + " &#10; ");
            }
            operationLog.setMessage(exceptionMsgBuilder.toString());
        }
        operationLog.setMethodName(method.getName());
        operationLog.setIsSuccess(isException == true ? 1 : 0);
        Object[] args = joinPoint.getArgs();
        StringBuilder argBuilder = new StringBuilder();
        boolean isJoint = false;
        for (Object argObj : args) {
            if (argObj instanceof JSONObject) {
                JSONObject parse = (JSONObject) JSONObject.parse(argObj.toString());
                if (StringUtils.isNotBlank(parse.getString("password"))) {
                    parse.put("password", "*******");
                }
                if (StringUtils.isNotBlank(parse.getString("rePassword"))) {
                    parse.put("rePassword", "*******");
                }
                if (StringUtils.isNotBlank(parse.getString("oldPassword"))) {
                    parse.put("oldPassword", "*******");
                }
                if (StringUtils.isNotBlank(parse.getString("salt"))) {
                    parse.put("salt", "*******");
                }
                operationLog.setActionArgs(parse.toString());
            } else if (argObj instanceof String
                    || argObj instanceof Long
                    || argObj instanceof Integer
                    || argObj instanceof Double
                    || argObj instanceof Float
                    || argObj instanceof Byte
                    || argObj instanceof Short
                    || argObj instanceof Character) {
                isJoint = true;
            } else if (argObj instanceof String[]
                    || argObj instanceof Long[]
                    || argObj instanceof Integer[]
                    || argObj instanceof Double[]
                    || argObj instanceof Float[]
                    || argObj instanceof Byte[]
                    || argObj instanceof Short[]
                    || argObj instanceof Character[]) {
                Object[] strs = (Object[]) argObj;
                StringBuilder sbArray = new StringBuilder();
                sbArray.append("[");
                for (Object str : strs) {
                    sbArray.append(str.toString() + ",");
                }
                sbArray.deleteCharAt(sbArray.length() - 1);
                sbArray.append("]");
                operationLog.setActionArgs(sbArray.toString());
            } else {
                continue;
            }
        }
        if(isJoint){
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (String key:parameterMap.keySet()) {
                String[] strings = parameterMap.get(key);
                for (String str:strings) {
                    argBuilder.append(key+"="+str+"&");
                }
            }
            operationLog.setActionArgs(argBuilder.deleteCharAt(argBuilder.length()-1).toString());
        }
        logger.info("执行方法信息:"+JSONObject.toJSON(operationLog));
        operationLogService.insert(operationLog);

    }


    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip + ":" + request.getRemotePort();
    }
}
