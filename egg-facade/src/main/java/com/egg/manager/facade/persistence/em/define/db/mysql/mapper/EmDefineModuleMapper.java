package com.egg.manager.facade.persistence.em.define.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.facade.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineModuleEntity;
import com.egg.manager.facade.persistence.em.define.pojo.dto.EmDefineModuleDto;
import com.egg.manager.facade.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmDefineModuleMapper extends MyEggMapper<EmDefineModuleEntity> {


    /**
     * [分页搜索查询] - 模块定义
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<EmDefineModuleDto> selectQueryPage(Page<EmDefineModuleDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);
}
