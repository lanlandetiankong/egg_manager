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
public enum DefineJobTypeEnum {
    SimpleJob(1, "普通职务", "普通职务");

    private Integer value;
    private String name;
    private String label;



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
