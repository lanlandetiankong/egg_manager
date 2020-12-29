package com.egg.manager.em.web.controller.emctl.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.facade.api.exchange.BaseController;
import com.egg.manager.facade.api.services.em.define.basic.EmDefineMenuService;
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
@Api(value = "API-角色_菜单接口")
@RestController
@RequestMapping("/emCtl/role/roleMenus")
public class EmRoleMenuController extends BaseController {
    @Reference
    private EmDefineMenuService emDefineMenuService;
}
