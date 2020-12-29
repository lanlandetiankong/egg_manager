package com.egg.manager.facade.persistence.em.define.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Getter
@AllArgsConstructor
public enum DefinePermissionTypeEnum {
    PageButton(0, "页面操作按钮", "页面操作按钮", true),
    TableActionBtn(100, "表格内部按钮", "表格内部按钮", true),
    ;

    private Integer value;
    private String name;
    private String label;
    private boolean isNeedFilter;




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
