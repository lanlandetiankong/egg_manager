package com.egg.manager.baseService.services.basic.serviceimpl.announcement;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.announcement.AnnouncementTagService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseService.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.beans.front.FrontEntitySelectBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.announcement.AnnouncementTagMapper;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.mysql.transfer.announcement.AnnouncementTagTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementTagVo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = AnnouncementTagService.class)
public class AnnouncementTagServiceImpl extends MyBaseMysqlServiceImpl<AnnouncementTagMapper, AnnouncementTag, AnnouncementTagVo>
        implements AnnouncementTagService {

    @Autowired
    private RoutineCommonFunc routineCommonFunc;


    @Autowired
    private AnnouncementTagMapper announcementTagMapper;

    @Override
    public MyCommonResult<AnnouncementTagVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<AnnouncementTagVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementTag> paginationBean,
                                                                         List<AntdvSortBean> sortBeans) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //解析 搜索条件
        QueryWrapper<AnnouncementTag> announcementTagEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        //取得 总数
        Integer total = announcementTagMapper.selectCount(announcementTagEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = announcementTagMapper.selectPage(page, announcementTagEntityWrapper);
        List<AnnouncementTag> announcementTags = iPage.getRecords();
        result.setResultList(AnnouncementTagTransfer.transferEntityToVoList(announcementTags));
        return result;
    }

    @Override
    public MyCommonResult<AnnouncementTagVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<AnnouncementTagVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<AnnouncementTagDto> paginationBean,
                                                                            List<AntdvSortBean> sortBeans) {
        Page<AnnouncementTagDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<AnnouncementTagDto> announcementTagDtoList = announcementTagMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(AnnouncementTagTransfer.transferDtoToVoList(announcementTagDtoList));
        return result;
    }

    @Override
    public Map<String, AnnouncementTag> dealGetAllToMap() {
        Map<String, AnnouncementTag> map = Maps.newHashMap();
        QueryWrapper<AnnouncementTag> announcementTagEntityWrapper = new QueryWrapper<AnnouncementTag>();
        announcementTagEntityWrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        List<AnnouncementTag> announcementTags = announcementTagMapper.selectList(announcementTagEntityWrapper);
        if (announcementTags != null && announcementTags.isEmpty() == false) {
            for (AnnouncementTag tag : announcementTags) {
                map.put(tag.getFid(), tag);
            }
        }
        return map;
    }


    @Override
    public Integer dealCreate(UserAccount loginUser, AnnouncementTagVo announcementTagVo) throws Exception {
        AnnouncementTag announcementTag = AnnouncementTagTransfer.transferVoToEntity(announcementTagVo);
        super.doBeforeCreate(loginUser, announcementTag, true);
        return announcementTagMapper.insert(announcementTag);
    }


    @Override
    public Integer dealUpdate(UserAccount loginUser, AnnouncementTagVo announcementTagVo, boolean updateAll) throws Exception {
        Integer changeCount = 0;
        AnnouncementTag announcementTag = AnnouncementTagTransfer.transferVoToEntity(announcementTagVo);
        announcementTag = super.doBeforeUpdate(loginUser, announcementTag);
        if (updateAll) {
            //更新所有字段
            changeCount = announcementTagMapper.updateById(announcementTag);
        } else {
            changeCount = announcementTagMapper.updateById(announcementTag);
        }
        return changeCount;
    }

    @Override
    public Integer dealBatchDelete(UserAccount loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = announcementTagMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    @Override
    public Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception {
        AnnouncementTag announcementTag = super.doBeforeDeleteOneById(loginUser, AnnouncementTag.class, delId);
        Integer delCount = announcementTagMapper.updateById(announcementTag);
        return delCount;
    }

    @Override
    public MyCommonResult dealResultListToEnums(MyCommonResult result) {
        List<FrontEntitySelectBean> enumList = new ArrayList<>();
        List<AnnouncementTagVo> resultList = result.getResultList();
        if (CollectionUtil.isNotEmpty(resultList)) {
            for (AnnouncementTagVo announcementTagVo : resultList) {
                enumList.add(new FrontEntitySelectBean(announcementTagVo.getFid(), announcementTagVo.getName()));
            }
        }
        result.setEnumList(enumList);
        return result;
    }
}
