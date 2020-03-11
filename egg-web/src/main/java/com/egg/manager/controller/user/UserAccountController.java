package com.egg.manager.controller.user;

import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.define.DefineJob;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.exception.form.LoginFormFieldDeficiencyException;
import com.egg.manager.mapper.define.DefineJobMapper;
import com.egg.manager.mapper.define.DefinePermissionMapper;
import com.egg.manager.mapper.define.DefineRoleMapper;
import com.egg.manager.mapper.user.UserAccountMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.vo.define.DefineJobVo;
import com.egg.manager.vo.define.DefinePermissionVo;
import com.egg.manager.vo.define.DefineRoleVo;
import com.egg.manager.vo.user.UserAccountVo;
import com.egg.manager.webvo.login.LoginAccountVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.webvo.session.UserAccountToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "API ==>>  UserAccountController ",description = "用户账号接口")
@RestController
@RequestMapping("/user/user_account")
public class UserAccountController extends BaseController {

    @Autowired
    private UserAccountMapper userAccountMapper ;
    @Autowired
    private DefineRoleMapper defineRoleMapper ;
    @Autowired
    private DefinePermissionMapper definePermissionMapper ;
    @Autowired
    private DefineJobMapper defineJobMapper ;
    @Autowired
    private UserAccountService userAccountService ;
    @Autowired
    private CommonFuncService commonFuncService ;
    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @OperLog(modelName="UserAccountController",action="用户登录接口",description = "账号密码方式登录接口")
    @ApiOperation(value = "用户登录接口", notes = "账号密码方式登录接口", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginAccountVo",value = "要登录用户的信息", required = true,dataTypeClass=LoginAccountVo.class),
    })
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
                    this.dealSetTokenToRedis(userAccountToken) ;
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


    @OperLog(modelName="UserAccountController",action="查询用户信息列表",description = "查询用户信息列表")
    @ApiOperation(value = "查询用户信息列表", notes = "查询用户信息列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllUserAccounts")
    public MyCommonResult<UserAccountVo> doGetAllUserAccounts(HttpServletRequest request, HttpServletResponse response,String queryObj,String paginationObj,String sortObj) {
        MyCommonResult<UserAccountVo> result = new MyCommonResult<UserAccountVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            userAccountService.dealGetUserAccountPages(result,queryFormFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询用户信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询用户信息", notes = "根据用户id查询用户信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserAccountController",action="查询用户信息",description = "根据用户id查询用户信息")
    @PostMapping(value = "/getUserAccountById")
    public MyCommonResult<UserAccountVo> doGetUserAccountById(HttpServletRequest request, HttpServletResponse response,String accountId) {
        MyCommonResult<UserAccountVo> result = new MyCommonResult<UserAccountVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            UserAccount account = userAccountMapper.selectById(accountId);
            result.setBean(UserAccountVo.transferEntityToVo(account));
            dealCommonSuccessCatch(result,"查询用户信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "查询用户所拥有的角色", notes = "根据用户id查询用户已有的角色", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserAccountController",action="查询用户所拥有的角色",description = "根据用户id查询用户已有的角色")
    @PostMapping(value = "/getAllRoleByUserAccountId")
    public MyCommonResult<DefineRoleVo> doGetAllRoleByUserAccountId(HttpServletRequest request, HttpServletResponse response, String userAccountId) {
        MyCommonResult<DefineRoleVo> result = new MyCommonResult<DefineRoleVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            List<DefineRole> defineRoleList = defineRoleMapper.findAllRoleByUserAcccountId(userAccountId,BaseStateEnum.ENABLED.getValue());
            result.setResultList(DefineRoleVo.transferEntityToVoList(defineRoleList));
            dealCommonSuccessCatch(result,"查询用户所拥有的角色:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "查询用户所拥有的权限", notes = "根据用户id查询用户已有的权限", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserAccountController",action="查询用户所拥有的权限",description = "根据用户id查询用户已有的权限")
    @PostMapping(value = "/getAllPermissionByUserAccountId")
    public MyCommonResult<DefinePermissionVo> doGetAllPermissionByUserAccountId(HttpServletRequest request, HttpServletResponse response, String userAccountId) {
        MyCommonResult<DefinePermissionVo> result = new MyCommonResult<DefinePermissionVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            List<DefinePermission> definePermissionList = definePermissionMapper.findAllPermissionByUserAcccountId(userAccountId);
            result.setResultList(DefinePermissionVo.transferEntityToVoList(definePermissionList));
            dealCommonSuccessCatch(result,"查询用户所拥有的权限:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询用户所拥有的职务", notes = "根据用户id查询用户已有的职务", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserAccountController",action="查询用户所拥有的职务",description = "根据用户id查询用户已有的职务")
    @PostMapping(value = "/getAllJobByUserAccountId")
    public MyCommonResult<DefineJobVo> doGetAllJobByUserAccountId(HttpServletRequest request, HttpServletResponse response, String userAccountId) {
        MyCommonResult<DefineJobVo> result = new MyCommonResult<DefineJobVo>() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            List<DefineJob> defineJobList = defineJobMapper.findAllJobByUserAcccountId(userAccountId,BaseStateEnum.ENABLED.getValue());
            result.setResultList(DefineJobVo.transferEntityToVoList(defineJobList));
            dealCommonSuccessCatch(result,"查询用户所拥有的职务:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "新增用户", notes = "表单方式新增用户", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserAccountController",action="新增用户",description = "表单方式新增用户")
    @PostMapping(value = "/doAddUserAccount")
    public MyCommonResult doAddUserAccount(HttpServletRequest request, HttpServletResponse response){
        MyCommonResult result = new MyCommonResult() ;
        Integer addCount = 0 ;
        try{
            UserAccountVo userAccountVo = this.getBeanFromRequest(request,"formObj",UserAccountVo.class,true) ;
            //当前登录用户
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(userAccountVo == null) {
                throw new Exception("未接收到有效的用户信息！");
            }   else {
                addCount = userAccountService.dealAddUserAccount(userAccountVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增用户:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新用户信息", notes = "表单方式更新用户信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserAccountController",action="更新用户信息",description = "表单方式更新用户信息")
    @PostMapping(value = "/doUpdateUserAccount")
    public MyCommonResult doUpdateUserAccount(HttpServletRequest request, HttpServletResponse response){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            UserAccountVo userAccountVo = this.getBeanFromRequest(request,"formObj",UserAccountVo.class,true) ;
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(userAccountVo == null) {
                throw new Exception("未接收到有效的用户信息！");
            }   else {
                changeCount = userAccountService.dealUpdateUserAccount(userAccountVo,loginUser,false) ;
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新用户:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="UserAccountController",action="批量删除用户",description = "根据用户id批量删除用户")
    @ApiOperation(value = "批量删除用户", notes = "根据用户id批量删除用户", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的用户id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelUserAccountByIds")
    public MyCommonResult doBatchDeleteUserAccountById(HttpServletRequest request, HttpServletResponse response,String[] delIds){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(delIds != null && delIds.length > 0) {
                //批量伪删除
                delCount = userAccountService.dealDelUserAccountByArr(delIds,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"批量删除用户:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="UserAccountController",action="删除用户",description = "根据用户id删除用户")
    @ApiOperation(value = "删除用户", notes = "根据用户id删除用户", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的用户id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneUserAccountById")
    public MyCommonResult doDelOneUserAccountById(HttpServletRequest request, HttpServletResponse response,String delId){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(StringUtils.isNotBlank(delId)){
                delCount = userAccountService.dealDelUserAccount(delId,loginUser);
                dealCommonSuccessCatch(result,"删除用户:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "修改用户锁定状态", notes = "根据用户id批量锁定或解锁用户", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserAccountController",action="修改用户锁定状态",description = "根据用户id批量锁定或解锁用户")
    @PostMapping(value = "/batchLockUserAccountByIds")
    public MyCommonResult doBatchLockUserAccountById(HttpServletRequest request, HttpServletResponse response,String[] lockIds,Boolean lockFlag){
        MyCommonResult result = new MyCommonResult() ;
        Integer lockCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            //操作类型为锁定？如果没传递值默认锁定
            lockFlag = lockFlag != null ? lockFlag : true ;
            String lockMsg = lockFlag ? "锁定" : "解锁" ;
            if(lockIds != null && lockIds.length > 0) {
                //批量伪删除
                lockCount = userAccountService.dealLockUserAccountByArr(lockIds,loginUser,lockFlag);
                result.setCount(lockCount);
                dealCommonSuccessCatch(result,"批量"+lockMsg+"用户:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "修改用户锁定状态", notes = "根据用户id锁定或解锁用户", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserAccountController",action="修改用户锁定状态",description = "根据用户id锁定或解锁用户")
    @PostMapping(value = "/lockOneUserAccountById")
    public MyCommonResult doLockOneUserAccountById(HttpServletRequest request, HttpServletResponse response,String lockId,Boolean lockFlag){
        MyCommonResult result = new MyCommonResult() ;
        Integer lockCount = 0;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            //操作类型为锁定？如果没传递值默认锁定
            lockFlag = lockFlag != null ? lockFlag : true ;
            String lockMsg = lockFlag ? "锁定" : "解锁" ;
            if(StringUtils.isNotBlank(lockId)){
                lockCount = userAccountService.dealLockUserAccount(lockId,loginUser,lockFlag);
                dealCommonSuccessCatch(result,lockMsg+"用户:"+actionSuccessMsg);
            }
            result.setCount(lockCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "用户分配角色", notes = "为用户分配角色", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserAccountController",action="用户分配角色",description = "为用户分配角色")
    @PostMapping(value = "/grantRoleToUser")
    public MyCommonResult doGrantRoleToUser(HttpServletRequest request, HttpServletResponse response, String userAccountId,String[] checkIds){
        MyCommonResult result = new MyCommonResult() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(StringUtils.isNotBlank(userAccountId)){
                Integer grantCount = userAccountService.dealGrantRoleToUser(userAccountId,checkIds,loginUser);
                result.setCount(grantCount);
                dealCommonSuccessCatch(result,"用户分配角色:"+actionSuccessMsg);
            }   else {
                throw new BusinessException("未知要分配角色的用户id");
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }



    @ApiOperation(value = "用户分配职务", notes = "为用户分配职务", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserAccountController",action="用户分配职务",description = "为用户分配职务")
    @PostMapping(value = "/grantJobToUser")
    public MyCommonResult doGrantJobToUser(HttpServletRequest request, HttpServletResponse response, String userAccountId,String[] checkIds){
        MyCommonResult result = new MyCommonResult() ;
        try{
            UserAccount loginUser = commonFuncService.gainUserAccountByRequest(request,true);
            if(StringUtils.isNotBlank(userAccountId)){
                Integer grantCount = userAccountService.dealGrantJobToUser(userAccountId,checkIds,loginUser);
                result.setCount(grantCount);
                dealCommonSuccessCatch(result,"用户分配职务:"+actionSuccessMsg);
            }   else {
                throw new BusinessException("未知要分配职务的用户id");
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }
}
