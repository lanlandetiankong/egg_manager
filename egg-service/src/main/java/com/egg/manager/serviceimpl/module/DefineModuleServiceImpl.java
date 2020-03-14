package com.egg.manager.serviceimpl.module;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.dto.module.DefineModuleDto;
import com.egg.manager.entity.module.DefineModule;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.module.DefineModuleMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.module.DefineModuleService;
import com.egg.manager.vo.module.DefineModuleVo;
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
public class DefineModuleServiceImpl extends ServiceImpl<DefineModuleMapper,DefineModule> implements DefineModuleService {



    @Autowired
    private DefineModuleMapper defineModuleMapper ;
    @Autowired
    private CommonFuncService commonFuncService ;

  


    /**
     * 分页查询 模块
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetDefineModulePages(MyCommonResult<DefineModuleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                             List<AntdvSortBean> sortBeans) {
        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<DefineModuleDto> defineModuleDtoList = defineModuleMapper.selectQueryPage(mpPagination, queryFieldBeanList,sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(DefineModuleVo.transferDtoToVoList(defineModuleDtoList));
    }




    /**
     * 模块定义-新增
     * @param defineModuleVo
     * @throws Exception
     */
    @Override
    public Integer dealAddDefineModule(DefineModuleVo defineModuleVo,UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        DefineModule defineModule = DefineModuleVo.transferVoToEntity(defineModuleVo);
        defineModule.setFid(MyUUIDUtil.renderSimpleUUID());
        defineModule.setState(BaseStateEnum.ENABLED.getValue());
        defineModule.setCreateTime(now);
        defineModule.setUpdateTime(now);
        if(loginUser != null){
            defineModule.setCreateUserId(loginUser.getFid());
            defineModule.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = defineModuleMapper.insert(defineModule) ;
        return addCount ;
    }


    /**
     * 模块定义-更新
     * @param defineModuleVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Override
    public Integer dealUpdateDefineModule(DefineModuleVo defineModuleVo,UserAccount loginUser,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        defineModuleVo.setUpdateTime(now);
        DefineModule defineModule = DefineModuleVo.transferVoToEntity(defineModuleVo);
        if(loginUser != null){
            defineModule.setLastModifyerId(loginUser.getFid());
        }
        if(updateAll){  //是否更新所有字段
            changeCount = defineModuleMapper.updateAllColumnById(defineModule) ;
        }   else {
            changeCount = defineModuleMapper.updateById(defineModule) ;
        }
        return changeCount ;
    }

    /**
     * 模块定义-删除
     * @param delIds 要删除的模块id 集合
     * @throws Exception
     */
    @Override
    public Integer dealDelDefineModuleByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = defineModuleMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 模块定义-删除
     * @param delId 要删除的模块id
     * @throws Exception
     */
    @Override
    public Integer dealDelDefineModule(String delId,UserAccount loginUser) throws Exception{
        DefineModule defineModule = DefineModule.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            defineModule.setLastModifyerId(loginUser.getFid());
        }
        Integer delCount = defineModuleMapper.updateById(defineModule);
        return delCount ;
    }
}
