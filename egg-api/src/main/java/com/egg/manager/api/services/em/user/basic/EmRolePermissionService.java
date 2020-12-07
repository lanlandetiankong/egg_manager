package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmRolePermissionEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmRolePermissionMapper;
import com.egg.manager.persistence.em.user.pojo.vo.EmRolePermissionVo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmRolePermissionService extends IService<EmRolePermissionEntity>, MyBaseMysqlService<EmRolePermissionEntity, EmRolePermissionMapper, EmRolePermissionVo> {

}
