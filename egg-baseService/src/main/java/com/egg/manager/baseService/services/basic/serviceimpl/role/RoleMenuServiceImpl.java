package com.egg.manager.baseService.services.basic.serviceimpl.role;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.basic.role.RoleMenuService;
import com.egg.manager.baseService.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.db.mysql.mapper.role.RoleMenuMapper;
import com.egg.manager.persistence.pojo.mysql.vo.role.RoleMenuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = RoleMenuService.class)
public class RoleMenuServiceImpl extends MyBaseMysqlServiceImpl<RoleMenuMapper, RoleMenu, RoleMenuVo> implements RoleMenuService {

}
