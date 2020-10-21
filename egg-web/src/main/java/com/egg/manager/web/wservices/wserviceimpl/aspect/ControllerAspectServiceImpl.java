package com.egg.manager.web.wservices.wserviceimpl.aspect;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.base.beans.request.RequestHeaderBean;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.bean.webvo.session.UserAccountToken;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebLoginLogMgo;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebOperationLogMgo;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMgo;
import com.egg.manager.web.wservices.wservice.aspect.ControllerAspectService;
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
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToQueryLog(PcWebQueryLogMgo pcWebQueryLogMgo, JoinPoint joinPoint, HttpServletRequest request) {
        try {
            if (pcWebQueryLogMgo.getStatus() == null) {
                pcWebQueryLogMgo.setStatus(BaseStateEnum.ENABLED.getValue());
            }
            //请求方法的参数
            JSONObject argJsonObj = this.dealGetMethodArgsArrayFromJoinPoint(joinPoint,request);
            pcWebQueryLogMgo.setActionArgs(argJsonObj.toJSONString());

            Signature signature = joinPoint.getSignature();
            String methodName = signature.getName();
            pcWebQueryLogMgo.setAspectKind(joinPoint.getKind());
            pcWebQueryLogMgo.setClassName(signature.getDeclaringTypeName());
            pcWebQueryLogMgo.setMethodName(methodName);
            pcWebQueryLogMgo.setSignatureLong(signature.toLongString());
            Method method = this.gainReqMethod(signature);
            if (method != null) {
                pcWebQueryLogMgo.setReturnTypeName(method.getReturnType().getName());
                pcWebQueryLogMgo.setDeclaredAnnotations(JSONObject.toJSONString(method.getDeclaredAnnotations()));
                PcWebQueryLog pcWebQueryLog = method.getAnnotation(PcWebQueryLog.class);
                if (pcWebQueryLog != null) {
                    pcWebQueryLogMgo.setAction(this.gainApiOperationMsgWhenBlank(method, pcWebQueryLog.action()));
                    pcWebQueryLogMgo.setLogDescription(this.gainApiOperationNotesIfBlank(method, pcWebQueryLog.description()));
                    //请求的全路径(代码中取得)
                    pcWebQueryLogMgo.setFullPath(pcWebQueryLog.fullPath());
                }
            }
            if (request != null) {
                //请求路径
                if(HttpMethodConstant.GET.equalsIgnoreCase(request.getMethod())){
                    String queryString = (StringUtils.isBlank(request.getQueryString())) ? "" : request.getQueryString() ;
                    pcWebQueryLogMgo.setRequestUri(request.getRequestURI() + queryString);
                    pcWebQueryLogMgo.setRequestUrl(request.getRequestURL().toString() + queryString);
                }   else {
                    pcWebQueryLogMgo.setRequestUri(request.getRequestURI());
                    pcWebQueryLogMgo.setRequestUrl(request.getRequestURL().toString());
                }
                pcWebQueryLogMgo.setReqMethod(request.getMethod());
                //请求的sessionid
                HttpSession session = request.getSession();
                if(session != null){
                    pcWebQueryLogMgo.setSessionId(session.getId());
                }
                //取得 请求头的token信息
                UserAccountToken userAccountToken = routineCommonFunc.gainUserAccountTokenBeanByRequest(request, false);
                if (userAccountToken != null) {
                    pcWebQueryLogMgo.setTokenBean(JSONObject.toJSONString(userAccountToken));
                    String userAccountId = userAccountToken.getUserAccountId();
                    pcWebQueryLogMgo.setUserAccountId(userAccountId);
                    pcWebQueryLogMgo.setCreateUserId(userAccountId);
                    pcWebQueryLogMgo.setLastModifyerId(userAccountId);
                }
                //取得 请求头bean
                RequestHeaderBean requestHeaderBean = routineCommonFunc.gainRequestHeaderBeanByRequest(request);
                if (requestHeaderBean != null) {
                    pcWebQueryLogMgo.setHeaders(JSONObject.toJSONString(requestHeaderBean));
                    pcWebQueryLogMgo.setIpAddr(request.getRemoteAddr());
                }
            }
            Date now = new Date();
            pcWebQueryLogMgo.setCreateTime(now);
            pcWebQueryLogMgo.setLastModifiedDate(now);
        } catch (Exception e) {

        }
    }


    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToOperationLog(PcWebOperationLogMgo pcWebOperationLogMgo, JoinPoint joinPoint, HttpServletRequest request) {
        try {
            if (pcWebOperationLogMgo.getStatus() == null) {
                pcWebOperationLogMgo.setStatus(BaseStateEnum.ENABLED.getValue());
            }
            //请求方法的参数
            JSONObject argJsonObj = this.dealGetMethodArgsArrayFromJoinPoint(joinPoint,request);
            pcWebOperationLogMgo.setActionArgs(argJsonObj.toJSONString());

            Signature signature = joinPoint.getSignature();
            String methodName = signature.getName();
            pcWebOperationLogMgo.setAspectKind(joinPoint.getKind());
            pcWebOperationLogMgo.setClassName(signature.getDeclaringTypeName());
            pcWebOperationLogMgo.setMethodName(methodName);
            pcWebOperationLogMgo.setSignatureLong(signature.toLongString());
            Method method = this.gainReqMethod(signature);
            if (method != null) {
                pcWebOperationLogMgo.setReturnTypeName(method.getReturnType().getName());
                pcWebOperationLogMgo.setDeclaredAnnotations(JSONObject.toJSONString(method.getDeclaredAnnotations()));
                PcWebQueryLog pcWebQueryLog = method.getAnnotation(PcWebQueryLog.class);
                if (pcWebQueryLog != null) {
                    pcWebOperationLogMgo.setAction(this.gainApiOperationMsgWhenBlank(method, pcWebQueryLog.action()));
                    pcWebOperationLogMgo.setLogDescription(this.gainApiOperationNotesIfBlank(method, pcWebQueryLog.description()));
                    //请求的全路径(代码中取得)
                    pcWebOperationLogMgo.setFullPath(pcWebQueryLog.fullPath());
                }
            }

            if (request != null) {
                //请求路径
                if(HttpMethodConstant.GET.equalsIgnoreCase(request.getMethod())){
                    String queryString = (StringUtils.isBlank(request.getQueryString())) ? "" : request.getQueryString() ;
                    pcWebOperationLogMgo.setRequestUri(request.getRequestURI() + queryString);
                    pcWebOperationLogMgo.setRequestUrl(request.getRequestURL().toString() + queryString);
                }   else {
                    pcWebOperationLogMgo.setRequestUri(request.getRequestURI());
                    pcWebOperationLogMgo.setRequestUrl(request.getRequestURL().toString());
                }
                pcWebOperationLogMgo.setReqMethod(request.getMethod());
                //请求的sessionid
                HttpSession session = request.getSession();
                if(session != null){
                    pcWebOperationLogMgo.setSessionId(session.getId());
                }
                //取得 请求头的token信息
                UserAccountToken userAccountToken = routineCommonFunc.gainUserAccountTokenBeanByRequest(request, false);
                if (userAccountToken != null) {
                    pcWebOperationLogMgo.setTokenBean(JSONObject.toJSONString(userAccountToken));
                    String userAccountId = userAccountToken.getUserAccountId();
                    pcWebOperationLogMgo.setUserAccountId(userAccountId);
                    pcWebOperationLogMgo.setCreateUserId(userAccountId);
                    pcWebOperationLogMgo.setLastModifyerId(userAccountId);
                }
                //取得 请求头bean
                RequestHeaderBean requestHeaderBean = routineCommonFunc.gainRequestHeaderBeanByRequest(request);
                if (requestHeaderBean != null) {
                    pcWebOperationLogMgo.setHeaders(JSONObject.toJSONString(requestHeaderBean));
                    pcWebOperationLogMgo.setIpAddr(request.getRemoteAddr());
                }
            }

            Date now = new Date();
            pcWebOperationLogMgo.setCreateTime(now);
            pcWebOperationLogMgo.setLastModifiedDate(now);
        } catch (Exception e) {

        }
    }

    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToLoginLog(PcWebLoginLogMgo pcWebLoginLogMgo, JoinPoint joinPoint, HttpServletRequest request) {
        try {
            if (pcWebLoginLogMgo.getStatus() == null) {
                pcWebLoginLogMgo.setStatus(BaseStateEnum.ENABLED.getValue());
            }
            //请求方法的参数
            JSONObject argJsonObj = this.dealGetMethodArgsArrayFromJoinPoint(joinPoint,request);
            pcWebLoginLogMgo.setActionArgs(argJsonObj.toJSONString());
            Signature signature = joinPoint.getSignature();
            String methodName = signature.getName();
            pcWebLoginLogMgo.setAspectKind(joinPoint.getKind());
            pcWebLoginLogMgo.setClassName(signature.getDeclaringTypeName());
            pcWebLoginLogMgo.setMethodName(methodName);
            pcWebLoginLogMgo.setSignatureLong(signature.toLongString());
            Method method = this.gainReqMethod(signature);
            if (method != null) {
                pcWebLoginLogMgo.setReturnTypeName(method.getReturnType().getName());
                pcWebLoginLogMgo.setDeclaredAnnotations(JSONObject.toJSONString(method.getDeclaredAnnotations()));
                PcWebQueryLog pcWebQueryLog = method.getAnnotation(PcWebQueryLog.class);
                if (pcWebQueryLog != null) {
                    pcWebLoginLogMgo.setAction(this.gainApiOperationMsgWhenBlank(method, pcWebQueryLog.action()));
                    pcWebLoginLogMgo.setLogDescription(this.gainApiOperationNotesIfBlank(method, pcWebQueryLog.description()));
                    //请求的全路径(代码中取得)
                    pcWebLoginLogMgo.setFullPath(pcWebQueryLog.fullPath());
                }
            }
            if (request != null) {
                //请求路径
                if(HttpMethodConstant.GET.equalsIgnoreCase(request.getMethod())){
                    String queryString = (StringUtils.isBlank(request.getQueryString())) ? "" : request.getQueryString() ;
                    pcWebLoginLogMgo.setRequestUri(request.getRequestURI() + queryString);
                    pcWebLoginLogMgo.setRequestUrl(request.getRequestURL().toString() + queryString);
                }   else {
                    pcWebLoginLogMgo.setRequestUri(request.getRequestURI());
                    pcWebLoginLogMgo.setRequestUrl(request.getRequestURL().toString());
                }
                pcWebLoginLogMgo.setReqMethod(request.getMethod());
                //请求的sessionid
                HttpSession session = request.getSession();
                if(session != null){
                    pcWebLoginLogMgo.setSessionId(session.getId());
                }
                //取得 请求头的token信息
                UserAccountToken userAccountToken = routineCommonFunc.gainUserAccountTokenBeanByRequest(request, false);
                if (userAccountToken != null) {
                    pcWebLoginLogMgo.setTokenBean(JSONObject.toJSONString(userAccountToken));
                    String userAccountId = userAccountToken.getUserAccountId();
                    pcWebLoginLogMgo.setUserAccountId(userAccountId);
                    pcWebLoginLogMgo.setCreateUserId(userAccountId);
                    pcWebLoginLogMgo.setLastModifyerId(userAccountId);
                }
                //取得 请求头bean
                RequestHeaderBean requestHeaderBean = routineCommonFunc.gainRequestHeaderBeanByRequest(request);
                if (requestHeaderBean != null) {
                    pcWebLoginLogMgo.setHeaders(JSONObject.toJSONString(requestHeaderBean));
                    pcWebLoginLogMgo.setIpAddr(request.getRemoteAddr());
                }
            }

            Date now = new Date();
            pcWebLoginLogMgo.setCreateTime(now);
            pcWebLoginLogMgo.setLastModifiedDate(now);
        } catch (Exception e) {

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
            e.printStackTrace();
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
            e.printStackTrace();
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
