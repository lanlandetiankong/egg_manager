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
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
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
    public MyCommonResult<AnnouncementTagVo> dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, MyCommonResult<AnnouncementTagVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementTagEntity> paginationBean,
                                                                    List<AntdvSortBean> sortBeans) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //解析 搜索条件
        QueryWrapper<AnnouncementTagEntity> announcementTagEntityWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryFieldBeanList, paginationBean, sortBeans);
        //取得 总数
        Integer total = announcementTagMapper.selectCount(announcementTagEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = announcementTagMapper.selectPage(page, announcementTagEntityWrapper);
        List<AnnouncementTagEntity> announcementTagEntities = iPage.getRecords();
        result.setResultList(AnnouncementTagTransfer.transferEntityToVoList(announcementTagEntities));
        return result;
    }

    @Override
    public MyCommonResult<AnnouncementTagVo> dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, MyCommonResult<AnnouncementTagVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementTagDto> paginationBean,
                                                                 List<AntdvSortBean> sortBeans) {
        Page<AnnouncementTagDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<AnnouncementTagDto> announcementTagDtoList = announcementTagMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(AnnouncementTagTransfer.transferDtoToVoList(announcementTagDtoList));
        return result;
    }

    @Override
    public Map<Long, AnnouncementTagEntity> dealGetAllToMap() {
        Map<Long, AnnouncementTagEntity> map = Maps.newHashMap();
        QueryWrapper<AnnouncementTagEntity> announcementTagEntityWrapper = new QueryWrapper<AnnouncementTagEntity>();
        announcementTagEntityWrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        List<AnnouncementTagEntity> announcementTagEntities = announcementTagMapper.selectList(announcementTagEntityWrapper);
        if (announcementTagEntities != null && announcementTagEntities.isEmpty() == false) {
            for (AnnouncementTagEntity tag : announcementTagEntities) {
                map.put(tag.getFid(), tag);
            }
        }
        return map;
    }


    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, AnnouncementTagVo announcementTagVo) throws Exception {
        AnnouncementTagEntity announcementTagEntity = AnnouncementTagTransfer.transferVoToEntity(announcementTagVo);
        super.doBeforeCreate(loginUserInfo, announcementTagEntity, true);
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
    public MyCommonResult dealResultListToEnums(MyCommonResult result) {
        List<FrontEntitySelectBean> enumList = new ArrayList<>();
        List<AnnouncementTagVo> resultList = result.getResultList();
        if (CollectionUtil.isNotEmpty(resultList)) {
            for (AnnouncementTagVo announcementTagVo : resultList) {
                enumList.add(new FrontEntitySelectBean<Long>(announcementTagVo.getFid(), announcementTagVo.getName()));
            }
        }
        result.setEnumList(enumList);
        return result;
    }
}
