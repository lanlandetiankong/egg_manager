package com.egg.manager.serviceimpl.announcement;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.beans.FrontEntitySelectBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.entity.announcement.AnnouncementTag;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.announcement.AnnouncementTagMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.announcement.AnnouncementTagService;
import com.egg.manager.vo.announcement.AnnouncementTagVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */
@Service
public class AnnouncementTagServiceImpl extends ServiceImpl<AnnouncementTagMapper,AnnouncementTag> implements AnnouncementTagService {




    @Autowired
    private AnnouncementTagMapper announcementTagMapper ;
    @Autowired
    private CommonFuncService commonFuncService ;



    /**
     * 分页查询 公告标签
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetAnnouncementTagPages(MyCommonResult<AnnouncementTagVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                             List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        EntityWrapper<AnnouncementTag> announcementTagEntityWrapper = new EntityWrapper<AnnouncementTag>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到 announcementTagEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(announcementTagEntityWrapper,queryFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                announcementTagEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        //取得 总数
        Integer total = announcementTagMapper.selectCount(announcementTagEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<AnnouncementTag> announcementTags = announcementTagMapper.selectPage(rowBounds,announcementTagEntityWrapper) ;
        result.setResultList(AnnouncementTagVo.transferEntityToVoList(announcementTags));
    }

    /***
     * 查询可用的 公告标签 并转为map
     * @return
     */
    @Override
    public Map<String,AnnouncementTag> dealGetAllAnnouncementTagToMap(){
        Map<String,AnnouncementTag> map = new HashMap<String,AnnouncementTag>() ;
        EntityWrapper<AnnouncementTag> announcementTagEntityWrapper = new EntityWrapper<AnnouncementTag>();
        announcementTagEntityWrapper.eq("state",BaseStateEnum.ENABLED.getValue());
        List<AnnouncementTag> announcementTags  = announcementTagMapper.selectList(announcementTagEntityWrapper);
        if(announcementTags != null && announcementTags.isEmpty() == false){
            for (AnnouncementTag tag : announcementTags){
                map.put(tag.getFid(),tag) ;
            }
        }
        return map ;
    }


    /**
     * 公告标签-新增
     * @param announcementTagVo
     * @throws Exception
     */
    @Override
    public Integer dealAddAnnouncementTag(AnnouncementTagVo announcementTagVo,UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        AnnouncementTag announcementTag = AnnouncementTagVo.transferVoToEntity(announcementTagVo);
        announcementTag.setFid(MyUUIDUtil.renderSimpleUUID());
        announcementTag.setState(BaseStateEnum.ENABLED.getValue());
        announcementTag.setCreateTime(now);
        announcementTag.setUpdateTime(now);
        if(loginUser != null){
            announcementTag.setCreateUserId(loginUser.getFid());
            announcementTag.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = announcementTagMapper.insert(announcementTag) ;
        return addCount ;
    }


    /**
     * 公告标签-更新
     * @param announcementTagVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Override
    public Integer dealUpdateAnnouncementTag(AnnouncementTagVo announcementTagVo,UserAccount loginUser,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        announcementTagVo.setUpdateTime(now);
        AnnouncementTag announcementTag = AnnouncementTagVo.transferVoToEntity(announcementTagVo);
        if(loginUser != null){
            announcementTag.setLastModifyerId(loginUser.getFid());
        }
        if(updateAll){  //是否更新所有字段
            changeCount = announcementTagMapper.updateAllColumnById(announcementTag) ;
        }   else {
            changeCount = announcementTagMapper.updateById(announcementTag) ;
        }
        return changeCount ;
    }

    /**
     * 公告标签-删除
     * @param delIds 要删除的公告标签id 集合
     * @throws Exception
     */
    @Override
    public Integer dealDelAnnouncementTagByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = announcementTagMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 公告标签-删除
     * @param delId 要删除的公告标签id
     * @throws Exception
     */
    @Override
    public Integer dealDelAnnouncementTag(String delId,UserAccount loginUser) throws Exception{
        AnnouncementTag announcementTag = AnnouncementTag.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            announcementTag.setLastModifyerId(loginUser.getFid());
        }
        Integer delCount = announcementTagMapper.updateById(announcementTag);
        return delCount ;
    }

    /**
     * 取得的结果 转为 枚举类型
     * @param result
     */
    @Override
    public void dealResultListSetToEntitySelect(MyCommonResult result){
        List<FrontEntitySelectBean> enumList = new ArrayList<>();
        List<AnnouncementTagVo> resultList = result.getResultList() ;
        if(resultList != null && resultList.isEmpty() == false){
            for(AnnouncementTagVo announcementTagVo : resultList){
                enumList.add(new FrontEntitySelectBean(announcementTagVo.getFid(),announcementTagVo.getName())) ;
            }
        }
        result.setEnumList(enumList);
    }
}
