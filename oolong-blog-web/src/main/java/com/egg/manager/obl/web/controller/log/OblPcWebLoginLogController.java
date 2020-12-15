package com.egg.manager.obl.web.controller.log;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.log.mongo.OblPcWebLoginLogMgoService;
import com.egg.manager.persistence.obl.log.db.mongo.repository.OblPcWebLoginLogRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucj
 * @description 登录日志
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-OolongBlog_PcWeb登录接口日志")
@RestController
@RequestMapping("/log/obl/pc/web/loginLog")
public class OblPcWebLoginLogController extends BaseController {
    @Autowired
    private OblPcWebLoginLogRepository oblPcWebLoginLogRepository;
    @Reference
    private OblPcWebLoginLogMgoService oblPcWebLoginLogMgoService;

}
