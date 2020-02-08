package com.egg.manager.controller.user;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.exception.form.LoginFormFieldDeficiencyException;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.vo.user.UserAccountVo;
import com.egg.manager.webvo.login.LoginAccountVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;
import com.egg.manager.webvo.session.UserAccountToken;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Api(value = "API -  UserAccountController ",description = "用户账号接口")
@RestController
@RequestMapping("/user/user_account")
public class UserAccountController extends BaseController {

    @Autowired
    private UserAccountMapper userAccountMapper ;
    @Autowired
    private UserAccountService userAccountService ;
    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(value = "用户登录接口", notes = "账号密码方式登录接口", response = String.class)
    @PostMapping(value = "/login/account")
    public MyCommonResult<UserAccount> doLoginCheckByAccount(HttpServletRequest request, HttpServletResponse response, LoginAccountVo loginAccountVo) {
        MyCommonResult<UserAccount> result = new MyCommonResult<UserAccount>() ;
        try{
            //判断前端传递的
            if(loginAccountVo == null || checkFieldStrBlank(loginAccountVo.getAccount(),loginAccountVo.getPassword())) {
                throw new LoginFormFieldDeficiencyException("账号名或密码");
            }
            UserAccount userAccount = userAccountService.dealGetAccountByDTO(LoginAccountVo.transferToLoginAccountDTO(loginAccountVo));
            if(userAccount == null) {
                throw new Exception("账号未注册！");
            }   else {
                if(userAccount.getPassword().equals(loginAccountVo.getPassword())) {
                    UserAccountToken userAccountToken = UserAccountToken.gainByUserAccount(userAccount) ;
                     //账号密码验证通过
                    result.setAccountToken(userAccountToken);
                    //redis30分钟过期
                    redisHelper.hashTtlPut(redisPropsOfShiroCache.getUserAccountKey(),userAccountToken.getAccount(),userAccountToken,redisPropsOfShiroCache.getUserAccountTtl());
                }   else {
                    throw new Exception("账号密码不匹配！");
                }
            }
            dealCommonSuccessCatch(result,"用户登录:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return result ;
    }


    @ApiOperation(value = "查询用户信息列表", notes = "查询用户信息列表", response = String.class)
    @PostMapping(value = "/getAllUserAccounts")
    public MyCommonResult<UserAccountVo> doGetAllUserAccounts(HttpServletRequest request, HttpServletResponse response,String queryObj,String paginationObj) {
        MyCommonResult<UserAccountVo> result = new MyCommonResult<UserAccountVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            userAccountService.dealGetUserAccountPages(result,queryFormFieldBeanList,paginationBean) ;
            dealCommonSuccessCatch(result,"查询用户信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询用户信息", notes = "根据用户id查询用户信息", response = String.class)
    @PostMapping(value = "/getUserAccountById")
    public MyCommonResult<UserAccountVo> doGetUserAccountById(HttpServletRequest request, HttpServletResponse response,String accountId) {
        MyCommonResult<UserAccountVo> result = new MyCommonResult<UserAccountVo>() ;
        try{
            UserAccount account = userAccountMapper.selectById(accountId);
            result.setBean(UserAccountVo.transferEntityToVo(account));
            dealCommonSuccessCatch(result,"查询用户信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }



    @ApiOperation(value = "新增用户", notes = "表单方式新增用户", response = String.class)
    @PostMapping(value = "/doAddUserAccount")
    public MyCommonResult doAddUserAccount(HttpServletRequest request, HttpServletResponse response, UserAccountVo userAccountVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer addCount = 0 ;
        try{
            if(userAccountVo == null) {
                throw new Exception("未接收到有效的用户信息！");
            }   else {
                addCount = userAccountService.dealAddUserAccount(userAccountVo) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增用户:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新用户信息", notes = "表单方式更新用户信息", response = String.class)
    @PostMapping(value = "/doUpdateUserAccount")
    public MyCommonResult doUpdateUserAccount(HttpServletRequest request, HttpServletResponse response, UserAccountVo userAccountVo){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            if(userAccountVo == null) {
                throw new Exception("未接收到有效的用户信息！");
            }   else {
                changeCount = userAccountService.dealUpdateUserAccount(userAccountVo,false) ;
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新用户:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "批量删除用户", notes = "根据用户id批量删除用户", response = String.class)
    @PostMapping(value = "/batchDelUserAccountByIds")
    public MyCommonResult doBatchDeleteUserAccountById(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(delIds != null && delIds.length > 0) {
                //批量伪删除
                delCount = userAccountService.dealDelUserAccountByArr(delIds);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"批量删除用户:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "删除用户", notes = "根据用户id删除用户", response = String.class)
    @PostMapping(value = "/delOneUserAccountById")
    public MyCommonResult doDelOneUserAccountById(HttpServletRequest request, HttpServletResponse response,String delId){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(StringUtils.isNotBlank(delId)){
                delCount = userAccountService.dealDelUserAccount(delId);
                dealCommonSuccessCatch(result,"删除用户:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "修改用户锁定状态", notes = "根据用户id批量锁定或解锁用户", response = String.class)
    @PostMapping(value = "/batchLockUserAccountByIds")
    public MyCommonResult doBatchLockUserAccountById(HttpServletRequest request, HttpServletResponse response,String[] lockIds,Boolean lockFlag){
        MyCommonResult result = new MyCommonResult() ;
        Integer lockCount = 0;
        try{
            //操作类型为锁定？如果没传递值默认锁定
            lockFlag = lockFlag != null ? lockFlag : true ;
            String lockMsg = lockFlag ? "锁定" : "解锁" ;
            if(lockIds != null && lockIds.length > 0) {
                //批量伪删除
                lockCount = userAccountService.dealLockUserAccountByArr(lockIds,lockFlag);
                result.setCount(lockCount);
                dealCommonSuccessCatch(result,"批量"+lockMsg+"用户:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "修改用户锁定状态", notes = "根据用户id锁定或解锁用户", response = String.class)
    @PostMapping(value = "/lockOneUserAccountById")
    public MyCommonResult doLockOneUserAccountById(HttpServletRequest request, HttpServletResponse response,String lockId,Boolean lockFlag){
        MyCommonResult result = new MyCommonResult() ;
        Integer lockCount = 0;
        try{
            //操作类型为锁定？如果没传递值默认锁定
            lockFlag = lockFlag != null ? lockFlag : true ;
            String lockMsg = lockFlag ? "锁定" : "解锁" ;
            if(StringUtils.isNotBlank(lockId)){
                lockCount = userAccountService.dealLockUserAccount(lockId,lockFlag);
                dealCommonSuccessCatch(result,lockMsg+"用户:"+actionSuccessMsg);
            }
            result.setCount(lockCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }
}
