package com.egg.manager.em.web.enhance.wservices.wserviceimpl.aspect;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.useragent.UserAgentUtil;
import org.apache.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.em.web.enhance.wservices.wservice.aspect.EmControllerAspectService;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.services.em.user.basic.EmUserAccountService;
import com.egg.manager.persistence.commons.base.beans.request.RequestHeaderBean;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebLoginLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebOperationLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebQueryLogMgo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.em.user.pojo.bean.UserAccountToken;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebLoginLog;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.persistence.exchange.db.mongo.mo.MyBaseWebLogMgo;
import com.egg.manager.persistence.exchange.db.mongo.mo.clazz.EggClazzInfoLogMgo;
import com.egg.manager.persistence.exchange.db.mongo.mo.http.EggRequestInfo;
import com.egg.manager.persistence.exchange.db.mongo.mo.http.ua.EggUserAgentMgo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Service(interfaceClass = EmControllerAspectService.class)
public class EmControllerAspectServiceImpl implements EmControllerAspectService {

    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Reference
    private EmUserAccountService emUserAccountService;

    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public JSONObject dealGetMethodArgsArrayFromJoinPoint(JoinPoint joinPoint, HttpServletRequest request) {
        JSONObject argJsonObj = new JSONObject();
        //先从request取得传递的参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (CollectionUtil.isNotEmpty(parameterMap)) {
            Set<String> strings = parameterMap.keySet();
            for (String key : strings) {
                argJsonObj.put(key, parameterMap.get(key));
            }
        }
        //取得方法上定义要取得的参数，如果从手动从request取得的参数在joinpoint无法取得，重复的key将会覆盖上方的参数值
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
            for (int i = 0; i < args.length; i++) {
                Object argObj = args[i];
                if (!checkObjectInnstanceOfHttpServlet(argObj)) {
                    //过滤掉HttpServlet类型的
                    String argName = argNames[i];
                    argJsonObj.put(argName, argObj);
                }
            }
        }
        return argJsonObj;
    }

    /**
     * 设置基本log的基本属性
     * @param logMgo    要处理的类必须继承 com.egg.manager.persistence.expand.db.mongo.mo.log.pcMyBaseWebLogMgo，并且只会对这个类所拥有的字段进行修改、赋值
     * @param joinPoint 切面
     * @param request   http请求
     * @param <T>       继承 com.egg.manager.persistence.expand.db.mongo.mo.log.pcMyBaseWebLogMgo的类
     * @return logMgo
     */
    protected <T extends MyBaseWebLogMgo> T dealSetValToBaseLogMgo(T logMgo, JoinPoint joinPoint, HttpServletRequest request) {
        try {
            EggClazzInfoLogMgo clazzInfoLog = new EggClazzInfoLogMgo();
            if (logMgo.getState() == null) {
                logMgo.setState(BaseStateEnum.ENABLED.getValue());
            }
            //请求方法的参数
            JSONObject argJsonObj = this.dealGetMethodArgsArrayFromJoinPoint(joinPoint, request);
            clazzInfoLog.setActionArgs(argJsonObj.toJSONString());

            Signature signature = joinPoint.getSignature();
            String methodName = signature.getName();
            clazzInfoLog.setAspectKind(joinPoint.getKind());
            clazzInfoLog.setClassName(signature.getDeclaringTypeName());
            clazzInfoLog.setMethodName(methodName);
            clazzInfoLog.setSignatureLong(signature.toLongString());
            Method method = this.gainReqMethod(signature);
            if (method != null) {
                clazzInfoLog.setReturnTypeName(method.getReturnType().getName());
                clazzInfoLog.setDeclaredAnnotations(JSONObject.toJSONString(method.getDeclaredAnnotations()));
                EmPcWebQueryLog emPcWebQueryLog = method.getAnnotation(EmPcWebQueryLog.class);
                if (emPcWebQueryLog != null) {
                    clazzInfoLog.setAction(this.gainApiOperationMsgWhenBlank(method, emPcWebQueryLog.action()));
                    clazzInfoLog.setLogDescription(this.gainApiOperationNotesIfBlank(method, emPcWebQueryLog.description()));
                    //请求的全路径(代码中取得)
                    logMgo.setFullPath(emPcWebQueryLog.fullPath());
                }
            }
            if (request != null) {
                //获取UserAgent相关信息
                String reqUserAgent = request.getHeader("User-Agent");
                if (StringUtils.isNotBlank(reqUserAgent)) {
                    EggUserAgentMgo eggUserAgent = new EggUserAgentMgo(UserAgentUtil.parse(reqUserAgent), reqUserAgent);
                    logMgo.setUserAgent(eggUserAgent);
                }
                EggRequestInfo requestInfo = new EggRequestInfo();
                //请求路径
                if (HttpMethodConstant.GET.equalsIgnoreCase(request.getMethod())) {
                    String queryString = (StringUtils.isBlank(request.getQueryString())) ? "" : request.getQueryString();
                    requestInfo.setRequestUri(request.getRequestURI() + queryString);
                    requestInfo.setRequestUrl(request.getRequestURL().toString() + queryString);
                } else {
                    requestInfo.setRequestUri(request.getRequestURI());
                    requestInfo.setRequestUrl(request.getRequestURL().toString());
                }
                requestInfo.setReqMethod(request.getMethod());
                //请求的sessionid
                HttpSession session = request.getSession();
                if (session != null) {
                    requestInfo.setSessionId(session.getId());
                }
                //取得 请求头的token信息
                UserAccountToken userAccountToken = routineCommonFunc.gainUserAccountTokenBeanByRequest(request, false);
                if (userAccountToken != null) {
                    //取得当前登录的用户
                    CurrentLoginEmUserInfo loginUserInfo = emUserAccountService.queryDbToCacheable(userAccountToken.getUserAccountId());
                    requestInfo.setTokenBean(JSONObject.toJSONString(userAccountToken));
                    String userAccountId = userAccountToken.getUserAccountId();
                    logMgo.setUserAccountId(userAccountId);
                    logMgo.setCreateUserId(userAccountId);
                    logMgo.setLastModifyerId(userAccountId);
                    //记录当前登录用户信息
                    if (loginUserInfo != null) {
                        logMgo.setUserName(loginUserInfo.getUserName());
                    }
                }
                //取得 请求头bean
                RequestHeaderBean requestHeaderBean = routineCommonFunc.gainRequestHeaderBeanByRequest(request);
                if (requestHeaderBean != null) {
                    requestInfo.setHeaders(JSONObject.toJSONString(requestHeaderBean));
                    requestInfo.setIpAddr(request.getRemoteAddr());
                }
                logMgo.setRequestInfo(requestInfo);
            }
            logMgo.setClazzInfo(clazzInfoLog);
            Date now = new Date();
            logMgo.setCreateTime(now);
            logMgo.setLastModifiedDate(now);
        } catch (Exception e) {
            log.error("Aop接口异常/Transfer:", e);
        }
        return logMgo;
    }

    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToQueryLog(EmPcWebQueryLogMgo logMgo, JoinPoint joinPoint, HttpServletRequest request, EmPcWebQueryLog queryLogAnno) {
        try {
            logMgo = this.dealSetValToBaseLogMgo(logMgo, joinPoint, request);
            if (queryLogAnno != null) {
                logMgo.setAnnotationOperationType(queryLogAnno.type());
            }
        } catch (Exception e) {
            log.error("Aop接口异常/Transfer:", e);
        }
    }


    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToOperationLog(EmPcWebOperationLogMgo logMgo, JoinPoint joinPoint, HttpServletRequest request, EmPcWebOperationLog operationLogAnno) {
        try {
            logMgo = this.dealSetValToBaseLogMgo(logMgo, joinPoint, request);
            if (operationLogAnno != null) {
                logMgo.setAnnotationOperationType(operationLogAnno.type());
            }
        } catch (Exception e) {
            log.error("Aop接口异常/Transfer:", e);
        }
    }

    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToLoginLog(EmPcWebLoginLogMgo logMgo, JoinPoint joinPoint, HttpServletRequest request, EmPcWebLoginLog loginLogAnno) {
        try {
            logMgo = this.dealSetValToBaseLogMgo(logMgo, joinPoint, request);
            if (loginLogAnno != null) {
                logMgo.setAnnotationOperationType(loginLogAnno.type());
            }
        } catch (Exception e) {
            log.error("Aop接口异常/Transfer:", e);
        }
    }


    /**
     * 取得当前调用的 method 对象
     * @param signature
     * @return
     */
    @Override
    public Method gainReqMethod(Signature signature) {
        Method method = null;
        try {
            String methodName = signature.getName();
            Class[] parameterTypes = ((MethodSignature) signature).getMethod().getParameterTypes();
            method = signature.getDeclaringType().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            log.error("Aop接口异常/方法未找到:", e);
        }
        return method;
    }

    /**
     * 取得当前调用的 method 对象
     * @param joinPoint
     * @return
     */
    @Override
    public Method gainReqMethod(JoinPoint joinPoint) {
        Method method = null;
        try {
            Signature signature = joinPoint.getSignature();
            String methodName = signature.getName();
            Class[] parameterTypes = ((MethodSignature) signature).getMethod().getParameterTypes();
            method = signature.getDeclaringType().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            log.error("Aop接口异常/方法未找到:", e);
        }
        return method;
    }


    /**
     * 判断一个对象是否属于  HttpServlet 下的
     * @param argObj
     * @return
     */
    public boolean checkObjectInnstanceOfHttpServlet(Object argObj) {
        boolean flag = false;
        if (argObj instanceof HttpServletRequest || argObj instanceof HttpServletResponse) {
            flag = true;
        }
        return flag;
    }


    /**
     * 取得方法上@ApiOperation的value
     * @param method 方法
     * @param value  要判断的值
     * @return
     */
    private String gainApiOperationMsgWhenBlank(Method method, String value) {
        if (StringUtils.isNotBlank(value)) {
            return value;
        }
        return gainApiOperationMsg(method);
    }

    /**
     * 取得方法上@ApiOperation的value
     * @param method 方法
     * @return
     */
    private String gainApiOperationMsg(Method method) {
        //是否有[Api操作]注解
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation anno = method.getAnnotation(ApiOperation.class);
            if (StringUtils.isNotBlank(anno.value())) {
                return anno.value();
            }
        }
        return "";
    }

    /**
     * 取得方法上@ApiOperation的note
     * @param method 方法
     * @param value  要判断的值
     * @return
     */
    private String gainApiOperationNotesIfBlank(Method method, String value) {
        if (StringUtils.isNotBlank(value)) {
            return value;
        }
        return gainApiOperationNotes(method);
    }


    /**
     * 取得方法上@ApiOperation的note
     * @param method 方法
     * @return
     */
    private String gainApiOperationNotes(Method method) {
        //是否有[Api操作]注解
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation anno = method.getAnnotation(ApiOperation.class);
            if (StringUtils.isNotBlank(anno.value())) {
                return anno.notes();
            }
        }
        return BaseRstMsgConstant.ErrorMsg.unknowMethodOperationName();
    }
}
