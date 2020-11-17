package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.helper.PasswordHelper;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.api.services.em.user.basic.UserGroupService;
import com.egg.manager.api.services.em.user.basic.UserJobService;
import com.egg.manager.api.services.em.user.basic.UserRoleService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
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
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineGroupEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineJobEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.*;
import com.egg.manager.persistence.em.user.db.mysql.mapper.*;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.em.user.pojo.dto.UserAccountDto;
import com.egg.manager.persistence.em.user.pojo.dto.login.LoginAccountDTO;
import com.egg.manager.persistence.em.user.pojo.excel.export.user.UserAccountXlsOutModel;
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

    @Reference
    private UserJobService userJobService ;
    @Reference
    private UserGroupService userGroupService ;
    @Reference
    private UserRoleService userRoleService ;

    @Override
    public UserAccountEntity dealGetEntityByDTO(LoginAccountDTO loginAccountDTO) {
        QueryWrapper<UserAccountEntity> wrapper = new QueryWrapper<UserAccountEntity>();
        wrapper.setEntity(new UserAccountEntity());
        wrapper.eq("account", loginAccountDTO.getAccount())
                .eq("state", UserAccountStateEnum.ENABLED.getValue());
        return userAccountMapper.selectOne(wrapper);
    }


    @Override
    public WebResult dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, WebResult result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserAccountEntity> paginationBean,
                                            List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<UserAccountEntity> userAccountEntityWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = userAccountMapper.selectCount(userAccountEntityWrapper);
        result.settingPage(paginationBean, Long.valueOf(total));
        IPage iPage = userAccountMapper.selectPage(page, userAccountEntityWrapper);
        List<UserAccountEntity> userAccountEntities = iPage.getRecords();
        result.putResultList(UserAccountTransfer.transferEntityToVoList(userAccountEntities));
        return result;
    }

    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserAccountDto> paginationBean,
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
        result.settingPage(paginationBean, mpPagination.getTotal());
        result.putResultList(UserAccountTransfer.transferDtoToVoList(userAccountDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, UserAccountVo userAccountVo) throws Exception {
        if (this.dealCheckDuplicateKey(userAccountVo, new QueryWrapper<>())) {
            throw new MyDbException("唯一键[账号]不允许重复！");
        }
        UserAccountEntity userAccountEntity = UserAccountTransfer.transferVoToEntity(userAccountVo);
        userAccountEntity = super.doBeforeCreate(loginUserInfo, userAccountEntity);
        //密码-操作助手
        PasswordHelper passwordHelper = new PasswordHelper();
        //盐&md5密码加密
        passwordHelper.encryptPassword(userAccountEntity);
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
        if (StringUtils.isNotBlank(userAccountEntity.getFid()) && StringUtils.isNotBlank(userAccountVo.getBelongTenantId())) {
            UserTenantEntity userTenantEntity = UserTenantPojoInitialize.generateSimpleInsertEntity(userAccountEntity.getFid(), userAccountVo.getBelongTenantId(), loginUserInfo);
            userTenantMapper.insert(userTenantEntity);
        } else {
            throw new BusinessException("关联用户与租户失败！创建用户失败！");
        }

        //关联 部门
        if (StringUtils.isNotBlank(userAccountEntity.getFid()) && StringUtils.isNotBlank(userAccountVo.getBelongDepartmentId())) {
            UserDepartmentEntity userDepartmentEntity = UserDepartmentPojoInitialize.generateSimpleInsertEntity(userAccountEntity.getFid(), userAccountVo.getBelongDepartmentId(), loginUserInfo);
            userDepartmentMapper.insert(userDepartmentEntity);
        } else {
            throw new BusinessException("关联用户与部门失败！创建用户失败！");
        }
        return addCount;
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, UserAccountVo userAccountVo) throws Exception {
        QueryWrapper<UserAccountEntity> uniWrapper = new QueryWrapper<UserAccountEntity>()
                .ne("fid", userAccountVo.getFid());
        if (dealCheckDuplicateKey(userAccountVo, uniWrapper)) {
            //已有重复键值
            throw new MyDbException("唯一键[账号]不允许重复！");
        }
        Integer changeCount = 0;
        UserAccountEntity userAccountEntity = UserAccountTransfer.transferVoToEntity(userAccountVo);
        userAccountEntity = super.doBeforeUpdate(loginUserInfo, userAccountEntity);
        changeCount = userAccountMapper.updateById(userAccountEntity);
        //关联 租户
        if (StringUtils.isNotBlank(userAccountEntity.getFid()) && StringUtils.isNotBlank(userAccountVo.getBelongTenantId())) {
            QueryWrapper<UserTenantEntity> tenantQueryWrapper = new QueryWrapper<UserTenantEntity>();
            tenantQueryWrapper.eq("user_account_id", userAccountEntity.getFid())
                    .eq("state", BaseStateEnum.ENABLED.getValue());
            UserTenantEntity userTenantEntity = userTenantMapper.selectOne(tenantQueryWrapper);
            if (userTenantEntity == null) {
                userTenantEntity = UserTenantPojoInitialize.generateSimpleInsertEntity(userAccountEntity.getFid(), userAccountVo.getBelongTenantId(), loginUserInfo);
                userTenantMapper.insert(userTenantEntity);
            } else {
                userTenantEntity.setDefineTenantId(userAccountVo.getBelongTenantId());
                userTenantMapper.updateById(userTenantEntity);
            }
        } else {
            throw new BusinessException("关联用户与租户失败！更新用户失败！");
        }
        //关联 部门
        if (StringUtils.isNotBlank(userAccountEntity.getFid()) && StringUtils.isNotBlank(userAccountVo.getBelongDepartmentId())) {
            QueryWrapper<UserDepartmentEntity> departmentQueryWrapper = new QueryWrapper<UserDepartmentEntity>();
            departmentQueryWrapper.eq("user_account_id", userAccountEntity.getFid())
                    .eq("state", BaseStateEnum.ENABLED.getValue());
            UserDepartmentEntity userDepartmentEntity = userDepartmentMapper.selectOne(departmentQueryWrapper);
            if (userDepartmentEntity == null) {
                userDepartmentEntity = UserDepartmentPojoInitialize.generateSimpleInsertEntity(userAccountEntity.getFid(), userAccountVo.getBelongDepartmentId(), loginUserInfo);
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
    public Integer dealBatchRenewLock(CurrentLoginUserInfo loginUserInfo, String[] lockIds, boolean isLock) throws Exception {
        int lockState = isLock ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue();
        Integer lockCount = 0;
        if (lockIds != null && lockIds.length > 0) {
            List<String> lockIdList = Lists.newArrayList(lockIds);
            //批量设置为 锁定
            lockCount = userAccountMapper.batchLockUserByIds(lockIdList, lockState, loginUserInfo);
        }
        return lockCount;
    }


    @Override
    public Integer dealRenewLock(CurrentLoginUserInfo loginUserInfo, String lockId, boolean isLock) throws Exception {
        Short lockState = isLock ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue();
        UserAccountEntity userAccountEntity = UserAccountEntity.builder().fid(lockId).locked(lockState).build();
        if (loginUserInfo != null) {
            userAccountEntity.setLastModifyerId(loginUserInfo.getFid());
        }
        Integer lockCount = userAccountMapper.updateById(userAccountEntity);
        return lockCount;
    }

    @Override
    public Integer dealGrantRoleToUser(CurrentLoginUserInfo loginUserInfo, String userAccountId, String[] checkIds) throws Exception {
        Integer changeCount = 0;
        String loginUserId = loginUserInfo != null ? loginUserInfo.getFid() : null;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = userAccountMapper.clearAllRoleByUserId(userAccountId, loginUserInfo);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的角色id 集合
            List<String> oldCheckRoleIds = userRoleMapper.findAllRoleIdByUserAccountId(userAccountId, false);
            if (CollectionUtil.isEmpty(oldCheckRoleIds)) {
                List<UserRoleEntity> addEntitys = new ArrayList<>();
                for (String checkId : checkIds) {
                    addEntitys.add(UserRolePojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUserInfo));
                }
                //批量新增行
                userRoleService.saveBatch(addEntitys);
            } else {
                List<String> checkIdList = new ArrayList<>(Lists.newArrayList(checkIds));
                List<String> enableIds = new ArrayList<>();
                List<String> disabledIds = new ArrayList<>();
                Iterator<String> oldCheckIter = oldCheckRoleIds.iterator();
                while (oldCheckIter.hasNext()) {
                    String oldCheckId = oldCheckIter.next();
                    boolean isOldRow = checkIdList.contains(oldCheckId);
                    if (isOldRow) {
                        //原本有的数据行
                        enableIds.add(oldCheckId);
                        checkIdList.remove(oldCheckId);
                    } else {
                        disabledIds.add(oldCheckId);
                    }
                }
                if (CollectionUtil.isNotEmpty(enableIds)) {
                    //批量启用
                    userRoleMapper.batchUpdateStateByUserAccountId(userAccountId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(disabledIds)) {
                    //批量禁用
                    userRoleMapper.batchUpdateStateByUserAccountId(userAccountId, disabledIds, BaseStateEnum.DISABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(checkIdList)) {
                    //有新勾选的权限，需要新增行
                    List<UserRoleEntity> addEntitys = new ArrayList<>();
                    for (String checkId : checkIdList) {
                        addEntitys.add(UserRolePojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUserInfo));
                    }
                    //批量新增行
                    userRoleService.saveBatch(addEntitys);
                }
            }
        }

        return changeCount;
    }


    @Override
    public Integer dealGrantJobToUser(CurrentLoginUserInfo loginUserInfo, String userAccountId, String[] checkIds) throws Exception {
        Integer changeCount = 0;
        String loginUserId = loginUserInfo != null ? loginUserInfo.getFid() : null;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = userAccountMapper.clearAllJobByUserId(userAccountId, loginUserInfo);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的职务id 集合
            List<String> oldCheckJobIds = userJobMapper.findAllJobIdByUserAccountId(userAccountId, false);
            if (CollectionUtil.isEmpty(oldCheckJobIds)) {
                List<UserJobEntity> addEntitys = new ArrayList<>();
                for (String checkId : checkIds) {
                    addEntitys.add(UserJobPojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUserInfo));
                }
                //批量新增行
                userJobService.saveBatch(addEntitys);
            } else {
                List<String> checkIdList = new ArrayList<>(Lists.newArrayList(checkIds));
                List<String> enableIds = new ArrayList<>();
                List<String> disabledIds = new ArrayList<>();
                Iterator<String> oldCheckIter = oldCheckJobIds.iterator();
                while (oldCheckIter.hasNext()) {
                    String oldCheckId = oldCheckIter.next();
                    boolean isOldRow = checkIdList.contains(oldCheckId);
                    if (isOldRow) {
                        //原本有的数据行
                        enableIds.add(oldCheckId);
                        checkIdList.remove(oldCheckId);
                    } else {
                        disabledIds.add(oldCheckId);
                    }
                }
                if (CollectionUtil.isNotEmpty(enableIds)) {
                    //批量启用
                    userJobMapper.batchUpdateStateByUserAccountId(userAccountId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(disabledIds)) {
                    //批量禁用
                    userJobMapper.batchUpdateStateByUserAccountId(userAccountId, disabledIds, BaseStateEnum.DISABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(checkIdList)) {
                    //有新勾选的权限，需要新增行
                    //批量新增行
                    List<UserJobEntity> addEntitys = new ArrayList<>();
                    for (String checkId : checkIdList) {
                        addEntitys.add(UserJobPojoInitialize.generateSimpleInsertEntity(userAccountId, checkId, loginUserInfo));
                    }
                    //批量新增行
                    userJobService.saveBatch(addEntitys);
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
    public List<UserAccountXlsOutModel> dealGetExportXlsModelList(CurrentLoginUserInfo loginUserInfo, String[] checkIds, QueryWrapper<UserAccountEntity> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        wrapper.in(checkIds != null,"fid", checkIds);
        return UserAccountTransfer.entityListToXlsOutModels(userAccountMapper.selectList(wrapper));
    }


    @Override
    public Set<String> dealGetExistAccountSet(CurrentLoginUserInfo loginUserInfo, Short state, QueryWrapper<UserAccountEntity> wrapper) {
        Set<String> accountSet = new HashSet<>();
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        wrapper.eq(state != null,"state", state);
        List<UserAccountEntity> userAccountEntityList = userAccountMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(userAccountEntityList)) {
            for (UserAccountEntity user : userAccountEntityList) {
                accountSet.add(user.getAccount());
            }
        }
        return accountSet;
    }

    @Override
    @Cacheable(value = RedisShiroKeyConstant.KEY_USER_ACCOUNT,key = "#userAccountId",condition = "#userAccountId!=null")
    public CurrentLoginUserInfo queryDbToCacheable(String userAccountId) {
        if(userAccountId == null){
            return null ;
        }
        //用户信息
        UserAccountEntity userAccountEntity = userAccountMapper.selectById(userAccountId);
        //用户所属职务
        List<DefineJobEntity> belongJobs = userJobService.queryAllUserBelong(userAccountId);
        List<DefineGroupEntity> belongGroups = userGroupService.queryAllUserBelong(userAccountId);
        CurrentLoginUserInfo loginUserInfo = CurrentLoginUserInfo.transferFromEntity(userAccountEntity);
        loginUserInfo.setBelongJobList(belongJobs);
        loginUserInfo.setBelongGroupList(belongGroups);
        return loginUserInfo;
    }

    @Override
    public boolean reflushSecurePwd(CurrentLoginUserInfo loginUserInfo) {
        QueryWrapper<UserAccountEntity> query = new QueryWrapper<>();
        query.isNotNull("password");
        List<UserAccountEntity> userAccountEntities = userAccountMapper.selectList(query);
        PasswordHelper passwordHelper = new PasswordHelper() ;
        for (UserAccountEntity userAccountEntity : userAccountEntities){
            passwordHelper.encryptPassword(userAccountEntity);
        }
        this.updateBatchById(userAccountEntities);
        return true;
    }
}
