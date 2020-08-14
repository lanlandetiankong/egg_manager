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
@TableName("em_role_menu")
public class RoleMenu extends Model<RoleMenu> {
    @TableId
    private String fid;


    @TableField(value = "define_role_id")
    private String defineRoleId;
    @TableField(value = "define_menu_id")
    private String defineMenuId;
    private Integer type;

    private String remark;
    private Short state;
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
     * @param defineMenuId
     * @param loginUser    当前登录用户
     * @return
     */
    public static RoleMenu generateSimpleInsertEntity(String defineRoleId, String defineMenuId, UserAccount loginUser) {
        return RoleMenu.generateSimpleInsertEntity(defineRoleId, defineMenuId, BaseStateEnum.ENABLED.getValue(), loginUser);
    }

    /**
     * 返回一个通用的 entity实例
     *
     * @param defineRoleId
     * @param defineMenuId
     * @param loginUser    当前登录用户
     * @return
     */
    public static RoleMenu generateSimpleInsertEntity(String defineRoleId, String defineMenuId, Short stateVal, UserAccount loginUser) {
        RoleMenu roleMenu = new RoleMenu();
        Date now = new Date();
        roleMenu.setFid(MyUUIDUtil.renderSimpleUUID());
        roleMenu.setDefineRoleId(defineRoleId);
        roleMenu.setDefineMenuId(defineMenuId);
        roleMenu.setType(1);
        roleMenu.setState(stateVal);
        roleMenu.setCreateTime(now);
        roleMenu.setUpdateTime(now);
        if (loginUser != null) {
            roleMenu.setCreateUserId(loginUser.getFid());
            roleMenu.setLastModifyerId(loginUser.getFid());
        }
        return roleMenu;
    }
}
