package com.egg.manager.persistence.obl.user.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserAttentionPersonEntity;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserAttentionPersonDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description 用户的关注人关联-Dao
 * @date 2020-12-03
 */
public interface OblUserAttentionPersonMapper extends MyEggMapper<OblUserAttentionPersonEntity> {

    /**
     * [分页搜索查询]-用户的关注人关联
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<OblUserAttentionPersonDto> selectQueryPage(Page<OblUserAttentionPersonDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);
}