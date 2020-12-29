package com.egg.manager.facade.persistence.em.announcement.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.facade.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.facade.persistence.em.announcement.db.mysql.entity.EmAnnouncementEntity;
import com.egg.manager.facade.persistence.em.announcement.pojo.dto.EmAnnouncementDto;
import com.egg.manager.facade.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmAnnouncementMapper extends MyEggMapper<EmAnnouncementEntity> {

    /**
     * [分页搜索查询] - 公告
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<EmAnnouncementDto> selectQueryPage(Page<EmAnnouncementDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);
}
