package com.egg.manager.api.services.basic.role;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.db.mysql.mapper.role.RoleMenuMapper;
import com.egg.manager.persistence.pojo.mysql.vo.role.RoleMenuVo;

/**
 *  [角色-菜单] Service
 * \* note:
 * \* User: zhouchengjie
 * \* Description:
 */
public interface RoleMenuService extends IService<RoleMenu>,MyBaseMysqlService<RoleMenuMapper,RoleMenu,RoleMenuVo> {

}
