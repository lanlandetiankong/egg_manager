package com.egg.manager.persistence.pojo.mysql.vo.user;

import com.egg.manager.common.base.beans.file.FileResBean;
import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineDepartmentVo;
import com.egg.manager.persistence.pojo.mysql.vo.organization.DefineTenantVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountVo extends MyBaseMysqlVo {
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
     * 用户类型
     * @see UserAccountBaseTypeEnum
     */
    private Integer userType;
    /**
     * 用户类型 数值
     */
    private Integer userTypeNum;

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
     * 所属租户-vo
     */
    private DefineTenantVo belongTenant;

    /**
     * 所属部门-id
     */
    private String belongDepartmentId ;
    /**
     * 所属部门-vo
     */
    private DefineDepartmentVo belongDepartment ;



}
