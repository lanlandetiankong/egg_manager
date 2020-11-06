package com.egg.manager.persistence.commons.base.enums.bill;

/**
 * @author zhoucj
 * @description 枚举->订单类型
 * @date 2020/10/20
 */
public enum BillTypeEnum {
    Money_In(1, "入账"),
    Money_Out(2, "出账");

    private Integer value;
    private String name;


    BillTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }


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
}
