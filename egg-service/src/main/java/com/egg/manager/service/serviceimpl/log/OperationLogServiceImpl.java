package com.egg.manager.service.serviceimpl.log;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.persistence.entity.log.OperationLog;
import com.egg.manager.persistence.mapper.log.OperationLogMapper;
import com.egg.manager.service.service.log.OperationLogService;
import org.springframework.stereotype.Service;

@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper,OperationLog> implements OperationLogService {
}
