package com.egg.manager.obl.web.controller.blconf;


import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.blconf.basic.OblBlogMenuConfService;
import com.egg.manager.persistence.obl.blconf.db.mysql.mapper.OblBlogMenuConfMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucj
 * @description 博客菜单定义表-Api
 * @date 2020-11-30
 */
@Slf4j
@Api(value = "API-博客菜单定义表")
@RestController
@RequestMapping("/oblBlogMenuConf")
public class OblBlogMenuConfController extends BaseController {

    @Autowired
    private OblBlogMenuConfMapper oblBlogMenuConfMapper;
    @Reference
    private OblBlogMenuConfService oblBlogMenuConfService;


}