package com.egg.manager.persistence.db.mysql.mapper.announcement;

import com.egg.manager.persistence.constant.pojo.mysql.EggMpSqlConst;
import com.egg.manager.persistence.db.mysql.mapper.MyEggMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementDraftDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface AnnouncementDraftMapper extends MyEggMapper<AnnouncementDraft> {


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
    int batchPublishByIds(@Param(EggMpSqlConst.PARAMOF_IDS) List<String> ids, @Param(EggMpSqlConst.PARAMOF_LOGIN_USER) UserAccount loginUser);
}
