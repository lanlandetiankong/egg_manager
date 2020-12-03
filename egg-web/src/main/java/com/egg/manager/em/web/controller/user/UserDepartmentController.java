package com.egg.manager.em.web.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.user.basic.UserDepartmentService;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserDepartmentMapper;
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
@Api(value = "API-用户_部门接口")
@RestController
@RequestMapping("/user/userDepartment")
public class UserDepartmentController extends BaseController {
    @Autowired
    private UserDepartmentMapper userDepartmentMapper;
    @Reference
    private UserDepartmentService userDepartmentService;
}
