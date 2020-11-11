package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.commons.base.constant.define.UserAccountConstant;
import com.egg.manager.persistence.commons.base.constant.redis.RedisShiroKeyConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.base.SwitchStateEnum;
import com.egg.manager.persistence.commons.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.commons.base.enums.user.UserAccountStateEnum;
import com.egg.manager.persistence.commons.base.exception.BusinessException;
import com.egg.manager.persistence.commons.base.exception.MyDbException;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.util.LongUtils;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.user.db.mysql.entity.*;
import com.egg.manager.persistence.em.user.db.mysql.mapper.*;
import com.egg.manager.persistence.em.user.pojo.dto.login.LoginAccountDTO;
import com.egg.manager.persistence.em.user.pojo.excel.export.user.UserAccountXlsOutModel;
import com.egg.manager.persistence.em.user.pojo.dto.UserAccountDto;
import com.egg.manager.persistence.em.user.pojo.initialize.UserDepartmentPojoInitialize;
import com.egg.manager.persistence.em.user.pojo.initialize.UserJobPojoInitialize;
import com.egg.manager.persistence.em.user.pojo.initialize.UserRolePojoInitialize;
import com.egg.manager.persistence.em.user.pojo.initialize.UserTenantPojoInitialize;
import com.egg.manager.persistence.em.user.pojo.transfer.UserAccountTransfer;
import com.egg.manager.persistence.em.user.pojo.vo.UserAccountVo;
import com.google.common.collect.Lists;
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
@Service(interfaceClass = UserAccountService.class)
public class UserAccountServiceImpl extends MyBaseMysqlServiceImpl<UserAccountMapper, UserAccountEntity, UserAccountVo> implements UserAccountService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserJobMapper userJobMapper;
    @Autowired
    private UserTenantMapper userTenantMapper;
    @Autowired
    private UserDepartmentMapper userDepartmentMapper;

    @Override
    public UserAccountEntity dealGetEntityByDTO(LoginAccountDTO loginAccountDTO) {
        QueryWrapper<UserAccountEntity> wrapper = new QueryWrapper<UserAccountEntity>();
        wrapper.setEntity(new UserAccountEntity());
        wrapper.eq("account", loginAccountDTO.getAccount())
                .eq("state", UserAccountStateEnum.ENABLED.getValue());
        return userAccountMapper.selectOne(wrapper);
    }


    @Override
    public MyCommonResult<UserAccountVo> dealQueryPageByEntitys(UserAccountEntity loginUser, MyCommonResult<UserAccountVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserAccountEntity> paginationBean,
                                                                List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<UserAccountEntity> userAccountEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = userAccountMapper.selectCount(userAccountEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = userAccountMapper.selectPage(page, userAccountEntityWrapper);
        List<UserAccountEntity> userAccountEntities = iPage.getRecords();
        result.setResultList(UserAccountTransfer.transferEntityToVoList(userAccountEntities));
        return result;
    }

    @Override
    public MyCommonResult<UserAccountVo> dealQueryPageByDtos(UserAccountEntity loginUser, MyCommonResult<UserAccountVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserAccountDto> paginationBean,
                                                             List<AntdvSortBean> sortBeans) {
        Page<UserAccountDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<QueryFormFieldBean> queryFieldBeanListTemp = new ArrayList<QueryFormFieldBean>();
        //用户与租户关联 的外表-搜索条件
        List<QueryFormFieldBean> queryTenantFieldBeanList = new ArrayList<QueryFormFieldBean>();
        //用户与部门关联 的外表-搜索条件
        List<QueryFormFieldBean> queryDepartmentFieldBeanList = new ArrayList<QueryFormFieldBean>();
        for (QueryFormFieldBean queryFormFieldBean : queryFieldBeanList) {
            //外部关联名，条件需单独识别
            String foreignName = queryFormFieldBean.getForeignName();
            if (StringUtils.isBlank(foreignName)) {
                queryFieldBeanListTemp.add(queryFormFieldBean);
            } else {
                //foreignName匹配一致的会被认定为 指定表的 搜索条件
                if (FOREIGN_NAME_OF_USER_TENANT.equals(foreignName)) {
                    //筛选-所属租户
                    queryTenantFieldBeanList.add(queryFormFieldBean);
                } else if (FOREIGN_NAME_OF_USER_DEPARTMENT.equals(foreignName)) {
                    //筛选-所属部门
                    queryDepartmentFieldBeanList.add(queryFormFieldBean);
                }
            }
        }
        List<UserAccountDto> userAccountDtoList = userAccountMapper.selectQueryPage(mpPagination, queryFieldBeanListTemp, sortBeans, queryTenantFieldBeanList, queryDepartmentFieldBeanList);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(UserAccountTransfer.transferDtoToVoList(userAccountDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(UserAccountEntity loginUser, UserAccountVo userAccountVo) throws Exception {
        if (this.dealCheckDuplicateKey(userAccountVo, new QueryWrapper<>())) {
            throw new MyDbException("唯一键[账号]不允许重复！");
        }
        UserAccountEntity userAccountEntity = UserAccountTransfer.transferVoToEntity(userAccountVo);
        userAccountEntity = super.doBeforeCreate(loginUser, userAccountEntity, true);
        if (null == userAccountVo.getLocked()) {
            //如果没设置值，默认不锁定
            userAccountEntity.setLocked(SwitchStateEnum.Close.getValue());
        }
        userAccountEntity.setUserType(UserAccountBaseTypeEnum.SimpleUser.getValue());
        if (StringUtils.isBlank(userAccountVo.getPassword())) {
            String pwd = UserAccountConstant.DEFAULT_PWD;
            userAccountVo.setPassword(pwd);
            userAccountEntity.setPassword(pwd);
        }
        Integer addCount = userAccountMapper.insert(userAccountEntity);
        //关联 租户
        if (LongUtils.isNotBlank(userAccountEntity.getFid()) && LongUtils.isNotBlank(userAccountVo.getBelongTenantId())) {
            UserTenantEntity userTenantEntity = UserTenantPojoInitialize.generateSimpleInsertEntity(userAccountEntity.getFid(), userAccountVo.getBelongTenantId(), loginUser);
            userTenantMapper.insert(userTenantEntity);
        } else {
            throw new BusinessException("关联用户与租户失败！创建用户失败！");
        }

        //关联 部门
        if (LongUtils.isNotBlank(userAccountEntity.getFid()) && LongUtils.isNotBlank(userAccountVo.getBelongDepartmentId())) {
            UserDepartmentEntity userDepartmentEntity = UserDepartmentPojoInitialize.generateSimpleInsertEntity(userAccountEntity.getFid(), userAccountVo.getBelongDepartmentId(), loginUser);
            userDepartmentMapper.insert(userDepartmentEntity);
        } else {
            throw new BusinessException("关联用户与部门失败！创建用户失败！");
        }
        return addCount;
    }


    @Override
    public Integer dealUpdate(UserAccountEntity loginUser, UserAccountVo userAccountVo) throws Exception {
        QueryWrapper<UserAccountEntity> uniWrapper = new QueryWrapper<UserAccountEntity>()
                .ne("fid", userAccountVo.getFid());
        if (dealCheckDuplicateKey(userAccountVo, uniWrapper)) {
            //已有重复键值
            throw new MyDbException("唯一键[账号]不允许重复！");
        }
        Integer changeCount = 0;
        UserAccountEntity userAccountEntity = UserAccountTransfer.transferVoToEntity(userAccountVo);
        userAccountEntity = super.doBeforeUpdate(loginUser, userAccountEntity);
        changeCount = userAccountMapper.updateById(userAccountEntity);
        //关联 租户
        if (LongUtils.isNotBlank(userAccountEntity.getFid()) && LongUtils.isNotBlank(userAccountVo.getBelongTenantId())) {
            QueryWrapper<UserTenantEntity> tenantQueryWrapper = new QueryWrapper<UserTenantEntity>();
            tenantQueryWrapper.eq("user_account_id", userAccountEntity.getFid())
                    .eq("state", BaseStateEnum.ENABLED.getValue());
            UserTenantEntity userTenantEntity = userTenantMapper.selectOne(tenantQueryWrapper);
            if (userTenantEntity == null) {
                userTenantEntity = UserTenantPojoInitialize.generateSimpleInsertEntity(userAccountEntity.getFid(), userAccountVo.getBelongTenantId(), loginUser);
                userTenantMapper.insert(userTenantEntity);
            } else {
                userTenantEntity.setDefineTenantId(userAccountVo.getBelongTenantId());
                userTenantMapper.updateById(userTenantEntity);
            }
        } else {
            throw new BusinessException("关联用户与租户失败！更新用户失败！");
        }
        //关联 部门
        if (LongUtils.isNotBlank(userAccountEntity.getFid()) && LongUtils.isNotBlank(userAccountVo.getBelongDepartmentId())) {
            QueryWrapper<UserDepartmentEntity> departmentQueryWrapper = new QueryWrapper<UserDepartmentEntity>();
            departmentQueryWrapper.eq("user_account_id", userAccountEntity.getFid())
                    .eq("state", BaseStateEnum.ENABLED.getValue());
            UserDepartmentEntity userDepartmentEntity = userDepartmentMapper.selectOne(departmentQueryWrapper);
            if (userDepartmentEntity == null) {
                userDepartmentEntity = UserDepartmentPojoInitialize.generateSimpleInsertEntity(userAccountEntity.getFid(), userAccountVo.getBelongDepartmentId(), loginUser);
                userDepartmentMapper.insert(userDepartmentEntity);
            } else {
                userDepartmentEntity.setDefineDepartmentId(userAccountVo.getBelongDepartmentId());
                userDepartmentMapper.updateById(userDepartmentEntity);
            }
        } else {
            throw new BusinessException("关联用户与租户失败！更新用户失败！");
        }
        return changeCount;
    }



    @Override
    public Integer dealBatchRenewLock(UserAccountEntity loginUser, String[] lockIds, boolean isLock) throws Exception {
        int lockState = isLock ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue();
        Integer lockCount = 0;
        if (lockIds != null && lockIds.length > 0) {
            List<String> lockIdList = Lists.newArrayList(lockIds);
            //批量设置为 锁定
            lockCount = userAccountMapper.batchLockUserByIds(lockIdList, lockState, loginUser);
        }
        return lockCount;
    }


    @Override
    public Integer dealRenewLock(UserAccountEntity loginUser, Long lockId, boolean isLock) throws Exception {
        Short lockState = isLock ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue();
        UserAccountEntity userAccountEntity = UserAccountEntity.builder().fid(lockId).locked(lockState).build();
        if (loginUser != null) {
            userAccountEntity.setLastModifyerId(loginUser.getFid());
        }
        Integer lockCount = userAccountMapper.updateById(userAccountEntity);
        return lockCount;
    }

    @Override
    public Integer dealGrantRoleToUser(UserAccountEntity loginUser, Long userAccountId, Long[] checkIds) throws Exception {
        Integer changeCount = 0;
        Long loginUserId = loginUser != null ? loginUser.getFid() : null;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = userAccountMapper.clearAllRoleByUserId(userAccountId, loginUser);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的角色id 集合
            List<Long> oldCheckRoleIds = userRoleMapper.findAllRoleIdByUserAccountId(userAccountId, false);
            if (oldCheckRoleIds == null || oldCheckRoleIds.isEmpty()) {
                List<UserRoleEntity> addEntitys = new ArrayList<>();
                for (Long checkId : checkIds) {
                    addEntitys.add(UserRolePojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUser));
                }
                //批量新增行
                userRoleMapper.customBatchInsert(addEntitys);
            } else {
                List<Long> checkIdList = new ArrayList<>(Lists.newArrayList(checkIds));
                List<Long> enableIds = new ArrayList<>();
                List<Long> disabledIds = new ArrayList<>();
                Iterator<Long> oldCheckIter = oldCheckRoleIds.iterator();
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
                    userRoleMapper.batchUpdateStateByUserAccountId(userAccountId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUser);
                }
                if (disabledIds.isEmpty() == false) {
                    //批量禁用
                    userRoleMapper.batchUpdateStateByUserAccountId(userAccountId, disabledIds, BaseStateEnum.DELETE.getValue(), loginUser);
                }
                if (checkIdList.isEmpty() == false) {
                    //有新勾选的权限，需要新增行
                    List<UserRoleEntity> addEntitys = new ArrayList<>();
                    for (Long checkId : checkIdList) {
                        addEntitys.add(UserRolePojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUser));
                    }
                    //批量新增行
                    userRoleMapper.customBatchInsert(addEntitys);
                }
            }
        }

        return changeCount;
    }


    @Override
    public Integer dealGrantJobToUser(UserAccountEntity loginUser, Long userAccountId, Long[] checkIds) throws Exception {
        Integer changeCount = 0;
        Long loginUserId = loginUser != null ? loginUser.getFid() : null;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = userAccountMapper.clearAllJobByUserId(userAccountId, loginUser);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的职务id 集合
            List<Long> oldCheckJobIds = userJobMapper.findAllJobIdByUserAccountId(userAccountId, false);
            if (oldCheckJobIds == null || oldCheckJobIds.isEmpty()) {
                List<UserJobEntity> addEntitys = new ArrayList<>();
                for (Long checkId : checkIds) {
                    addEntitys.add(UserJobPojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUser));
                }
                //批量新增行
                userJobMapper.customBatchInsert(addEntitys);
            } else {
                List<Long> checkIdList = new ArrayList<>(Lists.newArrayList(checkIds));
                List<Long> enableIds = new ArrayList<>();
                List<Long> disabledIds = new ArrayList<>();
                Iterator<Long> oldCheckIter = oldCheckJobIds.iterator();
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
                    userJobMapper.batchUpdateStateByUserAccountId(userAccountId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUser);
                }
                if (disabledIds.isEmpty() == false) {
                    //批量禁用
                    userJobMapper.batchUpdateStateByUserAccountId(userAccountId, disabledIds, BaseStateEnum.DELETE.getValue(), loginUser);
                }
                if (checkIdList.isEmpty() == false) {
                    //有新勾选的权限，需要新增行
                    //批量新增行
                    List<UserJobEntity> addEntitys = new ArrayList<>();
                    for (Long checkId : checkIdList) {
                        addEntitys.add(UserJobPojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUser));
                    }
                    //批量新增行
                    userJobMapper.customBatchInsert(addEntitys);
                }
            }
        }
        return changeCount;
    }


    @Override
    public boolean dealCheckDuplicateKey(UserAccountVo userAccountVo, QueryWrapper<UserAccountEntity> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        wrapper.eq("account", userAccountVo.getAccount());
        wrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        return userAccountMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<UserAccountXlsOutModel> dealGetExportXlsModelList(UserAccountEntity loginUser, Long[] checkIds, QueryWrapper<UserAccountEntity> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        if (checkIds != null) {
            wrapper.in("fid", checkIds);
        }
        return UserAccountTransfer.entityListToXlsOutModels(userAccountMapper.selectList(wrapper));
    }


    @Override
    public Set<String> dealGetExistAccountSet(UserAccountEntity loginUser, Short state, QueryWrapper<UserAccountEntity> wrapper) {
        Set<String> accountSet = new HashSet<>();
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        if (state != null) {
            wrapper.eq("state", state);
        }
        List<UserAccountEntity> userAccountEntityList = userAccountMapper.selectList(wrapper);
        if (userAccountEntityList != null && userAccountEntityList.isEmpty() == false) {
            for (UserAccountEntity user : userAccountEntityList) {
                accountSet.add(user.getAccount());
            }
        }
        return accountSet;
    }

    @Override
    @Cacheable(value = RedisShiroKeyConstant.KEY_USER_ACCOUNT,key = "#userAccountId",condition = "#userAccountId!=null")
    public UserAccountEntity queryDbToCacheable(Long userAccountId) {
        if(userAccountId == null){
            return null ;
        }
        return userAccountMapper.selectById(userAccountId);
    }
}