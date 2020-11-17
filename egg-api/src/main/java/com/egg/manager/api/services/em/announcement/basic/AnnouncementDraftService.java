package com.egg.manager.api.services.em.announcement.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementDraftEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementDraftMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDraftDto;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementDraftVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface AnnouncementDraftService extends IService<AnnouncementDraftEntity>, MyBaseMysqlService<AnnouncementDraftEntity, AnnouncementDraftMapper, AnnouncementDraftVo> {

    /**
     * 新增公告草稿
     * @param loginUserInfo           当前登录用户
     * @param announcementDraftVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, AnnouncementDraftVo announcementDraftVo) throws Exception;

    /**
     * 更新公告草稿
     * @param loginUserInfo           当前登录用户
     * @param announcementDraftVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, AnnouncementDraftVo announcementDraftVo) throws Exception;

    /**
     * 分页查询 公告草稿 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementDraftDto> paginationBean,
                                  List<AntdvSortBean> sortBeans);

    /**
     * 公告草稿-发布
     * @param loginUserInfo 当前登录用户
     * @param draftIds  要发布的公告草稿id 集合
     * @return
     * @throws Exception
     */
    Integer dealBatchPublishByDraft(CurrentLoginUserInfo loginUserInfo, String[] draftIds) throws Exception;

    /**
     * 公告草稿-发布
     * @param loginUserInfo  当前登录用户
     * @param draftId    要发布的公告草稿id
     * @param insertFlag
     * @return
     * @throws Exception
     */
    Integer dealPublishByDraft(CurrentLoginUserInfo loginUserInfo, String draftId, boolean insertFlag) throws Exception;

}
