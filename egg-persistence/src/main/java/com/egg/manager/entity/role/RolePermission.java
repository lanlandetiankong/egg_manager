package com.egg.manager.entity.role;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.entity.define.DefineRole;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("em_role_permission")
public class RolePermission extends Model<RolePermission> {
    @TableId
    private String fid ;
    @TableField("define_role_id")
    private String defineRoleId;
    @TableField("define_permission_id")
    private String definePermissionId;

    private Integer type;
    @Version
    private Integer version ;
    private Integer state ;
    @TableField("create_time")
    private Date createTime ;
    @TableField("update_time")
    private Date updateTime ;

    @TableField(value = "create_user")
    private String createUser ;
    @TableField(value = "last_modifyer")
    private String lastModifyer;
    private String remark;



    @Override
    protected Serializable pkVal() {
        return this.fid;
    }


    /**
     * 返回一个通用的 entity实例
     * @param defineRoleId
     * @param definePermissionId
     * @param createUser
     * @return
     */
    public static RolePermission generateSimpleInsertEntity(String defineRoleId,String definePermissionId,String createUser){
        RolePermission rolePermission = new RolePermission() ;
        Date now = new Date() ;
        rolePermission.setFid(MyUUIDUtil.renderSimpleUUID());
        rolePermission.setDefineRoleId(defineRoleId);
        rolePermission.setDefinePermissionId(definePermissionId);
        rolePermission.setType(1);
        rolePermission.setVersion(0);
        rolePermission.setState(BaseStateEnum.ENABLED.getValue());
        rolePermission.setCreateTime(now);
        rolePermission.setUpdateTime(now);
        rolePermission.setCreateUser(createUser);
        rolePermission.setLastModifyer(createUser);
        rolePermission.setRemark(null);
        return rolePermission ;
    }
}
