package com.egg.manager.persistence.em.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Getter
@AllArgsConstructor
public enum UserAccountStateEnum {
    ENABLED((short) 1, "启用", "启用"),
    DISABLED((short) 0, "禁用", "禁用"),
    DELETE((short) -1, "删除", "用户已被删除"),
    LOCKED((short) -10, "锁定", "用户已被锁定"),
    ;


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
}
