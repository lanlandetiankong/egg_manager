package com.egg.manager.web.controller.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.basic.module.DefineMenuService;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineMenuMapper;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* note:
 * @author: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API-角色_菜单接口")
@RestController
@RequestMapping("/role/roleMenus")
public class RoleMenuController extends BaseController {

    @Autowired
    private DefineMenuMapper defineMenuMapper;
    @Reference
    private DefineMenuService defineMenuService;


}
