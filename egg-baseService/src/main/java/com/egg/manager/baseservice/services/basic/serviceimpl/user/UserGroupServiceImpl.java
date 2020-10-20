package com.egg.manager.baseservice.services.basic.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.basic.user.UserGroupService;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.db.mysql.entity.user.UserGroup;
import com.egg.manager.persistence.db.mysql.mapper.user.UserGroupMapper;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * \* note:
 * @author: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = UserGroupService.class)
public class UserGroupServiceImpl extends MyBaseMysqlServiceImpl<UserGroupMapper, UserGroup, UserGroupVo> implements UserGroupService {


}
