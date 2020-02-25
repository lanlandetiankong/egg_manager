package com.egg.manager.service.announcement;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.common.web.pagination.AntdvSortBean;
import com.egg.manager.entity.announcement.Announcement;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.announcement.AnnouncementDraftVo;
import com.egg.manager.vo.announcement.AnnouncementVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */

public interface AnnouncementService extends IService<Announcement> {


    /**
     * 新增公告
     * @param announcementVo
     * @throws Exception
     */
    Integer dealAddAnnouncement(AnnouncementVo announcementVo, UserAccount loginUser) throws Exception;

    /**
     * 公告草稿发布
     * @param announcementDraftVo
     * @throws Exception
     */
    Integer dealAddAnnouncementFromDraft(AnnouncementDraftVo announcementDraftVo, UserAccount loginUser) throws Exception ;

    /**
     * 分页查询 公告
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetAnnouncementPages(MyCommonResult<AnnouncementVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                         List<AntdvSortBean> sortBeans);

    /**
     * 公告-删除
     * @param delIds 要删除的公告id 集合
     * @throws Exception
     */
    Integer dealDelAnnouncementByArr(String[] delIds,UserAccount loginUser) throws Exception ;
    /**
     * 公告-删除
     * @param delId 要删除的公告id
     * @throws Exception
     */
    Integer dealDelAnnouncement(String delId,UserAccount loginUser) throws Exception;
}
