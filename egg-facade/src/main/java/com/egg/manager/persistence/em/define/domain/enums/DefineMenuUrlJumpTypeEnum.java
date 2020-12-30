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
public enum DefineMenuUrlJumpTypeEnum {
    RouterUrlJump(1, "Router地址跳转", "Router地址跳转"),
    OutUrlJump(2, "在当前页面打开外部链接", "在当前页面打开外部链接"),
    OutUrlBlankJump(3, "在新页面打开外部链接", "在新页面打开外部链接"),
    ;

    private Integer value;
    private String name;
    private String label;


    public static DefineMenuUrlJumpTypeEnum doGetEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        DefineMenuUrlJumpTypeEnum[] enums = DefineMenuUrlJumpTypeEnum.values();
        for (DefineMenuUrlJumpTypeEnum enumObj : enums) {
            if (enumObj.value.equals(value)) {
                return enumObj;
            }
        }
        return null;
    }


}
