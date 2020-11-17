package com.egg.manager.persistence.em.announcement.db.mysql.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.persistence.commons.base.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementDraftEntity;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDraftDto;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.exchange.db.mysql.mapper.MyEggMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface AnnouncementDraftMapper extends MyEggMapper<AnnouncementDraftEntity> {


    /**
     * [分页搜索查询] - 公告草稿
     * @param page
     * @param queryFieldBeanList
     * @param sortBeans
     * @return
     */
    List<AnnouncementDraftDto> selectQueryPage(Page<AnnouncementDraftDto> page, @Param(EggMpSqlConst.PARAMOF_QUERY_FIELD_LIST) List<QueryFormFieldBean> queryFieldBeanList, @Param(EggMpSqlConst.PARAMOF_SORT_FIELD_LIST) List<AntdvSortBean> sortBeans);

    /**
     * 批量 发布 公告草稿
     * (只是修改 AnnouncementDraft 的值，具体发布到Announcement 需要自行另外操作)
     * @param ids
     * @param loginUser
     * @return
     */
    int batchPublishByIds(@Param(EggMpSqlConst.PARAMOF_IDS) List<String> ids, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccountEntity loginUser);
}
