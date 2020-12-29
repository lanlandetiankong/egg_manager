package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.facade.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.facade.api.services.em.define.basic.EmDefineGroupService;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineGroupEntity;
import com.egg.manager.facade.persistence.em.define.db.mysql.mapper.EmDefineGroupMapper;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = EmDefineGroupService.class)
public class EmDefineGroupServiceImpl extends MyBaseMysqlServiceImpl<EmDefineGroupMapper, EmDefineGroupEntity, EmDefineGroupVo> implements EmDefineGroupService {


}
