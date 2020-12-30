package com.egg.manager.api.services.em.user.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmRoleMenuEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmRoleMenuMapper;
import com.egg.manager.persistence.em.user.pojo.vo.EmRoleMenuVo;

import java.util.List;

/**
 * @author zhoucj
 * @description [角色-菜单] Service
 * @date 2020/10/20
 */
public interface EmRoleMenuService extends MyBaseMysqlService<EmRoleMenuEntity, EmRoleMenuMapper, EmRoleMenuVo> {
    /**
     * 根据角色id 修改指定权限 的可用状态
     * @param roleId
     * @param menuIdList
     * @param stateVal
     * @param loginUser
     * @return
     */
    int batchUpdateStateByRole(String roleId, List<String> menuIdList, Short stateVal
            , EmUserAccountEntity loginUser);
}
