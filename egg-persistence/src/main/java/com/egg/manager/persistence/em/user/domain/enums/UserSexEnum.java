package com.egg.manager.persistence.em.user.domain.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public enum UserSexEnum {
    Man((short) 1, "男", "男"),
    Woman((short) 0, "女", "女"),
    Unknow((short) -1, "未知", "未知"),
    ;
    private Short value;
    private String name;
    private String label;

    UserSexEnum(Short value, String name, String label) {
        this.value = value;
        this.name = name;
        this.label = label;
    }

    public static String dealGetNameByVal(Short value) {
        UserSexEnum[] enums = UserSexEnum.values();
        for (UserSexEnum enumObj : enums) {
            if (enumObj.getValue().equals(value)) {
                return enumObj.getName();
            }
        }
        return "";
    }

    public static Short dealGetValByName(String value) {
        UserSexEnum[] enums = UserSexEnum.values();
        value = StringUtils.isBlank(value) ? "" : value;
        //去除空格
        value = value.trim().replace(" ", "");
        for (UserSexEnum enumObj : enums) {
            if (enumObj.getName().equals(value)) {
                return enumObj.getValue();
            }
        }
        return Unknow.getValue();
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
