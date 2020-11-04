package com.egg.manager.baseservice.services.basic.serviceimpl.announcement;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.announcement.AnnouncementDraftService;
import com.egg.manager.api.services.basic.announcement.AnnouncementTagService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.bean.helper.MyCommonResult;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.Announcement;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementDraft;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTag;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
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
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = AnnouncementDraftService.class)
public class AnnouncementDraftServiceImpl extends MyBaseMysqlServiceImpl<AnnouncementDraftMapper, AnnouncementDraft, AnnouncementDraftVo>
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
    public MyCommonResult<AnnouncementDraftVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<AnnouncementDraftVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementDraftDto> paginationBean,
                                                                   List<AntdvSortBean> sortBeans) {
        //取得 公告标签 map
        Map<Long, AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllToMap();
        Page<AnnouncementDraftDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<AnnouncementDraftDto> announcementDraftDtoList = announcementDraftMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(AnnouncementDraftTransfer.transferDtoToVoList(announcementDraftDtoList, announcementTagMap));
        return result;
    }

    @Override
    public Integer dealCreate(UserAccount loginUser, AnnouncementDraftVo announcementDraftVo) throws Exception {
        AnnouncementDraft entity = AnnouncementDraftTransfer.transferVoToEntity(announcementDraftVo);
        entity = super.doBeforeCreate(loginUser, entity, true);
        Integer addCount = announcementDraftMapper.insert(entity);
        return addCount;
    }

    @Override
    public Integer dealUpdate(UserAccount loginUser, AnnouncementDraftVo announcementDraftVo) throws Exception {
        AnnouncementDraft entity = announcementDraftMapper.selectById(announcementDraftVo.getFid());
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
    public Integer dealBatchPublishByDraft(UserAccount loginUser, Long[] draftIds) throws Exception {
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
    public Integer dealPublishByDraft(UserAccount loginUser, Long draftId, boolean insertFlag) throws Exception {
        AnnouncementDraft announcementDraft = announcementDraftMapper.selectById(draftId);
        Announcement announcement = AnnouncementTransfer.transferFromDraft(loginUser, announcementDraft);
        if (announcement != null && insertFlag == true) {
            //发布
            announcementMapper.insert(announcement);
        }
        announcementDraft.setState(BaseStateEnum.DELETE.getValue());
        announcementDraft.setIsPublished(BaseStateEnum.ENABLED.getValue());
        if (loginUser != null) {
            announcementDraft.setLastModifyerId(loginUser.getFid());
        }
        //修稿 草稿标识
        Integer delCount = announcementDraftMapper.updateById(announcementDraft);
        return delCount;
    }


}
