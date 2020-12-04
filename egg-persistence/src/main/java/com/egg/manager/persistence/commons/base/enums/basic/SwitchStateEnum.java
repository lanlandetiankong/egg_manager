package com.egg.manager.persistence.commons.base.enums.basic;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public enum SwitchStateEnum {
    Open((short) 1, "启用", "启用"),
    Close((short) 0, "禁用", "禁用"),
    ;

    SwitchStateEnum(Short value, String name, String info) {
        this.value = value;
        this.name = name;
        this.info = info;
    }

    private Short value;
    private String name;
    private String info;

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


    /**
     * 判断是否属于枚举的有效值
     * @param value
     * @return
     */
    public static boolean checkIsValidVal(Integer value) {
        boolean flag = false;
        if (value != null) {
            SwitchStateEnum[] enums = SwitchStateEnum.values();
            for (SwitchStateEnum enumObj : enums) {
                if (value.equals(enumObj.getValue())) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 判断是否属于枚举的有效值
     * @param value
     * @return
     */
    public static String dealGetNameByVal(Short value) {
        String name = "";
        if (value != null) {
            SwitchStateEnum[] enums = SwitchStateEnum.values();
            for (SwitchStateEnum enumObj : enums) {
                if (value.equals(enumObj.getValue())) {
                    name = enumObj.getName();
                    break;
                }
            }
        }
        return name;
    }

}
