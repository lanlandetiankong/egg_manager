package com.egg.manager.service.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.service.service.user.UserGroupService;
import com.egg.manager.persistence.db.mysql.entity.user.UserGroup;
import com.egg.manager.persistence.db.mysql.mapper.user.UserGroupMapper;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service(interfaceClass = UserGroupService.class)
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper,UserGroup> implements UserGroupService {

    
}
