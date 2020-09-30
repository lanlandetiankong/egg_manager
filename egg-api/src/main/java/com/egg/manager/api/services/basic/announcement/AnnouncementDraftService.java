package com.egg.manager.api.services.basic.announcement;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.announcement.Announcement;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.announcement.AnnouncementDraftMapper;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementDraftDto;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementDraftVo;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */

public interface AnnouncementDraftService extends IService<AnnouncementDraft>,MyBaseMysqlService<AnnouncementDraftMapper,AnnouncementDraft,AnnouncementDraftVo> {

    /**
     * 新增公告草稿
     * @param loginUser 当前登录用户
     * @param announcementDraftVo
     * @throws Exception
     * @return Integer
     */
    Integer dealCreate(UserAccount loginUser,AnnouncementDraftVo announcementDraftVo) throws Exception ;

    /**
     * 更新公告草稿
     * @param loginUser 当前登录用户
     * @param announcementDraftVo
     * @throws Exception
     * @return Integer
     */
    Integer dealUpdate(UserAccount loginUser,AnnouncementDraftVo announcementDraftVo) throws Exception ;

    /**
     * 分页查询 公告草稿 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser 当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return MyCommonResult<AnnouncementDraftVo>
     */
    MyCommonResult<AnnouncementDraftVo> dealQueryPageByDtos(UserAccount loginUser,MyCommonResult<AnnouncementDraftVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementDraftDto> paginationBean,
                                                                         List<AntdvSortBean> sortBeans);

    /**
     * 公告草稿-删除
     * @param loginUser 当前登录用户
     * @param delIds 要删除的公告草稿id 集合
     * @throws Exception
     * @return
     */
    Integer dealBatchDelete(UserAccount loginUser,String[] delIds) throws Exception;


    /**
     * 公告草稿-删除
     * @param loginUser 当前登录用户
     * @param delId 要删除的公告草稿id
     * @throws Exception
     * @return
     */
    Integer dealDeleteById(UserAccount loginUser,String delId) throws Exception;


    /**
     * 公告草稿-发布
     * @param loginUser 当前登录用户
     * @param draftIds 要发布的公告草稿id 集合
     * @throws Exception
     * @return
     */
    Integer dealBatchPublishByDraft(UserAccount loginUser,String[] draftIds) throws Exception;

    /**
     * 公告草稿-发布
     * @param loginUser 当前登录用户
     * @param draftId 要发布的公告草稿id
     * @param insertFlag
     * @throws Exception
     * @return
     */
    Integer dealPublishByDraft(UserAccount loginUser,String draftId,boolean insertFlag) throws Exception ;

}
