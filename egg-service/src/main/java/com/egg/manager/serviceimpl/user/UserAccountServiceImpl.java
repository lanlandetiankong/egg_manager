package com.egg.manager.serviceimpl.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.user.UserAccountStateEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.dto.login.LoginAccountDTO;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.role.RolePermission;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserRole;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.mapper.user.UserRoleMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.vo.user.UserAccountVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private CommonFuncService commonFuncService ;

    /**
     * 根据 LoginAccountDTO 取得对应的 UserAccount
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
     * TODO 取得 用户 的所有权限
     * @param userAccount 用户账号
     * @return
     */
    @Override
    public List<DefinePermission> dealGetAllPermssionByAccount(UserAccount userAccount) {

        return  null ;
    }



    /**
     * 分页查询 用户列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetUserAccountPages(MyCommonResult<UserAccountVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean){
        //解析 搜索条件
        EntityWrapper<UserAccount> userAccountEntityWrapper = new EntityWrapper<UserAccount>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到userAccountEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(userAccountEntityWrapper,queryFormFieldBeanList) ;
        //取得 总数
        Integer total = userAccountMapper.selectCount(userAccountEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<UserAccount> userAccounts = userAccountMapper.selectPage(rowBounds,userAccountEntityWrapper) ;
        result.setResultList(UserAccountVo.transferEntityToVoList(userAccounts));
    }


    /**
     * 用户账号-新增
     * @param userAccountVo
     * @throws Exception
     */
    @Override
    public Integer dealAddUserAccount(UserAccountVo userAccountVo) throws Exception{
        Date now = new Date() ;
        UserAccount userAccount = UserAccountVo.transferVoToEntity(userAccountVo);
        userAccount.setFid(MyUUIDUtil.renderSimpleUUID());
        userAccount.setVersion(commonFuncService.defaultVersion);
        userAccount.setState(BaseStateEnum.ENABLED.getValue());
        userAccount.setCreateTime(now);
        userAccount.setUpdateTime(now);
        Integer addCount = userAccountMapper.insert(userAccount) ;
        return addCount ;
    }


    /**
     * 用户账号-更新
     * @param userAccountVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Override
    public Integer dealUpdateUserAccount(UserAccountVo userAccountVo,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        userAccountVo.setUpdateTime(now);
        UserAccount userAccount = UserAccountVo.transferVoToEntity(userAccountVo);
        if(updateAll){  //是否更新所有字段
            changeCount = userAccountMapper.updateAllColumnById(userAccount) ;
        }   else {
            changeCount = userAccountMapper.updateById(userAccount) ;
        }
        return changeCount ;
    }



    /**
     * 用户账号-删除
     * @param delIds 要删除的用户账号id 集合
     * @throws Exception
     */
    @Override
    public Integer dealDelUserAccountByArr(String[] delIds) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = userAccountMapper.batchFakeDelByIds(delIdList);
        }
        return delCount ;
    }

    /**
     * 用户账号-删除
     * @param delId 要删除的用户账号id
     * @throws Exception
     */
    @Override
    public Integer dealDelUserAccount(String delId) throws Exception{
        UserAccount userAccount = UserAccount.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        Integer delCount = userAccountMapper.updateById(userAccount);
        return delCount ;
    }


    /**
     * 用户账号-锁定
     * @param lockIds 要锁定的用户账号id 集合
     * @param isLock 是否锁定
     * @throws Exception
     */
    @Override
    public Integer dealLockUserAccountByArr(String[] lockIds,boolean isLock) throws Exception{
        int lockState = isLock ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue() ;
        Integer lockCount = 0 ;
        if(lockIds != null && lockIds.length > 0) {
            List<String> lockIdList = Arrays.asList(lockIds) ;
            //批量设置为 锁定
            lockCount = userAccountMapper.batchLockUserByIds(lockIdList,lockState);
        }
        return lockCount ;
    }

    /**
     * 用户账号-锁定
     * @param lockId 要锁定的用户账号id
     * @param isLock 是否锁定
     * @throws Exception
     */
    @Override
    public Integer dealLockUserAccount(String lockId,boolean isLock) throws Exception{
        int lockState = isLock ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue() ;
        UserAccount userAccount = UserAccount.builder().fid(lockId).locked(lockState).build() ;
        Integer lockCount = userAccountMapper.updateById(userAccount);
        return lockCount ;
    }



    /**
     * 用户分配角色
     * @param userAccountId 用户id
     * @param checkIds 角色id集合
     * @throws Exception
     */
    @Override
    public Integer dealGrantRoleToUser(String userAccountId,String[] checkIds,String loginUserId) throws Exception{
        Integer changeCount = 0 ;
        if(checkIds == null || checkIds.length == 0){   //清空所有权限
            changeCount = userAccountMapper.clearAllRoleByUserId(userAccountId);
        }   else {
            changeCount = checkIds.length ;
            //取得曾勾选的角色id 集合
            List<String> oldCheckRoleIds = userRoleMapper.findAllRoleIdByUserAccountId(userAccountId,false);
            if(oldCheckRoleIds == null || oldCheckRoleIds.isEmpty()){
                List<UserRole> addEntitys = new ArrayList<>() ;
                for (String checkId : checkIds){
                    addEntitys.add(UserRole.generateSimpleInsertEntity(userAccountId,checkId,loginUserId));
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
                    userRoleMapper.batchUpdateStateByUserAccountId(userAccountId,enableIds,BaseStateEnum.ENABLED.getValue());
                }
                if(disabledIds.isEmpty() == false){   //批量禁用
                    userRoleMapper.batchUpdateStateByUserAccountId(userAccountId,disabledIds,BaseStateEnum.DELETE.getValue());
                }
                if(checkIdList.isEmpty() == false){     //有新勾选的权限，需要新增行
                    //批量新增行
                    List<UserRole> addEntitys = new ArrayList<>() ;
                    for (String checkId : checkIdList){
                        addEntitys.add(UserRole.generateSimpleInsertEntity(userAccountId,checkId,loginUserId));
                    }
                    //批量新增行
                    userRoleMapper.customBatchInsert(addEntitys);
                }
            }
        }

        return changeCount ;
    }
}
