package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserDepartmentEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserDepartmentMapper;
import com.egg.manager.persistence.em.user.pojo.vo.UserDepartmentVo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface UserDepartmentService extends IService<UserDepartmentEntity>, MyBaseMysqlService<UserDepartmentEntity, UserDepartmentMapper, UserDepartmentVo> {

}
