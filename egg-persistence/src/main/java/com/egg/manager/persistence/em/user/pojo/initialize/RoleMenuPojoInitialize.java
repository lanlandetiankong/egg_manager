package com.egg.manager.persistence.em.user.pojo.initialize;

import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.em.user.db.mysql.entity.RoleMenu;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;

import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public class RoleMenuPojoInitialize {

    /**
     * 返回一个通用的 entity实例
     * @param defineRoleId
     * @param defineMenuId
     * @param loginUser    当前登录用户
     * @return
     */
    public static RoleMenu generateSimpleInsertEntity(Long defineRoleId, Long defineMenuId, UserAccount loginUser) {
        return RoleMenuPojoInitialize.generateSimpleInsertEntity(defineRoleId, defineMenuId, BaseStateEnum.ENABLED.getValue(), loginUser);
    }

    /**
     * 返回一个通用的 entity实例
     * @param defineRoleId
     * @param defineMenuId
     * @param loginUser    当前登录用户
     * @return
     */
    public static RoleMenu generateSimpleInsertEntity(Long defineRoleId, Long defineMenuId, Short stateVal, UserAccount loginUser) {
        RoleMenu roleMenu = new RoleMenu();
        Date now = new Date();
        //roleMenu.setFid(MyUUIDUtil.renderSimpleUuid());
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
