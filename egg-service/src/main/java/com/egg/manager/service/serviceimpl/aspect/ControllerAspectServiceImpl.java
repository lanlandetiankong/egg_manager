package com.egg.manager.service.serviceimpl.aspect;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.service.annotation.log.OperLog;
import com.egg.manager.common.base.beans.request.RequestHeaderBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.entity.log.OperationLog;
import com.egg.manager.service.service.CommonFuncService;
import com.egg.manager.service.service.aspect.ControllerAspectService;
import com.egg.manager.service.webvo.session.UserAccountToken;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

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
    private CommonFuncService commonFuncService ;

    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public JSONObject dealGetMethodArgsArrayFromJoinPoint(JoinPoint joinPoint){
        JSONObject argJsonObj = new JSONObject() ;
        Object[] args = joinPoint.getArgs();
        if(args != null && args.length > 0){
            String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();

            for (int i=0;i<args.length;i++) {
                Object argObj = args[i] ;
                if(!checkObjectInnstanceOfHttpServlet(argObj)){     //过滤掉HttpServlet类型的
                    String argName = argNames[i];
                    argJsonObj.put(argName,argObj);
                }
            }
        }
        return argJsonObj ;
    }


    /**
     * 取得 请求的参数
     * @param joinPoint
     * @return JSONObject
     */
    @Override
    public void dealSetValToOperationLog(OperationLog operationLog,JoinPoint joinPoint,HttpServletRequest request)  {
        try{
            if(StringUtils.isBlank(operationLog.getFid())){
                operationLog.setFid(MyUUIDUtil.renderSimpleUUID());
            }
            if(operationLog.getState() == null){
                operationLog.setState(BaseStateEnum.ENABLED.getValue());
            }
            //请求方法的参数
            JSONObject argJsonObj = this.dealGetMethodArgsArrayFromJoinPoint(joinPoint);
            operationLog.setActionArgs(argJsonObj.toJSONString());

            Signature signature = joinPoint.getSignature() ;
            String methodName = signature.getName();
            operationLog.setAspectKind(joinPoint.getKind());
            operationLog.setClassName(signature.getDeclaringTypeName());
            operationLog.setMethodName(methodName);
            operationLog.setSignatureLong(signature.toLongString());
            Method method = this.gainReqMethod(signature);
            if(method != null){
                operationLog.setReturnTypeName(method.getReturnType().getName());
                operationLog.setDeclaredAnnotations(JSONObject.toJSONString(method.getDeclaredAnnotations()));
                OperLog operLog = method.getAnnotation(OperLog.class);
                if(operLog != null){
                    operationLog.setAction(operLog.action());
                    operationLog.setModelName(operLog.modelName());
                    operationLog.setLogDescription(operLog.description());
                }
            }


            if(request != null){
                //请求路径
                operationLog.setRequestUri(request.getRequestURI());
                operationLog.setRequestUrl(request.getRequestURL().toString());
                //取得 请求头的token信息
                UserAccountToken userAccountToken = commonFuncService.gainUserAccountTokenBeanByRequest(request,false);
                if(userAccountToken != null){
                    operationLog.setTokenBean(JSONObject.toJSONString(userAccountToken));
                    String userAccountId = userAccountToken.getUserAccountId() ;
                    operationLog.setUserAccountId(userAccountId);
                    operationLog.setCreateUserId(userAccountId);
                    operationLog.setLastModifyerId(userAccountId);
                }
                //取得 请求头bean
                RequestHeaderBean requestHeaderBean = commonFuncService.gainRequestHeaderBeanByRequest(request);
                if(requestHeaderBean != null){
                    operationLog.setHeaders(JSONObject.toJSONString(requestHeaderBean));
                    operationLog.setIpAddr(request.getRemoteAddr());
                }
            }

            Date now = new Date() ;
            operationLog.setCreateTime(now);
            operationLog.setUpdateTime(now);
        }   catch (Exception e){

        }
    }





    /**
     * 取得当前调用的 method 对象
     * @param signature
     * @return
     */
    @Override
    public Method gainReqMethod(Signature signature){
        Method method = null ;
        try {
            String methodName = signature.getName();
            Class[] parameterTypes = ((MethodSignature)signature).getMethod().getParameterTypes();
            method = signature.getDeclaringType().getMethod(methodName,parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method ;
    }

    /**
     * 取得当前调用的 method 对象
     * @param joinPoint
     * @return
     */
    @Override
    public Method gainReqMethod(JoinPoint joinPoint){
        Method method = null ;
        try {
            Signature signature = joinPoint.getSignature();
            String methodName = signature.getName();
            Class[] parameterTypes = ((MethodSignature)signature).getMethod().getParameterTypes();
            method = signature.getDeclaringType().getMethod(methodName,parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method ;
    }



    /**
     * 判断一个对象是否属于  HttpServlet 下的
     * @param argObj
     * @return
     */
    public boolean checkObjectInnstanceOfHttpServlet(Object argObj){
        boolean flag = false ;
        if(argObj instanceof HttpServletRequest || argObj instanceof HttpServletResponse){
            flag = true;
        }
        return  flag ;
    }
}
