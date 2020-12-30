package com.egg.manager.persistence.commons.base.enums.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Deprecated
@Getter
@AllArgsConstructor
public enum PublicResultEnum {
    //常见错误
    Failed("Failed", "系统错误", "系统错误"),
    Success("Success", "操作成功", "操作成功"),
    Error("Error", "操作失败", "操作失败"),
    ErrorOfDb("ErrorOfDb", "数据库操作失败", "数据库操作失败"),
    DataError("DataError", "数据操作错误", "数据操作错误"),
    ErrorOfParam("ErrorOfParam", "参数错误", "参数错误"),

    TemplacteException("ExceptionOfTemplate", "thymeleaf模板错误！", "thymeleaf模板错误！"),
    //[用户相关]错误
    InvalidUserOrPassword("InvalidUserOrPassword", "用户名或密码错误", "用户名或密码错误"),
    InvalidRePassword("InvalidRePassword", "两次输入密码不一致", "两次输入密码不一致"),
    InvalidUserAccount("InvalidUserAccount", "用户不存在", "用户不存在"),
    InvalidUserAccountExist("InvalidUserAccountExist", "用户已存在", "用户已存在"),
    InvalidRole("InvalidRole", "角色不存在", "角色不存在"),
    UnauthorizedLoginUser("Unauthorized", "获取登录用户信息失败", "获取登录用户信息失败"),

    //[缺少权限]
    NoPermissionOfUser("NoPermissionOfUser", "操作失败！当前用户无该接口权限", "操作失败！当前用户无该接口权限"),

    //[被引用]
    BeUsingRole("BeUsingRole", "角色使用中,不可删除", "角色使用中,不可删除"),

    //[校验码]错误
    VerifyParamError("VerifyParamError", "校验码错误", "校验码错误"),
    VerifyParamDisabled("VerifyParamDisabled", "校验码过期", "校验码过期"),

    //[更新]错误
    UpdateErrorOfRoleInfo("UpdateErrorOfRoleInfo", "更新角色信息失败", "更新角色信息失败"),
    UpdateErrorOfRoot("UpdateErrorOfRoleInfo", "不能修改管理员信息", "不能修改管理员信息"),

    //删除[已启用]项 限制
    SwitchOpenChangeLimit("SwitchOpenChangeLimit", "已启用的项无法进行删除！", "已启用的项无法进行删除！"),

    //[数据格式]错误
    FormatErrorOfMobile("FormatErrorOfMobile", "手机号格式错误", "手机号格式错误"),
    FormatErrorOfEmail("FormatErrorOfEmail", "邮箱格式错误", "邮箱格式错误");


    private String value;
    private String name;
    private String label;

}

