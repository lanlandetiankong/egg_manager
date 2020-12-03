package com.egg.manager.persistence.obl.blconf.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import com.egg.manager.persistence.obl.blconf.db.mysql.entity.OblBlogMenuConfEntity;
import com.egg.manager.persistence.obl.blconf.pojo.dto.OblBlogMenuConfDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description 博客菜单定义表-Dao
 * @date 2020-11-30
 */
public interface OblBlogMenuConfMapper extends MyEggMapper<OblBlogMenuConfEntity> {

    /**
     * [分页搜索查询]-博客菜单定义表
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<OblBlogMenuConfDto> selectQueryPage(Page<OblBlogMenuConfDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);
}