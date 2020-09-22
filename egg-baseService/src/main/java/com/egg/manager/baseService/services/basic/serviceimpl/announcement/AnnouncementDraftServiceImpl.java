package com.egg.manager.baseService.services.basic.serviceimpl.announcement;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.announcement.AnnouncementDraftService;
import com.egg.manager.api.services.basic.announcement.AnnouncementTagService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseService.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.announcement.Announcement;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.announcement.AnnouncementDraftMapper;
import com.egg.manager.persistence.db.mysql.mapper.announcement.AnnouncementMapper;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementDraftDto;
import com.egg.manager.persistence.pojo.mysql.transfer.announcement.AnnouncementDraftTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementDraftVo;
import javafx.scene.control.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */
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


    /**
     * 分页查询 公告草稿 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<AnnouncementDraftVo> dealGetAnnouncementDraftDtoPages(UserAccount loginUser, MyCommonResult<AnnouncementDraftVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                                List<AntdvSortBean> sortBeans) {
        //取得 公告标签 map
        Map<String, AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllAnnouncementTagToMap();
        Page<AnnouncementDraftDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<AnnouncementDraftDto> announcementDraftDtoList = announcementDraftMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(AnnouncementDraftTransfer.transferDtoToVoList(announcementDraftDtoList, announcementTagMap));
        return result;
    }

    /**
     * 新增公告草稿
     *
     * @param announcementDraftVo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealAddAnnouncementDraft(UserAccount loginUser, AnnouncementDraftVo announcementDraftVo) throws Exception {
        AnnouncementDraft entity = AnnouncementDraftTransfer.transferVoToEntity(announcementDraftVo);
        entity = super.doBeforeCreate(loginUser, entity, true);
        Integer addCount = announcementDraftMapper.insert(entity);
        return addCount;
    }

    /**
     * 更新公告草稿
     *
     * @param announcementDraftVo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealUpdateAnnouncementDraft(UserAccount loginUser, AnnouncementDraftVo announcementDraftVo) throws Exception {
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


    /**
     * 公告草稿-删除
     *
     * @param delIds 要删除的公告草稿id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDelAnnouncementDraftByArr(UserAccount loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = announcementDraftMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    /**
     * 公告草稿-删除
     *
     * @param delId 要删除的公告草稿id
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDelAnnouncementDraft(UserAccount loginUser, String delId) throws Exception {
        AnnouncementDraft announcementDraft = super.doBeforeDeleteOneById(loginUser, AnnouncementDraft.class, delId);
        return announcementDraftMapper.updateById(announcementDraft);
    }


    /**
     * 公告草稿-发布
     *
     * @param draftIds 要发布的公告草稿id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealPublishAnnouncementDraftByArr(UserAccount loginUser, String[] draftIds) throws Exception {
        Integer delCount = 0;
        if (draftIds != null && draftIds.length > 0) {
            List<String> delIdList = Arrays.asList(draftIds);
            //批量伪删除
            for (String draftId : draftIds) {
                Integer addCount = this.dealPublishAnnouncementDraft(loginUser, draftId, true);
                if (addCount != null) {
                    delCount += addCount;
                }
            }
        }
        return delCount;
    }


    /**
     * 公告草稿-发布
     *
     * @param draftId 要发布的公告草稿id
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealPublishAnnouncementDraft(UserAccount loginUser, String draftId, boolean insertFlag) throws Exception {
        AnnouncementDraft announcementDraft = announcementDraftMapper.selectById(draftId);
        Announcement announcement = this.draftTranslateToAnnouncement(loginUser, announcementDraft);
        if (announcement != null && insertFlag == true) {       //发布
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


    @Override
    public Announcement draftTranslateToAnnouncement(UserAccount loginUser, AnnouncementDraft announcementDraft) {
        Announcement announcement = null;
        if (announcementDraft != null) {
            Date now = new Date();
            announcement = new Announcement();
            announcement.setFid(announcementDraft.getFid());
            announcement.setTitle(announcementDraft.getTitle());
            announcement.setKeyWord(announcementDraft.getKeyWord());
            announcement.setPublishDepartment(announcementDraft.getPublishDepartment());
            announcement.setContent(announcementDraft.getContent());
            announcement.setTagIds(announcementDraft.getTagIds());
            announcement.setAccessory(announcementDraft.getAccessory());
            announcement.setState(announcementDraft.getState());
            announcement.setRemark(announcementDraft.getRemark());
            announcement.setCreateTime(now);
            announcement.setUpdateTime(now);
            if (loginUser != null) {
                announcement.setCreateUserId(loginUser.getFid());
                announcement.setLastModifyerId(loginUser.getFid());
            } else {
                announcement.setCreateUserId(announcementDraft.getCreateUserId());
                announcement.setLastModifyerId(announcementDraft.getLastModifyerId());
            }
        }
        return announcement;
    }
}
