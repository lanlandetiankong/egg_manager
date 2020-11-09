package com.egg.manager.baseservice.serviceimpl.em.announcement.basic;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementDraftService;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementService;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementTagService;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTagEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementMapper;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementTagMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDto;
import com.egg.manager.persistence.em.announcement.pojo.transfer.AnnouncementDraftTransfer;
import com.egg.manager.persistence.em.announcement.pojo.transfer.AnnouncementTransfer;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementDraftVo;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = AnnouncementService.class)
public class AnnouncementServiceImpl extends MyBaseMysqlServiceImpl<AnnouncementMapper, AnnouncementEntity, AnnouncementVo>
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
    public MyCommonResult<AnnouncementVo> dealQueryPageByEntitys(UserAccountEntity loginUser, MyCommonResult<AnnouncementVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementEntity> paginationBean,
                                                                 List<AntdvSortBean> sortBeans) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //解析 搜索条件
        QueryWrapper<AnnouncementEntity> announcementEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        //取得 总数
        Integer total = announcementMapper.selectCount(announcementEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = announcementMapper.selectPage(page, announcementEntityWrapper);
        List<AnnouncementEntity> announcementEntities = iPage.getRecords();
        //取得 公告标签 map
        Map<Long, AnnouncementTagEntity> announcementTagMap = announcementTagService.dealGetAllToMap();
        result.setResultList(AnnouncementTransfer.transferEntityToVoList(announcementEntities, announcementTagMap));
        return result;
    }

    @Override
    public MyCommonResult<AnnouncementVo> dealQueryPageByDtos(UserAccountEntity loginUser, MyCommonResult<AnnouncementVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementDto> paginationBean,
                                                              List<AntdvSortBean> sortBeans) {
        //取得 公告标签 map
        Map<Long, AnnouncementTagEntity> announcementTagMap = announcementTagService.dealGetAllToMap();

        Page<AnnouncementDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<AnnouncementDto> announcementDtoList = announcementMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(AnnouncementTransfer.transferDtoToVoList(announcementDtoList, announcementTagMap));
        return result;
    }

    @Override
    public Integer dealCreate(UserAccountEntity loginUser, AnnouncementVo announcementVo) throws Exception {
        AnnouncementEntity announcementEntity = AnnouncementTransfer.transferVoToEntity(announcementVo);
        announcementEntity = super.doBeforeCreate(loginUser, announcementEntity, true);
        return announcementMapper.insert(announcementEntity);
    }


    @Override
    public Integer dealCreateFromDraft(UserAccountEntity loginUser, AnnouncementDraftVo announcementDraftVo) throws Exception {
        Date now = new Date();
        //公告草稿id
        Long draftId = announcementDraftVo.getFid();
        //发布公告
        AnnouncementEntity announcementEntity = AnnouncementTransfer.transferFromDraft(loginUser, AnnouncementDraftTransfer.transferVoToEntity(announcementDraftVo));
        //id->announcement.setFid(MyUUIDUtil.renderSimpleUuid());
        announcementEntity.setState(BaseStateEnum.ENABLED.getValue());
        announcementEntity.setCreateTime(now);
        announcementEntity.setUpdateTime(now);
        if (loginUser != null) {
            announcementEntity.setCreateUserId(loginUser.getFid());
            announcementEntity.setLastModifyerId(loginUser.getFid());
        }
        //修改 公告草稿 状态
        announcementDraftService.dealPublishByDraft(loginUser, draftId, false);
        Integer addCount = announcementMapper.insert(announcementEntity);
        return addCount;
    }

}
