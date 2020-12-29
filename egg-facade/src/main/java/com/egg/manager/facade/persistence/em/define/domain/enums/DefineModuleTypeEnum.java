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
public enum DefineModuleTypeEnum {
    DefaultHas(1, "默认拥有", "默认拥有"),
    AuthorizedDistribution(100, "授权分配", "授权分配");


    private Integer value;
    private String name;
    private String label;

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
