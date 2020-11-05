package com.egg.manager.persistence.em.logs.db.mongo.mo.pc;
import com.egg.manager.persistence.exchange.db.mongo.mo.MyBaseModelMgo;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;


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
    private Long userAccountId;
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

}