package com.egg.manager.persistence.obl.article.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserDefCollectCategoryEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserDefCollectCategoryDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description 用户定义的收藏类别-Dao
 * @date 2020-12-03
 */
public interface OblUserDefCollectCategoryMapper extends MyEggMapper<OblUserDefCollectCategoryEntity> {

    /**
     * [分页搜索查询]-用户定义的收藏类别
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<OblUserDefCollectCategoryDto> selectQueryPage(Page<OblUserDefCollectCategoryDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);
}