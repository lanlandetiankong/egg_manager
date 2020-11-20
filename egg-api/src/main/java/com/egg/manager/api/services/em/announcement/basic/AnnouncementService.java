package com.egg.manager.api.services.em.announcement.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.form.QueryFieldArr;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDto;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementDraftVo;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface AnnouncementService extends IService<AnnouncementEntity>, MyBaseMysqlService<AnnouncementEntity, AnnouncementMapper, AnnouncementVo> {


    /**
     * 新增公告
     * @param loginUserInfo  当前登录用户
     * @param announcementVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, AnnouncementVo announcementVo) throws Exception;

    /**
     * 公告草稿发布
     * @param loginUserInfo       当前登录用户
     * @param announcementDraftVo
     * @return
     * @throws Exception
     */
    Integer dealCreateFromDraft(CurrentLoginUserInfo loginUserInfo, AnnouncementDraftVo announcementDraftVo) throws Exception;

    /**
     * 分页查询 公告 列表
     * @param loginUserInfo  当前登录用户
     * @param result
     * @param queryFieldArr
     * @param vpage
     * @param sortMap
     * @return
     */
    WebResult dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryFieldArr queryFieldArr, AntdvPage<AnnouncementEntity> vpage,
                                     AntdvSortMap sortMap);

    /**
     * 分页查询 公告 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo  当前登录用户
     * @param result
     * @param queryFieldArr
     * @param vpage
     * @param sortMap
     * @return
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryFieldArr queryFieldArr, AntdvPage<AnnouncementDto> vpage,
                                  AntdvSortMap sortMap);

}
