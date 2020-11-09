package com.egg.manager.persistence.em.logs.db.mongo.mo.pc;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.exchange.db.mongo.mo.http.EggRequestInfo;
import com.egg.manager.persistence.exchange.db.mongo.mo.http.ua.EggUserAgentMgo;
import com.egg.manager.persistence.exchange.db.mongo.mo.MyBaseModelMgo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;


/**
 *@description
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
    private UserAccountEntity loginUser;
    /**
     * 注解操作类型
     */
    @Field(value = "annotationOperationType")
    private String annotationOperationType;
    /**
     * 请求的全路径(相对于项目路径)
     */
    @Field(value = "fullPath")
    private String fullPath;
    /**
     * 请求的Request信息
     */
    @Field(value = "requestInfo")
    private EggRequestInfo requestInfo ;
    /**
     * userAgent信息
     */
    @Field(value = "userAgent")
    private EggUserAgentMgo userAgent;

}