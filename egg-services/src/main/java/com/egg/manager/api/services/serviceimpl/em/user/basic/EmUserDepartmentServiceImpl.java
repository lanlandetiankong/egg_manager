package com.egg.manager.api.services.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.user.basic.EmUserDepartmentService;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserDepartmentEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmUserDepartmentMapper;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserDepartmentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = EmUserDepartmentService.class)
public class EmUserDepartmentServiceImpl extends MyBaseMysqlServiceImpl<EmUserDepartmentMapper, EmUserDepartmentEntity, EmUserDepartmentVo> implements EmUserDepartmentService {


}
