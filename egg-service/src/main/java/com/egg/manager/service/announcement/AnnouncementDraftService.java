package com.egg.manager.service.announcement;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.entity.announcement.Announcement;
import com.egg.manager.entity.announcement.AnnouncementDraft;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.announcement.AnnouncementDraftVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */

public interface AnnouncementDraftService extends IService<AnnouncementDraft> {

    /**
     * 新增公告草稿
     * @param announcementDraftVo
     * @throws Exception
     */
    Integer dealAddAnnouncementDraft(AnnouncementDraftVo announcementDraftVo, UserAccount loginUser) throws Exception ;

    /**
     * 更新公告草稿
     * @param announcementDraftVo
     * @throws Exception
     */
    Integer dealUpdateAnnouncementDraft(AnnouncementDraftVo announcementDraftVo, UserAccount loginUser) throws Exception ;
    /**
     * 分页查询 公告草稿
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetAnnouncementDraftPages(MyCommonResult<AnnouncementDraftVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                              List<AntdvSortBean> sortBeans);

    /**
     * 公告草稿-删除
     * @param delIds 要删除的公告草稿id 集合
     * @throws Exception
     */
    Integer dealDelAnnouncementDraftByArr(String[] delIds,UserAccount loginUser) throws Exception;


    /**
     * 公告草稿-删除
     * @param delId 要删除的公告草稿id
     * @throws Exception
     */
    Integer dealDelAnnouncementDraft(String delId,UserAccount loginUser) throws Exception;


    /**
     * 公告草稿-发布
     * @param draftIds 要发布的公告草稿id 集合
     * @throws Exception
     */
    Integer dealPublishAnnouncementDraftByArr(String[] draftIds,UserAccount loginUser) throws Exception;

    /**
     * 公告草稿-发布
     * @param draftId 要发布的公告草稿id
     * @throws Exception
     */
    Integer dealPublishAnnouncementDraft(String draftId,UserAccount loginUser,boolean insertFlag) throws Exception ;


    Announcement draftTranslateToAnnouncement(AnnouncementDraft announcementDraft, UserAccount loginUser);
}
