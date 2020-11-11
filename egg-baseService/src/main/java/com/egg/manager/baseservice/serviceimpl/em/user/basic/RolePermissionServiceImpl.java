package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.user.basic.RolePermissionService;
import com.egg.manager.persistence.em.user.db.mysql.entity.RolePermissionEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.RolePermissionMapper;
import com.egg.manager.persistence.em.user.pojo.vo.RolePermissionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = RolePermissionService.class)
public class RolePermissionServiceImpl extends MyBaseMysqlServiceImpl<RolePermissionMapper, RolePermissionEntity, RolePermissionVo> implements RolePermissionService {

}
