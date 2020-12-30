package com.egg.manager.persistence.commons.base.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Getter
@AllArgsConstructor
public enum ErrorActionEnum {
    AuthenticationExpired("AuthenticationExpired", "身份认证过期", "");

    private String type;
    private String description;
    private String info;

}
