package com.egg.manager.serviceimpl.define;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.dto.define.DefineJobDto;
import com.egg.manager.dto.define.DefinePermissionDto;
import com.egg.manager.entity.define.DefineJob;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.define.DefinePermissionMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.define.DefinePermissionService;
import com.egg.manager.vo.define.DefineJobVo;
import com.egg.manager.vo.define.DefinePermissionVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;
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
public class DefinePermissionServiceImpl extends ServiceImpl<DefinePermissionMapper,DefinePermission> implements DefinePermissionService {


    @Autowired
    private DefinePermissionMapper definePermissionMapper ;
    @Autowired
    private CommonFuncService commonFuncService ;

    /**
     * 根据 DefineRoleBean 取得 所有权限
     * @param defineRoles
     * @return
     */
    @Override
    public List<DefinePermission> dealGetAllPermissionByRoles(List<DefineRole> defineRoles) {
        EntityWrapper<DefinePermission> definePermissionEntityWrapper = new EntityWrapper<DefinePermission>() ;
        definePermissionEntityWrapper.where("state={0}", BaseStateEnum.ENABLED.getValue()) ;
        DefineRole role = new DefineRole() ;
        return null;
    }

    /**
     * 分页查询 权限定义 列表
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetDefinePermissionPages(MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                             List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        EntityWrapper<DefinePermission> definePermissionEntityWrapper = new EntityWrapper<DefinePermission>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到definePermissionEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(definePermissionEntityWrapper,queryFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                definePermissionEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        //取得 总数
        Integer total = definePermissionMapper.selectCount(definePermissionEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<DefinePermission> definePermissions = definePermissionMapper.selectPage(rowBounds,definePermissionEntityWrapper) ;
        result.setResultList(DefinePermissionVo.transferEntityToVoList(definePermissions));
    }

    /**
     * 分页查询 权限定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetDefinePermissionDtoPages(MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                             List<AntdvSortBean> sortBeans) {
        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<DefinePermissionDto> definePermissionDtos = definePermissionMapper.selectQueryPage(mpPagination, queryFieldBeanList,sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(DefinePermissionVo.transferDtoToVoList(definePermissionDtos));
    }




    /**
     * 权限定义-新增
     * @param definePermissionVo
     * @throws Exception
     */
    @Override
    public Integer dealAddDefinePermission(DefinePermissionVo definePermissionVo,UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        DefinePermission definePermission = DefinePermissionVo.transferVoToEntity(definePermissionVo);
        definePermission.setFid(MyUUIDUtil.renderSimpleUUID());
        definePermission.setState(BaseStateEnum.ENABLED.getValue());
        definePermission.setCreateTime(now);
        definePermission.setUpdateTime(now);
        if(loginUser != null){
            definePermission.setCreateUserId(loginUser.getFid());
            definePermission.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = definePermissionMapper.insert(definePermission) ;
        return addCount ;
    }


    /**
     * 权限定义-更新
     * @param definePermissionVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Override
    public Integer dealUpdateDefinePermission(DefinePermissionVo definePermissionVo,UserAccount loginUser,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        definePermissionVo.setUpdateTime(now);
        DefinePermission definePermission = DefinePermissionVo.transferVoToEntity(definePermissionVo);
        if(loginUser != null){
            definePermission.setLastModifyerId(loginUser.getFid());
        }
        if(updateAll){  //是否更新所有字段
            changeCount = definePermissionMapper.updateAllColumnById(definePermission) ;
        }   else {
            changeCount = definePermissionMapper.updateById(definePermission) ;
        }
        return changeCount ;
    }

    /**
     * 权限定义-删除
     * @param delIds 要删除的权限id 集合
     * @throws Exception
     */
    @Override
    public Integer dealDelDefinePermissionByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = definePermissionMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 权限定义-删除
     * @param delId 要删除的权限id
     * @throws Exception
     */
    @Override
    public Integer dealDelDefinePermission(String delId,UserAccount loginUser) throws Exception{
        DefinePermission definePermission = DefinePermission.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            definePermission.setLastModifyerId(loginUser.getFid());
        }
        Integer delCount = definePermissionMapper.updateById(definePermission);
        return delCount ;
    }
}
