package com.egg.manager.baseService.services.basic.serviceimpl.announcement;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.egg.manager.api.services.basic.announcement.AnnouncementDraftService;
import com.egg.manager.api.services.basic.announcement.AnnouncementService;
import com.egg.manager.api.services.basic.announcement.AnnouncementTagService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseService.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.announcement.Announcement;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.announcement.AnnouncementMapper;
import com.egg.manager.persistence.db.mysql.mapper.announcement.AnnouncementTagMapper;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementDto;
import com.egg.manager.persistence.pojo.mysql.transfer.announcement.AnnouncementDraftTransfer;
import com.egg.manager.persistence.pojo.mysql.transfer.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementDraftVo;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementVo;
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
@Service(interfaceClass = AnnouncementService.class)
public class AnnouncementServiceImpl extends MyBaseMysqlServiceImpl<AnnouncementMapper, Announcement, AnnouncementVo>
        implements AnnouncementService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private AnnouncementMapper announcementMapper;
    @Autowired
    private AnnouncementTagMapper announcementTagMapper;
    @Reference
    private AnnouncementTagService announcementTagService;
    @Autowired
    private AnnouncementDraftService announcementDraftService;


    /**
     * 分页查询 公告
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<AnnouncementVo> dealGetAnnouncementPages(UserAccount loginUser, MyCommonResult<AnnouncementVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                   List<AntdvSortBean> sortBeans) {
        //取得 分页配置
        RowBounds rowBounds = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //解析 搜索条件
        EntityWrapper<Announcement> announcementEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        //取得 总数
        Integer total = announcementMapper.selectCount(announcementEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, total);
        List<Announcement> announcements = announcementMapper.selectPage(rowBounds, announcementEntityWrapper);
        //取得 公告标签 map
        Map<String, AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllAnnouncementTagToMap();
        result.setResultList(AnnouncementTransfer.transferEntityToVoList(announcements, announcementTagMap));
        return result;
    }

    /**
     * 分页查询 公告 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<AnnouncementVo> dealGetAnnouncementDtoPages(UserAccount loginUser, MyCommonResult<AnnouncementVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                      List<AntdvSortBean> sortBeans) {
        //取得 公告标签 map
        Map<String, AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllAnnouncementTagToMap();

        Pagination mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<AnnouncementDto> announcementDtoList = announcementMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(AnnouncementTransfer.transferDtoToVoList(announcementDtoList, announcementTagMap));
        return result;
    }

    /**
     * 新增公告
     *
     * @param announcementVo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealAddAnnouncement(UserAccount loginUser, AnnouncementVo announcementVo) throws Exception {
        Date now = new Date();
        Announcement announcement = AnnouncementTransfer.transferVoToEntity(announcementVo);
        announcement = super.doBeforeCreate(loginUser, announcement, true);
        return announcementMapper.insert(announcement);
    }


    /**
     * 公告草稿发布
     *
     * @param announcementDraftVo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealAddAnnouncementFromDraft(UserAccount loginUser, AnnouncementDraftVo announcementDraftVo) throws Exception {
        Date now = new Date();
        //公告草稿id
        String draftId = announcementDraftVo.getFid();
        //发布公告
        Announcement announcement = announcementDraftService.draftTranslateToAnnouncement(loginUser, AnnouncementDraftTransfer.transferVoToEntity(announcementDraftVo));
        announcement.setFid(MyUUIDUtil.renderSimpleUUID());
        announcement.setState(BaseStateEnum.ENABLED.getValue());
        announcement.setCreateTime(now);
        announcement.setUpdateTime(now);
        if (loginUser != null) {
            announcement.setCreateUserId(loginUser.getFid());
            announcement.setLastModifyerId(loginUser.getFid());
        }
        //修改 公告草稿 状态
        announcementDraftService.dealPublishAnnouncementDraft(loginUser, draftId, false);
        Integer addCount = announcementMapper.insert(announcement);
        return addCount;
    }


    /**
     * 公告-删除
     *
     * @param delIds 要删除的公告id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDelAnnouncementByArr(UserAccount loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = announcementMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    /**
     * 公告-删除
     *
     * @param delId 要删除的公告id
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDelAnnouncement(UserAccount loginUser, String delId) throws Exception {
        Announcement announcement = super.doBeforeDeleteOneById(loginUser, Announcement.class, delId);
        return announcementMapper.updateById(announcement);
    }

}
