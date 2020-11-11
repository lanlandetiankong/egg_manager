package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserTenantEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserTenantMapper;
import com.egg.manager.persistence.em.user.pojo.vo.UserTenantVo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface UserTenantService extends IService<UserTenantEntity>, MyBaseMysqlService<UserTenantEntity, UserTenantMapper, UserTenantVo> {

}
