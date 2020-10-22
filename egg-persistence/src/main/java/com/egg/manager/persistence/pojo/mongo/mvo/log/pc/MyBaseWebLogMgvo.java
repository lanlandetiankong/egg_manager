package com.egg.manager.persistence.pojo.mongo.mvo.log.pc;

import com.egg.manager.persistence.pojo.mongo.mvo.BaseModelMgvo;
import lombok.Data;

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
    private String userAccountId;
    /**
     * 注解操作类型
     */
    private String annotationOperationType;
    /**
     * http请求方式
     */
    private String reqMethod ;
    /**
     * session id
     */
    private String sessionId ;
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
     * 请求的全路径(相对于项目路径)
     */
    private String fullPath;
    /**
     * 发起请求的ip地址
     */
    private String ipAddr;
}