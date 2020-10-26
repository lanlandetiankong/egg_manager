package com.egg.manager.baseservice.services.basic.serviceimpl.announcement;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.announcement.AnnouncementDraftService;
import com.egg.manager.api.services.basic.announcement.AnnouncementService;
import com.egg.manager.api.services.basic.announcement.AnnouncementTagService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
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


    @Override
    public MyCommonResult<AnnouncementVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<AnnouncementVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<Announcement> paginationBean,
                                                                 List<AntdvSortBean> sortBeans) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //解析 搜索条件
        QueryWrapper<Announcement> announcementEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        //取得 总数
        Integer total = announcementMapper.selectCount(announcementEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = announcementMapper.selectPage(page, announcementEntityWrapper);
        List<Announcement> announcements = iPage.getRecords();
        //取得 公告标签 map
        Map<String, AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllToMap();
        result.setResultList(AnnouncementTransfer.transferEntityToVoList(announcements, announcementTagMap));
        return result;
    }

    @Override
    public MyCommonResult<AnnouncementVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<AnnouncementVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementDto> paginationBean,
                                                              List<AntdvSortBean> sortBeans) {
        //取得 公告标签 map
        Map<String, AnnouncementTag> announcementTagMap = announcementTagService.dealGetAllToMap();

        Page<AnnouncementDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<AnnouncementDto> announcementDtoList = announcementMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(AnnouncementTransfer.transferDtoToVoList(announcementDtoList, announcementTagMap));
        return result;
    }

    @Override
    public Integer dealCreate(UserAccount loginUser, AnnouncementVo announcementVo) throws Exception {
        Announcement announcement = AnnouncementTransfer.transferVoToEntity(announcementVo);
        announcement = super.doBeforeCreate(loginUser, announcement, true);
        return announcementMapper.insert(announcement);
    }


    @Override
    public Integer dealCreateFromDraft(UserAccount loginUser, AnnouncementDraftVo announcementDraftVo) throws Exception {
        Date now = new Date();
        //公告草稿id
        String draftId = announcementDraftVo.getFid();
        //发布公告
        Announcement announcement = AnnouncementTransfer.transferFromDraft(loginUser, AnnouncementDraftTransfer.transferVoToEntity(announcementDraftVo));
        announcement.setFid(MyUUIDUtil.renderSimpleUuid());
        announcement.setState(BaseStateEnum.ENABLED.getValue());
        announcement.setCreateTime(now);
        announcement.setUpdateTime(now);
        if (loginUser != null) {
            announcement.setCreateUserId(loginUser.getFid());
            announcement.setLastModifyerId(loginUser.getFid());
        }
        //修改 公告草稿 状态
        announcementDraftService.dealPublishByDraft(loginUser, draftId, false);
        Integer addCount = announcementMapper.insert(announcement);
        return addCount;
    }


    @Override
    public Integer dealBatchDelete(UserAccount loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = announcementMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    @Override
    public Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception {
        return announcementMapper.fakeDeleteById(delId);
    }

}
