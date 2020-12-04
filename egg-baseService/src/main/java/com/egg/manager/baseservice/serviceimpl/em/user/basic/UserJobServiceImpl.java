package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.user.basic.UserJobService;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineJobEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineJobMapper;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserJobEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserJobMapper;
import com.egg.manager.persistence.em.user.pojo.vo.UserJobVo;
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
@Service(interfaceClass = UserJobService.class)
public class UserJobServiceImpl extends MyBaseMysqlServiceImpl<UserJobMapper, UserJobEntity, UserJobVo> implements UserJobService {

    @Autowired
    private DefineJobMapper defineJobMapper;


    @Override
    public List<DefineJobEntity> queryAllUserBelong(String userAccountId) {
        List<DefineJobEntity> allJobByUserAcccountId = defineJobMapper.findAllByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
        return allJobByUserAcccountId;
    }

}
