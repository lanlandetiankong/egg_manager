package com.egg.manager.persistence.db.mysql.entity.role;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色&菜单 关联-entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_role_menu")
public class RoleMenu extends Model<RoleMenu> {
    @TableId(value = "fid")
    private String fid;

    /**
     * 角色id
     */
    @TableField(value = "define_role_id")
    private String defineRoleId;
    /**
     * 菜单id
     */
    @TableField(value = "define_menu_id")
    private String defineMenuId;
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


}
