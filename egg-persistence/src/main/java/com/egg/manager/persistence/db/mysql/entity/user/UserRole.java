package com.egg.manager.persistence.db.mysql.entity.user;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户&角色 关联-entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_user_role")
public class UserRole extends Model<UserRole> {
    @TableId
    private String fid;
    /**
     * 账号id
     */
    @TableField(value = "user_account_id")
    private String userAccountId;
    /**
     * 角色id
     */
    @TableField(value = "define_role_id")
    private String defineRoleId;
    /**
     * 类型
     */
    @TableField("type")
    private Integer type;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 状态
     */
    @TableField("state")
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


    /**
     * 返回一个通用的 entity实例
     *
     * @param userAccountId
     * @param defineRoleId
     * @param loginUser     当前登录用户
     * @return
     */
    public static UserRole generateSimpleInsertEntity(String userAccountId, String defineRoleId, UserAccount loginUser) {
        UserRole userRole = new UserRole();
        Date now = new Date();
        userRole.setFid(MyUUIDUtil.renderSimpleUUID());
        userRole.setUserAccountId(userAccountId);
        userRole.setDefineRoleId(defineRoleId);
        userRole.setType(1);
        userRole.setState(BaseStateEnum.ENABLED.getValue());
        userRole.setCreateTime(now);
        userRole.setUpdateTime(now);
        if (loginUser != null) {
            userRole.setCreateUserId(loginUser.getFid());
            userRole.setLastModifyerId(loginUser.getFid());
        }
        return userRole;
    }
}
