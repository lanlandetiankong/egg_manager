package com.egg.manager.web.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.basic.user.UserDepartmentService;
import com.egg.manager.persistence.db.mysql.mapper.user.UserDepartmentMapper;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Description:
 * \
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
