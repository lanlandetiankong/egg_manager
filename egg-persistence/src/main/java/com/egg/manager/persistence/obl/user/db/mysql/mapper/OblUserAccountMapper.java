package com.egg.manager.persistence.obl.user.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserAccountEntity;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserAccountDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description 用户表-Dao
 * @date 2020-12-07
 */
public interface OblUserAccountMapper extends MyEggMapper<OblUserAccountEntity> {

    /**
     * [分页搜索查询]-用户表
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<OblUserAccountDto> selectQueryPage(Page<OblUserAccountDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);
}