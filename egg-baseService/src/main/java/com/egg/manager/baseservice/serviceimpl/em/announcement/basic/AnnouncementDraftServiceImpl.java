package com.egg.manager.baseservice.serviceimpl.em.announcement.basic;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementDraftService;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementTagService;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementDraftEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTagEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementDraftMapper;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDraftDto;
import com.egg.manager.persistence.em.announcement.pojo.transfer.AnnouncementDraftTransfer;
import com.egg.manager.persistence.em.announcement.pojo.transfer.AnnouncementTransfer;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementDraftVo;
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
@Service(interfaceClass = AnnouncementDraftService.class)
public class AnnouncementDraftServiceImpl extends MyBaseMysqlServiceImpl<AnnouncementDraftMapper, AnnouncementDraftEntity, AnnouncementDraftVo>
        implements AnnouncementDraftService {

    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private AnnouncementMapper announcementMapper;
    @Autowired
    private AnnouncementDraftMapper announcementDraftMapper;
    @Reference
    private AnnouncementTagService announcementTagService;


    @Override
    public MyCommonResult<AnnouncementDraftVo> dealQueryPageByDtos(UserAccountEntity loginUser, MyCommonResult<AnnouncementDraftVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementDraftDto> paginationBean,
                                                                   List<AntdvSortBean> sortBeans) {
        //取得 公告标签 map
        Map<Long, AnnouncementTagEntity> announcementTagMap = announcementTagService.dealGetAllToMap();
        Page<AnnouncementDraftDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<AnnouncementDraftDto> announcementDraftDtoList = announcementDraftMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(AnnouncementDraftTransfer.transferDtoToVoList(announcementDraftDtoList, announcementTagMap));
        return result;
    }

    @Override
    public Integer dealCreate(UserAccountEntity loginUser, AnnouncementDraftVo announcementDraftVo) throws Exception {
        AnnouncementDraftEntity entity = AnnouncementDraftTransfer.transferVoToEntity(announcementDraftVo);
        entity = super.doBeforeCreate(loginUser, entity, true);
        Integer addCount = announcementDraftMapper.insert(entity);
        return addCount;
    }

    @Override
    public Integer dealUpdate(UserAccountEntity loginUser, AnnouncementDraftVo announcementDraftVo) throws Exception {
        AnnouncementDraftEntity entity = announcementDraftMapper.selectById(announcementDraftVo.getFid());
        entity = super.doBeforeUpdate(loginUser, entity);
        entity.setTitle(announcementDraftVo.getTitle());
        entity.setKeyWord(announcementDraftVo.getKeyWord());
        entity.setPublishDepartment(announcementDraftVo.getPublishDepartment());
        entity.setContent(announcementDraftVo.getContent());
        List<String> tagIds = announcementDraftVo.getTagIds();
        if (tagIds != null && tagIds.size() > 0) {
            entity.setTagIds(JSON.toJSONString(tagIds));
        } else {
            entity.setTagIds("");
        }
        entity.setAccessory(announcementDraftVo.getAccessory());
        return announcementDraftMapper.updateById(entity);
    }

    @Override
    public Integer dealBatchPublishByDraft(UserAccountEntity loginUser, Long[] draftIds) throws Exception {
        Integer delCount = 0;
        if (draftIds != null && draftIds.length > 0) {
            //批量伪删除
            for (Long draftId : draftIds) {
                Integer addCount = this.dealPublishByDraft(loginUser, draftId, true);
                if (addCount != null) {
                    delCount += addCount;
                }
            }
        }
        return delCount;
    }


    @Override
    public Integer dealPublishByDraft(UserAccountEntity loginUser, Long draftId, boolean insertFlag) throws Exception {
        AnnouncementDraftEntity announcementDraftEntity = announcementDraftMapper.selectById(draftId);
        AnnouncementEntity announcementEntity = AnnouncementTransfer.transferFromDraft(loginUser, announcementDraftEntity);
        if (announcementEntity != null && insertFlag == true) {
            //发布
            announcementMapper.insert(announcementEntity);
        }
        announcementDraftEntity.setState(BaseStateEnum.DELETE.getValue());
        announcementDraftEntity.setIsPublished(BaseStateEnum.ENABLED.getValue());
        if (loginUser != null) {
            announcementDraftEntity.setLastModifyerId(loginUser.getFid());
        }
        //修稿 草稿标识
        Integer delCount = announcementDraftMapper.updateById(announcementDraftEntity);
        return delCount;
    }


}