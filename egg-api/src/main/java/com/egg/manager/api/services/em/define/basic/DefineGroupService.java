package com.egg.manager.api.services.em.define.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineGroupEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineGroupMapper;
import com.egg.manager.persistence.em.define.pojo.vo.DefineGroupVo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface DefineGroupService extends IService<DefineGroupEntity>, MyBaseMysqlService<DefineGroupEntity, DefineGroupMapper, DefineGroupVo> {


}
