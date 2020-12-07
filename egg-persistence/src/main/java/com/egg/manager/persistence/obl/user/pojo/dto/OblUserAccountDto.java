package com.egg.manager.persistence.obl.user.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhoucj
 * @description blog用户账号表-Dto
 * @date 2020-12-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblUserAccountDto extends MyBaseMysqlDto {
    private static final long serialVersionUID = -40890946498708852L;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 账号
     */
    private String account;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像url
     */
    private String avatarUrl;
    /**
     * 密码
     */
    private String password;
    /**
     * 密码的盐
     */
    private String salt;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 性别
     */
    private Short sex;
    /**
     * 地址
     */
    private String address;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 是否锁定
     */
    private Short locked;
}