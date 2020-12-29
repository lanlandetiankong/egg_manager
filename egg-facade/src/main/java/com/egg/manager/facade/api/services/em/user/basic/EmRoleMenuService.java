package com.egg.manager.facade.api.services.em.user.basic;

import com.egg.manager.facade.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmRoleMenuEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.EmRoleMenuMapper;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmRoleMenuVo;

/**
 * @author zhoucj
 * @description [角色-菜单] Service
 * @date 2020/10/20
 */
public interface EmRoleMenuService extends MyBaseMysqlService<EmRoleMenuEntity, EmRoleMenuMapper, EmRoleMenuVo> {

}
