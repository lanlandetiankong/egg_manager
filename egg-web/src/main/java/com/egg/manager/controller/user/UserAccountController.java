package com.egg.manager.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.common.base.constant.redis.RedisShiroKeyConstant;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.exception.form.LoginFormFieldDeficiencyException;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.vo.login.LoginAccountVo;
import com.egg.manager.vo.session.UserAccountToken;
import com.egg.manager.vo.user.UserAccountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        }   catch (Exception e){
            this.dealCommonErrorCatch(result,e) ;
        }
        return result ;
    }


    @ApiOperation(value = "查询用户信息列表", notes = "查询用户信息列表", response = String.class)
    @PostMapping(value = "/getAllUserAccounts")
    public MyCommonResult<UserAccount> doGetAllUserAccouts(HttpServletRequest request, HttpServletResponse response,String queryObj) {
        MyCommonResult<UserAccount> result = new MyCommonResult<UserAccount>() ;
        try{
            Map<String,Object> queryMap = this.parseQueryJsonToMap(queryObj) ;
            List<UserAccount> userAccounts = userAccountMapper.selectByMap(queryMap);
            result.setResultList(userAccounts);
        }   catch (Exception e){
            this.dealCommonErrorCatch(result,e) ;
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
                UserAccount userAccount = UserAccountVo.transferVoToEntity(userAccountVo);
                userAccount.setFid(MyUUIDUtil.renderSimpleUUID());
                addCount = userAccountMapper.insert(userAccount) ;
            }
            result.setCount(addCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(result,e) ;
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
                UserAccount userAccount = UserAccountVo.transferVoToEntity(userAccountVo);
                changeCount = userAccountMapper.updateById(userAccount) ;
            }
            result.setCount(changeCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "批量删除用户", notes = "根据用户id批量删除用户", response = String.class)
    @PostMapping(value = "/batchDelUserAccountByIds")
    public MyCommonResult doDeleteUserAccountById(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(delIds != null && delIds.length > 0) {
                List<String> delIdList = Arrays.asList(delIds) ;
                int delCount = userAccountMapper.deleteBatchIds(delIdList) ;
                result.setCount(delCount);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(result,e) ;
        }
        return  result;
    }


}
