package com.egg.manager.persistence.db.mysql.mapper.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.egg.manager.persistence.db.mysql.entity.role.RolePermission;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色权限 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {


    /**
     * 批量新增 角色-权限 关联
     *
     * @param permissionList
     * @return
     */
    int customBatchInsert(List<RolePermission> permissionList);

    /**
     * 根据角色id 修改指定权限 的可用状态
     *
     * @param roleId
     * @param permissionIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateStateByRole(@Param("roleId") String roleId, @Param("permissionIdList") List<String> permissionIdList, @Param("stateVal") Short stateVal
            , @Param("loginUser") UserAccount loginUser);
}
