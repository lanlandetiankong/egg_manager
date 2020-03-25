package com.egg.manager.web.controller.user;

import com.egg.manager.service.annotation.log.CurrentLoginUser;
import com.egg.manager.service.annotation.log.OperLog;
import com.egg.manager.service.annotation.shiro.ShiroPass;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.common.util.jwt.JWTUtil;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.web.config.shiro.JwtShiroToken;
import com.egg.manager.web.controller.BaseController;
import com.egg.manager.persistence.entity.define.DefineJob;
import com.egg.manager.persistence.entity.define.DefinePermission;
import com.egg.manager.persistence.entity.define.DefineRole;
import com.egg.manager.persistence.entity.organization.DefineTenant;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.service.exception.form.LoginFormFieldDeficiencyException;
import com.egg.manager.persistence.mapper.define.DefineJobMapper;
import com.egg.manager.persistence.mapper.define.DefinePermissionMapper;
import com.egg.manager.persistence.mapper.define.DefineRoleMapper;
import com.egg.manager.persistence.mapper.user.UserAccountMapper;
import com.egg.manager.persistence.mapper.user.UserTenantMapper;
import com.egg.manager.service.service.CommonFuncService;
import com.egg.manager.service.service.user.UserAccountService;
import com.egg.manager.persistence.vo.define.DefineJobVo;
import com.egg.manager.persistence.vo.define.DefinePermissionVo;
import com.egg.manager.persistence.vo.define.DefineRoleVo;
import com.egg.manager.persistence.vo.user.UserAccountVo;
import com.egg.manager.service.webvo.login.LoginAccountVo;
import com.egg.manager.service.webvo.session.UserAccountToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    private UserTenantMapper userTenantMapper ;
    @Autowired
    private CommonFuncService commonFuncService ;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @OperLog(modelName="UserAccountController",action="用户登录接口",description = "账号密码方式登录接口")
    @ApiOperation(value = "用户登录接口", notes = "账号密码方式登录接口", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginAccountVo",value = "要登录用户的信息", required = true,dataTypeClass=LoginAccountVo.class),
    })
    @ShiroPass
    @PostMapping(value = "/login/account")
    public MyCommonResult<UserAccount> doLoginCheckByAccount(HttpServletRequest request,  LoginAccountVo loginAccountVo) {
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


                    //用户登录信息验证成功，在shiro进行一些登录处理
                    //添加用户认证信息
                    Subject subject = SecurityUtils.getSubject();
                    String authorization = JWTUtil.sign(userAccount.getFid());
                    JwtShiroToken jwtShiroToken = new JwtShiroToken(authorization);
                    //进行验证，这里可以捕获异常，然后返回对应信息
                    subject.login(jwtShiroToken);

                    userAccountToken.setAuthorization(authorization);
                    //redis30分钟过期
                    this.dealSetTokenToRedis(userAccountToken,userAccount) ;
                    //返回给前端 jwt jwt值
                    result.setAuthorization(authorization);
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


    @RequiresRoles(value = {"Root","SuperRoot"},logical= Logical.OR)
    @OperLog(modelName="UserAccountController",action="查询用户信息-Dto列表",description = "查询用户信息-Dto列表")
    @ApiOperation(value = "查询用户信息-Dto列表", notes = "查询用户信息-Dto列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllUserAccountDtos")
    public MyCommonResult<UserAccountVo> doGetAllUserAccountDtos(HttpServletRequest request,String queryObj, String paginationObj, String sortObj,
                                                                 @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserAccountVo> result = new MyCommonResult<UserAccountVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            userAccountService.dealGetUserAccountDtoPages(result,queryFormFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询用户信息-Dto列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询用户信息", notes = "根据用户id查询用户信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserAccountController",action="查询用户信息",description = "根据用户id查询用户信息")
    @PostMapping(value = "/getUserAccountById")
    public MyCommonResult<UserAccountVo> doGetUserAccountById(HttpServletRequest request, String accountId,@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserAccountVo> result = new MyCommonResult<UserAccountVo>() ;
        try{
            UserAccount account = userAccountMapper.selectById(accountId);
            UserAccountVo userAccountVo = UserAccountVo.transferEntityToVo(account);
            //取得 所属的 租户定义
            DefineTenant belongTenant = userTenantMapper.selectOneOfUserBelongTenant(account.getFid(),BaseStateEnum.ENABLED.getValue());
            if(belongTenant != null){
                userAccountVo.setBelongTenantId(belongTenant.getFid());
            }
            result.setBean(userAccountVo);
            dealCommonSuccessCatch(result,"查询用户信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(logger,result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "查询用户所拥有的角色", notes = "根据用户id查询用户已有的角色", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="UserAccountController",action="查询用户所拥有的角色",description = "根据用户id查询用户已有的角色")
    @PostMapping(value = "/getAllRoleByUserAccountId")
    public MyCommonResult<DefineRoleVo> doGetAllRoleByUserAccountId(HttpServletRequest request,String userAccountId,@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineRoleVo> result = new MyCommonResult<DefineRoleVo>() ;
        try{
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
    public MyCommonResult<DefinePermissionVo> doGetAllPermissionByUserAccountId(HttpServletRequest request,String userAccountId,
                                                                                @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermissionVo> result = new MyCommonResult<DefinePermissionVo>() ;
        try{
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
    public MyCommonResult<DefineJobVo> doGetAllJobByUserAccountId(HttpServletRequest request,  String userAccountId,@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineJobVo> result = new MyCommonResult<DefineJobVo>() ;
        try{
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
    public MyCommonResult doAddUserAccount(HttpServletRequest request,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer addCount = 0 ;
        try{
            UserAccountVo userAccountVo = this.getBeanFromRequest(request,"formObj",UserAccountVo.class,true) ;
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
    public MyCommonResult doUpdateUserAccount(HttpServletRequest request,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            UserAccountVo userAccountVo = this.getBeanFromRequest(request,"formObj",UserAccountVo.class,true) ;
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
    public MyCommonResult doBatchDeleteUserAccountById(HttpServletRequest request, String[] delIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
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
    public MyCommonResult doDelOneUserAccountById(HttpServletRequest request, String delId,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
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
    public MyCommonResult doBatchLockUserAccountById(HttpServletRequest request,String[] lockIds,Boolean lockFlag,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer lockCount = 0;
        try{
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
    public MyCommonResult doLockOneUserAccountById(HttpServletRequest request, String lockId,Boolean lockFlag,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer lockCount = 0;
        try{
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
    public MyCommonResult doGrantRoleToUser(HttpServletRequest request,String userAccountId,String[] checkIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
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
    public MyCommonResult doGrantJobToUser(HttpServletRequest request,String userAccountId,String[] checkIds,
                                           @CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
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
