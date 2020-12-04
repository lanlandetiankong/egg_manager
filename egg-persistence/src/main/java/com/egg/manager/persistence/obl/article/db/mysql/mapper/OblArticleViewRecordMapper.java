package com.egg.manager.persistence.obl.article.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleViewRecordEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleViewRecordDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description 文章查看记录-Dao
 * @date 2020-12-04
 */
public interface OblArticleViewRecordMapper extends MyEggMapper<OblArticleViewRecordEntity> {

    /**
     * [分页搜索查询]-文章查看记录
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<OblArticleViewRecordDto> selectQueryPage(Page<OblArticleViewRecordDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);
}