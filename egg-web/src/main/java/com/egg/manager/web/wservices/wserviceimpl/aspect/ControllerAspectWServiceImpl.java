package com.egg.manager.web.wservices.wserviceimpl.aspect;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.common.annotation.log.OperLog;
import com.egg.manager.api.services.basic.CommonFuncService;
import com.egg.manager.web.wservices.wservice.aspect.ControllerAspectWService;
import com.egg.manager.common.base.beans.request.RequestHeaderBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.db.mongo.mo.log.OperationLogMO;
import com.egg.manager.persistence.bean.webvo.session.UserAccountToken;
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
    public void dealSetValToOperationLog(OperationLogMO operationLogMO, JoinPoint joinPoint, HttpServletRequest request)  {
        try{
            if(StringUtils.isBlank(operationLogMO.getFid())){
                //operationLogMO.setFid(MyUUIDUtil.renderSimpleUUID());
            }
            if(operationLogMO.getStatus() == null){
                operationLogMO.setStatus(BaseStateEnum.ENABLED.getValue());
            }
            //请求方法的参数
            JSONObject argJsonObj = this.dealGetMethodArgsArrayFromJoinPoint(joinPoint);
            operationLogMO.setActionArgs(argJsonObj.toJSONString());

            Signature signature = joinPoint.getSignature() ;
            String methodName = signature.getName();
            operationLogMO.setAspectKind(joinPoint.getKind());
            operationLogMO.setClassName(signature.getDeclaringTypeName());
            operationLogMO.setMethodName(methodName);
            operationLogMO.setSignatureLong(signature.toLongString());
            Method method = this.gainReqMethod(signature);
            if(method != null){
                operationLogMO.setReturnTypeName(method.getReturnType().getName());
                operationLogMO.setDeclaredAnnotations(JSONObject.toJSONString(method.getDeclaredAnnotations()));
                OperLog operLog = method.getAnnotation(OperLog.class);
                if(operLog != null){
                    operationLogMO.setAction(operLog.action());
                    operationLogMO.setLogDescription(operLog.description());
                    //请求的全路径(代码中取得)
                    operationLogMO.setFullPath(operLog.fullPath());
                }
            }

            if(request != null){
                //请求路径
                operationLogMO.setRequestUri(request.getRequestURI());
                operationLogMO.setRequestUrl(request.getRequestURL().toString());
                //取得 请求头的token信息
                UserAccountToken userAccountToken = commonFuncService.gainUserAccountTokenBeanByRequest(request,false);
                if(userAccountToken != null){
                    operationLogMO.setTokenBean(JSONObject.toJSONString(userAccountToken));
                    String userAccountId = userAccountToken.getUserAccountId() ;
                    operationLogMO.setUserAccountId(userAccountId);
                    operationLogMO.setCreateUserId(userAccountId);
                    operationLogMO.setLastModifyerId(userAccountId);
                }
                //取得 请求头bean
                RequestHeaderBean requestHeaderBean = commonFuncService.gainRequestHeaderBeanByRequest(request);
                if(requestHeaderBean != null){
                    operationLogMO.setHeaders(JSONObject.toJSONString(requestHeaderBean));
                    operationLogMO.setIpAddr(request.getRemoteAddr());
                }
            }

            Date now = new Date() ;
            operationLogMO.setCreateTime(now);
            operationLogMO.setLastModifiedDate(now);
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
