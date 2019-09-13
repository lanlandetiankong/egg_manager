package com.egg.manager.serviceimpl.log;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.entity.log.OperationLog;
import com.egg.manager.mapper.log.OperationLogMapper;
import com.egg.manager.service.log.OperationLogService;
import org.springframework.stereotype.Service;

@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper,OperationLog> implements OperationLogService {
}
