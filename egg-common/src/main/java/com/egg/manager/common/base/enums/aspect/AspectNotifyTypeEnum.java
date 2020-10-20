package com.egg.manager.common.base.enums.aspect;

/**
 * 切面通知 的类型 枚举
 * \* note:
 * @author: zhouchengjie
 * \* Date: 2019/10/6
 * \* Time: 0:34
 * \* Description:
 * \
 */
public enum AspectNotifyTypeEnum {
    Before("Before","前置通知","在方法之前执行。"),
    After("After","后置通知","方法之后执行。"),
    AfterReturning("AfterReturning","返回通知","方法返回结果后执行。"),
    AfterThrowing("AfterThrowing","异常通知","在方法抛出异常之后。 "),
    Around("Around","环绕通知","围绕方法执行。")
    ;

    AspectNotifyTypeEnum(String value, String name, String info) {
        this.value = value;
        this.name = name;
        this.info = info;
    }

    private String value ;
    private String name ;
    private String info ;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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
