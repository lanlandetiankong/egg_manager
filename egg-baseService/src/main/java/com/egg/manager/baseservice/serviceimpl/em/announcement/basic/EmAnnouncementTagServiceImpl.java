package com.egg.manager.baseservice.serviceimpl.em.announcement.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.facade.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.facade.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.facade.api.services.em.announcement.basic.EmAnnouncementTagService;
import com.egg.manager.facade.persistence.commons.base.beans.front.FrontSelectBean;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.facade.persistence.commons.base.query.FieldConst;
import com.egg.manager.facade.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.facade.persistence.em.announcement.db.mysql.entity.EmAnnouncementTagEntity;
import com.egg.manager.facade.persistence.em.announcement.db.mysql.mapper.EmAnnouncementTagMapper;
import com.egg.manager.facade.persistence.em.announcement.pojo.dto.EmAnnouncementTagDto;
import com.egg.manager.facade.persistence.em.announcement.pojo.transfer.EmAnnouncementTagTransfer;
import com.egg.manager.facade.persistence.em.announcement.pojo.vo.EmAnnouncementTagVo;
import com.egg.manager.facade.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = EmAnnouncementTagService.class)
public class EmAnnouncementTagServiceImpl extends MyBaseMysqlServiceImpl<EmAnnouncementTagMapper, EmAnnouncementTagEntity, EmAnnouncementTagVo>
        implements EmAnnouncementTagService {

    @Autowired
    private RoutineCommonFunc routineCommonFunc;


    @Autowired
    private EmAnnouncementTagMapper emAnnouncementTagMapper;

    @Override
    public WebResult dealQueryPageByEntitys(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean queryPageBean) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(queryPageBean.getPageConf());
        //解析 搜索条件
        QueryWrapper<EmAnnouncementTagEntity> announcementTagEntityWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryPageBean);
        //取得 总数
        Integer total = emAnnouncementTagMapper.selectCount(announcementTagEntityWrapper);
        result.settingPage(queryPageBean.getPageConf(), Long.valueOf(total));
        IPage iPage = emAnnouncementTagMapper.selectPage(page, announcementTagEntityWrapper);
        List<EmAnnouncementTagEntity> announcementTagEntities = iPage.getRecords();
        result.putGridList(EmAnnouncementTagTransfer.transferEntityToVoList(announcementTagEntities));
        return result;
    }

    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmAnnouncementTagDto> queryPage) {
        Page<EmAnnouncementTagDto> mpPagination = queryPage.toMpPage();
        List<EmAnnouncementTagDto> emAnnouncementTagDtoList = emAnnouncementTagMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage, mpPagination);
        result.putGridList(EmAnnouncementTagTransfer.transferDtoToVoList(emAnnouncementTagDtoList));
        return result;
    }

    @Override
    public Map<String, EmAnnouncementTagEntity> dealGetAllToMap() {
        Map<String, EmAnnouncementTagEntity> map = Maps.newHashMap();
        QueryWrapper<EmAnnouncementTagEntity> announcementTagEntityWrapper = new QueryWrapper<EmAnnouncementTagEntity>();
        announcementTagEntityWrapper.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        List<EmAnnouncementTagEntity> announcementTagEntities = emAnnouncementTagMapper.selectList(announcementTagEntityWrapper);
        if (CollectionUtil.isNotEmpty(announcementTagEntities)) {
            for (EmAnnouncementTagEntity tag : announcementTagEntities) {
                map.put(tag.getFid(), tag);
            }
        }
        return map;
    }


    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmAnnouncementTagVo emAnnouncementTagVo) throws Exception {
        EmAnnouncementTagEntity emAnnouncementTagEntity = EmAnnouncementTagTransfer.transferVoToEntity(emAnnouncementTagVo);
        super.doBeforeCreate(loginUserInfo, emAnnouncementTagEntity);
        return emAnnouncementTagMapper.insert(emAnnouncementTagEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmAnnouncementTagVo emAnnouncementTagVo) throws Exception {
        Integer changeCount = 0;
        EmAnnouncementTagEntity emAnnouncementTagEntity = EmAnnouncementTagTransfer.transferVoToEntity(emAnnouncementTagVo);
        emAnnouncementTagEntity = super.doBeforeUpdate(loginUserInfo, emAnnouncementTagEntity);
        changeCount = emAnnouncementTagMapper.updateById(emAnnouncementTagEntity);
        return changeCount;
    }


    @Override
    public WebResult dealResultListToEnums(WebResult result) {
        List<FrontSelectBean> enumList = new ArrayList<>();
        List<EmAnnouncementTagVo> resultList = result.getGridList();
        if (CollectionUtil.isNotEmpty(resultList)) {
            for (EmAnnouncementTagVo emAnnouncementTagVo : resultList) {
                enumList.add(new FrontSelectBean<String>(emAnnouncementTagVo.getFid(), emAnnouncementTagVo.getName()));
            }
        }
        result.putEnumData(enumList);
        return result;
    }
}
