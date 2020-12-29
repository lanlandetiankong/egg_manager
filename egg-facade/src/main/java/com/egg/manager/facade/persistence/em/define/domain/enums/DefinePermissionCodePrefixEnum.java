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
public enum DefinePermissionCodePrefixEnum {
    PermCtrl("PermCtrl:", "权限控制前缀", "PermCtrl:", true),
    ;

    private String value;
    private String name;
    private String label;
    private boolean defaultCheck;


}
