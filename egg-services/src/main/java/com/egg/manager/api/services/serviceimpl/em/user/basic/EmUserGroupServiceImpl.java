package com.egg.manager.api.services.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.facade.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.facade.api.services.em.user.basic.EmUserGroupService;
import com.egg.manager.facade.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineGroupEntity;
import com.egg.manager.facade.persistence.em.define.db.mysql.mapper.EmDefineGroupMapper;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserGroupEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.EmUserGroupMapper;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = EmUserGroupService.class)
public class EmUserGroupServiceImpl extends MyBaseMysqlServiceImpl<EmUserGroupMapper, EmUserGroupEntity, EmUserGroupVo> implements EmUserGroupService {
    @Autowired
    private EmDefineGroupMapper emDefineGroupMapper;


    @Override
    public List<EmDefineGroupEntity> queryAllUserBelong(String userAccountId) {
        List<EmDefineGroupEntity> allJobByUserAcccountId = emDefineGroupMapper.findAllByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
        return allJobByUserAcccountId;
    }
}
