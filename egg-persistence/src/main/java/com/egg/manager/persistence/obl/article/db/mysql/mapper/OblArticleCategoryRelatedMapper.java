package com.egg.manager.persistence.obl.article.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCategoryRelatedEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCategoryRelatedDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description 文章分类关联表-Dao
 * @date 2020-11-30
 */
public interface OblArticleCategoryRelatedMapper extends MyEggMapper<OblArticleCategoryRelatedEntity> {

    /**
     * [分页搜索查询]-文章分类关联表
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<OblArticleCategoryRelatedDto> selectQueryPage(Page<OblArticleCategoryRelatedDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);
}