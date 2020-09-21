package com.egg.manager.persistence.db.mysql.entity.role;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色&权限 关联-entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_role_permission")
public class RolePermission extends Model<RolePermission> {
    @TableId
    private String fid;
    /**
     * 角色id
     */
    @TableField("define_role_id")
    private String defineRoleId;
    /**
     * 权限id
     */
    @TableField("define_permission_id")
    private String definePermissionId;
    /**
     * 类型
     */
    private Integer type;


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


    /**
     * 返回一个通用的 entity实例
     *
     * @param defineRoleId
     * @param definePermissionId
     * @param loginUser
     * @return
     */
    public static RolePermission generateSimpleInsertEntity(String defineRoleId, String definePermissionId, UserAccount loginUser) {
        RolePermission rolePermission = new RolePermission();
        Date now = new Date();
        rolePermission.setFid(MyUUIDUtil.renderSimpleUUID());
        rolePermission.setDefineRoleId(defineRoleId);
        rolePermission.setDefinePermissionId(definePermissionId);
        rolePermission.setType(1);
        rolePermission.setState(BaseStateEnum.ENABLED.getValue());
        rolePermission.setCreateTime(now);
        rolePermission.setUpdateTime(now);
        if (loginUser != null) {
            rolePermission.setCreateUserId(loginUser.getFid());
            rolePermission.setLastModifyerId(loginUser.getFid());
        }
        rolePermission.setRemark(null);
        return rolePermission;
    }
}
