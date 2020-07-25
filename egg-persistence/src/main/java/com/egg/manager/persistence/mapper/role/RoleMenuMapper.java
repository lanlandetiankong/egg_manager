package com.egg.manager.persistence.mapper.role;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.persistence.dto.user.UserRoleDto;
import com.egg.manager.persistence.entity.role.RoleMenu;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.entity.user.UserRole;
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
     * @param roleId
     * @param menuIdList
     * @param stateVal
     * @return
     */
    int batchUpdateStateByRole(@Param("roleId") String roleId,@Param("menuIdList")List<String> menuIdList,@Param("stateVal")Short stateVal
            ,@Param("loginUser")UserAccount loginUser);
}
