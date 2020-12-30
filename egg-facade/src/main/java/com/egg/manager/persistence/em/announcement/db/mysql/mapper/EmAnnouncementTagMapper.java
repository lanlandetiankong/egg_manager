package com.egg.manager.persistence.em.announcement.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.EmAnnouncementTagEntity;
import com.egg.manager.persistence.em.announcement.pojo.dto.EmAnnouncementTagDto;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmAnnouncementTagMapper extends MyEggMapper<EmAnnouncementTagEntity> {

    /**
     * [分页搜索查询] - 公告标签
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<EmAnnouncementTagDto> selectQueryPage(Page<EmAnnouncementTagDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);

}
