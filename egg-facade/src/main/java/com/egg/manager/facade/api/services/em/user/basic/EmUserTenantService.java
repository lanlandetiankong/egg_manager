package com.egg.manager.facade.api.services.em.user.basic;

import com.egg.manager.facade.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserTenantEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.EmUserTenantMapper;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserTenantVo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmUserTenantService extends MyBaseMysqlService<EmUserTenantEntity, EmUserTenantMapper, EmUserTenantVo> {

}
