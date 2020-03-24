package com.egg.manager.service.serviceimpl.role;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.entity.define.DefinePermission;
import com.egg.manager.persistence.entity.define.DefineRole;
import com.egg.manager.persistence.entity.role.RoleMenu;
import com.egg.manager.persistence.entity.role.RolePermission;
import com.egg.manager.persistence.mapper.role.RoleMenuMapper;
import com.egg.manager.persistence.mapper.role.RolePermissionMapper;
import com.egg.manager.service.service.role.RoleMenuService;
import com.egg.manager.service.service.role.RolePermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper,RoleMenu>  implements RoleMenuService {

}
