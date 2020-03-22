package com.egg.manager.common.base.beans.request;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/19
 * \* Time: 20:26
 * \* Description:
 * \
 */
@Data
public class RequestHeaderBean {
    private String host ;
    private String connection ;
    private String contentLength ;
    private String accept ;
    private String origin ;
    private String token ;
    private String userAgent;
    private String contentType;
    private String referer;
    private String acceptEncoding;
    private String acceptLanguage;




    public static RequestHeaderBean jsonObjectToBean(JSONObject jsonObject){
        RequestHeaderBean requestHeaderBean = null ;
        if(jsonObject != null) {
            requestHeaderBean = new RequestHeaderBean() ;
            requestHeaderBean.setHost(jsonObject.getString("host"));
            requestHeaderBean.setConnection(jsonObject.getString("connection"));
            requestHeaderBean.setContentLength(jsonObject.getString("content-length"));
            requestHeaderBean.setAccept(jsonObject.getString("accept"));
            requestHeaderBean.setOrigin(jsonObject.getString("origin"));
            requestHeaderBean.setToken(jsonObject.getString("token"));
            requestHeaderBean.setUserAgent(jsonObject.getString("user-agent"));
            requestHeaderBean.setContentType(jsonObject.getString("content-type"));
            requestHeaderBean.setReferer(jsonObject.getString("referer"));
            requestHeaderBean.setAcceptEncoding(jsonObject.getString("accept-encoding"));
            requestHeaderBean.setAcceptLanguage(jsonObject.getString("accept-language"));
        }
        return requestHeaderBean ;
    }




}
