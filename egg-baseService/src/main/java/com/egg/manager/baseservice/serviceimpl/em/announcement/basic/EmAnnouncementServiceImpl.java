package com.egg.manager.baseservice.serviceimpl.em.announcement.basic;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.announcement.basic.EmAnnouncementDraftService;
import com.egg.manager.api.services.em.announcement.basic.EmAnnouncementService;
import com.egg.manager.api.services.em.announcement.basic.EmAnnouncementTagService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.EmAnnouncementEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.EmAnnouncementTagEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.EmAnnouncementMapper;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.EmAnnouncementTagMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.EmAnnouncementDto;
import com.egg.manager.persistence.em.announcement.pojo.transfer.EmAnnouncementDraftTransfer;
import com.egg.manager.persistence.em.announcement.pojo.transfer.EmAnnouncementTransfer;
import com.egg.manager.persistence.em.announcement.pojo.vo.EmAnnouncementDraftVo;
import com.egg.manager.persistence.em.announcement.pojo.vo.EmAnnouncementVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
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
@Service(interfaceClass = EmAnnouncementService.class)
public class EmAnnouncementServiceImpl extends MyBaseMysqlServiceImpl<EmAnnouncementMapper, EmAnnouncementEntity, EmAnnouncementVo>
        implements EmAnnouncementService {

    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private EmAnnouncementMapper emAnnouncementMapper;
    @Autowired
    private EmAnnouncementTagMapper emAnnouncementTagMapper;
    @Reference
    private EmAnnouncementTagService emAnnouncementTagService;
    @Autowired
    private EmAnnouncementDraftService emAnnouncementDraftService;


    @Override
    public WebResult dealQueryPageByEntitys(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmAnnouncementDto> queryPageBean) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(queryPageBean.getPageConf());
        //解析 搜索条件
        QueryWrapper<EmAnnouncementEntity> announcementEntityWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryPageBean);
        //取得 总数
        Integer total = emAnnouncementMapper.selectCount(announcementEntityWrapper);
        result.settingPage(queryPageBean.getPageConf(), Long.valueOf(total));
        IPage iPage = emAnnouncementMapper.selectPage(page, announcementEntityWrapper);
        List<EmAnnouncementEntity> announcementEntities = iPage.getRecords();
        //取得 公告标签 map
        Map<String, EmAnnouncementTagEntity> announcementTagMap = emAnnouncementTagService.dealGetAllToMap();
        result.putGridList(EmAnnouncementTransfer.transferEntityToVoList(announcementEntities, announcementTagMap));
        return result;
    }

    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmAnnouncementDto> queryPageBean) {
        //取得 公告标签 map
        Map<String, EmAnnouncementTagEntity> announcementTagMap = emAnnouncementTagService.dealGetAllToMap();

        Page<EmAnnouncementDto> mpPagination = queryPageBean.toMpPage();
        List<EmAnnouncementDto> emAnnouncementDtoList = emAnnouncementMapper.selectQueryPage(mpPagination, queryPageBean.getQuery(), queryPageBean.getSortMap());
        result.settingPage(queryPageBean,mpPagination);
        result.putGridList(EmAnnouncementTransfer.transferDtoToVoList(emAnnouncementDtoList, announcementTagMap));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmAnnouncementVo emAnnouncementVo) throws Exception {
        EmAnnouncementEntity emAnnouncementEntity = EmAnnouncementTransfer.transferVoToEntity(emAnnouncementVo);
        emAnnouncementEntity = super.doBeforeCreate(loginUserInfo, emAnnouncementEntity);
        return emAnnouncementMapper.insert(emAnnouncementEntity);
    }


    @Override
    public Integer dealCreateFromDraft(CurrentLoginEmUserInfo loginUserInfo, EmAnnouncementDraftVo emAnnouncementDraftVo) throws Exception {
        Date now = new Date();
        //公告草稿id
        String draftId = emAnnouncementDraftVo.getFid();
        //发布公告
        EmAnnouncementEntity emAnnouncementEntity = EmAnnouncementTransfer.transferFromDraft(loginUserInfo, EmAnnouncementDraftTransfer.transferVoToEntity(emAnnouncementDraftVo));
        emAnnouncementEntity.setState(BaseStateEnum.ENABLED.getValue());
        emAnnouncementEntity.setCreateTime(now);
        emAnnouncementEntity.setUpdateTime(now);
        if (loginUserInfo != null) {
            emAnnouncementEntity.setCreateUserId(loginUserInfo.getFid());
            emAnnouncementEntity.setLastModifyerId(loginUserInfo.getFid());
        }
        //修改 公告草稿 状态
        emAnnouncementDraftService.dealPublishByDraft(loginUserInfo, draftId, false);
        Integer addCount = emAnnouncementMapper.insert(emAnnouncementEntity);
        return addCount;
    }

}
