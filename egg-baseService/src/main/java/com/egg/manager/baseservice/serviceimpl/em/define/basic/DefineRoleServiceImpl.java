package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.helper.redis.RedisHelper;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.define.basic.DefineRoleService;
import com.egg.manager.api.services.em.user.basic.UserRoleService;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.constant.redis.RedisShiroKeyConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.util.LongUtils;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenuEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineRoleEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineMenuMapper;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefinePermissionMapper;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineRoleMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineRoleDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineRoleTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineRoleVo;
import com.egg.manager.persistence.em.user.db.mysql.entity.RolePermissionEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserRoleEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.RolePermissionMapper;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.em.user.pojo.initialize.RolePermissionPojoInitialize;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = DefineRoleService.class)
public class DefineRoleServiceImpl extends MyBaseMysqlServiceImpl<DefineRoleMapper, DefineRoleEntity, DefineRoleVo> implements DefineRoleService {

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
    public List<DefineRoleEntity> dealGetRolesByAccountFromDb(Long userAccountId, Short stateVal) {
        if (LongUtils.isBlank(userAccountId)) {
            return null;
        }
        UserAccountEntity userAccountEntity = userAccountMapper.selectById(userAccountId);
        if (UserAccountBaseTypeEnum.SuperRoot.getValue().equals(userAccountEntity.getUserTypeNum())) {
            //如果是[超级管理员]的话可以访问全部菜单
            return queryAllEnableList(null);
        } else {
            return defineRoleMapper.findAllRoleByUserAcccountId(userAccountId, stateVal);
        }
    }


    @Override
    @Cacheable(value= RedisShiroKeyConstant.KEY_USER_ROLE,key="#userAccountId",condition = "#userAccountId!=null")
    public Set<String> queryDbToCacheable(Long userAccountId) {
        Set<String> codeSet = Sets.newHashSet();
        List<DefineRoleEntity> defineRoleEntities = this.dealGetRolesByAccountFromDb(userAccountId, BaseStateEnum.ENABLED.getValue());
        if (defineRoleEntities != null && defineRoleEntities.isEmpty() == false) {
            for (DefineRoleEntity defineRoleEntity : defineRoleEntities) {
                String roleCode = defineRoleEntity.getCode();
                if (StringUtils.isNotBlank(roleCode)) {
                    codeSet.add(roleCode);
                }
            }
        }
        return codeSet;
    }


    @Override
    public List<DefineMenuEntity> dealGetMenusByRoleIdFromDb(Long roleId, Short stateVal) {
        if (LongUtils.isBlank(roleId)) {
            return null;
        } else {
            return defineMenuMapper.findAllMenuByRoleId(roleId, stateVal);
        }
    }


    @Override
    public List<DefineRoleEntity> queryAllEnableList(QueryWrapper<DefineRoleEntity> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<DefineRoleEntity>();
        //筛选与排序
        wrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        wrapper.orderBy(true, false, "create_time");
        return defineRoleMapper.selectList(wrapper);
    }


    @Override
    public Set<Long> dealGetMenuIdSetByRoleIdFromDb(Long roleId, Short stateVal) {
        Set<Long> idSet = Sets.newHashSet();
        List<DefineMenuEntity> defineMenuEntityList = this.dealGetMenusByRoleIdFromDb(roleId, stateVal);
        if (defineMenuEntityList != null && defineMenuEntityList.isEmpty() == false) {
            for (DefineMenuEntity defineMenuEntity : defineMenuEntityList) {
                Long menuFid = defineMenuEntity.getFid();
                if (LongUtils.isNotBlank(menuFid)) {
                    idSet.add(menuFid);
                }
            }
        }
        return idSet;
    }


