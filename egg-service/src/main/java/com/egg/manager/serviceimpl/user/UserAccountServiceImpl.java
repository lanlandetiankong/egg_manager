package com.egg.manager.serviceimpl.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.user.UserAccountStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.dto.login.LoginAccountDTO;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.vo.user.UserAccountVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
}
