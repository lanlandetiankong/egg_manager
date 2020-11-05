package com.egg.manager.web.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.user.basic.UserRoleService;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserRoleMapper;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-用户_角色接口")
@RestController
@RequestMapping("/user/userRole")
public class UserRoleController extends BaseController {


    @Autowired
    private UserRoleMapper userRoleMapper;
    @Reference
    private UserRoleService userRoleService;


}
