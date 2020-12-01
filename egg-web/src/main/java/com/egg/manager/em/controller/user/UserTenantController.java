package com.egg.manager.em.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.user.basic.UserTenantService;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserTenantMapper;
import com.egg.manager.api.exchange.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
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
