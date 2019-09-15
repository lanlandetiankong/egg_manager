package com.egg.manager.common.base.enums;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 15:34
 * \* Description:
 * \
 */
public enum PublicResultEnum {
    //常见错误
    Failed("Failed", "系统错误", "系统错误"),
    Success("Success", "操作成功", "操作成功"),
    Error("Error", "操作失败", "操作失败"),
    ErrorOfDb("ErrorOfDb", "数据库操作失败", "数据库操作失败"),
    DataError("DataError", "数据操作错误", "数据操作错误"),
    ErrorOfParam("ErrorOfParam", "参数错误", "参数错误"),

    //[用户相关]错误
    InvalidUserOrPassword("InvalidUserOrPassword", "用户名或密码错误", "用户名或密码错误"),
    InvalidRePassword("InvalidRePassword", "两次输入密码不一致", "两次输入密码不一致"),
    InvalidUserAccount("InvalidUserAccount", "用户不存在", "用户不存在"),
    InvalidUserAccountExist("InvalidUserAccountExist", "用户已存在", "用户已存在"),
    InvalidRole("InvalidRole", "角色不存在", "角色不存在"),
    Unauthorized("Unauthorized", "获取登录用户信息失败", "获取登录用户信息失败"),

    //[缺少权限]
    NoPermissionOfUser("NoPermissionOfUser", "当前用户无该接口权限", "当前用户无该接口权限"),

    //[被引用]
    BeUsingRole("BeUsingRole", "角色使用中,不可删除", "角色使用中,不可删除"),

    //[校验码]错误
    VerifyParamError("VerifyParamError", "校验码错误", "校验码错误"),
    VerifyParamDisabled("VerifyParamDisabled", "校验码过期", "校验码过期"),

    //[更新]错误
    UpdateErrorOfRoleInfo("UpdateErrorOfRoleInfo", "更新角色信息失败", "更新角色信息失败"),
    UpdateErrorOfRoot("UpdateErrorOfRoleInfo", "不能修改管理员信息", "不能修改管理员信息"),

    //[数据格式]错误
    FormatErrorOfMobile("FormatErrorOfMobile", "手机号格式错误", "手机号格式错误"),
    FormatErrorOfEmail("FormatErrorOfEmail", "邮箱格式错误", "邮箱格式错误")

    ;

    PublicResultEnum(String value, String name, String label) {
        this.value = value;
        this.name = name;
        this.label = label;
    }

    private String value;
    private String name;
    private String label;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

