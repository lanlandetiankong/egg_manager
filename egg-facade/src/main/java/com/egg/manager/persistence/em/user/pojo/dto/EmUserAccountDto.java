package com.egg.manager.persistence.em.user.pojo.dto;

import com.egg.manager.persistence.commons.base.beans.file.FileResBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineGroupEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineDepartmentDto;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineTenantDto;
import com.egg.manager.persistence.em.user.domain.enums.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.em.user.domain.enums.UserSexEnum;
import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmUserAccountDto extends MyBaseMysqlDto {
    private static final long serialVersionUID = 7450950462643130355L;
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
    private EmDefineTenantDto belongTenant;

    /**
     * 所属用户组_id
     */
    private String belongGroupId;
    /**
     * 所属用户组-entity
     */
    private EmDefineGroupEntity belongGroup;

    /**
     * 所属部门-id
     */
    private String belongDepartmentId;
    /**
     * 所属部门
     */
    private EmDefineDepartmentDto belongDepartment;
}
