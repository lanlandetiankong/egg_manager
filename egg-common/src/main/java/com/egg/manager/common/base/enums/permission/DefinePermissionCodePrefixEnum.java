package com.egg.manager.common.base.enums.permission;

import lombok.*;

@Getter
public enum DefinePermissionCodePrefixEnum {
    PermCtrl("PermCtrl:","权限控制前缀","PermCtrl:",true),
    ;

    DefinePermissionCodePrefixEnum(String value, String name, String label,boolean defaultCheck) {
        this.value = value;
        this.name = name;
        this.label = label;
        this.defaultCheck = defaultCheck;
    }

    private String value ;
    private String name ;
    private String label ;
    private boolean defaultCheck ;



}
