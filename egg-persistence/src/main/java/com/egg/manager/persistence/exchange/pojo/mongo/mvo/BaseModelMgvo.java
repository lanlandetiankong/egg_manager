package com.egg.manager.persistence.exchange.pojo.mongo.mvo;

import com.egg.manager.persistence.exchange.db.mongo.mo.clazz.EggClazzInfoLogMgo;
import lombok.Data;

import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
public class BaseModelMgvo<K> extends BaseMgvo {
    private K fid;


    /**
     * 切面类的class相关信息
     */
    private EggClazzInfoLogMgo clazzInfo ;

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
     * aop通知方式
     */
    private String aspectNotifyType;
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
