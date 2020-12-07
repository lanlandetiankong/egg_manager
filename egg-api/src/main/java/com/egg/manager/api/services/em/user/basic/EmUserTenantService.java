package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserTenantEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmUserTenantMapper;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserTenantVo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmUserTenantService extends MyBaseMysqlService<EmUserTenantEntity, EmUserTenantMapper, EmUserTenantVo> {

}
