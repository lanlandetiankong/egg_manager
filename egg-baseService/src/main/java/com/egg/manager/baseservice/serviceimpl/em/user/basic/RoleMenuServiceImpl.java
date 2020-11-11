package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.user.basic.RoleMenuService;
import com.egg.manager.persistence.em.user.db.mysql.entity.RoleMenuEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.RoleMenuMapper;
import com.egg.manager.persistence.em.user.pojo.vo.RoleMenuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = RoleMenuService.class)
public class RoleMenuServiceImpl extends MyBaseMysqlServiceImpl<RoleMenuMapper, RoleMenuEntity, RoleMenuVo> implements RoleMenuService {

}
