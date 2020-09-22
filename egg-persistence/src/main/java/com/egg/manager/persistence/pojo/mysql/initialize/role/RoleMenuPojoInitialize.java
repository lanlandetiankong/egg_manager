package com.egg.manager.persistence.pojo.mysql.initialize.role;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.db.mysql.entity.role.RolePermission;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;

import java.util.Date;

/**
 * @Description:
 * @ClassName: UserAccountPojoInitialize
 * @Author: zhoucj
 * @Date: 2020/9/22 11:57
 */
public class RoleMenuPojoInitialize {

    /**
     * 返回一个通用的 entity实例
     *
     * @param defineRoleId
     * @param defineMenuId
     * @param loginUser    当前登录用户
     * @return
     */
    public static RoleMenu generateSimpleInsertEntity(String defineRoleId, String defineMenuId, UserAccount loginUser) {
        return RoleMenuPojoInitialize.generateSimpleInsertEntity(defineRoleId, defineMenuId, BaseStateEnum.ENABLED.getValue(), loginUser);
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
