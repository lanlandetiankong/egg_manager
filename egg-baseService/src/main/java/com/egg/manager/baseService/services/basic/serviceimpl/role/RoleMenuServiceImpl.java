package com.egg.manager.baseService.services.basic.serviceimpl.role;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.services.basic.role.RoleMenuService;
import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.db.mysql.mapper.role.RoleMenuMapper;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service(interfaceClass = RoleMenuService.class)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper,RoleMenu>  implements RoleMenuService {

}
