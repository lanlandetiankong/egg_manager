package com.egg.manager.em.web.controller.emctl.index.hello;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.exchange.helper.UserGenerateHelper;
import com.egg.manager.api.services.em.user.basic.EmUserAccountService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
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
public class EmUserToolController extends BaseController {

    @Reference
    private EmUserAccountService emUserAccountService;


    @EmPcWebQueryLog(fullPath = "/index/usertool/batchGenerate", flag = false)
    @ApiOperation(value = "批量生成测试用户", response = WebResult.class, httpMethod = HttpMethodConstant.GET)
    @GetMapping(value = "/batchGenerate")
    public String doGetAllDefineDepartmentDtos() {
        //随机生成100个用户
        ArrayList<EmUserAccountEntity> userAccountEntities = UserGenerateHelper.batchGenerateUser(100);
        emUserAccountService.saveBatch(userAccountEntities);
        return "ok";
    }


}