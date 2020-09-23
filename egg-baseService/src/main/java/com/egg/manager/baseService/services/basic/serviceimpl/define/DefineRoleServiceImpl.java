package com.egg.manager.baseService.services.basic.serviceimpl.define;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.define.DefineRoleService;
import com.egg.manager.api.services.basic.user.UserRoleService;
import com.egg.manager.api.services.redis.service.RedisHelper;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseService.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.db.mysql.entity.role.RolePermission;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineMenuMapper;
import com.egg.manager.persistence.db.mysql.mapper.define.DefinePermissionMapper;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineRoleMapper;
import com.egg.manager.persistence.db.mysql.mapper.role.RolePermissionMapper;
import com.egg.manager.persistence.db.mysql.mapper.user.UserAccountMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineRoleDto;
import com.egg.manager.persistence.pojo.mysql.initialize.role.RolePermissionPojoInitialize;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefineRoleTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineRoleVo;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
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
public class DefineRoleServiceImpl extends MyBaseMysqlServiceImpl<DefineRoleMapper, DefineRole, DefineRoleVo> implements DefineRoleService {
    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache;
    @Reference
    private RedisHelper redisHelper;
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

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
    private UserRoleService userRoleService;


    /**
     * 取得用户 所拥有的 角色定义-List集合
     *
     * @param userAccountId
     * @return
     */
    @Override
    public List<DefineRole> dealGetRolesByAccountFromDb(String userAccountId, Short stateVal) {
        if (StringUtils.isBlank(userAccountId)) {
            return null;
        }
        UserAccount userAccount = userAccountMapper.selectById(userAccountId);
        if (UserAccountBaseTypeEnum.SuperRoot.getValue().equals(userAccount.getUserTypeNum())) {    //如果是[超级管理员]的话可以访问全部菜单
            return queryAllEnableList(null);
        } else {
            return defineRoleMapper.findAllRoleByUserAcccountId(userAccountId, stateVal);
        }
    }

    /**
     * 取得用户 所拥有的 角色code-Set集合
     *
     * @param userAccountId
     * @return
     */
    @Override
    public Set<String> dealGetRoleCodeSetByAccountFromDb(String userAccountId) {
        Set<String> codeSet = Sets.newHashSet();
        List<DefineRole> defineRoles = this.dealGetRolesByAccountFromDb(userAccountId, BaseStateEnum.ENABLED.getValue());
        if (defineRoles != null && defineRoles.isEmpty() == false) {
            for (DefineRole defineRole : defineRoles) {
                String roleCode = defineRole.getCode();
                if (StringUtils.isNotBlank(roleCode)) {
                    codeSet.add(roleCode);
                }
            }
        }
        return codeSet;
    }


    /**
     * 取得角色 所拥有的 菜单定义-List集合
     *
     * @param roleId
     * @return
     */
    @Override
    public List<DefineMenu> dealGetMenusByRoleIdFromDb(String roleId, Short stateVal) {
        if (StringUtils.isBlank(roleId)) {
            return null;
        } else {
            return defineMenuMapper.findAllMenuByRoleId(roleId, stateVal);
        }
    }

    /**
     * 查询 所有[可用状态]的 [角色定义]
     *
     * @param wrapper
     * @return
     */
    @Override
    public List<DefineRole> queryAllEnableList(QueryWrapper<DefineRole> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<DefineRole>();
        //筛选与排序
        wrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        wrapper.orderBy( true,false,"create_time");
        return defineRoleMapper.selectList(wrapper);
    }


    /**
     * 取得角色 所拥有的 菜单定义id-Set集合
     *
     * @param roleId
     * @return
     */
    @Override
    public Set<String> dealGetMenuIdSetByRoleIdFromDb(String roleId, Short stateVal) {
        Set<String> idSet = Sets.newHashSet();
        List<DefineMenu> defineMenuList = this.dealGetMenusByRoleIdFromDb(roleId, stateVal);
        if (defineMenuList != null && defineMenuList.isEmpty() == false) {
            for (DefineMenu defineMenu : defineMenuList) {
                String menuFid = defineMenu.getFid();
                if (StringUtils.isNotBlank(menuFid)) {
                    idSet.add(menuFid);
                }
            }
        }
        return idSet;
    }



    @Override
    public List<DefineRole> dealGetListFormRedisByAccount(UserAccount userAccount) {
        List<UserRole> userRoles = userRoleService.dealGetAllByAccount(userAccount);
        List<DefineRole> defineRoles = null;
        if (userRoles == null || userRoles.isEmpty()) {
            return defineRoles;
        } else {
            Set<String> roleIds = new HashSet<String>();
            for (UserRole userRole : userRoles) {
                if (StringUtils.isNotBlank(userRole.getDefineRoleId())) {
                    roleIds.add(userRole.getDefineRoleId());
                }
            }
            QueryWrapper<DefineRole> defineRoleEntityWrapper = new QueryWrapper<DefineRole>();
            defineRoleEntityWrapper.eq("state", BaseStateEnum.ENABLED.getValue())
                    .in(true, "define_role_id", roleIds);
            defineRoles = defineRoleMapper.selectList(defineRoleEntityWrapper);
        }
        return defineRoles;
    }


