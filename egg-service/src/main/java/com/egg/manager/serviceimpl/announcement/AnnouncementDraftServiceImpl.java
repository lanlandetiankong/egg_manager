package com.egg.manager.serviceimpl.announcement;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.entity.announcement.Announcement;
import com.egg.manager.entity.announcement.AnnouncementDraft;
import com.egg.manager.entity.announcement.AnnouncementTag;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.announcement.AnnouncementDraftMapper;
import com.egg.manager.mapper.announcement.AnnouncementMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.announcement.AnnouncementDraftService;
import com.egg.manager.service.announcement.AnnouncementTagService;
import com.egg.manager.vo.announcement.AnnouncementDraftVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class AnnouncementDraftServiceImpl extends ServiceImpl<AnnouncementDraftMapper,AnnouncementDraft> implements AnnouncementDraftService {

    @Autowired
    private AnnouncementMapper announcementMapper;
    @Autowired
    private AnnouncementDraftMapper announcementDraftMapper;

    @Autowired
    private CommonFuncService commonFuncService ;
    @Autowired
    private AnnouncementTagService announcementTagService ;

    /**
     * 新增公告草稿
     * @param announcementDraftVo
     * @throws Exception
     */
    @Override
    public Integer dealAddAnnouncementDraft(AnnouncementDraftVo announcementDraftVo, UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        AnnouncementDraft announcementDraft = AnnouncementDraftVo.transferVoToEntity(announcementDraftVo);
        announcementDraft.setFid(MyUUIDUtil.renderSimpleUUID());
        announcementDraft.setState(BaseStateEnum.ENABLED.getValue());
        announcementDraft.setCreateTime(now);
        announcementDraft.setUpdateTime(now);
        if(loginUser != null){
            announcementDraft.setCreateUser(loginUser.getFid());
            announcementDraft.setLastModifyer(loginUser.getFid());
        }
        Integer addCount = announcementDraftMapper.insert(announcementDraft) ;
        return addCount ;
    }

    /**
     * 更新公告草稿
     * @param announcementDraftVo
     * @throws Exception
     */
    @Override
    public Integer dealUpdateAnnouncementDraft(AnnouncementDraftVo announcementDraftVo, UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        AnnouncementDraft announcementDraft = announcementDraftMapper.selectById(announcementDraftVo.getFid());
        announcementDraft.setTitle(announcementDraftVo.getTitle());
        announcementDraft.setKeyWord(announcementDraftVo.getKeyWord());
        announcementDraft.setPublishDepartment(announcementDraftVo.getPublishDepartment());
        announcementDraft.setContent(announcementDraftVo.getContent());
        List<String> tagIds = announcementDraftVo.getTagIds();
        if(tagIds != null && tagIds.size() > 0){
            announcementDraft.setTagIds(JSON.toJSONString(tagIds));
        }   else {
            announcementDraft.setTagIds("");
        }
        announcementDraft.setAccessory(announcementDraftVo.getAccessory());

        announcementDraft.setUpdateTime(now);
        if(loginUser != null){
            announcementDraft.setLastModifyer(loginUser.getFid());
        }
        Integer addCount = announcementDraftMapper.updateById(announcementDraft) ;
        return addCount ;
    }


    /**
     * 分页查询 公告草稿
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetAnnouncementDraftPages(MyCommonResult<AnnouncementDraftVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                         List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        EntityWrapper<AnnouncementDraft> announcementDraftEntityWrapper = new EntityWrapper<AnnouncementDraft>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到 announcementDraftEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(announcementDraftEntityWrapper,queryFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                announcementDraftEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        //取得 总数
        Integer total = announcementDraftMapper.selectCount(announcementDraftEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<AnnouncementDraft> announcementDrafts = announcementDraftMapper.selectPage(rowBounds,announcementDraftEntityWrapper) ;
        //取得 公告标签 map
        Map<String,AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllAnnouncementTagToMap();
        result.setResultList(AnnouncementDraftVo.transferEntityToVoList(announcementDrafts,announcementTagMap));
    }



    /**
     * 公告草稿-删除
     * @param delIds 要删除的公告草稿id 集合
     * @throws Exception
     */
    @Override
    public Integer dealDelAnnouncementDraftByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = announcementDraftMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 公告草稿-删除
     * @param delId 要删除的公告草稿id
     * @throws Exception
     */
    @Override
    public Integer dealDelAnnouncementDraft(String delId,UserAccount loginUser) throws Exception{
        AnnouncementDraft announcementDraft = AnnouncementDraft.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            announcementDraft.setLastModifyer(loginUser.getFid());
        }
        Integer delCount = announcementDraftMapper.updateById(announcementDraft);
        return delCount ;
    }


    /**
     * 公告草稿-发布
     * @param draftIds 要发布的公告草稿id 集合
     * @throws Exception
     */
    @Override
    public Integer dealPublishAnnouncementDraftByArr(String[] draftIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(draftIds != null && draftIds.length > 0) {
            List<String> delIdList = Arrays.asList(draftIds) ;
            //批量伪删除
            for(String draftId : draftIds){
                Integer addCount = this.dealPublishAnnouncementDraft(draftId,loginUser,true) ;
                if(addCount != null){
                    delCount += addCount ;
                }
            }
        }
        return delCount ;
    }


    /**
     * 公告草稿-发布
     * @param draftId 要发布的公告草稿id
     * @throws Exception
     */
    @Override
    public Integer dealPublishAnnouncementDraft(String draftId,UserAccount loginUser,boolean insertFlag) throws Exception{
        AnnouncementDraft announcementDraft = announcementDraftMapper.selectById(draftId);
        Announcement announcement = this.draftTranslateToAnnouncement(announcementDraft,loginUser);
        if(announcement != null && insertFlag == true){       //发布
            announcementMapper.insert(announcement);
        }
        announcementDraft.setState(BaseStateEnum.DELETE.getValue());
        announcementDraft.setIsPublished(BaseStateEnum.ENABLED.getValue());
        if(loginUser != null){
            announcementDraft.setLastModifyer(loginUser.getFid());
        }
        //修稿 草稿标识
        Integer delCount = announcementDraftMapper.updateById(announcementDraft);
        return delCount ;
    }


    @Override
    public Announcement draftTranslateToAnnouncement(AnnouncementDraft announcementDraft,UserAccount loginUser){
        Announcement announcement = null;
        if(announcementDraft != null){
            Date now = new Date() ;
            announcement = new Announcement() ;
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
            if(loginUser != null){
                announcement.setCreateUser(loginUser.getFid());
                announcement.setLastModifyer(loginUser.getFid());
            }   else {
                announcement.setCreateUser(announcementDraft.getCreateUser());
                announcement.setLastModifyer(announcementDraft.getLastModifyer());
            }
        }
        return announcement ;
    }
}
