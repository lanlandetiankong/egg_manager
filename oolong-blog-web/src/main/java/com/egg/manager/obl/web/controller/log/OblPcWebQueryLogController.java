package com.egg.manager.obl.web.controller.log;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.log.mongo.OblPcWebQueryLogMgoService;
import com.egg.manager.persistence.obl.log.db.mongo.repository.OblPcWebQueryLogRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucj
 * @description 查询日志
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-OolongBlog_PcWeb查询日志")
@RestController
@RequestMapping("/log/obl/pc/web/queryLog")
public class OblPcWebQueryLogController extends BaseController {
    @Autowired
    private OblPcWebQueryLogRepository pcWebQueryLogRepository;
    @Reference
    private OblPcWebQueryLogMgoService pcWebQueryLogMgoService;

}
