package com.egg.manager.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.common.base.constant.define.UserAccountConstant;
import com.egg.manager.common.base.constant.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.constant.redis.RedisShiroKeyConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scala.Int;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    public MyCommonResult<UserAccount> doGetAllUserAccouts(HttpServletRequest request, HttpServletResponse response,String queryObj,String paginationObj) {
        MyCommonResult<UserAccount> result = new MyCommonResult<UserAccount>() ;
        try{
            //解析 搜索条件
            Map<String,Object> queryMap = this.parseQueryJsonToMap(queryObj) ;
            queryMap.put("state", BaseStateEnum.ENABLED.getValue());
            EntityWrapper<UserAccount> userAccountEntityWrapper = new EntityWrapper<UserAccount>();
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            RowBounds rowBounds = this.parsePaginationToRowBounds(paginationBean) ;
            //取得 总数
            Integer total = userAccountMapper.selectCount(userAccountEntityWrapper);
            result.myAntdvPaginationBeanSet(paginationBean,total);
            //调用方法将查询条件设置到userAccountEntityWrapper
            dealSetConditionsMapToEntityWrapper(userAccountEntityWrapper,queryMap) ;
            List<UserAccount> userAccounts = userAccountMapper.selectPage(rowBounds,userAccountEntityWrapper) ;
            //List<UserAccount> userAccounts = userAccountMapper.selectByMap(queryMap);
            result.setResultList(UserAccountVo.transferEntityToVoList(userAccounts));
            dealCommonSuccessCatch(result,"查询用户信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询用户信息", notes = "根据用户id查询用户信息", response = String.class)
    @PostMapping(value = "/getUserAccountById")
    public MyCommonResult<UserAccount> doGetUserAccountById(HttpServletRequest request, HttpServletResponse response,String accountId) {
        MyCommonResult<UserAccount> result = new MyCommonResult<UserAccount>() ;
        try{
            UserAccount vo = userAccountMapper.selectById(accountId);
            result.setBean(vo);
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
                Date now = new Date() ;
                UserAccount userAccount = UserAccountVo.transferVoToEntity(userAccountVo);
                userAccount.setFid(MyUUIDUtil.renderSimpleUUID());
                userAccount.setState(BaseStateEnum.ENABLED.getValue());
                userAccount.setPassword(UserAccountConstant.DEFAULT_PWD);
                userAccount.setCreateTime(now);
                userAccount.setUpdateTime(now);
                addCount = userAccountMapper.insert(userAccount) ;
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
                Date now = new Date() ;
                userAccountVo.setUpdateTime(now);
                UserAccount userAccount = UserAccountVo.transferVoToEntity(userAccountVo);
                changeCount = userAccountMapper.updateById(userAccount) ;
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"新增用户:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "批量删除用户", notes = "根据用户id批量删除用户", response = String.class)
    @PostMapping(value = "/batchDelUserAccountByIds")
    public MyCommonResult doBatchDeleteUserAccountById(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(delIds != null && delIds.length > 0) {
                List<String> delIdList = Arrays.asList(delIds) ;
                //批量伪删除
                int delCount = userAccountMapper.batchFakeDelByIds(delIdList);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"批量删除用户:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "删除用户", notes = "根据用户id删除用户", response = String.class)
    @PostMapping(value = "/delOneUserAccountByIds")
    public MyCommonResult doDelOneUserAccountById(HttpServletRequest request, HttpServletResponse response,String delId){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(StringUtils.isNotBlank(delId)){
                UserAccount userAccount = UserAccount.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
                int delCount = userAccountMapper.updateById(userAccount);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除用户:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

}
