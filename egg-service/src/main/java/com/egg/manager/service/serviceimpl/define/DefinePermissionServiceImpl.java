package com.egg.manager.service.serviceimpl.define;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.service.service.CommonFuncService;
import com.egg.manager.api.service.service.define.DefinePermissionService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.common.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.base.exception.MyDbException;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.dto.define.DefinePermissionDto;
import com.egg.manager.persistence.entity.define.DefinePermission;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.persistence.mapper.define.DefinePermissionMapper;
import com.egg.manager.persistence.mapper.user.UserAccountMapper;
import com.egg.manager.persistence.transfer.define.DefinePermissionTransfer;
import com.egg.manager.persistence.vo.define.DefinePermissionVo;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service(interfaceClass = DefinePermissionService.class)
public class DefinePermissionServiceImpl extends ServiceImpl<DefinePermissionMapper,DefinePermission> implements DefinePermissionService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc ;

    @Autowired
    private DefinePermissionMapper definePermissionMapper ;
    @Autowired
    private UserAccountMapper userAccountMapper ;
    @Reference
    private CommonFuncService commonFuncService ;

    /**
     * 查询 所有[可用状态]的 [权限定义]
     * @param wrapper
     * @return
     */
    @Override
    public List<DefinePermission> getAllEnableDefinePermissions(EntityWrapper<DefinePermission> wrapper){
        wrapper = wrapper != null ? wrapper : new EntityWrapper<DefinePermission>();
        //筛选与排序
        wrapper.eq("state",BaseStateEnum.ENABLED.getValue());
        wrapper.orderBy("create_time",false);
        return this.selectList(wrapper);
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
        RowBounds rowBounds = routineCommonFunc.parsePaginationToRowBounds(paginationBean) ;
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
        result.setResultList(DefinePermissionTransfer.transferEntityToVoList(definePermissions));
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
        result.setResultList(DefinePermissionTransfer.transferDtoToVoList(definePermissionDtos));
    }




    /**
     * 权限定义-新增
     * @param definePermissionVo
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealAddDefinePermission(DefinePermissionVo definePermissionVo,UserAccount loginUser) throws Exception{
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(definePermissionVo,new EntityWrapper<DefinePermission>());
        if(verifyDuplicateBean.isSuccessFlag() == false){    //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Date now = new Date() ;
        DefinePermission definePermission = DefinePermissionTransfer.transferVoToEntity(definePermissionVo,null);
        definePermission.setFid(MyUUIDUtil.renderSimpleUUID());
        definePermission.setEnsure(BaseStateEnum.DISABLED.getValue());
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
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealUpdateDefinePermission(DefinePermissionVo definePermissionVo,UserAccount loginUser,boolean updateAll) throws Exception{
        Wrapper<DefinePermission> uniWrapper  = new EntityWrapper<DefinePermission>()
                .ne("fid",definePermissionVo.getFid());
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(definePermissionVo,uniWrapper);
        if(verifyDuplicateBean.isSuccessFlag() == false){    //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Integer changeCount = 0;
        Date now = new Date() ;
        definePermissionVo.setUpdateTime(now);
        DefinePermission updateEntity = DefinePermissionTransfer.transferVoToEntity(definePermissionVo,null);
        if(loginUser != null){
            updateEntity.setLastModifyerId(loginUser.getFid());
        }
        DefinePermission oldEntity = definePermissionMapper.selectById(definePermissionVo.getFid());
        if(SwitchStateEnum.Open.getValue().equals(oldEntity.getEnsure())){    //如果已经启用
            DefinePermissionTransfer.handleSwitchOpenChangeFieldChange(updateEntity,oldEntity);
        }
        if(updateAll){  //是否更新所有字段
            changeCount = definePermissionMapper.updateAllColumnById(updateEntity) ;
        }   else {
            changeCount = definePermissionMapper.updateById(updateEntity) ;
        }
        return changeCount ;
    }



    /**
     * 权限定义-删除
     * @param delIds 要删除的权限id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelDefinePermissionByArr(String[] delIds,UserAccount loginUser) {
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = definePermissionMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 权限定义-启用
     * @param ensureIds 要启用的权限id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealEnsureDefinePermissionByArr(String[] ensureIds,UserAccount loginUser) {
        Integer delCount = 0 ;
        if(ensureIds != null && ensureIds.length > 0) {
            List<String> delIdList = Arrays.asList(ensureIds) ;
            //批量伪删除
            delCount = definePermissionMapper.batchEnsureByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 权限定义-删除
     * @param delId 要删除的权限id
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelDefinePermission(String delId,UserAccount loginUser) throws Exception{
        return this.dealDelDefinePermissionByArr(new String[]{delId},loginUser);
    }



    /**
     * 取得用户 所拥有的 权限定义-List集合
     * @param userAccountId
     * @return
     */
    @Override
    public List<DefinePermission> dealGetPermissionsByAccountFromDb(String userAccountId) {
        if(StringUtils.isBlank(userAccountId)) {
            return null ;
        }
        UserAccount userAccount = userAccountMapper.selectById(userAccountId);
        if(UserAccountBaseTypeEnum.SuperRoot.getValue().equals(userAccount.getUserTypeNum())){    //如果是[超级管理员]的话可以访问全部菜单
            return getAllEnableDefinePermissions(null);
        }   else {
            return definePermissionMapper.findAllPermissionByUserAcccountId(userAccountId);
        }
    }

    /**
     * 取得用户 所拥有的 权限code-Set集合
     * @param userAccountId
     * @return
     */
    @Override
    public Set<String> dealGetPermissionCodeSetByAccountFromDb(String userAccountId) {
        Set<String> codeSet = Sets.newHashSet();
        List<DefinePermission> definePermissions = this.dealGetPermissionsByAccountFromDb(userAccountId);
        if(definePermissions != null && definePermissions.isEmpty() == false){
            for (DefinePermission definePermission : definePermissions){
                String permissionCode = definePermission.getCode() ;
                if(StringUtils.isNotBlank(permissionCode)){
                    codeSet.add(permissionCode);
                }
            }
        }
        return codeSet ;
    }


    /**
     * 验证 数据库 中的唯一冲突
     * @param definePermissionVo
     * @param definePermissionWrap
     * @return
     */
    @Override
    public MyVerifyDuplicateBean dealCheckDuplicateKey(DefinePermissionVo definePermissionVo, Wrapper<DefinePermission> definePermissionWrap){
        MyVerifyDuplicateBean verifyBean = new MyVerifyDuplicateBean();
        definePermissionWrap = definePermissionWrap != null ? definePermissionWrap : new EntityWrapper<>() ;
        definePermissionWrap.eq("code",definePermissionVo.getCode()) ;
        definePermissionWrap.eq("state",BaseStateEnum.ENABLED.getValue()) ;
        boolean successFlag = definePermissionMapper.selectCount(definePermissionWrap) == 0 ;
        if(successFlag == false){
            verifyBean.setErrorMsg("唯一键[编码]不允许重复！");
            verifyBean.dealAddColumn("code");
            verifyBean.dealAddFieldName("编码");
        }
        verifyBean.setSuccessFlag(successFlag);
        return verifyBean;
    }
}
