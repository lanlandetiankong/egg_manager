package com.egg.manager.service.serviceimpl.define;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.service.service.define.DefineGroupService;
import com.egg.manager.persistence.entity.define.DefineGroup;
import com.egg.manager.persistence.mapper.define.DefineGroupMapper;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service(interfaceClass = DefineGroupService.class)
public class DefineGroupServiceImpl extends ServiceImpl<DefineGroupMapper,DefineGroup> implements DefineGroupService {


}
