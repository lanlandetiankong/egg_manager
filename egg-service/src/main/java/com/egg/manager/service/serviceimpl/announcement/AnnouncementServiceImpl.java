package com.egg.manager.service.serviceimpl.announcement;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.transfer.announcement.AnnouncementDraftTransfer;
import com.egg.manager.persistence.transfer.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.persistence.dto.announcement.AnnouncementDto;
import com.egg.manager.persistence.entity.announcement.Announcement;
import com.egg.manager.persistence.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mapper.announcement.AnnouncementMapper;
import com.egg.manager.persistence.mapper.announcement.AnnouncementTagMapper;
import com.egg.manager.service.service.CommonFuncService;
import com.egg.manager.service.service.announcement.AnnouncementDraftService;
import com.egg.manager.service.service.announcement.AnnouncementService;
import com.egg.manager.service.service.announcement.AnnouncementTagService;
import com.egg.manager.persistence.vo.announcement.AnnouncementDraftVo;
import com.egg.manager.persistence.vo.announcement.AnnouncementVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper,Announcement> implements AnnouncementService {


    @Autowired
    private AnnouncementMapper announcementMapper ;
    @Autowired
    private AnnouncementTagMapper announcementTagMapper ;
    @Autowired
    private CommonFuncService commonFuncService ;
    @Autowired
    private AnnouncementTagService announcementTagService ;
    @Autowired
    private AnnouncementDraftService announcementDraftService ;


    /**
     * 分页查询 公告
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetAnnouncementPages(MyCommonResult<AnnouncementVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                         List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        EntityWrapper<Announcement> announcementEntityWrapper = new EntityWrapper<Announcement>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到 announcementEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(announcementEntityWrapper,queryFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                announcementEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        //取得 总数
        Integer total = announcementMapper.selectCount(announcementEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<Announcement> announcements = announcementMapper.selectPage(rowBounds,announcementEntityWrapper) ;
        //取得 公告标签 map
        Map<String,AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllAnnouncementTagToMap();
        result.setResultList(AnnouncementTransfer.transferEntityToVoList(announcements,announcementTagMap));
    }

    /**
     * 分页查询 公告 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetAnnouncementDtoPages(MyCommonResult<AnnouncementVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                            List<AntdvSortBean> sortBeans) {
        //取得 公告标签 map
        Map<String,AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllAnnouncementTagToMap();

        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<AnnouncementDto> announcementDtoList = announcementMapper.selectQueryPage(mpPagination, queryFieldBeanList,sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(AnnouncementTransfer.transferDtoToVoList(announcementDtoList,announcementTagMap));
    }

    /**
     * 新增公告
     * @param announcementVo
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealAddAnnouncement(AnnouncementVo announcementVo, UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        Announcement announcement = AnnouncementTransfer.transferVoToEntity(announcementVo);
        announcement.setFid(MyUUIDUtil.renderSimpleUUID());
        announcement.setState(BaseStateEnum.ENABLED.getValue());
        announcement.setCreateTime(now);
        announcement.setUpdateTime(now);
        if(loginUser != null){
            announcement.setCreateUserId(loginUser.getFid());
            announcement.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = announcementMapper.insert(announcement) ;
        return addCount ;
    }


    /**
     * 公告草稿发布
     * @param announcementDraftVo
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealAddAnnouncementFromDraft(AnnouncementDraftVo announcementDraftVo, UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        //公告草稿id
        String draftId = announcementDraftVo.getFid();
        //发布公告
        Announcement announcement = announcementDraftService.draftTranslateToAnnouncement(AnnouncementDraftTransfer.transferVoToEntity(announcementDraftVo),loginUser);
        announcement.setFid(MyUUIDUtil.renderSimpleUUID());
        announcement.setState(BaseStateEnum.ENABLED.getValue());
        announcement.setCreateTime(now);
        announcement.setUpdateTime(now);
        if(loginUser != null){
            announcement.setCreateUserId(loginUser.getFid());
            announcement.setLastModifyerId(loginUser.getFid());
        }
        //修改 公告草稿 状态
        announcementDraftService.dealPublishAnnouncementDraft(draftId,loginUser,false);
        Integer addCount = announcementMapper.insert(announcement) ;
        return addCount ;
    }





    /**
     * 公告-删除
     * @param delIds 要删除的公告id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelAnnouncementByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = announcementMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 公告-删除
     * @param delId 要删除的公告id
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelAnnouncement(String delId,UserAccount loginUser) throws Exception{
        Announcement announcement = Announcement.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            announcement.setLastModifyerId(loginUser.getFid());
        }
        Integer delCount = announcementMapper.updateById(announcement);
        return delCount ;
    }

}
