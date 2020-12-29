package com.egg.manager.facade.api.services.em.user.basic;

import com.egg.manager.facade.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmRolePermissionEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.EmRolePermissionMapper;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmRolePermissionVo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmRolePermissionService extends MyBaseMysqlService<EmRolePermissionEntity, EmRolePermissionMapper, EmRolePermissionVo> {

}
