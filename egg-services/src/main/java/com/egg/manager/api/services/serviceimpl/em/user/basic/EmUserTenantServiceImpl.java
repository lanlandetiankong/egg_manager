package com.egg.manager.api.services.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.user.basic.EmUserTenantService;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserTenantEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmUserTenantMapper;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserTenantVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = EmUserTenantService.class)
public class EmUserTenantServiceImpl extends MyBaseMysqlServiceImpl<EmUserTenantMapper, EmUserTenantEntity, EmUserTenantVo> implements EmUserTenantService {


}
