package com.egg.manager.web.controller.login;

import com.egg.manager.api.services.basic.user.UserAccountService;
import com.egg.manager.common.annotation.log.pc.web.PcWebLoginLog;
import com.egg.manager.common.annotation.shiro.ShiroPass;
import com.egg.manager.common.exception.form.LoginFormFieldDeficiencyException;
import com.egg.manager.common.util.jwt.JWTUtil;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.bean.webvo.login.LoginAccountVo;
import com.egg.manager.persistence.bean.webvo.session.UserAccountToken;
import com.egg.manager.persistence.bean.webvo.verification.login.LoginAccountVerifyO;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfCreate;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.web.config.shiro.JwtShiroToken;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/3/20
 * \* Time: 20:22
 * \* Description:
 * \
 */
@Slf4j
@RestController
@RequestMapping("user/login")
public class UserLoginController extends BaseController {

    @Autowired
    private UserAccountService userAccountService ;

    @PcWebLoginLog(action="用户登录接口",description = "账号密码方式登录接口",fullPath = "/user/login/byAccountForm")
    @ApiOperation(value = "用户登录接口", notes = "账号密码方式登录接口", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginAccountVo",value = "要登录用户的相关信息", required = true,dataTypeClass= LoginAccountVo.class),
    })
    @ShiroPass
    @PostMapping(value = "/byAccountForm")
    public MyCommonResult<UserAccount> doLoginCheckByAccount(HttpServletRequest request,LoginAccountVo loginAccountVo,
                                                             @Validated({VerifyGroupOfDefault.class})LoginAccountVerifyO loginAccountVerifyO
                                                             ) {
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
                    this.dealSetTokenToRedis(userAccountToken,result) ;
                    //返回给前端 jwt jwt值
                    result.setAuthorization(authorization);
                }   else {
                    throw new Exception("账号密码不匹配！");
                }
            }

            dealCommonSuccessCatch(result,"用户登录:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return result ;
    }
}
