package com.egg.manager.api.services.basic.announcement;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.bean.helper.MyCommonResult;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.Announcement;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDto;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementDraftVo;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementVo;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface AnnouncementService extends IService<Announcement>, MyBaseMysqlService<Announcement, AnnouncementMapper, AnnouncementVo> {


    /**
     * 新增公告
     * @param loginUser      当前登录用户
     * @param announcementVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser, AnnouncementVo announcementVo) throws Exception;

    /**
     * 公告草稿发布
     * @param loginUser           当前登录用户
     * @param announcementDraftVo
     * @return
     * @throws Exception
     */
    Integer dealCreateFromDraft(UserAccount loginUser, AnnouncementDraftVo announcementDraftVo) throws Exception;

    /**
     * 分页查询 公告 列表
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<AnnouncementVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<AnnouncementVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<Announcement> paginationBean,
                                                          List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 公告 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<AnnouncementVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<AnnouncementVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementDto> paginationBean,
                                                       List<AntdvSortBean> sortBeans);

}
