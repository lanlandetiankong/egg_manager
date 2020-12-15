package com.egg.manager.obl.web.controller.log;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.log.mongo.OblPcWebOperationLogMgoService;
import com.egg.manager.persistence.obl.log.db.mongo.repository.OblPcWebOperationLogRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucj
 * @description 操作接口日志
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-OolongBlog_PcWeb操作接口日志")
@RestController
@RequestMapping("/log/obl/pc/web/operationLog")
public class OblPcWebOperationLogController extends BaseController {
    @Autowired
    private OblPcWebOperationLogRepository oblPcWebOperationLogRepository;
    @Reference
    private OblPcWebOperationLogMgoService oblPcWebOperationLogMgoService;

}
