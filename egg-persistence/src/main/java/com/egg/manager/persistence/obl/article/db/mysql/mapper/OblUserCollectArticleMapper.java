package com.egg.manager.persistence.obl.article.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserCollectArticleEntity;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserCollectArticleDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description 用户收藏的文章-Dao
 * @date 2020-12-03
 */
public interface OblUserCollectArticleMapper extends MyEggMapper<OblUserCollectArticleEntity> {

    /**
     * [分页搜索查询]-用户收藏的文章
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<OblUserCollectArticleDto> selectQueryPage(Page<OblUserCollectArticleDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);
}