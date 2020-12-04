package com.egg.manager.persistence.em.define.domain.enums;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public enum DefineJobTypeEnum {
    SimpleJob(1, "普通职务", "普通职务");

    DefineJobTypeEnum(Integer value, String name, String label) {
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


    public static DefineJobTypeEnum doGetEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        DefineJobTypeEnum[] enums = DefineJobTypeEnum.values();
        for (DefineJobTypeEnum enumObj : enums) {
            if (enumObj.value.equals(value)) {
                return enumObj;
            }
        }
        return null;
    }


}
