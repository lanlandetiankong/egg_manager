package com.egg.manager.api.services.basic.announcement;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.bean.helper.MyCommonResult;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementDraft;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementDraftMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDraftDto;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementDraftVo;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface AnnouncementDraftService extends IService<AnnouncementDraft>, MyBaseMysqlService<AnnouncementDraft, AnnouncementDraftMapper, AnnouncementDraftVo> {

    /**
     * 新增公告草稿
     * @param loginUser           当前登录用户
     * @param announcementDraftVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser, AnnouncementDraftVo announcementDraftVo) throws Exception;

    /**
     * 更新公告草稿
     * @param loginUser           当前登录用户
     * @param announcementDraftVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser, AnnouncementDraftVo announcementDraftVo) throws Exception;

    /**
     * 分页查询 公告草稿 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return MyCommonResult<AnnouncementDraftVo>
     */
    MyCommonResult<AnnouncementDraftVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<AnnouncementDraftVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementDraftDto> paginationBean,
                                                            List<AntdvSortBean> sortBeans);

    /**
     * 公告草稿-发布
     * @param loginUser 当前登录用户
     * @param draftIds  要发布的公告草稿id 集合
     * @return
     * @throws Exception
     */
    Integer dealBatchPublishByDraft(UserAccount loginUser, Long[] draftIds) throws Exception;

    /**
     * 公告草稿-发布
     * @param loginUser  当前登录用户
     * @param draftId    要发布的公告草稿id
     * @param insertFlag
     * @return
     * @throws Exception
     */
    Integer dealPublishByDraft(UserAccount loginUser, Long draftId, boolean insertFlag) throws Exception;

}
