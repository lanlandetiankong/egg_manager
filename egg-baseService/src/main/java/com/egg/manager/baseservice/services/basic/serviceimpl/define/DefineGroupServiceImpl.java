package com.egg.manager.baseservice.services.basic.serviceimpl.define;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.basic.define.DefineGroupService;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.db.mysql.entity.define.DefineGroup;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineGroupMapper;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = DefineGroupService.class)
public class DefineGroupServiceImpl extends MyBaseMysqlServiceImpl<DefineGroupMapper, DefineGroup, DefineGroupVo> implements DefineGroupService {


}