    /**
     * 分页查询 角色定义 列表
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<DefineRoleVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                               List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<DefineRole> defineRoleEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = defineRoleMapper.selectCount(defineRoleEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, new Long(total));
        IPage iPage = defineRoleMapper.selectPage(page, defineRoleEntityWrapper);
        List<DefineRole> defineRoles = iPage.getRecords();
        result.setResultList(DefineRoleTransfer.transferEntityToVoList(defineRoles));
        return result;
    }


    /**
     * 分页查询 角色定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<DefineRoleVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                  List<AntdvSortBean> sortBeans) {
        Page<DefineRoleDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineRoleDto> defineRoleDtoList = defineRoleMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(DefineRoleTransfer.transferDtoToVoList(defineRoleDtoList));
        return result;
    }


    /**
     * 角色定义-新增
     *
     * @param defineRoleVo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealCreate(UserAccount loginUser, DefineRoleVo defineRoleVo) throws Exception {
        Date now = new Date();
        DefineRole defineRole = DefineRoleTransfer.transferVoToEntity(defineRoleVo);
        defineRole = super.doBeforeCreate(loginUser, defineRole, true);
        return defineRoleMapper.insert(defineRole);
    }


    /**
     * 角色定义-更新
     *
     * @param defineRoleVo
     * @param updateAll    是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealUpdate(UserAccount loginUser, DefineRoleVo defineRoleVo, boolean updateAll) throws Exception {
        Integer changeCount = 0;
        DefineRole defineRole = DefineRoleTransfer.transferVoToEntity(defineRoleVo);
        defineRole = super.doBeforeUpdate(loginUser, defineRole);
        if (updateAll) {  //是否更新所有字段
            changeCount = defineRoleMapper.updateById(defineRole);
        } else {
            changeCount = defineRoleMapper.updateById(defineRole);
        }
        return changeCount;
    }

    /**
     * 角色定义-删除
     *
     * @param delIds 要删除的角色id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealBatchDelete(UserAccount loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = defineRoleMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    /**
     * 角色定义-删除
     *
     * @param delId 要删除的角色id
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception {
        DefineRole defineRole = super.doBeforeDeleteOneById(loginUser, DefineRole.class, delId);
        return defineRoleMapper.updateById(defineRole);
    }


    /**
     * 角色授权
     *
     * @param roleId   要授权的角色id
     * @param checkIds 权限id集合
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealGrantPermissionToRole(UserAccount loginUser, String roleId, String[] checkIds) throws Exception {
        Integer changeCount = 0;
        if (checkIds == null || checkIds.length == 0) {   //清空所有权限
            changeCount = definePermissionMapper.clearAllPermissionByRoleId(roleId, loginUser);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的权限id 集合
            List<String> oldCheckPermIds = definePermissionMapper.findAllPermissionIdByRoleId(roleId, false);
            if (oldCheckPermIds == null || oldCheckPermIds.isEmpty()) {
                List<RolePermission> addEntitys = new ArrayList<>();
                for (String checkId : checkIds) {
                    addEntitys.add(RolePermissionPojoInitialize.generateSimpleInsertEntity(roleId, checkId, loginUser));
                }
                //批量新增行
                rolePermissionMapper.customBatchInsert(addEntitys);
            } else {
                List<String> checkIdList = new ArrayList<>(Arrays.asList(checkIds));
                List<String> enableIds = new ArrayList<>();
                List<String> disabledIds = new ArrayList<>();
                Iterator<String> oldCheckIter = oldCheckPermIds.iterator();
                while (oldCheckIter.hasNext()) {
                    String oldCheckId = oldCheckIter.next();
                    boolean isOldRow = checkIdList.contains(oldCheckId);
                    if (isOldRow) {   //原本有的数据行
                        enableIds.add(oldCheckId);
                        checkIdList.remove(oldCheckId);
                    } else {
                        disabledIds.add(oldCheckId);
                    }
                }
                if (enableIds.isEmpty() == false) {   //批量启用
                    rolePermissionMapper.batchUpdateStateByRole(roleId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUser);
                }
                if (disabledIds.isEmpty() == false) {   //批量禁用
                    rolePermissionMapper.batchUpdateStateByRole(roleId, disabledIds, BaseStateEnum.DELETE.getValue(), loginUser);
                }
                if (checkIdList.isEmpty() == false) {     //有新勾选的权限，需要新增行
                    //批量新增行
                    List<RolePermission> addEntitys = new ArrayList<>();
                    for (String checkId : checkIdList) {
                        addEntitys.add(RolePermissionPojoInitialize.generateSimpleInsertEntity(roleId, checkId, loginUser));
                    }
                    //批量新增行
                    rolePermissionMapper.customBatchInsert(addEntitys);
                }
            }
        }
        return changeCount;
    }
}
