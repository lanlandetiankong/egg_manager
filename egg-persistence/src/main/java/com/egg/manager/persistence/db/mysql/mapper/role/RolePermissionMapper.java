package com.egg.manager.persistence.db.mysql.mapper.role;

import com.egg.manager.persistence.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.db.mysql.mapper.MyEggMapper;
import com.egg.manager.persistence.db.mysql.entity.role.RolePermission;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface RolePermissionMapper extends MyEggMapper<RolePermission> {


    /**
     * 批量新增 角色-权限 关联
     * @param permissionList
     * @return
     */
    int customBatchInsert(List<RolePermission> permissionList);

    /**
     * 根据角色id 修改指定权限 的可用状态
     * @param roleId
     * @param permissionIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateStateByRole(@Param("roleId") Long roleId, @Param("permissionIdList") List<Long> permissionIdList, @Param("stateVal") Short stateVal
            , @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccount loginUser);
}
