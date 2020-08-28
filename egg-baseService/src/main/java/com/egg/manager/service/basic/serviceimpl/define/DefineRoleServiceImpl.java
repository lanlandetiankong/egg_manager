package com.egg.manager.service.basic.serviceimpl.define;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.service.redis.service.RedisHelper;
import com.egg.manager.api.service.service.CommonFuncService;
import com.egg.manager.api.service.service.define.DefineRoleService;
import com.egg.manager.api.service.service.user.UserRoleService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.pojo.dto.define.DefineRoleDto;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.db.mysql.entity.role.RolePermission;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineMenuMapper;
import com.egg.manager.persistence.db.mysql.mapper.define.DefinePermissionMapper;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineRoleMapper;
import com.egg.manager.persistence.db.mysql.mapper.role.RolePermissionMapper;
import com.egg.manager.persistence.db.mysql.mapper.user.UserAccountMapper;
import com.egg.manager.persistence.pojo.transfer.define.DefineRoleTransfer;
import com.egg.manager.persistence.pojo.vo.define.DefineRoleVo;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service(interfaceClass = DefineRoleService.class)
public class DefineRoleServiceImpl extends ServiceImpl<DefineRoleMapper,DefineRole> implements DefineRoleService {
    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;
    @Reference
    private RedisHelper redisHelper ;
    @Autowired
    private RoutineCommonFunc routineCommonFunc ;

    @Autowired
    private DefineRoleMapper defineRoleMapper;
    @Autowired
    private DefineMenuMapper defineMenuMapper;
    @Autowired
    private DefinePermissionMapper definePermissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private UserAccountMapper userAccountMapper;

    @Reference
    private UserRoleService userRoleService ;
    @Reference
    private CommonFuncService commonFuncService ;





    /**
     * 取得用户 所拥有的 角色定义-List集合
     * @param userAccountId
     * @return
     */
    @Override
    public List<DefineRole> dealGetRolesByAccountFromDb(String userAccountId,Short stateVal) {
        if(StringUtils.isBlank(userAccountId)) {
            return null ;
        }
        UserAccount userAccount = userAccountMapper.selectById(userAccountId);
        if(UserAccountBaseTypeEnum.SuperRoot.getValue().equals(userAccount.getUserTypeNum())){    //如果是[超级管理员]的话可以访问全部菜单
            return getAllEnableDefineRoles(null);
        }   else {
            return defineRoleMapper.findAllRoleByUserAcccountId(userAccountId,stateVal);
        }
    }

    /**
     * 取得用户 所拥有的 角色code-Set集合
     * @param userAccountId
     * @return
     */
    @Override
    public Set<String> dealGetRoleCodeSetByAccountFromDb(String userAccountId) {
        Set<String> codeSet = Sets.newHashSet();
        List<DefineRole> defineRoles = this.dealGetRolesByAccountFromDb(userAccountId,BaseStateEnum.ENABLED.getValue());
        if(defineRoles != null && defineRoles.isEmpty() == false){
            for (DefineRole defineRole : defineRoles){
                String roleCode = defineRole.getCode() ;
                if(StringUtils.isNotBlank(roleCode)){
                    codeSet.add(roleCode);
                }
            }
        }
        return codeSet ;
    }


    /**
     * 取得角色 所拥有的 菜单定义-List集合
     * @param roleId
     * @return
     */
    @Override
    public List<DefineMenu> dealGetMenusByRoleIdFromDb(String roleId, Short stateVal) {
        if(StringUtils.isBlank(roleId)) {
            return null ;
        }   else {
            return defineMenuMapper.findAllMenuByRoleId(roleId,stateVal);
        }
    }

    /**
     * 查询 所有[可用状态]的 [角色定义]
     * @param wrapper
     * @return
     */
    @Override
    public List<DefineRole> getAllEnableDefineRoles(EntityWrapper<DefineRole> wrapper){
        wrapper = wrapper != null ? wrapper : new EntityWrapper<DefineRole>();
        //筛选与排序
        wrapper.eq("state",BaseStateEnum.ENABLED.getValue());
        wrapper.orderBy("create_time",false);
        return this.selectList(wrapper);
    }


    /**
     * 取得角色 所拥有的 菜单定义id-Set集合
     * @param roleId
     * @return
     */
    @Override
    public Set<String> dealGetMenuIdSetByRoleIdFromDb(String roleId,Short stateVal) {
        Set<String> idSet = Sets.newHashSet();
        List<DefineMenu> defineMenuList = this.dealGetMenusByRoleIdFromDb(roleId,stateVal);
        if(defineMenuList != null && defineMenuList.isEmpty() == false){
            for (DefineMenu defineMenu : defineMenuList){
                String menuFid = defineMenu.getFid() ;
                if(StringUtils.isNotBlank(menuFid)){
                    idSet.add(menuFid);
                }
            }
        }
        return idSet ;
    }





    /**
     * 根据 用户账号 取得所有角色
     * @param userAccount
     * @return
     */
    @Override
    public List<DefineRole> dealGetRolesFormRedisByAccount(UserAccount userAccount) {
        List<UserRole> userRoles = userRoleService.dealGetAllUserRoleByAccount(userAccount);
        List<DefineRole> defineRoles = null ;
        if(userRoles == null || userRoles.isEmpty()) {
            return defineRoles;
        }   else {
            Set<String> roleIds = new HashSet<String>();
            for(UserRole userRole : userRoles){
                if(StringUtils.isNotBlank(userRole.getDefineRoleId())){
                    roleIds.add(userRole.getDefineRoleId());
                }
            }
            EntityWrapper<DefineRole> defineRoleEntityWrapper = new EntityWrapper<DefineRole>() ;
            defineRoleEntityWrapper.where("state={0}",BaseStateEnum.ENABLED.getValue())
                    .in(true,"define_role_id",roleIds);
            defineRoles = selectList(defineRoleEntityWrapper);
        }
        return defineRoles ;
    }










