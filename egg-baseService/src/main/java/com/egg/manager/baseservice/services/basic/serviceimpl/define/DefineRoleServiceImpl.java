package com.egg.manager.baseservice.services.basic.serviceimpl.define;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.define.DefineRoleService;
import com.egg.manager.api.services.basic.user.UserRoleService;
import com.egg.manager.api.services.redis.service.RedisHelper;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.util.LongUtils;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenu;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineRole;
import com.egg.manager.persistence.em.user.db.mysql.entity.RolePermission;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserRole;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineMenuMapper;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefinePermissionMapper;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineRoleMapper;
import com.egg.manager.persistence.em.user.db.mysql.mapper.RolePermissionMapper;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserAccountMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineRoleDto;
import com.egg.manager.persistence.em.user.pojo.initialize.RolePermissionPojoInitialize;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineRoleTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineRoleVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = DefineRoleService.class)
public class DefineRoleServiceImpl extends MyBaseMysqlServiceImpl<DefineRoleMapper, DefineRole, DefineRoleVo> implements DefineRoleService {

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


    @Override
    public List<DefineRole> dealGetRolesByAccountFromDb(Long userAccountId, Short stateVal) {
        if (LongUtils.isBlank(userAccountId)) {
            return null;
        }
        UserAccount userAccount = userAccountMapper.selectById(userAccountId);
        if (UserAccountBaseTypeEnum.SuperRoot.getValue().equals(userAccount.getUserTypeNum())) {
            //如果是[超级管理员]的话可以访问全部菜单
            return queryAllEnableList(null);
        } else {
            return defineRoleMapper.findAllRoleByUserAcccountId(userAccountId, stateVal);
        }
    }

    @Override
    public Set<String> dealGetRoleCodeSetByAccountFromDb(Long userAccountId) {
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


    @Override
    public List<DefineMenu> dealGetMenusByRoleIdFromDb(Long roleId, Short stateVal) {
        if (LongUtils.isBlank(roleId)) {
            return null;
        } else {
            return defineMenuMapper.findAllMenuByRoleId(roleId, stateVal);
        }
    }


    @Override
    public List<DefineRole> queryAllEnableList(QueryWrapper<DefineRole> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<DefineRole>();
        //筛选与排序
        wrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        wrapper.orderBy(true, false, "create_time");
        return defineRoleMapper.selectList(wrapper);
    }


    @Override
    public Set<Long> dealGetMenuIdSetByRoleIdFromDb(Long roleId, Short stateVal) {
        Set<Long> idSet = Sets.newHashSet();
        List<DefineMenu> defineMenuList = this.dealGetMenusByRoleIdFromDb(roleId, stateVal);
        if (defineMenuList != null && defineMenuList.isEmpty() == false) {
            for (DefineMenu defineMenu : defineMenuList) {
                Long menuFid = defineMenu.getFid();
                if (LongUtils.isNotBlank(menuFid)) {
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
            Set<Long> roleIds = new HashSet<Long>();
            for (UserRole userRole : userRoles) {
                if (LongUtils.isNotBlank(userRole.getDefineRoleId())) {
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


    @Override
    public MyCommonResult<DefineRoleVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineRole> paginationBean,
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


    @Override
    public MyCommonResult<DefineRoleVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineRoleDto> paginationBean,
                                                            List<AntdvSortBean> sortBeans) {
        Page<DefineRoleDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineRoleDto> defineRoleDtoList = defineRoleMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(DefineRoleTransfer.transferDtoToVoList(defineRoleDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(UserAccount loginUser, DefineRoleVo defineRoleVo) throws Exception {
        Date now = new Date();
        DefineRole defineRole = DefineRoleTransfer.transferVoToEntity(defineRoleVo);
        defineRole = super.doBeforeCreate(loginUser, defineRole, true);
        return defineRoleMapper.insert(defineRole);
    }


    @Override
    public Integer dealUpdate(UserAccount loginUser, DefineRoleVo defineRoleVo) throws Exception {
        Integer changeCount = 0;
        DefineRole defineRole = DefineRoleTransfer.transferVoToEntity(defineRoleVo);
        defineRole = super.doBeforeUpdate(loginUser, defineRole);
        changeCount = defineRoleMapper.updateById(defineRole);
        return changeCount;
    }



    @Override
    public Integer dealGrantPermissionToRole(UserAccount loginUser, Long roleId, Long[] checkIds) throws Exception {
        Integer changeCount = 0;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = definePermissionMapper.clearAllPermissionByRoleId(roleId, loginUser);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的权限id 集合
            List<Long> oldCheckPermIds = definePermissionMapper.findAllPermissionIdByRoleId(roleId, false);
            if (oldCheckPermIds == null || oldCheckPermIds.isEmpty()) {
                List<RolePermission> addEntitys = new ArrayList<>();
                for (Long checkId : checkIds) {
                    addEntitys.add(RolePermissionPojoInitialize.generateSimpleInsertEntity(roleId, checkId, loginUser));
                }
                //批量新增行
                rolePermissionMapper.customBatchInsert(addEntitys);
            } else {
                List<Long> checkIdList = new ArrayList<>(Lists.newArrayList(checkIds));
                List<Long> enableIds = new ArrayList<>();
                List<Long> disabledIds = new ArrayList<>();
                Iterator<Long> oldCheckIter = oldCheckPermIds.iterator();
                while (oldCheckIter.hasNext()) {
                    Long oldCheckId = oldCheckIter.next();
                    boolean isOldRow = checkIdList.contains(oldCheckId);
                    if (isOldRow) {
                        //原本有的数据行
                        enableIds.add(oldCheckId);
                        checkIdList.remove(oldCheckId);
                    } else {
                        disabledIds.add(oldCheckId);
                    }
                }
                if (enableIds.isEmpty() == false) {
                    //批量启用
                    rolePermissionMapper.batchUpdateStateByRole(roleId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUser);
                }
                if (disabledIds.isEmpty() == false) {
                    //批量禁用
                    rolePermissionMapper.batchUpdateStateByRole(roleId, disabledIds, BaseStateEnum.DELETE.getValue(), loginUser);
                }
                if (checkIdList.isEmpty() == false) {
                    //有新勾选的权限，需要新增行
                    List<RolePermission> addEntitys = new ArrayList<>();
                    for (Long checkId : checkIdList) {
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
