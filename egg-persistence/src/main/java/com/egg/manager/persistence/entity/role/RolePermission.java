package com.egg.manager.persistence.entity.role;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.entity.user.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_role_permission")
public class RolePermission extends Model<RolePermission> {
    @TableId
    private String fid;
    @TableField("define_role_id")
    private String defineRoleId;
    @TableField("define_permission_id")
    private String definePermissionId;
    private Integer type;

    private Short state;
    private String remark;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField(value = "create_user_id")
    private String createUserId;
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
