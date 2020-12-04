package com.egg.manager.persistence.em.define.domain.enums;

import lombok.Getter;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Getter
public enum DefinePermissionCodePrefixEnum {
    PermCtrl("PermCtrl:", "权限控制前缀", "PermCtrl:", true),
    ;

    DefinePermissionCodePrefixEnum(String value, String name, String label, boolean defaultCheck) {
        this.value = value;
        this.name = name;
        this.label = label;
        this.defaultCheck = defaultCheck;
    }

    private String value;
    private String name;
    private String label;
    private boolean defaultCheck;


}