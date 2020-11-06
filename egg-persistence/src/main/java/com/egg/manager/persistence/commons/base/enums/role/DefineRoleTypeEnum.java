package com.egg.manager.persistence.commons.base.enums.role;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public enum DefineRoleTypeEnum {
    SimpleRole(1, "普通角色", "普通角色", true),
    ManagerRole(100, "管理角色", "管理角色", true),
    ;

    DefineRoleTypeEnum(Integer value, String name, String label, boolean isNeedFilter) {
        this.value = value;
        this.name = name;
        this.label = label;
        this.isNeedFilter = isNeedFilter;
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


    public static DefineRoleTypeEnum doGetEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        DefineRoleTypeEnum[] enums = DefineRoleTypeEnum.values();
        for (DefineRoleTypeEnum enumObj : enums) {
            if (enumObj.value.equals(value)) {
                return enumObj;
            }
        }
        return null;
    }


}
