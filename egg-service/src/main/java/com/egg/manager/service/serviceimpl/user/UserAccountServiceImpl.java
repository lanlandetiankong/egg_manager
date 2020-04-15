package com.egg.manager.service.serviceimpl.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.constant.define.UserAccountConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.base.enums.user.UserAccountStateEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.exception.MyDbException;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.dto.login.LoginAccountDTO;
import com.egg.manager.persistence.dto.user.UserAccountDto;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.entity.user.UserJob;
import com.egg.manager.persistence.entity.user.UserRole;
import com.egg.manager.persistence.entity.user.UserTenant;
import com.egg.manager.persistence.excel.user.UserAccountXlsModel;
import com.egg.manager.persistence.mapper.user.UserAccountMapper;
import com.egg.manager.persistence.mapper.user.UserJobMapper;
import com.egg.manager.persistence.mapper.user.UserRoleMapper;
import com.egg.manager.persistence.mapper.user.UserTenantMapper;
import com.egg.manager.persistence.vo.user.UserAccountVo;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.service.service.CommonFuncService;
import com.egg.manager.service.service.user.UserAccountService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper,UserAccount> implements UserAccountService{



    @Autowired
    private UserAccountMapper userAccountMapper ;
    @Autowired
    private UserRoleMapper userRoleMapper ;
    @Autowired
    private UserJobMapper userJobMapper ;
    @Autowired
    private UserTenantMapper userTenantMapper ;
    @Autowired
    private CommonFuncService commonFuncService ;

    /**
     * 根据 LoginAccountDTO 取得对应的 UserAccountXlsModel
     * @param loginAccountDTO
     * @return
     */
    @Override
    public UserAccount dealGetAccountByDTO(LoginAccountDTO loginAccountDTO) {
        EntityWrapper<UserAccount> wrapper = new EntityWrapper<UserAccount>() ;
        wrapper.setEntity(new UserAccount());
        wrapper.where("account={0}",loginAccountDTO.getAccount())
                .and("state>{0}", UserAccountStateEnum.DELETE) ;
        return selectOne(wrapper);
    }





    /**
     * 分页查询 用户列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetUserAccountPages(MyCommonResult<UserAccountVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                        List<AntdvSortBean> sortBeans){
        //解析 搜索条件
        EntityWrapper<UserAccount> userAccountEntityWrapper = new EntityWrapper<UserAccount>();

        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到userAccountEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(userAccountEntityWrapper,queryFormFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                userAccountEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }

        //取得 总数
        Integer total = userAccountMapper.selectCount(userAccountEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<UserAccount> userAccounts = userAccountMapper.selectPage(rowBounds,userAccountEntityWrapper) ;
        result.setResultList(UserAccountVo.transferEntityToVoList(userAccounts));
    }

    /**
     * 分页查询 用户 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetUserAccountDtoPages(MyCommonResult<UserAccountVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                        List<AntdvSortBean> sortBeans){
        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<QueryFormFieldBean> queryFieldBeanListTemp = new ArrayList<QueryFormFieldBean>();
        //用户与租户关联 的外表-搜索条件
        List<QueryFormFieldBean> queryTenantFieldBeanList = new ArrayList<QueryFormFieldBean>();
        for(QueryFormFieldBean queryFormFieldBean : queryFieldBeanList){
            //外部关联名，条件需单独识别
            String foreignName = queryFormFieldBean.getForeignName() ;
            if(StringUtils.isBlank(foreignName)){
                queryFieldBeanListTemp.add(queryFormFieldBean);
            }   else {
                if(FOREIGN_NAME_OF_USER_TENANT.equals(foreignName)){   //foreignName匹配一致的会被认定为 指定表的 搜索条件
                    queryTenantFieldBeanList.add(queryFormFieldBean);
                }
            }
        }
        List<UserAccountDto> userAccountDtoList = userAccountMapper.selectQueryPage(mpPagination, queryFieldBeanListTemp,sortBeans,queryTenantFieldBeanList);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(UserAccountVo.transferDtoToVoList(userAccountDtoList));
    }


    /**
     * 用户账号-新增
     * @param userAccountVo
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealAddUserAccount(UserAccountVo userAccountVo,UserAccount loginUser) throws Exception{
        if(this.dealCheckDuplicateKey(userAccountVo,new EntityWrapper<>())){
            throw new MyDbException("唯一键[账号]不允许重复！");
        }
        Date now = new Date() ;
        UserAccount userAccount = UserAccountVo.transferVoToEntity(userAccountVo);
        userAccount.setFid(MyUUIDUtil.renderSimpleUUID());
        if(null == userAccountVo.getLocked()){  //如果没设置值，默认不锁定
            userAccount.setLocked(SwitchStateEnum.Close.getValue());
        }
        userAccount.setUserType(UserAccountBaseTypeEnum.SimpleUser.getValue());
        userAccount.setState(BaseStateEnum.ENABLED.getValue());
        userAccount.setCreateTime(now);
        userAccount.setUpdateTime(now);
        if(StringUtils.isBlank(userAccountVo.getPassword())){
            String pwd = UserAccountConstant.DEFAULT_PWD ;
            userAccountVo.setPassword(pwd);
            userAccount.setPassword(pwd);
        }
        if(loginUser != null){
            userAccount.setCreateUserId(loginUser.getFid());
            userAccount.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = userAccountMapper.insert(userAccount) ;
        //关联 租户
        if(StringUtils.isNotBlank(userAccount.getFid()) && StringUtils.isNotBlank(userAccountVo.getBelongTenantId())){
            UserTenant userTenant = UserTenant.generateSimpleInsertEntity(userAccount.getFid(),userAccountVo.getBelongTenantId(),loginUser);
            userTenantMapper.insert(userTenant);
        }   else {
            throw new BusinessException("关联用户与租户失败！创建用户失败！") ;
        }
        return addCount ;
    }


    /**
     * 用户账号-更新
     * @param userAccountVo
     * @param loginUser 当前登录用户
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealUpdateUserAccount(UserAccountVo userAccountVo,UserAccount loginUser,boolean updateAll) throws Exception{
        Wrapper<UserAccount> uniWrapper  = new EntityWrapper<UserAccount>()
                .ne("fid",userAccountVo.getFid());
        if(dealCheckDuplicateKey(userAccountVo,uniWrapper)){    //已有重复键值
            throw new MyDbException("唯一键[账号]不允许重复！");
        }
        Integer changeCount = 0;
        Date now = new Date() ;
        userAccountVo.setUpdateTime(now);
        UserAccount userAccount = UserAccountVo.transferVoToEntity(userAccountVo);
        if(loginUser != null){
            userAccount.setLastModifyerId(loginUser.getFid());
        }
        if(updateAll){  //是否更新所有字段
            changeCount = userAccountMapper.updateAllColumnById(userAccount) ;
        }   else {
            changeCount = userAccountMapper.updateById(userAccount) ;
        }
        //关联 租户
        if(StringUtils.isNotBlank(userAccount.getFid()) && StringUtils.isNotBlank(userAccountVo.getBelongTenantId())){
            UserTenant userTenantQuery = UserTenant.builder().userAccountId(userAccount.getFid()).state(BaseStateEnum.ENABLED.getValue()).build();
            UserTenant userTenant = userTenantMapper.selectOne(userTenantQuery);
            userTenant.setDefineTenantId(userAccountVo.getBelongTenantId());
            userTenantMapper.updateById(userTenant);
        }   else {
            throw new BusinessException("关联用户与租户失败！更新用户失败！") ;
        }
        return changeCount ;
    }



    /**
     * 用户账号-删除
     * @param delIds 要删除的用户账号id 集合
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelUserAccountByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = userAccountMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 用户账号-删除
     * @param delId 要删除的用户账号id
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelUserAccount(String delId,UserAccount loginUser) throws Exception{
        UserAccount userAccount = UserAccount.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            userAccount.setLastModifyerId(loginUser.getFid());
        }
        Integer delCount = userAccountMapper.updateById(userAccount);
        return delCount ;
    }


    /**
     * 用户账号-锁定
     * @param lockIds 要锁定的用户账号id 集合
     *  @param loginUser 当前登录用户
     * @param isLock 是否锁定
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealLockUserAccountByArr(String[] lockIds,UserAccount loginUser,boolean isLock) throws Exception{
        int lockState = isLock ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue() ;
        Integer lockCount = 0 ;
        if(lockIds != null && lockIds.length > 0) {
            List<String> lockIdList = Arrays.asList(lockIds) ;
            //批量设置为 锁定
            lockCount = userAccountMapper.batchLockUserByIds(lockIdList,lockState,loginUser);
        }
        return lockCount ;
    }

    /**
     * 用户账号-锁定
     * @param lockId 要锁定的用户账号id
     * @param loginUser 当前登录用户
     * @param isLock 是否锁定
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealLockUserAccount(String lockId,UserAccount loginUser,boolean isLock) throws Exception{
        int lockState = isLock ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue() ;
        UserAccount userAccount = UserAccount.builder().fid(lockId).locked(lockState).build() ;
        if(loginUser != null){
            userAccount.setLastModifyerId(loginUser.getFid());
        }
        Integer lockCount = userAccountMapper.updateById(userAccount);
        return lockCount ;
    }



    /**
     * 用户分配角色
     * @param userAccountId 用户id
     * @param checkIds 角色id集合
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealGrantRoleToUser(String userAccountId,String[] checkIds,UserAccount loginUser) throws Exception{
        Integer changeCount = 0 ;
        String loginUserId = loginUser != null ? loginUser.getFid() : null ;
        if(checkIds == null || checkIds.length == 0){   //清空所有权限
            changeCount = userAccountMapper.clearAllRoleByUserId(userAccountId,loginUser);
        }   else {
            changeCount = checkIds.length ;
            //取得曾勾选的角色id 集合
            List<String> oldCheckRoleIds = userRoleMapper.findAllRoleIdByUserAccountId(userAccountId,false);
            if(oldCheckRoleIds == null || oldCheckRoleIds.isEmpty()){
                List<UserRole> addEntitys = new ArrayList<>() ;
                for (String checkId : checkIds){
                    addEntitys.add(UserRole.generateSimpleInsertEntity(userAccountId,checkId,loginUser));
                }
                //批量新增行
                userRoleMapper.customBatchInsert(addEntitys);
            }   else {
                List<String> checkIdList = new ArrayList<>(Arrays.asList(checkIds));
                List<String> enableIds = new ArrayList<>() ;
                List<String> disabledIds = new ArrayList<>() ;
                Iterator<String> oldCheckIter = oldCheckRoleIds.iterator();
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
                    userRoleMapper.batchUpdateStateByUserAccountId(userAccountId,enableIds,BaseStateEnum.ENABLED.getValue(),loginUser);
                }
                if(disabledIds.isEmpty() == false){   //批量禁用
                    userRoleMapper.batchUpdateStateByUserAccountId(userAccountId,disabledIds,BaseStateEnum.DELETE.getValue(),loginUser);
                }
                if(checkIdList.isEmpty() == false){     //有新勾选的权限，需要新增行
                    //批量新增行
                    List<UserRole> addEntitys = new ArrayList<>() ;
                    for (String checkId : checkIdList){
                        addEntitys.add(UserRole.generateSimpleInsertEntity(userAccountId,checkId,loginUser));
                    }
                    //批量新增行
                    userRoleMapper.customBatchInsert(addEntitys);
                }
            }
        }

        return changeCount ;
    }


    /**
     * 用户分配职务
     * @param userAccountId 用户id
     * @param checkIds 职务id集合
     * @param loginUser 当前登录用户
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealGrantJobToUser(String userAccountId,String[] checkIds,UserAccount loginUser) throws Exception{
        Integer changeCount = 0 ;
        String loginUserId = loginUser != null ? loginUser.getFid() : null ;
        if(checkIds == null || checkIds.length == 0){   //清空所有权限
            changeCount = userAccountMapper.clearAllJobByUserId(userAccountId,loginUser);
        }   else {
            changeCount = checkIds.length ;
            //取得曾勾选的职务id 集合
            List<String> oldCheckJobIds = userJobMapper.findAllJobIdByUserAccountId(userAccountId,false);
            if(oldCheckJobIds == null || oldCheckJobIds.isEmpty()){
                List<UserJob> addEntitys = new ArrayList<>() ;
                for (String checkId : checkIds){
                    addEntitys.add(UserJob.generateSimpleInsertEntity(userAccountId,checkId,loginUser));
                }
                //批量新增行
                userJobMapper.customBatchInsert(addEntitys);
            }   else {
                List<String> checkIdList = new ArrayList<>(Arrays.asList(checkIds));
                List<String> enableIds = new ArrayList<>() ;
                List<String> disabledIds = new ArrayList<>() ;
                Iterator<String> oldCheckIter = oldCheckJobIds.iterator();
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
                    userJobMapper.batchUpdateStateByUserAccountId(userAccountId,enableIds,BaseStateEnum.ENABLED.getValue(),loginUser);
                }
                if(disabledIds.isEmpty() == false){   //批量禁用
                    userJobMapper.batchUpdateStateByUserAccountId(userAccountId,disabledIds,BaseStateEnum.DELETE.getValue(),loginUser);
                }
                if(checkIdList.isEmpty() == false){     //有新勾选的权限，需要新增行
                    //批量新增行
                    List<UserJob> addEntitys = new ArrayList<>() ;
                    for (String checkId : checkIdList){
                        addEntitys.add(UserJob.generateSimpleInsertEntity(userAccountId,checkId,loginUser));
                    }
                    //批量新增行
                    userJobMapper.customBatchInsert(addEntitys);
                }
            }
        }
        return changeCount ;
    }

    /**
     * 验证 数据库 中的唯一冲突
     * @param userAccountVo
     * @param wrapper
     * @return
     */
    @Override
    public boolean dealCheckDuplicateKey(UserAccountVo userAccountVo, Wrapper<UserAccount> wrapper){
        wrapper = wrapper != null ? wrapper : new EntityWrapper<>() ;
        wrapper.eq("account",userAccountVo.getAccount()) ;
        wrapper.eq("state",BaseStateEnum.ENABLED.getValue()) ;
        return userAccountMapper.selectCount(wrapper) > 0 ;
    }


    @Override
    public List<UserAccountXlsModel> dealGetExportXlsModelList(String[] checkIds, Wrapper<UserAccount> wrapper){
        wrapper = wrapper != null ? wrapper : new EntityWrapper<>() ;
        if(checkIds != null){
            wrapper.in("fid",checkIds);
        }
        return UserAccountXlsModel.voListToXlsModels(userAccountMapper.selectList(wrapper));
    }
}
