package com.egg.manager.facade.api.services.em.user.basic;

import com.egg.manager.facade.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserRoleEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.EmUserRoleMapper;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserRoleVo;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmUserRoleService extends MyBaseMysqlService<EmUserRoleEntity, EmUserRoleMapper, EmUserRoleVo> {
    /**
     * 取得当前用户关联的 UserRole
     * @param account
     * @return
     */
    List<EmUserRoleEntity> dealGetAllByAccount(EmUserAccountEntity account);


}
