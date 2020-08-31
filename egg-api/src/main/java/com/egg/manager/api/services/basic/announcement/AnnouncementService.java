package com.egg.manager.api.services.basic.announcement;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.announcement.Announcement;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementDraftMysqlVo;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementMysqlVo;

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
    Integer dealAddAnnouncement(AnnouncementMysqlVo announcementVo, UserAccount loginUser) throws Exception;

    /**
     * 公告草稿发布
     * @param announcementDraftVo
     * @throws Exception
     */
    Integer dealAddAnnouncementFromDraft(AnnouncementDraftMysqlVo announcementDraftVo, UserAccount loginUser) throws Exception ;

    /**
     * 分页查询 公告 列表
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<AnnouncementMysqlVo> dealGetAnnouncementPages(MyCommonResult<AnnouncementMysqlVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                 List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 公告 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<AnnouncementMysqlVo> dealGetAnnouncementDtoPages(MyCommonResult<AnnouncementMysqlVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
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
