package com.egg.manager.persistence.db.mysql.entity.user;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.egg.manager.common.base.enums.base.UserSexEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户账号-entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_user_account")
public class UserAccount extends Model<UserAccount> {
    @TableId
    private String fid;
    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 账号
     */
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
    private String phone;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 性别
     *
     * @see UserSexEnum
     */
    private Short sex;
    /**
     * 用户类型
     *
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
    private String remark;
    /**
     * 状态
     */
    private Short state;
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
     * 创建用户id
     */
    @TableField(value = "create_user_id")
    private String createUserId;
    /**
     * 最后修改用户id
     */
    @TableField(value = "last_modifyer_id")
    private String lastModifyerId;


    @Override
    protected Serializable pkVal() {
        return this.fid;
    }


    public static UserAccount dealGetVisitor() {
        return UserAccount.builder().fid("Visitor").userType(UserAccountBaseTypeEnum.Visitor.getValue()).build();
    }
}
