package com.egg.manager.baseService.services.basic.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.basic.CommonFuncService;
import com.egg.manager.api.services.redis.service.RedisHelper;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/7
 * \* Time: 12:55
 * \* Description:
 * \
 */
@Service(interfaceClass = CommonFuncService.class)
public class CommonFuncServiceImpl implements CommonFuncService {


    @Reference
    private RedisHelper redisHelper;


}
