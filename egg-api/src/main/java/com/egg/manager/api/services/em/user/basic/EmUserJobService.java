package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineJobEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserJobEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmUserJobMapper;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserJobVo;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmUserJobService extends MyBaseMysqlService<EmUserJobEntity, EmUserJobMapper, EmUserJobVo> {

    /**
     * 查询用户所归属的职务列表
     * @param userAccountId
     * @return
     */
    List<EmDefineJobEntity> queryAllUserBelong(String userAccountId);
}
