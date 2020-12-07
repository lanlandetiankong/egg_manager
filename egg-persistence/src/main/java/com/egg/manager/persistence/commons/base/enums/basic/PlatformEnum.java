package com.egg.manager.persistence.commons.base.enums.basic;

/**
 * @Description: 平台类型 枚举
 * @ClassName: PlatformEnum
 * @Author: zhoucj
 * @Date: 2020/12/7 9:21
 */
public enum PlatformEnum {
    EggManager("egg_manager"),
    OolongBlog("oolong_blog");

    PlatformEnum(String value) {
        this.value = value;
    }

    private String value;
    private String name;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
