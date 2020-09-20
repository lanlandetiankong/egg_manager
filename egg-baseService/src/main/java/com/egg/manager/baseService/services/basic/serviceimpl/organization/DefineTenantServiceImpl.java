package com.egg.manager.baseService.services.basic.serviceimpl.organization;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.services.basic.CommonFuncService;
import com.egg.manager.api.services.basic.organization.DefineTenantService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseService.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
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
import com.egg.manager.persistence.utils.reflex.config.EggPojoReflexFieldConfig;
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
public class DefineTenantServiceImpl extends MyBaseMysqlServiceImpl<DefineTenantMapper,DefineTenant,DefineTenantVo>
        implements DefineTenantService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc ;

    @Autowired
    private DefineTenantMapper defineTenantMapper ;
    @Reference
    private CommonFuncService commonFuncService ;




    /**
     * 分页查询 租户
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<DefineTenantVo> dealGetDefineTenantDtoPages(UserAccount loginUser,MyCommonResult<DefineTenantVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
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
    public Integer dealAddDefineTenant(UserAccount loginUser,DefineTenantVo defineTenantVo) throws Exception{
        DefineTenant defineTenant = DefineTenantTransfer.transferVoToEntity(defineTenantVo);
        defineTenant = super.doBeforeCreate(loginUser,defineTenant,true);
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
    public Integer dealUpdateDefineTenant(UserAccount loginUser,DefineTenantVo defineTenantVo,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        DefineTenant defineTenant = DefineTenantTransfer.transferVoToEntity(defineTenantVo);
        defineTenant = super.doBeforeUpdate(loginUser,defineTenant);
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
    public Integer dealDelDefineTenantByArr(UserAccount loginUser,String[] delIds) throws Exception{
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
    public Integer dealDelDefineTenant(UserAccount loginUser,String delId) throws Exception{
        DefineTenant updateWrapper = super.doBeforeDeleteOneById(loginUser,DefineTenant.class,delId);
        Integer count = defineTenantMapper.updateById(updateWrapper);
        return count ;
    }



    /**
     * 取得的结果 转为 枚举类型
     * @param result
     */
    @Override
    public MyCommonResult dealResultListSetToEntitySelect(UserAccount loginUser,MyCommonResult result){
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
