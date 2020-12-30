package com.egg.manager.api.services.em.user.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserDepartmentEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmUserDepartmentMapper;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserDepartmentVo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmUserDepartmentService extends MyBaseMysqlService<EmUserDepartmentEntity, EmUserDepartmentMapper, EmUserDepartmentVo> {

}
