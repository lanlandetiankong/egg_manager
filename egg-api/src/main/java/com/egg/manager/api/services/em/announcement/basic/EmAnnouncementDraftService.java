package com.egg.manager.api.services.em.announcement.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.EmAnnouncementDraftEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.EmAnnouncementDraftMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.EmAnnouncementDraftDto;
import com.egg.manager.persistence.em.announcement.pojo.vo.EmAnnouncementDraftVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmAnnouncementDraftService extends MyBaseMysqlService<EmAnnouncementDraftEntity, EmAnnouncementDraftMapper, EmAnnouncementDraftVo> {

    /**
     * 新增公告草稿
     * @param loginUserInfo       当前登录用户
     * @param emAnnouncementDraftVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmAnnouncementDraftVo emAnnouncementDraftVo) throws Exception;

    /**
     * 更新公告草稿
     * @param loginUserInfo       当前登录用户
     * @param emAnnouncementDraftVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmAnnouncementDraftVo emAnnouncementDraftVo) throws Exception;

    /**
     * 分页查询 公告草稿 dto列表
     * (查询的是 com.egg.manager.persistence.obl.article.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmAnnouncementDraftDto> queryPageBean);

    /**
     * 公告草稿-发布
     * @param loginUserInfo 当前登录用户
     * @param draftIds      要发布的公告草稿id 集合
     * @return
     * @throws Exception
     */
    Integer dealBatchPublishByDraft(CurrentLoginEmUserInfo loginUserInfo, String[] draftIds) throws Exception;

    /**
     * 公告草稿-发布
     * @param loginUserInfo 当前登录用户
     * @param draftId       要发布的公告草稿id
     * @param insertFlag
     * @return
     * @throws Exception
     */
    Integer dealPublishByDraft(CurrentLoginEmUserInfo loginUserInfo, String draftId, boolean insertFlag) throws Exception;

}
