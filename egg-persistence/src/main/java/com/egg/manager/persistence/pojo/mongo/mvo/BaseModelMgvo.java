package com.egg.manager.persistence.pojo.mongo.mvo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Data
public class BaseModelMgvo<K> extends BaseMgvo {
    private K fid;

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
     * 日志描述
     */
    private String logDescription;

    /**
     * aop通知方式
     */
    private String aspectNotifyType;

    /**
     * 返回结果-json
     */
    private String result;
    /**
     * 异常信息
     */
    private String exception;

    /**
     * 是否成功 1:成功 2异常
     */
    private Short isSuccess;
    /**
     * 异常堆栈信息
     */
    private String message;

    /**
     * 方法参数->json
     */
    private String actionArgs;
    /**
     * method 返回值类型
     */
    private String returnTypeName;

    /**
     * 请求的方法完整内容
     */
    private String signatureLong;
    /**
     * aop类型，由joinPoint取得
     */
    private String aspectKind;

    /**
     * 定义的注解->json
     */
    private String declaredAnnotations;

    /**
     * 顺序
     */
    private Integer orderNum;
    /**
     * 状态
     */
    private Short status;
    private Integer version;

    /**
     * 创建人id
     */
    private String createUserId;
    /**
     * 创建人名称
     */
    private String createUserNickName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新用户id
     */
    private String lastModifyerId;
    /**
     * 最后更新用户名称
     */
    private String lastModifyerNickName;
    /**
     * 最后更新时间
     */
    private Date lastModifiedDate;
    private String remark;

    /**
     * 方法总花费时间
     */
    private Long totalSpendTime ;
    /**
     * 计时器 输出信息
     */
    private String stopWatchPrint ;
}
