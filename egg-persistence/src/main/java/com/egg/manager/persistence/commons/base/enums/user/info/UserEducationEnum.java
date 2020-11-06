package com.egg.manager.persistence.commons.base.enums.user.info;


/**
 * @author zhoucj
 * @description 学历 类型枚举类
 * @date 2020/10/20
 */
public enum UserEducationEnum {
    Others(0, "其他", "其他"),
    PrimarySchool(100, "小学", "小学"),
    JuniorHighSchool(100, "初中", "初中"),
    SeniorHighSchool(100, "高中", "高中"),
    JuniorCollege(100, "专科", "专科"),
    RegularCollege(0, "本科", "本科"),
    Doctor(100, "博士", "博士"),
    Master(100, "硕士", "硕士"),
    ;

    UserEducationEnum(Integer value, String name, String label) {
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


    public static UserEducationEnum doGetEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        UserEducationEnum[] enums = UserEducationEnum.values();
        for (UserEducationEnum enumObj : enums) {
            if (enumObj.value.equals(value)) {
                return enumObj;
            }
        }
        return null;
    }
}
