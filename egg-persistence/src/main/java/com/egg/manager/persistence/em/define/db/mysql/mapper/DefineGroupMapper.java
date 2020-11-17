package com.egg.manager.persistence.em.define.db.mysql.mapper;

import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineGroupEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineJobEntity;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface DefineGroupMapper extends MyEggMapper<DefineGroupEntity> {

    /**
     * 查询指定用户的 用户-职务 关联表
     * @param userAccountId
     * @param stateVal      状态值
     * @return
     */
    List<DefineGroupEntity> findAllByUserAcccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId, @Param("stateVal") Short stateVal);

}
