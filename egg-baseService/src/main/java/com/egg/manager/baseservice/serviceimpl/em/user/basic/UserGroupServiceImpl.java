package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.user.basic.UserGroupService;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineGroupEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineGroupMapper;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserGroupEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserGroupMapper;
import com.egg.manager.persistence.em.user.pojo.vo.UserGroupVo;
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
@Service(interfaceClass = UserGroupService.class)
public class UserGroupServiceImpl extends MyBaseMysqlServiceImpl<UserGroupMapper, UserGroupEntity, UserGroupVo> implements UserGroupService {
    @Autowired
    private DefineGroupMapper defineGroupMapper;


    @Override
    public List<DefineGroupEntity> queryAllUserBelong(String userAccountId) {
        List<DefineGroupEntity> allJobByUserAcccountId = defineGroupMapper.findAllByUserAcccountId(userAccountId, BaseStateEnum.ENABLED.getValue());
        return allJobByUserAcccountId ;
    }
}
