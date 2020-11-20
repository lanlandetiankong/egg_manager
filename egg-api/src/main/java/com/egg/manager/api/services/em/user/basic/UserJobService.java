package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineJobEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserJobEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserJobMapper;
import com.egg.manager.persistence.em.user.pojo.vo.UserJobVo;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface UserJobService extends IService<UserJobEntity>, MyBaseMysqlService<UserJobEntity, UserJobMapper, UserJobVo> {

    /**
     * 查询用户所归属的职务列表
     * @param userAccountId
     * @return
     */
    List<DefineJobEntity> queryAllUserBelong(String userAccountId);
}
