package com.egg.manager.api.services.basic.role;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.db.mysql.mapper.role.RoleMenuMapper;
import com.egg.manager.persistence.pojo.mysql.vo.role.RoleMenuVo;

/**
 * @author zhoucj
 * @description: [角色-菜单] Service
 * @date 2020/10/20
 */
public interface RoleMenuService extends IService<RoleMenu>, MyBaseMysqlService<RoleMenu, RoleMenuMapper, RoleMenuVo> {

}
