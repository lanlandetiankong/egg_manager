package com.egg.manager.baseservice.serviceimpl.em.announcement.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.announcement.basic.AnnouncementTagService;
import com.egg.manager.persistence.commons.base.beans.front.FrontEntitySelectBean;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.form.QueryField;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTagEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.mapper.AnnouncementTagMapper;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementTagDto;
import com.egg.manager.persistence.em.announcement.pojo.transfer.AnnouncementTagTransfer;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementTagVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
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
@Service(interfaceClass = AnnouncementTagService.class)
public class AnnouncementTagServiceImpl extends MyBaseMysqlServiceImpl<AnnouncementTagMapper, AnnouncementTagEntity, AnnouncementTagVo>
        implements AnnouncementTagService {

    @Autowired
    private RoutineCommonFunc routineCommonFunc;


    @Autowired
    private AnnouncementTagMapper announcementTagMapper;

    @Override
    public WebResult dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, WebResult result, List<QueryField> queryFieldList, AntdvPage<AnnouncementTagEntity> vpage,
                                            AntdvSortMap sortMap) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(vpage);
        //解析 搜索条件
        QueryWrapper<AnnouncementTagEntity> announcementTagEntityWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryFieldList, vpage, sortMap);
        //取得 总数
        Integer total = announcementTagMapper.selectCount(announcementTagEntityWrapper);
        result.settingPage(vpage, Long.valueOf(total));
        IPage iPage = announcementTagMapper.selectPage(page, announcementTagEntityWrapper);
        List<AnnouncementTagEntity> announcementTagEntities = iPage.getRecords();
        result.putResultList(AnnouncementTagTransfer.transferEntityToVoList(announcementTagEntities));
        return result;
    }

    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, List<QueryField> queryFieldList, AntdvPage<AnnouncementTagDto> vpage,
                                         AntdvSortMap sortMap) {
        Page<AnnouncementTagDto> mpPagination = super.dealAntvPageToPagination(vpage);
        List<AnnouncementTagDto> announcementTagDtoList = announcementTagMapper.selectQueryPage(mpPagination, queryFieldList, sortMap);
        result.settingPage(vpage, mpPagination.getTotal());
        result.putResultList(AnnouncementTagTransfer.transferDtoToVoList(announcementTagDtoList));
        return result;
    }

    @Override
    public Map<String, AnnouncementTagEntity> dealGetAllToMap() {
        Map<String, AnnouncementTagEntity> map = Maps.newHashMap();
        QueryWrapper<AnnouncementTagEntity> announcementTagEntityWrapper = new QueryWrapper<AnnouncementTagEntity>();
        announcementTagEntityWrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        List<AnnouncementTagEntity> announcementTagEntities = announcementTagMapper.selectList(announcementTagEntityWrapper);
        if (CollectionUtil.isNotEmpty(announcementTagEntities)) {
            for (AnnouncementTagEntity tag : announcementTagEntities) {
                map.put(tag.getFid(), tag);
            }
        }
        return map;
    }


    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, AnnouncementTagVo announcementTagVo) throws Exception {
        AnnouncementTagEntity announcementTagEntity = AnnouncementTagTransfer.transferVoToEntity(announcementTagVo);
        super.doBeforeCreate(loginUserInfo, announcementTagEntity);
        return announcementTagMapper.insert(announcementTagEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, AnnouncementTagVo announcementTagVo) throws Exception {
        Integer changeCount = 0;
        AnnouncementTagEntity announcementTagEntity = AnnouncementTagTransfer.transferVoToEntity(announcementTagVo);
        announcementTagEntity = super.doBeforeUpdate(loginUserInfo, announcementTagEntity);
        changeCount = announcementTagMapper.updateById(announcementTagEntity);
        return changeCount;
    }


    @Override
    public WebResult dealResultListToEnums(WebResult result) {
        List<FrontEntitySelectBean> enumList = new ArrayList<>();
        List<AnnouncementTagVo> resultList = result.getResultList();
        if (CollectionUtil.isNotEmpty(resultList)) {
            for (AnnouncementTagVo announcementTagVo : resultList) {
                enumList.add(new FrontEntitySelectBean<String>(announcementTagVo.getFid(), announcementTagVo.getName()));
            }
        }
        result.putEnumList(enumList);
        return result;
    }
}
