package com.egg.manager.obl.web.controller.user;


import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.user.basic.OblUserCalculateInfoService;
import com.egg.manager.persistence.obl.user.db.mysql.mapper.OblUserCalculateInfoMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucj
 * @description 用户的计算信息-Api
 * @date 2020-12-03
 */
@Slf4j
@Api(value = "API-用户的计算信息")
@RestController
@RequestMapping("/oblUserCalculateInfo")
public class OblUserCalculateInfoController extends BaseController {

    @Autowired
    private OblUserCalculateInfoMapper oblUserCalculateInfoMapper;
    @Reference
    private OblUserCalculateInfoService oblUserCalculateInfoService;


}