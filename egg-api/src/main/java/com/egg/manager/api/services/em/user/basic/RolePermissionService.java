package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.user.db.mysql.entity.RolePermissionEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.RolePermissionMapper;
import com.egg.manager.persistence.em.user.pojo.vo.RolePermissionVo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface RolePermissionService extends IService<RolePermissionEntity>, MyBaseMysqlService<RolePermissionEntity, RolePermissionMapper, RolePermissionVo> {

}
