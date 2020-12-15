package com.egg.manager.obl.web.controller.user;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.user.basic.OblUserAccountService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserAccountEntity;
import com.egg.manager.persistence.obl.user.db.mysql.mapper.OblUserAccountMapper;
import com.egg.manager.persistence.obl.user.pojo.transfer.OblUserAccountTransfer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhoucj
 * @description 用户表-Api
 * @date 2020-12-07
 */
@Slf4j
@Api(value = "API-用户表")
@RestController
@RequestMapping("/oblUserAccount")
public class OblUserAccountController extends BaseController {

    @Autowired
    private OblUserAccountMapper oblUserAccountMapper;
    @Reference
    private OblUserAccountService oblUserAccountService;


    @ApiOperation(value = "根据id查询->用户表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebQueryLog(fullPath = "/oblUserAccount/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblUserAccountId,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblUserAccountId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblUserAccountEntity oblUserAccountEntity = oblUserAccountMapper.selectById(oblUserAccountId);
        result.putBean(OblUserAccountTransfer.transferEntityToVo(oblUserAccountEntity));
        return result;
    }

}