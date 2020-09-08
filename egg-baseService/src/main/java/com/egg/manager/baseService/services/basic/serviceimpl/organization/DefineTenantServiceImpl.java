package com.egg.manager.baseService.services.basic.serviceimpl.organization;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.services.basic.CommonFuncService;
import com.egg.manager.api.services.basic.organization.DefineTenantService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.common.base.beans.front.FrontEntitySelectBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.organization.DefineTenantMapper;
import com.egg.manager.persistence.pojo.mysql.dto.organization.DefineTenantDto;
import com.egg.manager.persistence.pojo.mysql.transfer.organization.DefineTenantTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.organization.DefineTenantVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
@Service(interfaceClass = DefineTenantService.class)
public class DefineTenantServiceImpl extends ServiceImpl<DefineTenantMapper,DefineTenant> implements DefineTenantService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc ;

    @Autowired
    private DefineTenantMapper defineTenantMapper ;
    @Reference
    private CommonFuncService commonFuncService ;



    /**
     * 分页查询 租户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<DefineTenantVo> dealGetDefineTenantPages(MyCommonResult<DefineTenantVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                   List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        EntityWrapper<DefineTenant> defineTenantEntityWrapper = new EntityWrapper<DefineTenant>();
        //取得 分页配置
        RowBounds rowBounds = routineCommonFunc.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到 defineTenantEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(defineTenantEntityWrapper,queryFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                defineTenantEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        //取得 总数
        Integer total = defineTenantMapper.selectCount(defineTenantEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<DefineTenant> defineTenants = defineTenantMapper.selectPage(rowBounds,defineTenantEntityWrapper) ;
        result.setResultList(DefineTenantTransfer.transferEntityToVoList(defineTenants));
        return result ;
    }


    /**
     * 分页查询 租户
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<DefineTenantVo> dealGetDefineTenantDtoPages(MyCommonResult<DefineTenantVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                      List<AntdvSortBean> sortBeans) {
        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<DefineTenantDto> defineTenantDtoList = defineTenantMapper.selectQueryPage(mpPagination, queryFieldBeanList,sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(DefineTenantTransfer.transferDtoToVoList(defineTenantDtoList));
        return result ;
    }




    /**
     * 租户定义-新增
     * @param defineTenantVo
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealAddDefineTenant(DefineTenantVo defineTenantVo, UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        DefineTenant defineTenant = DefineTenantTransfer.transferVoToEntity(defineTenantVo);
        defineTenant.setFid(MyUUIDUtil.renderSimpleUUID());
        defineTenant.setState(BaseStateEnum.ENABLED.getValue());
        defineTenant.setCreateTime(now);
        defineTenant.setUpdateTime(now);
        if(loginUser != null){
            defineTenant.setCreateUserId(loginUser.getFid());
            defineTenant.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = defineTenantMapper.insert(defineTenant) ;
        return addCount ;
    }


    /**
     * 租户定义-更新
     * @param defineTenantVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealUpdateDefineTenant(DefineTenantVo defineTenantVo, UserAccount loginUser, boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        defineTenantVo.setUpdateTime(now);
        DefineTenant defineTenant = DefineTenantTransfer.transferVoToEntity(defineTenantVo);
        if(loginUser != null){
            defineTenant.setLastModifyerId(loginUser.getFid());
        }
        if(updateAll){  //是否更新所有字段
            changeCount = defineTenantMapper.updateAllColumnById(defineTenant) ;
        }   else {
            changeCount = defineTenantMapper.updateById(defineTenant) ;
        }
        return changeCount ;
    }

    /**
     * 租户定义-删除
     * @param delIds 要删除的租户id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelDefineTenantByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = defineTenantMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 租户定义-删除
     * @param delId 要删除的租户id
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelDefineTenant(String delId,UserAccount loginUser) throws Exception{
        DefineTenant defineTenant = DefineTenant.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            defineTenant.setLastModifyerId(loginUser.getFid());
        }
        Integer delCount = defineTenantMapper.updateById(defineTenant);
        return delCount ;
    }



    /**
     * 取得的结果 转为 枚举类型
     * @param result
     */
    @Override
    public MyCommonResult dealResultListSetToEntitySelect(MyCommonResult result){
        List<FrontEntitySelectBean> enumList = new ArrayList<>();
        List<DefineTenantVo> resultList = result.getResultList() ;
        if(resultList != null && resultList.isEmpty() == false){
            for(DefineTenantVo defineTenantVo : resultList){
                enumList.add(new FrontEntitySelectBean(defineTenantVo.getFid(),defineTenantVo.getName())) ;
            }
        }
        result.setEnumList(enumList);
        return result;
    }
}
