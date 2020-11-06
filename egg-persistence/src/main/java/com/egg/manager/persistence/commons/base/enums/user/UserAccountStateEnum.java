package com.egg.manager.persistence.commons.base.enums.user;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public enum UserAccountStateEnum {
    ENABLED((short) 1, "启用", "启用"),
    DISABLED((short) 0, "禁用", "禁用"),
    DELETE((short) -1, "删除", "用户已被删除"),
    LOCKED((short) -10, "锁定", "用户已被锁定"),
    ;

    UserAccountStateEnum(Short value, String name, String info) {
        this.value = value;
        this.name = name;
        this.info = info;
    }

    private Short value;
    private String name;
    private String info;


    public static String doGetEnumInfoByValue(Short value) {
        if (value == null) {
            return null;
        }
        UserAccountStateEnum[] enums = UserAccountStateEnum.values();
        for (UserAccountStateEnum enumObj : enums) {
            if (enumObj.value.equals(value)) {
                return enumObj.getInfo();
            }
        }
        return "";
    }


    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
