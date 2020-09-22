package com.egg.manager.persistence.db.mysql.mapper.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [角色-菜单] Mapper 接口
 * </p>
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    /**
     * 根据角色id 修改指定权限 的可用状态
     *
     * @param roleId
     * @param menuIdList
     * @param stateVal
     * @return
     */
    int batchUpdateStateByRole(@Param("roleId") String roleId, @Param("menuIdList") List<String> menuIdList, @Param("stateVal") Short stateVal
            , @Param("loginUser") UserAccount loginUser);
}
