package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.user.db.mysql.entity.RoleMenu;
import com.egg.manager.persistence.em.user.db.mysql.mapper.RoleMenuMapper;
import com.egg.manager.persistence.em.user.pojo.vo.RoleMenuVo;

/**
 * @author zhoucj
 * @description: [角色-菜单] Service
 * @date 2020/10/20
 */
public interface RoleMenuService extends IService<RoleMenu>, MyBaseMysqlService<RoleMenu, RoleMenuMapper, RoleMenuVo> {

}
