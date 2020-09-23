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
@Service(interfaceClass = AnnouncementTagService.class)
public class AnnouncementTagServiceImpl extends MyBaseMysqlServiceImpl<AnnouncementTagMapper, AnnouncementTag, AnnouncementTagVo>
        implements AnnouncementTagService {

    @Autowired
    private RoutineCommonFunc routineCommonFunc;


    @Autowired
    private AnnouncementTagMapper announcementTagMapper;

    /**
     * 分页查询 公告标签 列表
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<AnnouncementTagVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<AnnouncementTagVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
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

    /**
     * 分页查询 公告标签 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<AnnouncementTagVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<AnnouncementTagVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                            List<AntdvSortBean> sortBeans) {
        Page<AnnouncementTagDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<AnnouncementTagDto> announcementTagDtoList = announcementTagMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(AnnouncementTagTransfer.transferDtoToVoList(announcementTagDtoList));
        return result;
    }

    /***
     * 查询可用的 公告标签 并转为map
     * @return
     */
    @Override
    public Map<String, AnnouncementTag> dealGetAllToMap() {
        Map<String, AnnouncementTag> map = new HashMap<String, AnnouncementTag>();
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


    /**
     * 公告标签-新增
     *
     * @param announcementTagVo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealCreate(UserAccount loginUser, AnnouncementTagVo announcementTagVo) throws Exception {
        AnnouncementTag announcementTag = AnnouncementTagTransfer.transferVoToEntity(announcementTagVo);
        super.doBeforeCreate(loginUser, announcementTag, true);
        return announcementTagMapper.insert(announcementTag);
    }


    /**
     * 公告标签-更新
     *
     * @param announcementTagVo
     * @param updateAll         是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealUpdate(UserAccount loginUser, AnnouncementTagVo announcementTagVo, boolean updateAll) throws Exception {
        Integer changeCount = 0;
        AnnouncementTag announcementTag = AnnouncementTagTransfer.transferVoToEntity(announcementTagVo);
        announcementTag = super.doBeforeUpdate(loginUser, announcementTag);
        if (updateAll) {  //是否更新所有字段
            changeCount = announcementTagMapper.updateById(announcementTag);
        } else {
            changeCount = announcementTagMapper.updateById(announcementTag);
        }
        return changeCount;
    }

    /**
     * 公告标签-删除
     *
     * @param delIds 要删除的公告标签id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
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

    /**
     * 公告标签-删除
     *
     * @param delId 要删除的公告标签id
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception {
        AnnouncementTag announcementTag = super.doBeforeDeleteOneById(loginUser, AnnouncementTag.class, delId);
        Integer delCount = announcementTagMapper.updateById(announcementTag);
        return delCount;
    }

    /**
     * 取得的结果 转为 枚举类型
     *
     * @param result
     */
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
