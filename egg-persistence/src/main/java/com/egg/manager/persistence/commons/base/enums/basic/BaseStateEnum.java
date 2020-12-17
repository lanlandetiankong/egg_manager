package com.egg.manager.persistence.commons.base.enums.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Getter
@AllArgsConstructor
public enum BaseStateEnum {
    ENABLED((short) 1, "启用", "启用"),
    DISABLED((short) 0, "禁用", "禁用"),
    LOCKED((short) -10, "锁定", "锁定");

    private Short value;
    private String name;
    private String info;

}
