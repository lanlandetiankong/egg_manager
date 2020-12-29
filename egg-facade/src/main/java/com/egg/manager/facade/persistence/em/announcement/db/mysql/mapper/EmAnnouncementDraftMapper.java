package com.egg.manager.facade.persistence.em.announcement.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.facade.persistence.commons.base.constant.db.mysql.EggMpSqlConst;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.facade.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.facade.persistence.em.announcement.db.mysql.entity.EmAnnouncementDraftEntity;
import com.egg.manager.facade.persistence.em.announcement.pojo.dto.EmAnnouncementDraftDto;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmAnnouncementDraftMapper extends MyEggMapper<EmAnnouncementDraftEntity> {


    /**
     * [分页搜索查询] - 公告草稿
     * @param page
     * @param queryFieldArr
     * @param sortMap
     * @return
     */
    List<EmAnnouncementDraftDto> selectQueryPage(Page<EmAnnouncementDraftDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) QueryFieldArr queryFieldArr, @Param(EggMpSqlConst.PARAMOF_SORT_MAP) AntdvSortMap sortMap);

    /**
     * 批量 发布 公告草稿
     * (只是修改 AnnouncementDraft 的值，具体发布到Announcement 需要自行另外操作)
     * @param ids
     * @param loginUser
     * @return
     */
    int batchPublishByIds(@Param(EggMpSqlConst.PARAMOF_IDS) List<String> ids, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) EmUserAccountEntity loginUser);
}
