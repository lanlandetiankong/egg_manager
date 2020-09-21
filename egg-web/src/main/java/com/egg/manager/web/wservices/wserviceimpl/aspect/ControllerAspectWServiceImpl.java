package com.egg.manager.web.wservices.wserviceimpl.aspect;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.base.beans.request.RequestHeaderBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.bean.webvo.session.UserAccountToken;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebLoginLogMO;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebOperationLogMO;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMO;
import com.egg.manager.web.wservices.wservice.aspect.ControllerAspectWService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/19
 * \* Time: 16:10
 * \* Description:
 * \
 */
@Service
public class ControllerAspectWServiceImpl implements ControllerAspectWService {

    @Reference
    private RoutineCommonFunc routineCommonFunc;

    /**
     * 取得 请求的参数
     *
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public JSONObject dealGetMethodArgsArrayFromJoinPoint(JoinPoint joinPoint) {
        JSONObject argJsonObj = new JSONObject();
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();

            for (int i = 0; i < args.length; i++) {
                Object argObj = args[i];
                if (!checkObjectInnstanceOfHttpServlet(argObj)) {     //过滤掉HttpServlet类型的
                    String argName = argNames[i];
                    argJsonObj.put(argName, argObj);
                }
            }
        }
        return argJsonObj;
    }


    /**
     * 取得 请求的参数
     *
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToQueryLog(PcWebQueryLogMO pcWebQueryLogMO, JoinPoint joinPoint, HttpServletRequest request) {
        try {
            if (StringUtils.isBlank(pcWebQueryLogMO.getFid())) {
                //pcWebQueryLogMO.setFid(MyUUIDUtil.renderSimpleUUID());
            }
            if (pcWebQueryLogMO.getStatus() == null) {
                pcWebQueryLogMO.setStatus(BaseStateEnum.ENABLED.getValue());
            }
            //请求方法的参数
            JSONObject argJsonObj = this.dealGetMethodArgsArrayFromJoinPoint(joinPoint);
            pcWebQueryLogMO.setActionArgs(argJsonObj.toJSONString());

            Signature signature = joinPoint.getSignature();
            String methodName = signature.getName();
            pcWebQueryLogMO.setAspectKind(joinPoint.getKind());
            pcWebQueryLogMO.setClassName(signature.getDeclaringTypeName());
            pcWebQueryLogMO.setMethodName(methodName);
            pcWebQueryLogMO.setSignatureLong(signature.toLongString());
            Method method = this.gainReqMethod(signature);
            if (method != null) {
                pcWebQueryLogMO.setReturnTypeName(method.getReturnType().getName());
                pcWebQueryLogMO.setDeclaredAnnotations(JSONObject.toJSONString(method.getDeclaredAnnotations()));
                PcWebQueryLog pcWebQueryLog = method.getAnnotation(PcWebQueryLog.class);
                if (pcWebQueryLog != null) {
                    pcWebQueryLogMO.setAction(pcWebQueryLog.action());
                    pcWebQueryLogMO.setLogDescription(pcWebQueryLog.description());
                    //请求的全路径(代码中取得)
                    pcWebQueryLogMO.setFullPath(pcWebQueryLog.fullPath());
                }
            }

            if (request != null) {
                //请求路径
                pcWebQueryLogMO.setRequestUri(request.getRequestURI());
                pcWebQueryLogMO.setRequestUrl(request.getRequestURL().toString());
                //取得 请求头的token信息
                UserAccountToken userAccountToken = routineCommonFunc.gainUserAccountTokenBeanByRequest(request, false);
                if (userAccountToken != null) {
                    pcWebQueryLogMO.setTokenBean(JSONObject.toJSONString(userAccountToken));
                    String userAccountId = userAccountToken.getUserAccountId();
                    pcWebQueryLogMO.setUserAccountId(userAccountId);
                    pcWebQueryLogMO.setCreateUserId(userAccountId);
                    pcWebQueryLogMO.setLastModifyerId(userAccountId);
                }
                //取得 请求头bean
                RequestHeaderBean requestHeaderBean = routineCommonFunc.gainRequestHeaderBeanByRequest(request);
                if (requestHeaderBean != null) {
                    pcWebQueryLogMO.setHeaders(JSONObject.toJSONString(requestHeaderBean));
                    pcWebQueryLogMO.setIpAddr(request.getRemoteAddr());
                }
            }

            Date now = new Date();
            pcWebQueryLogMO.setCreateTime(now);
            pcWebQueryLogMO.setLastModifiedDate(now);
        } catch (Exception e) {

        }
    }


    /**
     * 取得 请求的参数
     *
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToOperationLog(PcWebOperationLogMO pcWebOperationLogMO, JoinPoint joinPoint, HttpServletRequest request) {
        try {
            if (StringUtils.isBlank(pcWebOperationLogMO.getFid())) {
                //pcWebQueryLogMO.setFid(MyUUIDUtil.renderSimpleUUID());
            }
            if (pcWebOperationLogMO.getStatus() == null) {
                pcWebOperationLogMO.setStatus(BaseStateEnum.ENABLED.getValue());
            }
            //请求方法的参数
            JSONObject argJsonObj = this.dealGetMethodArgsArrayFromJoinPoint(joinPoint);
            pcWebOperationLogMO.setActionArgs(argJsonObj.toJSONString());

            Signature signature = joinPoint.getSignature();
            String methodName = signature.getName();
            pcWebOperationLogMO.setAspectKind(joinPoint.getKind());
            pcWebOperationLogMO.setClassName(signature.getDeclaringTypeName());
            pcWebOperationLogMO.setMethodName(methodName);
            pcWebOperationLogMO.setSignatureLong(signature.toLongString());
            Method method = this.gainReqMethod(signature);
            if (method != null) {
                pcWebOperationLogMO.setReturnTypeName(method.getReturnType().getName());
                pcWebOperationLogMO.setDeclaredAnnotations(JSONObject.toJSONString(method.getDeclaredAnnotations()));
                PcWebQueryLog pcWebQueryLog = method.getAnnotation(PcWebQueryLog.class);
                if (pcWebQueryLog != null) {
                    pcWebOperationLogMO.setAction(pcWebQueryLog.action());
                    pcWebOperationLogMO.setLogDescription(pcWebQueryLog.description());
                    //请求的全路径(代码中取得)
                    pcWebOperationLogMO.setFullPath(pcWebQueryLog.fullPath());
                }
            }

            if (request != null) {
                //请求路径
                pcWebOperationLogMO.setRequestUri(request.getRequestURI());
                pcWebOperationLogMO.setRequestUrl(request.getRequestURL().toString());
                //取得 请求头的token信息
                UserAccountToken userAccountToken = routineCommonFunc.gainUserAccountTokenBeanByRequest(request, false);
                if (userAccountToken != null) {
                    pcWebOperationLogMO.setTokenBean(JSONObject.toJSONString(userAccountToken));
                    String userAccountId = userAccountToken.getUserAccountId();
                    pcWebOperationLogMO.setUserAccountId(userAccountId);
                    pcWebOperationLogMO.setCreateUserId(userAccountId);
                    pcWebOperationLogMO.setLastModifyerId(userAccountId);
                }
                //取得 请求头bean
                RequestHeaderBean requestHeaderBean = routineCommonFunc.gainRequestHeaderBeanByRequest(request);
                if (requestHeaderBean != null) {
                    pcWebOperationLogMO.setHeaders(JSONObject.toJSONString(requestHeaderBean));
                    pcWebOperationLogMO.setIpAddr(request.getRemoteAddr());
                }
            }

            Date now = new Date();
            pcWebOperationLogMO.setCreateTime(now);
            pcWebOperationLogMO.setLastModifiedDate(now);
        } catch (Exception e) {

        }
    }

    /**
     * 取得 请求的参数
     *
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToLoginLog(PcWebLoginLogMO pcWebLoginLogMO, JoinPoint joinPoint, HttpServletRequest request) {
        try {
            if (StringUtils.isBlank(pcWebLoginLogMO.getFid())) {
                //pcWebQueryLogMO.setFid(MyUUIDUtil.renderSimpleUUID());
            }
            if (pcWebLoginLogMO.getStatus() == null) {
                pcWebLoginLogMO.setStatus(BaseStateEnum.ENABLED.getValue());
            }
            //请求方法的参数
            JSONObject argJsonObj = this.dealGetMethodArgsArrayFromJoinPoint(joinPoint);
            pcWebLoginLogMO.setActionArgs(argJsonObj.toJSONString());
            Signature signature = joinPoint.getSignature();
            String methodName = signature.getName();
            pcWebLoginLogMO.setAspectKind(joinPoint.getKind());
            pcWebLoginLogMO.setClassName(signature.getDeclaringTypeName());
            pcWebLoginLogMO.setMethodName(methodName);
            pcWebLoginLogMO.setSignatureLong(signature.toLongString());
            Method method = this.gainReqMethod(signature);
            if (method != null) {
                pcWebLoginLogMO.setReturnTypeName(method.getReturnType().getName());
                pcWebLoginLogMO.setDeclaredAnnotations(JSONObject.toJSONString(method.getDeclaredAnnotations()));
                PcWebQueryLog pcWebQueryLog = method.getAnnotation(PcWebQueryLog.class);
                if (pcWebQueryLog != null) {
                    pcWebLoginLogMO.setAction(pcWebQueryLog.action());
                    pcWebLoginLogMO.setLogDescription(pcWebQueryLog.description());
                    //请求的全路径(代码中取得)
                    pcWebLoginLogMO.setFullPath(pcWebQueryLog.fullPath());
                }
            }
            if (request != null) {
                //请求路径
                pcWebLoginLogMO.setRequestUri(request.getRequestURI());
                pcWebLoginLogMO.setRequestUrl(request.getRequestURL().toString());
                //取得 请求头的token信息
                UserAccountToken userAccountToken = routineCommonFunc.gainUserAccountTokenBeanByRequest(request, false);
                if (userAccountToken != null) {
                    pcWebLoginLogMO.setTokenBean(JSONObject.toJSONString(userAccountToken));
                    String userAccountId = userAccountToken.getUserAccountId();
                    pcWebLoginLogMO.setUserAccountId(userAccountId);
                    pcWebLoginLogMO.setCreateUserId(userAccountId);
                    pcWebLoginLogMO.setLastModifyerId(userAccountId);
                }
                //取得 请求头bean
                RequestHeaderBean requestHeaderBean = routineCommonFunc.gainRequestHeaderBeanByRequest(request);
                if (requestHeaderBean != null) {
                    pcWebLoginLogMO.setHeaders(JSONObject.toJSONString(requestHeaderBean));
                    pcWebLoginLogMO.setIpAddr(request.getRemoteAddr());
                }
            }

            Date now = new Date();
            pcWebLoginLogMO.setCreateTime(now);
            pcWebLoginLogMO.setLastModifiedDate(now);
        } catch (Exception e) {

        }
    }


    /**
     * 取得当前调用的 method 对象
     *
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
     *
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
     *
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
}
