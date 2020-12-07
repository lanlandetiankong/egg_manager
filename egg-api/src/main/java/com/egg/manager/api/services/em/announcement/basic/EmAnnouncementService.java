package com.egg.manager.api.services.em.announcement.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.EmAnnouncementEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.EmAnnouncementMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.EmAnnouncementDto;
import com.egg.manager.persistence.em.announcement.pojo.vo.EmAnnouncementDraftVo;
import com.egg.manager.persistence.em.announcement.pojo.vo.EmAnnouncementVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmAnnouncementService extends IService<EmAnnouncementEntity>, MyBaseMysqlService<EmAnnouncementEntity, EmAnnouncementMapper, EmAnnouncementVo> {


    /**
     * 新增公告
     * @param loginUserInfo  当前登录用户
     * @param emAnnouncementVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmAnnouncementVo emAnnouncementVo) throws Exception;

    /**
     * 公告草稿发布
     * @param loginUserInfo       当前登录用户
     * @param emAnnouncementDraftVo
     * @return
     * @throws Exception
     */
    Integer dealCreateFromDraft(CurrentLoginEmUserInfo loginUserInfo, EmAnnouncementDraftVo emAnnouncementDraftVo) throws Exception;

    /**
     * 分页查询 公告 列表
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return
     */
    WebResult dealQueryPageByEntitys(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmAnnouncementDto> queryPageBean);

    /**
     * 分页查询 公告 dto列表
     * (查询的是 com.egg.manager.persistence.obl.article.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmAnnouncementDto> queryPageBean);

}
