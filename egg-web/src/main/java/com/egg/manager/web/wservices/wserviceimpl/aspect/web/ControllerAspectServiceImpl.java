package com.egg.manager.web.wservices.wserviceimpl.aspect.web;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.api.services.em.user.redis.UserAccountRedisService;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebLoginLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.commons.base.beans.request.RequestHeaderBean;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.em.user.pojo.bean.UserAccountToken;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.MyBaseWebLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebLoginLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebOperationLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebQueryLogMgo;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.web.wservices.wservice.aspect.web.ControllerAspectService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Service
public class ControllerAspectServiceImpl implements ControllerAspectService {

    private final String UNKNOW_METHOD_ACTION_NAME = "未知方法操作名!!!";

    @Autowired
    private RoutineCommonFunc routineCommonFunc;
    @Reference
    private UserAccountRedisService userAccountRedisService;

    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public JSONObject dealGetMethodArgsArrayFromJoinPoint(JoinPoint joinPoint,HttpServletRequest request) {
        JSONObject argJsonObj = new JSONObject();
        //先从request取得传递的参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        if(CollectionUtil.isNotEmpty(parameterMap)){
            Set<String> strings = parameterMap.keySet();
            for(String key : strings){
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
     * @param logMgo 要处理的类必须继承 com.egg.manager.persistence.expand.db.mongo.mo.log.pcMyBaseWebLogMgo，并且只会对这个类所拥有的字段进行修改、赋值
     * @param joinPoint 切面
     * @param request http请求
     * @param <T>  继承 com.egg.manager.persistence.expand.db.mongo.mo.log.pcMyBaseWebLogMgo的类
     * @return logMgo
     */
    protected <T extends MyBaseWebLogMgo> T dealSetValToBaseLogMgo(T logMgo,JoinPoint joinPoint, HttpServletRequest request){
        try {
            if (logMgo.getStatus() == null) {
                logMgo.setStatus(BaseStateEnum.ENABLED.getValue());
            }
            //请求方法的参数
            JSONObject argJsonObj = this.dealGetMethodArgsArrayFromJoinPoint(joinPoint,request);
            logMgo.setActionArgs(argJsonObj.toJSONString());

            Signature signature = joinPoint.getSignature();
            String methodName = signature.getName();
            logMgo.setAspectKind(joinPoint.getKind());
            logMgo.setClassName(signature.getDeclaringTypeName());
            logMgo.setMethodName(methodName);
            logMgo.setSignatureLong(signature.toLongString());
            Method method = this.gainReqMethod(signature);
            if (method != null) {
                logMgo.setReturnTypeName(method.getReturnType().getName());
                logMgo.setDeclaredAnnotations(JSONObject.toJSONString(method.getDeclaredAnnotations()));
                PcWebQueryLog pcWebQueryLog = method.getAnnotation(PcWebQueryLog.class);
                if (pcWebQueryLog != null) {
                    logMgo.setAction(this.gainApiOperationMsgWhenBlank(method, pcWebQueryLog.action()));
                    logMgo.setLogDescription(this.gainApiOperationNotesIfBlank(method, pcWebQueryLog.description()));
                    //请求的全路径(代码中取得)
                    logMgo.setFullPath(pcWebQueryLog.fullPath());
                }
            }
            if (request != null) {
                //请求路径
                if(HttpMethodConstant.GET.equalsIgnoreCase(request.getMethod())){
                    String queryString = (StringUtils.isBlank(request.getQueryString())) ? "" : request.getQueryString() ;
                    logMgo.setRequestUri(request.getRequestURI() + queryString);
                    logMgo.setRequestUrl(request.getRequestURL().toString() + queryString);
                }   else {
                    logMgo.setRequestUri(request.getRequestURI());
                    logMgo.setRequestUrl(request.getRequestURL().toString());
                }
                logMgo.setReqMethod(request.getMethod());
                //请求的sessionid
                HttpSession session = request.getSession();
                if(session != null){
                    logMgo.setSessionId(session.getId());
                }
                //取得 请求头的token信息
                UserAccountToken userAccountToken = routineCommonFunc.gainUserAccountTokenBeanByRequest(request, false);
                if (userAccountToken != null) {
                    //取得当前登录的用户
                    UserAccount loginUser = userAccountRedisService.dealGetCurrentLoginUserByAuthorization(null, userAccountToken.getAuthorization());
                    logMgo.setTokenBean(JSONObject.toJSONString(userAccountToken));
                    Long userAccountId = userAccountToken.getUserAccountId();
                    logMgo.setUserAccountId(userAccountId);
                    logMgo.setCreateUserId(userAccountId);
                    logMgo.setLastModifyerId(userAccountId);
                    //记录当前登录用户信息
                    if(loginUser != null){
                        logMgo.setUserNickName(loginUser.getNickName());
                        logMgo.setLoginUser(loginUser);
                    }
                }
                //取得 请求头bean
                RequestHeaderBean requestHeaderBean = routineCommonFunc.gainRequestHeaderBeanByRequest(request);
                if (requestHeaderBean != null) {
                    logMgo.setHeaders(JSONObject.toJSONString(requestHeaderBean));
                    logMgo.setIpAddr(request.getRemoteAddr());
                }
            }
            Date now = new Date();
            logMgo.setCreateTime(now);
            logMgo.setLastModifiedDate(now);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return logMgo ;
    }

    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToQueryLog(PcWebQueryLogMgo logMgo, JoinPoint joinPoint, HttpServletRequest request,PcWebQueryLog queryLogAnno) {
        try {
            logMgo = this.dealSetValToBaseLogMgo(logMgo,joinPoint,request);
            if(queryLogAnno != null){
                logMgo.setAnnotationOperationType(queryLogAnno.type());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToOperationLog(PcWebOperationLogMgo logMgo, JoinPoint joinPoint, HttpServletRequest request, PcWebOperationLog operationLogAnno) {
        try {
            logMgo = this.dealSetValToBaseLogMgo(logMgo,joinPoint,request);
            if(operationLogAnno != null){
                logMgo.setAnnotationOperationType(operationLogAnno.type());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToLoginLog(PcWebLoginLogMgo logMgo, JoinPoint joinPoint, HttpServletRequest request, PcWebLoginLog loginLogAnno) {
        try {
            logMgo = this.dealSetValToBaseLogMgo(logMgo,joinPoint,request);
            if(loginLogAnno != null){
                logMgo.setAnnotationOperationType(loginLogAnno.type());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
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
            log.error(e.getMessage());
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
            log.error(e.getMessage());
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
        return UNKNOW_METHOD_ACTION_NAME;
    }
}
