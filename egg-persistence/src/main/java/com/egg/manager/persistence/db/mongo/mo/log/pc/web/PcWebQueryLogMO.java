package com.egg.manager.persistence.db.mongo.mo.log.pc.web;

import com.egg.manager.persistence.db.mongo.mo.MyBaseModelMO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>
 * 查询日志表 - MongoDB
 * </p>
 *
 * @author zhouchengjie123
 * @since 2020-07-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "em_pc_web_query_log")
public class PcWebQueryLogMO extends MyBaseModelMO<String> {

    private static final long serialVersionUID = 1L;
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
     * 操作
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
    private Short isSuccess;
    /**
     * 异常堆栈信息
     */
    private String message;
    /**
     * 发起请求的ip地址
     */
    private String ipAddr;
    /**
     * 返回结果-json
     */
    private String result;
    /**
     *
     */
    private String exception;

    /**
     * 请求的方法完整内容
     */
    private String signatureLong;
    private String aspectKind;
    /**
     * aop通知方式
     */
    private String aspectNotifyType;
    /**
     * 发起请求的token ->json(从request取得)
     */
    private String tokenBean;
    /**
     * 发起请求的header ->json(从request取得)
     */
    private String headers;
    /**
     * 发起请求的uri(从request取得)
     */
    private String requestUri;
    /**
     * 发起请求的路径(从request取得)
     */
    private String requestUrl;

    /**
     * method 返回值类型
     */
    private String returnTypeName;
    /**
     * 定义的注解->json
     */
    private String declaredAnnotations;

    private String remark;

}
