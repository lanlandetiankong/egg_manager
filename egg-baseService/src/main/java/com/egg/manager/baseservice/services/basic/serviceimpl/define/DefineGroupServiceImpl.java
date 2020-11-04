package com.egg.manager.baseservice.services.basic.serviceimpl.define;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.basic.define.DefineGroupService;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineGroup;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineGroupMapper;
import com.egg.manager.persistence.em.define.pojo.vo.DefineGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = DefineGroupService.class)
public class DefineGroupServiceImpl extends MyBaseMysqlServiceImpl<DefineGroupMapper, DefineGroup, DefineGroupVo> implements DefineGroupService {


}
