package com.egg.manager.em.web.controller.index.hello;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.exchange.helper.UserGenerateHelper;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @Description:
 * @ClassName: UserToolController
 * @Author: zhoucj
 * @Date: 2020/12/1 14:44
 */
@Slf4j
@Api(value = "API-用户工具接口")
@RestController
@RequestMapping("/index/usertool")
public class UserToolController extends BaseController {

    @Reference
    private UserAccountService userAccountService ;


    @PcWebQueryLog(fullPath = "/index/usertool/batchGenerate", flag = false)
    @ApiOperation(value = "批量生成测试用户", response = WebResult.class, httpMethod = HttpMethodConstant.GET)
    @GetMapping(value = "/batchGenerate")
    public String doGetAllDefineDepartmentDtos() {
        //随机生成100个用户
        ArrayList<UserAccountEntity> userAccountEntities = UserGenerateHelper.batchGenerateUser(100);
        userAccountService.saveBatch(userAccountEntities);
        return "ok" ;
    }



}