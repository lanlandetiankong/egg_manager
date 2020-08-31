package com.egg.manager.baseService.services.basic.serviceimpl.announcement;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.services.basic.CommonFuncService;
import com.egg.manager.api.services.basic.announcement.AnnouncementDraftService;
import com.egg.manager.api.services.basic.announcement.AnnouncementTagService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementDraftMysqlDto;
import com.egg.manager.persistence.db.mysql.entity.announcement.Announcement;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.mapper.announcement.AnnouncementDraftMapper;
import com.egg.manager.persistence.db.mysql.mapper.announcement.AnnouncementMapper;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementDraftMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementDraftMysqlVo;
import org.apache.ibatis.session.RowBounds;
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
public class AnnouncementDraftServiceImpl extends ServiceImpl<AnnouncementDraftMapper,AnnouncementDraft>
        implements AnnouncementDraftService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc ;

    @Autowired
    private AnnouncementMapper announcementMapper;
    @Autowired
    private AnnouncementDraftMapper announcementDraftMapper;

    @Reference
    private CommonFuncService commonFuncService ;
    @Reference
    private AnnouncementTagService announcementTagService ;


    /**
     * 分页查询 公告草稿
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<AnnouncementDraftMysqlVo> dealGetAnnouncementDraftPages(MyCommonResult<AnnouncementDraftMysqlVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                                  List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        EntityWrapper<AnnouncementDraft> announcementDraftEntityWrapper = new EntityWrapper<AnnouncementDraft>();
        //取得 分页配置
        RowBounds rowBounds = routineCommonFunc.parsePaginationToRowBounds(paginationBean) ;
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
        result.setResultList(AnnouncementDraftMysqlTransfer.transferEntityToVoList(announcementDrafts,announcementTagMap));
        return result ;
    }

    /**
     * 分页查询 公告草稿 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<AnnouncementDraftMysqlVo> dealGetAnnouncementDraftDtoPages(MyCommonResult<AnnouncementDraftMysqlVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                                     List<AntdvSortBean> sortBeans) {
        //取得 公告标签 map
        Map<String,AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllAnnouncementTagToMap();

        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<AnnouncementDraftMysqlDto> announcementDraftDtoList = announcementDraftMapper.selectQueryPage(mpPagination, queryFieldBeanList,sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(AnnouncementDraftMysqlTransfer.transferDtoToVoList(announcementDraftDtoList,announcementTagMap));
        return result ;
    }

    /**
     * 新增公告草稿
     * @param announcementDraftVo
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealAddAnnouncementDraft(AnnouncementDraftMysqlVo announcementDraftVo, UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        AnnouncementDraft announcementDraft = AnnouncementDraftMysqlTransfer.transferVoToEntity(announcementDraftVo);
        announcementDraft.setFid(MyUUIDUtil.renderSimpleUUID());
        announcementDraft.setState(BaseStateEnum.ENABLED.getValue());
        announcementDraft.setCreateTime(now);
        announcementDraft.setUpdateTime(now);
        if(loginUser != null){
            announcementDraft.setCreateUserId(loginUser.getFid());
            announcementDraft.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = announcementDraftMapper.insert(announcementDraft) ;
        return addCount ;
    }

    /**
     * 更新公告草稿
     * @param announcementDraftVo
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealUpdateAnnouncementDraft(AnnouncementDraftMysqlVo announcementDraftVo, UserAccount loginUser) throws Exception{
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
            announcementDraft.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = announcementDraftMapper.updateById(announcementDraft) ;
        return addCount ;
    }






    /**
     * 公告草稿-删除
     * @param delIds 要删除的公告草稿id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
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
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelAnnouncementDraft(String delId,UserAccount loginUser) throws Exception{
        AnnouncementDraft announcementDraft = AnnouncementDraft.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            announcementDraft.setLastModifyerId(loginUser.getFid());
        }
        Integer delCount = announcementDraftMapper.updateById(announcementDraft);
        return delCount ;
    }


    /**
     * 公告草稿-发布
     * @param draftIds 要发布的公告草稿id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
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
    @Transactional(rollbackFor=Exception.class)
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
            announcementDraft.setLastModifyerId(loginUser.getFid());
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
                announcement.setCreateUserId(loginUser.getFid());
                announcement.setLastModifyerId(loginUser.getFid());
            }   else {
                announcement.setCreateUserId(announcementDraft.getCreateUserId());
                announcement.setLastModifyerId(announcementDraft.getLastModifyerId());
            }
        }
        return announcement ;
    }
}
