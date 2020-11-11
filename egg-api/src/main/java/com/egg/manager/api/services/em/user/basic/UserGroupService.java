package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserGroupEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserGroupMapper;
import com.egg.manager.persistence.em.user.pojo.vo.UserGroupVo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface UserGroupService extends IService<UserGroupEntity>, MyBaseMysqlService<UserGroupEntity, UserGroupMapper, UserGroupVo> {


}