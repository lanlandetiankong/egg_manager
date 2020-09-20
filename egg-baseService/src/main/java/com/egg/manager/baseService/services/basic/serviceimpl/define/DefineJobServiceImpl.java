package com.egg.manager.baseService.services.basic.serviceimpl.define;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.services.basic.CommonFuncService;
import com.egg.manager.api.services.basic.define.DefineJobService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseService.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineJobMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineJobDto;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefineJobTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineJobVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service(interfaceClass = DefineJobService.class)
public class DefineJobServiceImpl extends MyBaseMysqlServiceImpl<DefineJobMapper,DefineJob,DefineJobVo> implements DefineJobService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc ;

    @Autowired
    private DefineJobMapper defineJobMapper;
    @Reference
    private CommonFuncService commonFuncService;

    /**
     * 分页查询 职务定义 列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<DefineJobVo> dealGetDefineJobPages(UserAccount loginUser,MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                                             List<AntdvSortBean> sortBeans){
        //取得 分页配置
        RowBounds rowBounds = routineCommonFunc.parsePaginationToRowBounds(paginationBean) ;
        //解析 搜索条件
        EntityWrapper<DefineJob> defineJobEntityWrapper = super.doGetPageQueryWrapper(loginUser,result,queryFormFieldBeanList,paginationBean,sortBeans);
        //取得 总数
        Integer total = defineJobMapper.selectCount(defineJobEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<DefineJob> defineJobs = defineJobMapper.selectPage(rowBounds,defineJobEntityWrapper) ;
        result.setResultList(DefineJobTransfer.transferEntityToVoList(defineJobs));
        return result ;
    }

    /**
     * 分页查询 职务定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<DefineJobVo> dealGetDefineJobDtoPages(UserAccount loginUser,MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                List<AntdvSortBean> sortBeans){
        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<DefineJobDto> defineDepartmentDtoList = defineJobMapper.selectQueryPage(mpPagination, queryFieldBeanList,sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(DefineJobTransfer.transferDtoToVoList(defineDepartmentDtoList));
        return result ;
    }


    /**
     * 职务-新增
     * @param defineJobVo
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealAddDefineJob(UserAccount loginUser,DefineJobVo defineJobVo) throws Exception{
        DefineJob defineJob = DefineJobTransfer.transferVoToEntity(defineJobVo);
        defineJob = super.doBeforeCreate(loginUser,defineJob,true);
        return defineJobMapper.insert(defineJob) ;
    }


    /**
     * 职务-更新
     * @param defineJobVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealUpdateDefineJob(UserAccount loginUser,DefineJobVo defineJobVo,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        DefineJob defineJob = DefineJobTransfer.transferVoToEntity(defineJobVo);
        defineJob = super.doBeforeUpdate(loginUser,defineJob);
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
    public Integer dealDelDefineJobByArr(UserAccount loginUser,String[] delIds) throws Exception{
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
    public Integer dealDelDefineJob(UserAccount loginUser,String delId) throws Exception{
        DefineJob defineJob = super.doBeforeDeleteOneById(loginUser,DefineJob.class,delId);
        return defineJobMapper.updateById(defineJob);
    }



}