    /**
     * 分页查询 角色定义 列表
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetDefineRolePages(MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                       List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        EntityWrapper<DefineRole> defineRoleEntityWrapper = new EntityWrapper<DefineRole>();
        //取得 分页配置
        RowBounds rowBounds = routineCommonFunc.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到defineRoleEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(defineRoleEntityWrapper,queryFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                defineRoleEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        //取得 总数
        Integer total = defineRoleMapper.selectCount(defineRoleEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<DefineRole> defineRoles = defineRoleMapper.selectPage(rowBounds,defineRoleEntityWrapper) ;
        result.setResultList(DefineRoleTransfer.transferEntityToVoList(defineRoles));
    }


    /**
     * 分页查询 角色定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetDefineRoleDtoPages(MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                       List<AntdvSortBean> sortBeans) {
        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<DefineRoleDto> defineRoleDtoList = defineRoleMapper.selectQueryPage(mpPagination, queryFieldBeanList,sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(DefineRoleTransfer.transferDtoToVoList(defineRoleDtoList));
    }




    /**
     * 角色定义-新增
     * @param defineRoleVo
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealAddDefineRole(DefineRoleVo defineRoleVo,UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        DefineRole defineRole = DefineRoleTransfer.transferVoToEntity(defineRoleVo);
        defineRole.setFid(MyUUIDUtil.renderSimpleUUID());
        defineRole.setState(BaseStateEnum.ENABLED.getValue());
        defineRole.setCreateTime(now);
        defineRole.setUpdateTime(now);
        if(loginUser != null){
            defineRole.setCreateUserId(loginUser.getFid());
            defineRole.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = defineRoleMapper.insert(defineRole) ;
        return addCount ;
    }


    /**
     * 角色定义-更新
     * @param defineRoleVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealUpdateDefineRole(DefineRoleVo defineRoleVo,UserAccount loginUser,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        defineRoleVo.setUpdateTime(now);
        DefineRole defineRole = DefineRoleTransfer.transferVoToEntity(defineRoleVo);
        if(loginUser != null){
            defineRole.setLastModifyerId(loginUser.getFid());
        }
        if(updateAll){  //是否更新所有字段
            changeCount = defineRoleMapper.updateAllColumnById(defineRole) ;
        }   else {
            changeCount = defineRoleMapper.updateById(defineRole) ;
        }
        return changeCount ;
    }

    /**
     * 角色定义-删除
     * @param delIds 要删除的角色id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelDefineRoleByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = defineRoleMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 角色定义-删除
     * @param delId 要删除的角色id
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelDefineRole(String delId,UserAccount loginUser) throws Exception{
        DefineRole defineRole = DefineRole.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            defineRole.setLastModifyerId(loginUser.getFid());
        }
        Integer delCount = defineRoleMapper.updateById(defineRole);
        return delCount ;
    }


    /**
     * 角色授权
     * @param roleId 要授权的角色id
     * @param checkIds 权限id集合
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealGrantPermissionToRole(String roleId,String[] checkIds,UserAccount loginUser) throws Exception{
        Integer changeCount = 0 ;
        if(checkIds == null || checkIds.length == 0){   //清空所有权限
            changeCount = definePermissionMapper.clearAllPermissionByRoleId(roleId,loginUser);
        }   else {
            changeCount = checkIds.length ;
            //取得曾勾选的权限id 集合
            List<String> oldCheckPermIds = definePermissionMapper.findAllPermissionIdByRoleId(roleId,false);
            if(oldCheckPermIds == null || oldCheckPermIds.isEmpty()){
                List<RolePermission> addEntitys = new ArrayList<>() ;
                for (String checkId : checkIds){
                    addEntitys.add(RolePermission.generateSimpleInsertEntity(roleId,checkId,loginUser));
                }
                //批量新增行
                rolePermissionMapper.customBatchInsert(addEntitys);
            }   else {
                List<String> checkIdList = new ArrayList<>(Arrays.asList(checkIds));
                List<String> enableIds = new ArrayList<>() ;
                List<String> disabledIds = new ArrayList<>() ;
                Iterator<String> oldCheckIter = oldCheckPermIds.iterator();
                while (oldCheckIter.hasNext()){
                    String oldCheckId = oldCheckIter.next() ;
                    boolean isOldRow = checkIdList.contains(oldCheckId);
                    if(isOldRow){   //原本有的数据行
                        enableIds.add(oldCheckId) ;
                        checkIdList.remove(oldCheckId);
                    }   else {
                        disabledIds.add(oldCheckId);
                    }
                }
                if(enableIds.isEmpty() == false){   //批量启用
                    rolePermissionMapper.batchUpdateStateByRole(roleId,enableIds,BaseStateEnum.ENABLED.getValue(),loginUser);
                }
                if(disabledIds.isEmpty() == false){   //批量禁用
                    rolePermissionMapper.batchUpdateStateByRole(roleId,disabledIds,BaseStateEnum.DELETE.getValue(),loginUser);
                }
                if(checkIdList.isEmpty() == false){     //有新勾选的权限，需要新增行
                    //批量新增行
                    List<RolePermission> addEntitys = new ArrayList<>() ;
                    for (String checkId : checkIdList){
                        addEntitys.add(RolePermission.generateSimpleInsertEntity(roleId,checkId,loginUser));
                    }
                    //批量新增行
                    rolePermissionMapper.customBatchInsert(addEntitys);
                }
            }
        }

        return changeCount ;
    }
}
