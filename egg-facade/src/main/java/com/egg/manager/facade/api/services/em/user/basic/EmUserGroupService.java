package com.egg.manager.facade.api.services.em.user.basic;

import com.egg.manager.facade.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineGroupEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserGroupEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.EmUserGroupMapper;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserGroupVo;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmUserGroupService extends MyBaseMysqlService<EmUserGroupEntity, EmUserGroupMapper, EmUserGroupVo> {

    /**
     * 查询-用户拥有的分组-列表
     * @param userAccountId
     * @return
     */
    List<EmDefineGroupEntity> queryAllUserBelong(String userAccountId);
}
