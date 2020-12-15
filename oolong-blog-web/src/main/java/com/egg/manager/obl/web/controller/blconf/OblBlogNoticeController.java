package com.egg.manager.obl.web.controller.blconf;


import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.blconf.basic.OblBlogNoticeService;
import com.egg.manager.persistence.obl.blconf.db.mysql.mapper.OblBlogNoticeMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucj
 * @description 博客通知表-Api
 * @date 2020-11-30
 */
@Slf4j
@Api(value = "API-博客通知表")
@RestController
@RequestMapping("/oblBlogNotice")
public class OblBlogNoticeController extends BaseController {

    @Autowired
    private OblBlogNoticeMapper oblBlogNoticeMapper;
    @Reference
    private OblBlogNoticeService oblBlogNoticeService;

}