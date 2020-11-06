package com.egg.manager.persistence.em.logs.pojo.mvo.pc;

import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.exchange.db.mongo.mo.http.EggRequestInfo;
import com.egg.manager.persistence.exchange.db.mongo.mo.http.ua.EggUserAgentMgo;
import com.egg.manager.persistence.exchange.pojo.mongo.mvo.BaseModelMgvo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *@description:
 *@author: zhoucj
 *@create: 2020-10-21 14:59
 */
@Data
public class MyBaseWebLogMgvo<K> extends BaseModelMgvo<K> {
    /**
     * 登录的用户id
     */
    private Long userAccountId;
    /**
     * 登录的用户名称
     */
    private String userNickName;
    /**
     * 登录的用户
     */
    private UserAccount loginUser;
    /**
     * 注解操作类型
     */
    private String annotationOperationType;
    /**
     * 请求的Request信息
     */
    private EggRequestInfo requestInfo ;
    /**
     * userAgent信息
     */
    private EggUserAgentMgo userAgent;

    /**
     * 请求的全路径(相对于项目路径)
     */
    private String fullPath;

}