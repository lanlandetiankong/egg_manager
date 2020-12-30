package com.egg.manager.persistence.em.user.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.em.user.domain.enums.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.em.user.domain.enums.UserSexEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoucj
 * @description 用户账号
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("em_user_account")
public class EmUserAccountEntity extends Model<EmUserAccountEntity> {
    private static final long serialVersionUID = -7361446552841255287L;
    @TableId(type = IdType.ASSIGN_ID, value = FieldConst.COL_FID)
    private String fid;
    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 账号
     */
    @TableField("account")
    private String account;
    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 头像地址
     */
    @TableField("avatar_url")
    private String avatarUrl;
    /**
     * 密码
     */
    @JsonIgnore
    @TableField("password")
    private String password;
    /**
     * 密码的盐
     * 真实密码->md5(明文password+salt)
     * 验证密码->先取得salt,再结合 md5(明文password+salt)去验证是否与数据库的password一致
     */
    @JsonIgnore
    @TableField("salt")
    private String salt;

    /**
     * 手机号码
     */
    @TableField("phone")
    private String phone;
    /**
     * 邮箱地址
     */
    @TableField("email")
    private String email;
    /**
     * 性别
     * @see UserSexEnum
     */
    @TableField("sex")
    private Short sex;

    /**
     * 地址
     */
    @TableField("address")
    private String address;
    /**
     * 用户类型
     * @see UserAccountBaseTypeEnum
     */
    @TableField("user_type")
    private Integer userType;
    /**
     * 是否锁定账号
     */
    @TableField("locked")
    private short locked;


    /**
     * 备注
     */
    @TableField(FieldConst.COL_REMARK)
    private String remark;
    /**
     * 状态
     */
    @TableField(value = FieldConst.COL_STATE, fill = FieldFill.INSERT)
    private Short state;
    /**
     * 创建时间
     */
    @TableField(value = FieldConst.COL_CREATE_TIME, fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = FieldConst.COL_UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 创建用户id
     */
    @TableField(value = FieldConst.COL_CREATE_USER_ID)
    private String createUserId;
    /**
     * 最后修改用户id
     */
    @TableField(value = FieldConst.COL_LAST_MODIFYER_ID)
    private String lastModifyerId;
    /**
     * 版本号
     */
    @Version
    @TableField(value = FieldConst.COL_VERSION)
    private Integer version;
    /**
     * 是否已经删除，0:否 1:是
     */
    @TableLogic
    @TableField(value = FieldConst.COL_IS_DELETED)
    private short isDeleted;
    /**
     * 数据删除时间
     */
    @TableField(value = "deleted_time")
    private Date deletedTime;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }


}
