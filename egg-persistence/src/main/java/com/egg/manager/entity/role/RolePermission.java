package com.egg.manager.entity.role;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
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

    private String type;
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

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }
}
