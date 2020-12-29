package com.egg.manager.facade.persistence.exchange.db.mongo.mo.clazz;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Description:
 * @ClassName: EggClazzInfoLogMgo
 * @Author: zhoucj
 * @Date: 2020/11/6 15:11
 */
@Data
public class EggClazzInfoLogMgo {

    /**
     * 类名称
     */
    @Field(value = "className")
    private String className;
    /**
     * 方法名称
     */
    @Field(value = "methodName")
    private String methodName;
    /**
     * 定义的注解->json
     */
    @Field(value = "declaredAnnotations")
    private String declaredAnnotations;

    /**
     * 请求的方法完整内容
     */
    @Field(value = "signatureLong")
    private String signatureLong;

    /**
     * 方法参数->json
     */
    @Field(value = "actionArgs")
    private String actionArgs;
    /**
     * method 返回值类型
     */
    @Field(value = "returnTypeName")
    private String returnTypeName;


    /**
     * aop类型，由joinPoint取得
     */
    @Field(value = "aspectKind")
    private String aspectKind;


    /**
     * 操作
     */
    @Field(value = "action")
    private String action;

    /**
     * 日志描述
     */
    @Field(value = "logDescription")
    private String logDescription;


}