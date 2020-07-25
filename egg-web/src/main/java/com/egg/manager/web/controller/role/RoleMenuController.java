package com.egg.manager.web.controller.role;

import com.egg.manager.persistence.mapper.define.DefineMenuMapper;
import com.egg.manager.service.service.module.DefineMenuService;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
@Api(value = "API ==>>  RoleMenuController ",description = "角色-菜单接口")
@RestController
@RequestMapping("/role/role_menus")
public class RoleMenuController extends BaseController{

    @Autowired
    private DefineMenuMapper defineMenuMapper;
    @Autowired
    private DefineMenuService defineMenuService ;



    
}