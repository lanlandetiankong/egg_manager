package com.egg.manager.persistence.pojo.mongo.mvo.log.pc.web;

import com.egg.manager.persistence.pojo.mongo.mvo.MyBaseModelMVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 查询日志表 - MongoDB
 * </p>
 *
 * @author zhouchengjie123
 * @since 2020-07-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PcWebQueryLogMVO extends MyBaseModelMVO<String> {
    /**
     * 登录的用户id
     */
    private String userAccountId;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String methodName;

    /**
     * @OperLog 操作
     */
    private String action;
    /**
     * 请求的全路径(相对于项目路径)
     */
    private String fullPath;
    /**
     * 日志描述
     */
    private String logDescription;
    /**
     * 方法参数->json
     */
    private String actionArgs;

    /**
     * 是否成功 1:成功 2异常
     */
    private Integer isSuccess;
    /**
     * 异常堆栈信息
     */
    private String message;
    /**
     * 请求的ip地址
     */
    private String ipAddr;
    /**
     * 返回结果-json
     */
    private String result;
    /**
     * 异常信息
     */
    private String exception;

    /**
     * 请求的方法完整内容
     */
    private String signatureLong;
    /**
     * aop方式
     */
    private String aspectKind;
    /**
     * aop通知方式
     */
    private String aspectNotifyType;
    /**
     * 发起请求的token ->json
     */
    private String tokenBean;
    /**
     * 发起请求的header ->json
     */
    private String headers;
    /**
     * 发起请求的uri
     */
    private String requestUri;
    /**
     * 发起请求的路径
     */
    private String requestUrl;

    /**
     * 返回值类型
     */
    private String returnTypeName;
    /**
     * 定义的注解->json
     */
    private String declaredAnnotations;


    private String remark;


}
