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
public enum UserAccountBaseTypeEnum {
    SimpleUser(0, "普通用户", "普通用户", true),
    Root(10, "管理员", "管理员", true),
    SuperRoot(100, "超级管理员", "超级管理员", true),
    Vip(110, "会员", "会员", true),
    SuperVip(190, "超级会员", "超级会员", true),
    GeneratedUser(-20, "随机生成用户", "随机生成用户", true),
    Visitor(-10, "游客", "游客", false);


    public Integer value;
    public String name;
    public String label;
    /**
     * 是否可转化为 list
     */
    public boolean listAble;

    public static UserAccountBaseTypeEnum doGetEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        UserAccountBaseTypeEnum[] enums = UserAccountBaseTypeEnum.values();
        for (UserAccountBaseTypeEnum enumObj : enums) {
            if (enumObj.value.equals(value)) {
                return enumObj;
            }
        }
        return null;
    }

    public static String doGetEnumNameByValue(Integer value, String nullToVal) {
        UserAccountBaseTypeEnum baseTypeEnum = doGetEnumByValue(value);
        if (baseTypeEnum != null) {
            return (baseTypeEnum.getName() != null) ? baseTypeEnum.getName() : nullToVal;
        }
        return nullToVal;
    }


}
