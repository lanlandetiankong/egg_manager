package com.egg.manager.controller.log;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.entity.log.OperationLog;
import com.egg.manager.mapper.log.OperationLogMapper;
import com.egg.manager.service.log.OperationLogService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "API ==>>  OperationLogController ",description = "操作日志记录接口")
@RestController
@RequestMapping("/log/operation_log")
public class OperationLogController {

}
