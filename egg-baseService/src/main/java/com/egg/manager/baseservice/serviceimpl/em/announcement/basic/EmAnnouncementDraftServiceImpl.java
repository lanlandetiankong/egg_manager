package com.egg.manager.baseservice.serviceimpl.em.announcement.basic;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.facade.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.facade.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.facade.api.services.em.announcement.basic.EmAnnouncementDraftService;
import com.egg.manager.facade.api.services.em.announcement.basic.EmAnnouncementTagService;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.facade.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.facade.persistence.em.announcement.db.mysql.entity.EmAnnouncementDraftEntity;
import com.egg.manager.facade.persistence.em.announcement.db.mysql.entity.EmAnnouncementEntity;
import com.egg.manager.facade.persistence.em.announcement.db.mysql.entity.EmAnnouncementTagEntity;
import com.egg.manager.facade.persistence.em.announcement.db.mysql.mapper.EmAnnouncementDraftMapper;
import com.egg.manager.facade.persistence.em.announcement.db.mysql.mapper.EmAnnouncementMapper;
import com.egg.manager.facade.persistence.em.announcement.pojo.dto.EmAnnouncementDraftDto;
import com.egg.manager.facade.persistence.em.announcement.pojo.transfer.EmAnnouncementDraftTransfer;
import com.egg.manager.facade.persistence.em.announcement.pojo.transfer.EmAnnouncementTransfer;
import com.egg.manager.facade.persistence.em.announcement.pojo.vo.EmAnnouncementDraftVo;
import com.egg.manager.facade.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = EmAnnouncementDraftService.class)
public class EmAnnouncementDraftServiceImpl extends MyBaseMysqlServiceImpl<EmAnnouncementDraftMapper, EmAnnouncementDraftEntity, EmAnnouncementDraftVo>
        implements EmAnnouncementDraftService {

    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private EmAnnouncementMapper emAnnouncementMapper;
    @Autowired
    private EmAnnouncementDraftMapper emAnnouncementDraftMapper;
    @Reference
    private EmAnnouncementTagService emAnnouncementTagService;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmAnnouncementDraftDto> queryPageBean) {
        //取得 公告标签 map
        Map<String, EmAnnouncementTagEntity> announcementTagMap = emAnnouncementTagService.dealGetAllToMap();
        Page<EmAnnouncementDraftDto> mpPagination = queryPageBean.toMpPage();
        List<EmAnnouncementDraftDto> emAnnouncementDraftDtoList = emAnnouncementDraftMapper.selectQueryPage(mpPagination, queryPageBean.getQuery(), queryPageBean.getSortMap());
        result.settingPage(queryPageBean, mpPagination);
        result.putGridList(EmAnnouncementDraftTransfer.transferDtoToVoList(emAnnouncementDraftDtoList, announcementTagMap));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmAnnouncementDraftVo emAnnouncementDraftVo) throws Exception {
        EmAnnouncementDraftEntity entity = EmAnnouncementDraftTransfer.transferVoToEntity(emAnnouncementDraftVo);
        entity = super.doBeforeCreate(loginUserInfo, entity);
        Integer addCount = emAnnouncementDraftMapper.insert(entity);
        return addCount;
    }

    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmAnnouncementDraftVo emAnnouncementDraftVo) throws Exception {
        EmAnnouncementDraftEntity entity = emAnnouncementDraftMapper.selectById(emAnnouncementDraftVo.getFid());
        entity = super.doBeforeUpdate(loginUserInfo, entity);
        entity.setTitle(emAnnouncementDraftVo.getTitle());
        entity.setKeyWord(emAnnouncementDraftVo.getKeyWord());
        entity.setPublishDepartment(emAnnouncementDraftVo.getPublishDepartment());
        entity.setContent(emAnnouncementDraftVo.getContent());
        List<String> tagIds = emAnnouncementDraftVo.getTagIds();
        if (tagIds != null && tagIds.size() > 0) {
            entity.setTagIds(JSON.toJSONString(tagIds));
        } else {
            entity.setTagIds("");
        }
        entity.setAccessory(emAnnouncementDraftVo.getAccessory());
        return emAnnouncementDraftMapper.updateById(entity);
    }

    @Override
    public Integer dealBatchPublishByDraft(CurrentLoginEmUserInfo loginUserInfo, String[] draftIds) throws Exception {
        Integer delCount = 0;
        if (draftIds != null && draftIds.length > 0) {
            //批量逻辑删除
            for (String draftId : draftIds) {
                Integer addCount = this.dealPublishByDraft(loginUserInfo, draftId, true);
                if (addCount != null) {
                    delCount += addCount;
                }
            }
        }
        return delCount;
    }


    @Override
    public Integer dealPublishByDraft(CurrentLoginEmUserInfo loginUserInfo, String draftId, boolean insertFlag) throws Exception {
        EmAnnouncementDraftEntity emAnnouncementDraftEntity = emAnnouncementDraftMapper.selectById(draftId);
        EmAnnouncementEntity emAnnouncementEntity = EmAnnouncementTransfer.transferFromDraft(loginUserInfo, emAnnouncementDraftEntity);
        if (emAnnouncementEntity != null && insertFlag == true) {
            //发布
            emAnnouncementMapper.insert(emAnnouncementEntity);
        }
        emAnnouncementDraftEntity.setState(BaseStateEnum.DISABLED.getValue());
        emAnnouncementDraftEntity.setIsPublished(BaseStateEnum.ENABLED.getValue());
        if (loginUserInfo != null) {
            emAnnouncementDraftEntity.setLastModifyerId(loginUserInfo.getFid());
        }
        //修稿 草稿标识
        Integer delCount = emAnnouncementDraftMapper.updateById(emAnnouncementDraftEntity);
        return delCount;
    }


}
