package com.egg.manager.facade.api.services.em.define.basic;

import com.egg.manager.facade.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineGroupEntity;
import com.egg.manager.facade.persistence.em.define.db.mysql.mapper.EmDefineGroupMapper;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineGroupVo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmDefineGroupService extends MyBaseMysqlService<EmDefineGroupEntity, EmDefineGroupMapper, EmDefineGroupVo> {


}
