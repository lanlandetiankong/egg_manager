package com.egg.manager.web.controller.role;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.persistence.entity.define.DefineMenu;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.entity.user.UserRole;
import com.egg.manager.persistence.mapper.define.DefineMenuMapper;
import com.egg.manager.persistence.mapper.user.UserAccountMapper;
import com.egg.manager.persistence.vo.user.UserRoleVo;
import com.egg.manager.service.annotation.log.CurrentLoginUser;
import com.egg.manager.service.annotation.log.OperLog;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.service.redis.service.RedisHelper;
import com.egg.manager.service.service.module.DefineMenuService;
import com.egg.manager.service.service.user.UserAccountService;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Api(value = "API ==>>  RoleMenuController ",description = "角色-菜单接口")
@RestController
@RequestMapping("/role/role_menus")
public class RoleMenuController extends BaseController{

    @Autowired
    private DefineMenuMapper defineMenuMapper;
    @Autowired
    private DefineMenuService defineMenuService ;
    @Autowired
    private UserAccountService userAccountService ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    
}
