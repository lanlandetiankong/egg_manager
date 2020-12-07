package com.egg.manager.persistence.obl.user.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoucj
 * @description blog用户账号表-Entity
 * @date 2020-12-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("obl_user_account")
public class OblUserAccountEntity extends Model<OblUserAccountEntity> {
    private static final long serialVersionUID = 863845299152271437L;

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
     * 头像url
     */
    @TableField("avatar_url")
    private String avatarUrl;
    /**
     * 密码
     */
    @TableField("password")
    private String password;
    /**
     * 密码的盐
     */
    @TableField("salt")
    private String salt;
    /**
     * 手机号
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
     */
    @TableField("user_type")
    private Integer userType;
    /**
     * 状态值
     */
    @TableField("state")
    private Short state;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 最后修改人id
     */
    @TableField("last_modifyer_id")
    private String lastModifyerId;
    /**
     * 创建人id
     */
    @TableField("create_user_id")
    private String createUserId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 是否锁定
     */
    @TableField("locked")
    private Short locked;
    /**
     * 版本号
     */
    @TableField("version")
    private Integer version;
    /**
     * 是否已删除?0:否1:是
     */
    @TableField("is_deleted")
    private Short isDeleted;
    /**
     * 数据删除时间
     */
    @TableField("deleted_time")
    private Date deletedTime;


    @Override
    protected Serializable pkVal() {
        return this.fid;
    }
}