package com.egg.manager.api.services.em.define.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineGroupEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineGroupMapper;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineGroupVo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmDefineGroupService extends IService<EmDefineGroupEntity>, MyBaseMysqlService<EmDefineGroupEntity, EmDefineGroupMapper, EmDefineGroupVo> {


}
