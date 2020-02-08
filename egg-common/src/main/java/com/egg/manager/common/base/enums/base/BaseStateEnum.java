package com.egg.manager.common.base.enums.base;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/10/6
 * \* Time: 0:34
 * \* Description:
 * \
 */
public enum  BaseStateEnum {
    ENABLED(1,"启用","启用"),
    DISABLED(0,"禁用","禁用"),
    DELETE(-1,"删除","删除")
    ;

    BaseStateEnum(Integer value, String name, String info) {
        this.value = value;
        this.name = name;
        this.info = info;
    }

    private Integer value ;
    private String name ;
    private String info ;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
