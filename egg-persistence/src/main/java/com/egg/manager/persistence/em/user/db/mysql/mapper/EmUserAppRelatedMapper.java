package com.egg.manager.persistence.em.user.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAppRelatedEntity;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserAppRelatedDto;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description app用户关联表-Dao
 * @date 2020-12-07
 */
public interface EmUserAppRelatedMapper extends MyEggMapper<EmUserAppRelatedEntity> {

    /**
     * [分页搜索查询]-app用户关联表
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<EmUserAppRelatedDto> selectQueryPage(Page<EmUserAppRelatedDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);
}