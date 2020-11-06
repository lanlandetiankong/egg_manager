package com.egg.manager.persistence.commons.base.helper;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
public enum ErrorActionEnum {
    AuthenticationExpired("AuthenticationExpired", "身份认证过期", "");

    ErrorActionEnum() {
    }

    ErrorActionEnum(String type, String description, String info) {
        this.type = type;
        this.description = description;
        this.info = info;
    }

    private String type;
    private String description;
    private String info;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
