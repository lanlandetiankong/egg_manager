package com.egg.manager.persistence.em.user.pojo.vo;

import com.egg.manager.persistence.commons.base.beans.file.FileResBean;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineDepartmentVo;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineTenantVo;
import com.egg.manager.persistence.em.user.domain.enums.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.em.user.domain.enums.UserSexEnum;
import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmUserAccountVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = -7587749196398332898L;
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
     * 头像地址
     */
    private String avatarUrl;
    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 性别
     * @see UserSexEnum
     */
    private Short sex;
    /**
     * 地址
     */
    private String address;
    /**
     * 用户类型
     * @see UserAccountBaseTypeEnum
     */
    private Integer userType;

    /**
     * 用户类型_名称
     */
    private String userTypeStr;
    /**
     * 上传图片_bean
     */
    private FileResBean uploadImgBean;
    /**
     * 是否已锁
     */
    private Short locked;
    /**
     * 所属租户id
     */
    private String belongTenantId;
    /**
     * 所属租户
     */
    private EmDefineTenantVo belongTenant;

    /**
     * 所属部门-id
     */
    private String belongDepartmentId;
    /**
     * 所属部门
     */
    private EmDefineDepartmentVo belongDepartment;


}
