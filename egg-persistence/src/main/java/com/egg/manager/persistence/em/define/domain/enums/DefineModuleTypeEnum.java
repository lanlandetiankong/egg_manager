package com.egg.manager.persistence.em.define.domain.enums;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public enum DefineModuleTypeEnum {
    DefaultHas(1, "默认拥有", "默认拥有"),
    AuthorizedDistribution(100, "授权分配", "授权分配");

    DefineModuleTypeEnum(Integer value, String name, String label) {
        this.value = value;
        this.name = name;
        this.label = label;
    }

    private Integer value;
    private String name;
    private boolean isNeedFilter;
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

    public boolean getIsNeedFilter() {
        return isNeedFilter;
    }

    public void setLabel(boolean isNeedFilter) {
        this.isNeedFilter = isNeedFilter;
    }


    public static DefineModuleTypeEnum doGetEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        DefineModuleTypeEnum[] enums = DefineModuleTypeEnum.values();
        for (DefineModuleTypeEnum enumObj : enums) {
            if (enumObj.value.equals(value)) {
                return enumObj;
            }
        }
        return null;
    }


}
