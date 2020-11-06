package com.egg.manager.persistence.em.user.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.commons.base.enums.base.UserSexEnum;
import com.egg.manager.persistence.commons.base.enums.user.UserAccountBaseTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@TableName("em_user_account")
public class UserAccount extends Model<UserAccount> {
    @TableId(type=IdType.ASSIGN_ID,value = "fid")
    private Long fid;
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
    private String password;
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
     * 用户类型
     * @see UserAccountBaseTypeEnum
     */
    @TableField("user_type")
    private Integer userType;
    /**
     * 用户类型 数值
     */
    @TableField("user_type_num")
    private Integer userTypeNum;
    /**
     * 是否锁定账号
     */
    @TableField("locked")
    private short locked;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 状态
     */
    @TableField(value = "state", fill = FieldFill.INSERT)
    private Short state;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 创建用户id
     */
    @TableField(value = "create_user_id")
    private Long createUserId;
    /**
     * 最后修改用户id
     */
    @TableField(value = "last_modifyer_id")
    private Long lastModifyerId;
    /**
     * 版本号
     */
    @Version
    @TableField(value = "version")
    private Integer version;
    /**
     * 是否已经删除，0:否 1:是
     */
    @TableLogic
    @TableField(value = "is_deleted")
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
