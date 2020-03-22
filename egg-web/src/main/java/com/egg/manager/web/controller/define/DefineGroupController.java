package com.egg.manager.web.controller.define;

import com.egg.manager.persistence.mapper.user.UserAccountMapper;
import com.egg.manager.service.service.user.UserAccountService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Api(value = "API ==>>  DefineGroupController ",description = "组别定义接口")
@RestController
@RequestMapping("/define/define_group")
public class DefineGroupController {

    @Autowired
    private UserAccountMapper userAccountMapper ;
    @Autowired
    private UserAccountService userAccountService ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

}