    @Override
    public List<DefineRoleEntity> dealGetListFormRedisByAccount(UserAccountEntity userAccountEntity) {
        List<UserRoleEntity> userRoleEntities = userRoleService.dealGetAllByAccount(userAccountEntity);
        List<DefineRoleEntity> defineRoleEntities = null;
        if (userRoleEntities == null || userRoleEntities.isEmpty()) {
            return defineRoleEntities;
        } else {
            Set<Long> roleIds = new HashSet<Long>();
            for (UserRoleEntity userRoleEntity : userRoleEntities) {
                if (LongUtils.isNotBlank(userRoleEntity.getDefineRoleId())) {
                    roleIds.add(userRoleEntity.getDefineRoleId());
                }
            }
            QueryWrapper<DefineRoleEntity> defineRoleEntityWrapper = new QueryWrapper<DefineRoleEntity>();
            defineRoleEntityWrapper.eq("state", BaseStateEnum.ENABLED.getValue())
                    .in(true, "define_role_id", roleIds);
            defineRoleEntities = defineRoleMapper.selectList(defineRoleEntityWrapper);
        }
        return defineRoleEntities;
    }


    @Override
    public MyCommonResult<DefineRoleVo> dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineRoleEntity> paginationBean,
                                                               List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<DefineRoleEntity> defineRoleEntityWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = defineRoleMapper.selectCount(defineRoleEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, new Long(total));
        IPage iPage = defineRoleMapper.selectPage(page, defineRoleEntityWrapper);
        List<DefineRoleEntity> defineRoleEntities = iPage.getRecords();
        result.setResultList(DefineRoleTransfer.transferEntityToVoList(defineRoleEntities));
        return result;
    }


    @Override
    public MyCommonResult<DefineRoleVo> dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineRoleDto> paginationBean,
                                                            List<AntdvSortBean> sortBeans) {
        Page<DefineRoleDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineRoleDto> defineRoleDtoList = defineRoleMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(DefineRoleTransfer.transferDtoToVoList(defineRoleDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, DefineRoleVo defineRoleVo) throws Exception {
        Date now = new Date();
        DefineRoleEntity defineRoleEntity = DefineRoleTransfer.transferVoToEntity(defineRoleVo);
        defineRoleEntity = super.doBeforeCreate(loginUserInfo, defineRoleEntity, true);
        return defineRoleMapper.insert(defineRoleEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, DefineRoleVo defineRoleVo) throws Exception {
        Integer changeCount = 0;
        DefineRoleEntity defineRoleEntity = DefineRoleTransfer.transferVoToEntity(defineRoleVo);
        defineRoleEntity = super.doBeforeUpdate(loginUserInfo, defineRoleEntity);
        changeCount = defineRoleMapper.updateById(defineRoleEntity);
        return changeCount;
    }



    @Override
    public Integer dealGrantPermissionToRole(CurrentLoginUserInfo loginUserInfo, Long roleId, Long[] checkIds) throws Exception {
        Integer changeCount = 0;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = definePermissionMapper.clearAllPermissionByRoleId(roleId, loginUserInfo);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的权限id 集合
            List<Long> oldCheckPermIds = definePermissionMapper.findAllPermissionIdByRoleId(roleId, false);
            if (oldCheckPermIds == null || oldCheckPermIds.isEmpty()) {
                List<RolePermissionEntity> addEntitys = new ArrayList<>();
                for (Long checkId : checkIds) {
                    addEntitys.add(RolePermissionPojoInitialize.generateSimpleInsertEntity(roleId, checkId, loginUserInfo));
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
                    rolePermissionMapper.batchUpdateStateByRole(roleId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUserInfo);
                }
                if (disabledIds.isEmpty() == false) {
                    //批量禁用
                    rolePermissionMapper.batchUpdateStateByRole(roleId, disabledIds, BaseStateEnum.DELETE.getValue(), loginUserInfo);
                }
                if (checkIdList.isEmpty() == false) {
                    //有新勾选的权限，需要新增行
                    List<RolePermissionEntity> addEntitys = new ArrayList<>();
                    for (Long checkId : checkIdList) {
                        addEntitys.add(RolePermissionPojoInitialize.generateSimpleInsertEntity(roleId, checkId, loginUserInfo));
                    }
                    //批量新增行
                    rolePermissionMapper.customBatchInsert(addEntitys);
                }
            }
        }
        return changeCount;
    }
}
