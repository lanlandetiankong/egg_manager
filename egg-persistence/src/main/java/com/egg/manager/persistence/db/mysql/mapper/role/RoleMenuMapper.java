package com.egg.manager.persistence.db.mysql.mapper.role;

import com.egg.manager.persistence.db.mysql.mapper.MyEggMapper;
import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface RoleMenuMapper extends MyEggMapper<RoleMenu> {
    /**
     * 根据角色id 修改指定权限 的可用状态
     * @param roleId
     * @param menuIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateStateByRole(@Param("roleId") String roleId, @Param("menuIdList") List<String> menuIdList, @Param("stateVal") Short stateVal
            , @Param("loginUser") UserAccount loginUser);
}
