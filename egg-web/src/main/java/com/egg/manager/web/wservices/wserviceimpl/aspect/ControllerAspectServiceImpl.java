package com.egg.manager.web.wservices.wserviceimpl.aspect;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.base.beans.request.RequestHeaderBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.bean.webvo.session.UserAccountToken;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebLoginLogMgo;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebOperationLogMgo;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMgo;
import com.egg.manager.web.wservices.wservice.aspect.ControllerAspectService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ControllerAspectServiceImpl implements ControllerAspectService {

    @Autowired
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
     *
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToQueryLog(PcWebQueryLogMgo pcWebQueryLogMgo, JoinPoint joinPoint, HttpServletRequest request) {
        try {
            if (StringUtils.isBlank(pcWebQueryLogMgo.getFid())) {
                //pcWebQueryLogMgo.setFid(MyUUIDUtil.renderSimpleUuid());
            }
            if (pcWebQueryLogMgo.getStatus() == null) {
                pcWebQueryLogMgo.setStatus(BaseStateEnum.ENABLED.getValue());
            }
            //请求方法的参数
            JSONObject argJsonObj = this.dealGetMethodArgsArrayFromJoinPoint(joinPoint);
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
                    pcWebQueryLogMgo.setAction(pcWebQueryLog.action());
                    pcWebQueryLogMgo.setLogDescription(pcWebQueryLog.description());
                    //请求的全路径(代码中取得)
                    pcWebQueryLogMgo.setFullPath(pcWebQueryLog.fullPath());
                }
            }

            if (request != null) {
                //请求路径
                pcWebQueryLogMgo.setRequestUri(request.getRequestURI());
                pcWebQueryLogMgo.setRequestUrl(request.getRequestURL().toString());
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
     *
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToOperationLog(PcWebOperationLogMgo pcWebOperationLogMgo, JoinPoint joinPoint, HttpServletRequest request) {
        try {
            if (StringUtils.isBlank(pcWebOperationLogMgo.getFid())) {
                //pcWebQueryLogMO.setFid(MyUUIDUtil.renderSimpleUuid());
            }
            if (pcWebOperationLogMgo.getStatus() == null) {
                pcWebOperationLogMgo.setStatus(BaseStateEnum.ENABLED.getValue());
            }
            //请求方法的参数
            JSONObject argJsonObj = this.dealGetMethodArgsArrayFromJoinPoint(joinPoint);
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
                    pcWebOperationLogMgo.setAction(pcWebQueryLog.action());
                    pcWebOperationLogMgo.setLogDescription(pcWebQueryLog.description());
                    //请求的全路径(代码中取得)
                    pcWebOperationLogMgo.setFullPath(pcWebQueryLog.fullPath());
                }
            }

            if (request != null) {
                //请求路径
                pcWebOperationLogMgo.setRequestUri(request.getRequestURI());
                pcWebOperationLogMgo.setRequestUrl(request.getRequestURL().toString());
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
     *
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToLoginLog(PcWebLoginLogMgo pcWebLoginLogMgo, JoinPoint joinPoint, HttpServletRequest request) {
        try {
            if (StringUtils.isBlank(pcWebLoginLogMgo.getFid())) {
                //pcWebQueryLogMO.setFid(MyUUIDUtil.renderSimpleUuid());
            }
            if (pcWebLoginLogMgo.getStatus() == null) {
                pcWebLoginLogMgo.setStatus(BaseStateEnum.ENABLED.getValue());
            }
            //请求方法的参数
            JSONObject argJsonObj = this.dealGetMethodArgsArrayFromJoinPoint(joinPoint);
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
                    pcWebLoginLogMgo.setAction(pcWebQueryLog.action());
                    pcWebLoginLogMgo.setLogDescription(pcWebQueryLog.description());
                    //请求的全路径(代码中取得)
                    pcWebLoginLogMgo.setFullPath(pcWebQueryLog.fullPath());
                }
            }
            if (request != null) {
                //请求路径
                pcWebLoginLogMgo.setRequestUri(request.getRequestURI());
                pcWebLoginLogMgo.setRequestUrl(request.getRequestURL().toString());
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
