package com.egg.manager.serviceimpl.organization;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.common.web.pagination.AntdvSortBean;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.organization.DefineTenant;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.organization.DefineTenantMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.organization.DefineTenantService;
import com.egg.manager.vo.organization.DefineTenantVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class DefineTenantServiceImpl extends ServiceImpl<DefineTenantMapper,DefineTenant> implements DefineTenantService {


    @Autowired
    private DefineTenantMapper defineTenantMapper ;
    @Autowired
    private CommonFuncService commonFuncService ;



    /**
     * 分页查询 租户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetDefineTenantPages(MyCommonResult<DefineTenantVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                             List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        EntityWrapper<DefineTenant> defineTenantEntityWrapper = new EntityWrapper<DefineTenant>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
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
        result.setResultList(DefineTenantVo.transferEntityToVoList(defineTenants));
    }




    /**
     * 租户定义-新增
     * @param defineTenantVo
     * @throws Exception
     */
    @Override
    public Integer dealAddDefineTenant(DefineTenantVo defineTenantVo,UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        DefineTenant defineTenant = DefineTenantVo.transferVoToEntity(defineTenantVo);
        defineTenant.setFid(MyUUIDUtil.renderSimpleUUID());
        defineTenant.setState(BaseStateEnum.ENABLED.getValue());
        defineTenant.setCreateTime(now);
        defineTenant.setUpdateTime(now);
        if(loginUser != null){
            defineTenant.setCreateUser(loginUser.getFid());
            defineTenant.setLastModifyer(loginUser.getFid());
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
    @Override
    public Integer dealUpdateDefineTenant(DefineTenantVo defineTenantVo,UserAccount loginUser,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        defineTenantVo.setUpdateTime(now);
        DefineTenant defineTenant = DefineTenantVo.transferVoToEntity(defineTenantVo);
        if(loginUser != null){
            defineTenant.setLastModifyer(loginUser.getFid());
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
    @Override
    public Integer dealDelDefineTenant(String delId,UserAccount loginUser) throws Exception{
        DefineTenant defineTenant = DefineTenant.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            defineTenant.setLastModifyer(loginUser.getFid());
        }
        Integer delCount = defineTenantMapper.updateById(defineTenant);
        return delCount ;
    }
}
