package com.egg.manager.persistence.em.define.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineJobEntity;
import com.egg.manager.persistence.em.define.pojo.dto.DefineJobDto;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface DefineJobMapper extends MyEggMapper<DefineJobEntity> {

    /**
     * [分页搜索查询] - 职务定义
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<DefineJobDto> selectQueryPage(Page<DefineJobDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);

    /**
     * 查询指定用户的 用户-职务 关联表
     * @param userAccountId
     * @param stateVal      指定state的值
     * @return
     */
    List<DefineJobEntity> findAllByUserAcccountId(@Param(EggMpSqlConst.PARAMOF_USER_ACCOUNT_ID) String userAccountId, @Param("stateVal") Short stateVal);

}
