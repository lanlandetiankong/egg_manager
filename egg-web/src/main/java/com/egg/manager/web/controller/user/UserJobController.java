package com.egg.manager.web.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.basic.user.UserJobService;
import com.egg.manager.persistence.db.mysql.mapper.user.UserJobMapper;
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
@Api(value = "API-用户_职务接口")
@RestController
@RequestMapping("/user/userJob")
public class UserJobController extends BaseController {

    @Autowired
    private UserJobMapper userJobMapper;
    @Reference
    private UserJobService userJobService;




}
