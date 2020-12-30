package com.egg.manager.persistence.em.define.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Getter
@AllArgsConstructor
public enum DefineRoleTypeEnum {
    SimpleRole(1, "普通角色", "普通角色", true),
    ManagerRole(100, "管理角色", "管理角色", true),
    ;

    private Integer value;
    private String name;
    private String label;
    private boolean isNeedFilter;


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
