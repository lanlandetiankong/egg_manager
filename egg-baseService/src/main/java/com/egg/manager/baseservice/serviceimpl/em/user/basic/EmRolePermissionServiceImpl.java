package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.facade.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.facade.api.services.em.user.basic.EmRolePermissionService;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmRolePermissionEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.EmRolePermissionMapper;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmRolePermissionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = EmRolePermissionService.class)
public class EmRolePermissionServiceImpl extends MyBaseMysqlServiceImpl<EmRolePermissionMapper, EmRolePermissionEntity, EmRolePermissionVo> implements EmRolePermissionService {

}
