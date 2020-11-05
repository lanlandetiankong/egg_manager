package com.egg.manager.persistence.commons.base.enums.permission;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public enum DefinePermissionTypeEnum {
    PageButton(0, "页面操作按钮", "页面操作按钮", true),
    TableActionBtn(100, "表格内部按钮", "表格内部按钮", true),
    ;

    DefinePermissionTypeEnum(Integer value, String name, String label, boolean isNeedFilter) {
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


    public static DefinePermissionTypeEnum doGetEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        DefinePermissionTypeEnum[] enums = DefinePermissionTypeEnum.values();
        for (DefinePermissionTypeEnum enumObj : enums) {
            if (enumObj.value.equals(value)) {
                return enumObj;
            }
        }
        return null;
    }


}
