package com.egg.manager.common.base.enums.user;

public enum UserAccountBaseTypeEnum {
    SimpleUser(10,"普通用户"),
    SuperRoot(100,"超级管理员");

    UserAccountBaseTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    private Integer value ;
    private String name ;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
