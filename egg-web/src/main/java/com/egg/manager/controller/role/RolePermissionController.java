package com.egg.manager.controller.role;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.entity.role.RolePermission;
import com.egg.manager.mapper.role.RolePermissionMapper;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.role.RolePermissionService;
import com.egg.manager.service.user.UserAccountService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Api(value = "API ==>>  RolePermissionController ",description = "角色权限接口")
@RestController
@RequestMapping("/role/role_permission")
public class RolePermissionController {

    @Autowired
    private UserAccountMapper userAccountMapper ;
    @Autowired
    private UserAccountService userAccountService ;
    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
}
