package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineGroupEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserGroupEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserGroupMapper;
import com.egg.manager.persistence.em.user.pojo.vo.UserGroupVo;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface UserGroupService extends IService<UserGroupEntity>, MyBaseMysqlService<UserGroupEntity, UserGroupMapper, UserGroupVo> {

    /**
     * 查询-用户拥有的分组-列表
     * @param userAccountId
     * @return
     */
    List<DefineGroupEntity> queryAllUserBelong(Long userAccountId);
}
