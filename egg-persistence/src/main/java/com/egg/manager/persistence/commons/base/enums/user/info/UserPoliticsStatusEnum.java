package com.egg.manager.persistence.commons.base.enums.user.info;

/**
 * @author zhoucj
 * @description 政治面貌 类型枚举类
 * @date 2020/10/20
 */
public enum UserPoliticsStatusEnum {
    PuTongGongMin(0, "普通公民", "普通公民"),
    GongQingTuanYuan(60, "共青团员", "共青团员"),
    ZhongGongYuBeiTuanYuan(80, "中共预备党员", "中共预备党员"),
    ZhongGongDangYuan(100, "中共党员", "中共党员");

    UserPoliticsStatusEnum(Integer value, String name, String label) {
        this.value = value;
        this.name = name;
        this.label = label;

    }

    private Integer value;
    private String name;
    private String label;


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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public static UserPoliticsStatusEnum doGetEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        UserPoliticsStatusEnum[] enums = UserPoliticsStatusEnum.values();
        for (UserPoliticsStatusEnum enumObj : enums) {
            if (enumObj.value.equals(value)) {
                return enumObj;
            }
        }
        return null;
    }
}
