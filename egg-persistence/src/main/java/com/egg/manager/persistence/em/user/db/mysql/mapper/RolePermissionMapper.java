package com.egg.manager.persistence.em.user.db.mysql.mapper;

import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.em.user.db.mysql.entity.RolePermissionEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface RolePermissionMapper extends MyEggMapper<RolePermissionEntity> {

    /**
     * 根据角色id 修改指定权限 的可用状态
     * @param roleId
     * @param permissionIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateStateByRole(@Param("roleId") String roleId, @Param("permissionIdList") List<String> permissionIdList, @Param("stateVal") Short stateVal
            , @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccountEntity loginUser);
}
