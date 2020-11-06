package com.egg.manager.persistence.exchange.db.mongo.mo.http;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Description: 请求信息
 * @ClassName: EggRequestInfo
 * @Author: zhoucj
 * @Date: 2020/11/6 14:54
 */
@Data
public class EggRequestInfo {
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
     * 发起请求的ip地址
     */
    @Field(value = "ipAddr")
    private String ipAddr;

}