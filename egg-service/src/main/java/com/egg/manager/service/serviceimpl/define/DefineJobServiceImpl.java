package com.egg.manager.service.serviceimpl.define;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.transfer.define.DefineJobTransfer;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.persistence.dto.define.DefineJobDto;
import com.egg.manager.persistence.entity.define.DefineJob;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mapper.define.DefineJobMapper;
import com.egg.manager.api.service.service.CommonFuncService;
import com.egg.manager.api.service.service.define.DefineJobService;
import com.egg.manager.persistence.vo.define.DefineJobVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service
public class DefineJobServiceImpl extends ServiceImpl<DefineJobMapper,DefineJob> implements DefineJobService {


    @Autowired
    private DefineJobMapper defineJobMapper;
    @Autowired
    private CommonFuncService commonFuncService;

    /**
     * 分页查询 职务定义 列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetDefineJobPages(MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                      List<AntdvSortBean> sortBeans){
        //解析 搜索条件
        EntityWrapper<DefineJob> defineJobEntityWrapper = new EntityWrapper<DefineJob>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到 defineJobEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(defineJobEntityWrapper,queryFormFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                defineJobEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        //取得 总数
        Integer total = defineJobMapper.selectCount(defineJobEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<DefineJob> defineJobs = defineJobMapper.selectPage(rowBounds,defineJobEntityWrapper) ;
        result.setResultList(DefineJobTransfer.transferEntityToVoList(defineJobs));
    }

    /**
     * 分页查询 职务定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetDefineJobDtoPages(MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                      List<AntdvSortBean> sortBeans){
        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<DefineJobDto> defineDepartmentDtoList = defineJobMapper.selectQueryPage(mpPagination, queryFieldBeanList,sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(DefineJobTransfer.transferDtoToVoList(defineDepartmentDtoList));
    }


    /**
     * 职务-新增
     * @param defineJobVo
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealAddDefineJob(DefineJobVo defineJobVo,UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        DefineJob defineJob = DefineJobTransfer.transferVoToEntity(defineJobVo);
        defineJob.setFid(MyUUIDUtil.renderSimpleUUID());
        defineJob.setState(BaseStateEnum.ENABLED.getValue());
        defineJob.setCreateTime(now);
        defineJob.setUpdateTime(now);
        if(loginUser != null){
            defineJob.setCreateUserId(loginUser.getFid());
            defineJob.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = defineJobMapper.insert(defineJob) ;
        return addCount ;
    }


    /**
     * 职务-更新
     * @param defineJobVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealUpdateDefineJob(DefineJobVo defineJobVo,UserAccount loginUser,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        defineJobVo.setUpdateTime(now);
        DefineJob defineJob = DefineJobTransfer.transferVoToEntity(defineJobVo);
        if(loginUser != null){
            defineJob.setLastModifyerId(loginUser.getFid());
        }
        if(updateAll){  //是否更新所有字段
            changeCount = defineJobMapper.updateAllColumnById(defineJob) ;
        }   else {
            changeCount = defineJobMapper.updateById(defineJob) ;
        }
        return changeCount ;
    }



    /**
     * 职务-删除
     * @param delIds 要删除的职务id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelDefineJobByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = defineJobMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 职务-删除
     * @param delId 要删除的职务id
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelDefineJob(String delId,UserAccount loginUser) throws Exception{
        DefineJob defineJob = DefineJob.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            defineJob.setLastModifyerId(loginUser.getFid());
        }
        Integer delCount = defineJobMapper.updateById(defineJob);
        return delCount ;
    }



}
