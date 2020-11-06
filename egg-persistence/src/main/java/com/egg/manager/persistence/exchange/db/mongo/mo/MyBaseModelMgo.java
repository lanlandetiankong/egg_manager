package com.egg.manager.persistence.exchange.db.mongo.mo;

import com.egg.manager.persistence.exchange.db.mongo.mo.clazz.EggClazzInfoLogMgo;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Data
public class MyBaseModelMgo<K> implements Serializable {

    @Id
    private K fid;

    /**
     * 切面类的class相关信息
     */
    @Field(value = "clazzInfo")
    private EggClazzInfoLogMgo clazzInfo ;


    /**
     * 返回结果-json
     */
    @Field(value = "result")
    private String result;
    /**
     * 异常信息
     */
    @Field(value = "exception")
    private String exception;

    /**
     * 是否成功 1:成功 2异常
     */
    @Field(value = "isSuccess")
    private Short isSuccess;
    /**
     * 异常堆栈信息
     */
    @Field(value = "message")
    private String message;


    /**
     * aop通知方式
     */
    @Field(value = "aspectNotifyType")
    private String aspectNotifyType;

    /**
     * 顺序
     */
    @Field(value = "orderNum")
    private Integer orderNum;
    /**
     * 状态
     */
    @Field(value = "status")
    private Short status;

    @Version
    @Field(value = "version")
    private Integer version;

    /**
     * 创建人id
     */
    @Field(value = "createUserId")
    private Long createUserId;
    /**
     * 创建人名称
     */
    @Field(value = "createUserNickName")
    private String createUserNickName;
    /**
     * 创建时间
     */
    @CreatedDate
    @Field(value = "createTime")
    private Date createTime;
    /**
     * 最后更新用户id
     */
    @Field(value = "lastModifyerId")
    private Long lastModifyerId;
    /**
     * 最后更新用户名称
     */
    @Field(value = "lastModifyerNickName")
    private String lastModifyerNickName;
    /**
     * 最后更新时间
     */
    @LastModifiedDate
    @Field(value = "lastModifiedDate")
    private Date lastModifiedDate;
    /**
     * 备注信息
     */
    @Field(value = "remark")
    private String remark;
    /**
     * 方法总花费时间
     */
    @Field(value = "totalSpendTime")
    private Long totalSpendTime ;
    /**
     * 计时器 输出信息
     */
    @Field(value = "stopWatchPrint")
    private String stopWatchPrint ;
}
