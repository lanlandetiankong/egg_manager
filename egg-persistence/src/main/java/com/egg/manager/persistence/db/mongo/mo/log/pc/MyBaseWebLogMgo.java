package com.egg.manager.persistence.db.mongo.mo.log.pc;
import com.egg.manager.persistence.db.mongo.mo.MyBaseModelMgo;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 *@description:
 *@author: zhoucj
 *@create: 2020-10-21 14:59
 */
@Data
public class MyBaseWebLogMgo <K> extends MyBaseModelMgo<K> {
    /**
     * 登录的用户id
     */
    @Field(value = "userAccountId")
    private String userAccountId;
    /**
     * 登录的用户名称
     */
    @Field(value = "userNickName")
    private String userNickName;
    /**
     * 登录的用户
     */
    @Field(value = "loginUser")
    private UserAccount loginUser;
    /**
     * 注解操作类型
     */
    @Field(value = "annotationOperationType")
    private String annotationOperationType;
    /**
     * http请求方式
     */
    @Field(value = "reqMethod")
    private String reqMethod ;
    /**
     * session id
     */
    @Field(value = "sessionId")
    private String sessionId ;
    /**
     * 发起请求的token ->json(从request取得)
     */
    @Field(value = "tokenBean")
    private String tokenBean;
    /**
     * 发起请求的header ->json(从request取得)
     */
    @Field(value = "headers")
    private String headers;
    /**
     * 发起请求的uri(从request取得)
     */
    @Field(value = "requestUri")
    private String requestUri;
    /**
     * 发起请求的路径(从request取得)
     */
    @Field(value = "requestUrl")
    private String requestUrl;
    /**
     * 请求的全路径(相对于项目路径)
     */
    @Field(value = "fullPath")
    private String fullPath;
    /**
     * 发起请求的ip地址
     */
    @Field(value = "ipAddr")
    private String ipAddr;
    /**
     * 方法开始执行时间
     */
    @Field(value = "startMethodTime")
    private Date startMethodTime ;
    /**
     * 方法执行结束时间
     */
    @Field(value = "endMethodTime")
    private Date endMethodTime ;
    /**
     * 方法总花费时间
     */
    @Field(value = "totalSpendTime")
    private Long totalSpendTime ;
    /**
     * 计时器 输出信息
     */
    private String stopWatchPrint ;
}