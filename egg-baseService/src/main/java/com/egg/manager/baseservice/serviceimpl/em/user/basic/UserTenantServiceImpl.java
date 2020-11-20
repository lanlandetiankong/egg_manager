package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.user.basic.UserTenantService;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserTenantEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserTenantMapper;
import com.egg.manager.persistence.em.user.pojo.vo.UserTenantVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = UserTenantService.class)
public class UserTenantServiceImpl extends MyBaseMysqlServiceImpl<UserTenantMapper, UserTenantEntity, UserTenantVo> implements UserTenantService {


}
