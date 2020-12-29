package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.facade.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.facade.api.services.em.user.basic.EmUserJobService;
import com.egg.manager.facade.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineJobEntity;
import com.egg.manager.facade.persistence.em.define.db.mysql.mapper.EmDefineJobMapper;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserJobEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.EmUserJobMapper;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserJobVo;
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
@Service(interfaceClass = EmUserJobService.class)
public class EmUserJobServiceImpl extends MyBaseMysqlServiceImpl<EmUserJobMapper, EmUserJobEntity, EmUserJobVo> implements EmUserJobService {

    @Autowired
    private EmDefineJobMapper emDefineJobMapper;


    @Override
    public List<EmDefineJobEntity> queryAllUserBelong(String userAccountId) {
        List<EmDefineJobEntity> allJobByUserAcccountId = emDefineJobMapper.findAllByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
        return allJobByUserAcccountId;
    }

}
