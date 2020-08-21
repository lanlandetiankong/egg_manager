package com.egg.manager.persistence.mongo.mo.log;

import com.egg.manager.persistence.mongo.mo.MyBaseModelMO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>
 * 操作日志表 - MongoDB
 * </p>
 *
 * @author zhouchengjie123
 * @since 2020-07-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "em_operation_log")
public class OperationLogMO extends MyBaseModelMO<String> {

    private static final long serialVersionUID = 1L;

    private String userAccountId;       //登录的用户id
    private String className;   //类名称
    private String methodName;  //方法名称

    /**
     * @OperLog
     */
    private String action;  //操作
    private String logDescription;  //日志描述

    private String actionArgs;  //方法参数->json


    private Short isSuccess;  //是否成功 1:成功 2异常
    private String message; //异常堆栈信息
    private String ipAddr;

    private String result;     //返回结果-json
    private String exception;     //返回结果-json


    private String signatureLong;   //请求的方法完整内容
    private String aspectKind;
    private String aspectNotifyType;   //aop通知方式
    /**
     * request
     */
    private String tokenBean;   //发起请求的token ->json
    private String headers;    //发起请求的header ->json
    private String requestUri;     //发起请求的uri
    private String requestUrl;     //发起请求的路径

    /**
     * method
     */
    private String returnTypeName;  //返回值类型
    private String declaredAnnotations;    //定义的注解->json

    private String remark;

}
