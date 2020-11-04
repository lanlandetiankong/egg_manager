package com.egg.manager.baseservice.services.basic.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.basic.user.UserGroupService;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserGroup;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserGroupMapper;
import com.egg.manager.persistence.em.user.pojo.vo.UserGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = UserGroupService.class)
public class UserGroupServiceImpl extends MyBaseMysqlServiceImpl<UserGroupMapper, UserGroup, UserGroupVo> implements UserGroupService {


}
