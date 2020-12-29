package com.egg.manager.facade.persistence.em.user.db.mysql.mapper;

import com.egg.manager.facade.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmRolePermissionEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmRolePermissionMapper extends MyEggMapper<EmRolePermissionEntity> {

    /**
     * 根据角色id 修改指定权限 的可用状态
     * @param roleId
     * @param permissionIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateStateByRole(@Param("roleId") String roleId, @Param("permissionIdList") List<String> permissionIdList, @Param("stateVal") Short stateVal
            , @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) EmUserAccountEntity loginUser);
}
