package com.egg.manager.web.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.basic.user.UserTenantService;
import com.egg.manager.persistence.db.mysql.mapper.user.UserTenantMapper;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * \* note:
 * @author: zhouchengjie
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API-用户_租户接口")
@RestController
@RequestMapping("/user/userTenant")
public class UserTenantController extends BaseController {
    @Autowired
    private UserTenantMapper userTenantMapper;
    @Reference
    private UserTenantService userTenantService;

}
