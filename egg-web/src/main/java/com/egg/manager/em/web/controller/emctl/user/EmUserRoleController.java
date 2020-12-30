package com.egg.manager.em.web.controller.emctl.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.user.basic.EmUserRoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-用户_角色接口")
@RestController
@RequestMapping("/emCtl/user/userRole")
public class EmUserRoleController extends BaseController {
    @Reference
    private EmUserRoleService emUserRoleService;
}
