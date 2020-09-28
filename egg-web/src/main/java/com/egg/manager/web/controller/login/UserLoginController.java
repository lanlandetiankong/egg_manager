package com.egg.manager.web.controller.login;

import cn.hutool.core.lang.Assert;
import com.egg.manager.api.constants.funcModule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcModule.controllers.announcement.AnnouncementFuncModuleConstant;
import com.egg.manager.api.constants.funcModule.controllers.login.UserLoginFuncModuleConstant;
import com.egg.manager.api.services.basic.user.UserAccountService;
import com.egg.manager.common.annotation.log.pc.web.PcWebLoginLog;
import com.egg.manager.common.annotation.shiro.ShiroPass;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.util.jwt.JWTUtil;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.bean.webvo.login.LoginAccountVo;
import com.egg.manager.persistence.bean.webvo.session.UserAccountToken;
import com.egg.manager.persistence.bean.webvo.verification.login.LoginAccountVerifyO;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineDepartmentMapper;
import com.egg.manager.persistence.db.mysql.mapper.organization.DefineTenantMapper;
import com.egg.manager.persistence.pojo.common.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineDepartmentDto;
import com.egg.manager.persistence.pojo.mysql.dto.organization.DefineTenantDto;
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
    private DefineTenantMapper defineTenantMapper;
    @Autowired
    private DefineDepartmentMapper defineDepartmentMapper;
    @Autowired
    private UserAccountService userAccountService;

    @PcWebLoginLog(action = "用户登录接口", description = "账号密码方式登录接口", fullPath = "/user/login/byAccountForm")
    @ApiOperation(value = "用户登录接口", notes = "账号密码方式登录接口", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginAccountVo", value = "要登录用户的相关信息", required = true, dataTypeClass = LoginAccountVo.class),
    })
    @ShiroPass
    @PostMapping(value = "/byAccountForm")
    public MyCommonResult<UserAccount> doLoginCheckByAccount(HttpServletRequest request, LoginAccountVo loginAccountVo,
                                                             @Validated({VerifyGroupOfDefault.class}) LoginAccountVerifyO loginAccountVerifyO
            , @CurrentLoginUser(required = false) UserAccount loginUser
    ) {
        MyCommonResult<UserAccount> result = MyCommonResult.gainQueryResult(UserAccount.class, UserLoginFuncModuleConstant.Success.loginOper);
        try {
            Assert.notNull(loginAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            Assert.notEmpty(loginAccountVo.getAccount(),BaseRstMsgConstant.ErrorMsg.emptyLoginAccount());
            Assert.notNull(loginAccountVo.getPassword(),BaseRstMsgConstant.ErrorMsg.emptyLoginPassword());
            //取得用户
            UserAccount userAccount = userAccountService.dealGetEntityByDTO(LoginAccountVo.transferToLoginAccountDTO(loginAccountVo));
            Assert.notNull(userAccount,BaseRstMsgConstant.ErrorMsg.nullLoginAccount());
            Assert.isTrue(userAccount.getPassword().equals(loginAccountVo.getPassword()),BaseRstMsgConstant.ErrorMsg.notMatchaccountPassword());
            if (userAccount.getPassword().equals(loginAccountVo.getPassword())) {
                UserAccountToken userAccountToken = UserAccountToken.gainByUserAccount(userAccount);
                //账号密码验证通过
                result.setAccountToken(userAccountToken);
                //用户登录信息验证成功，在shiro进行一些登录处理
                //添加用户认证信息
                Subject subject = SecurityUtils.getSubject();
                String authorization = JWTUtil.sign(userAccount.getFid());
                JwtShiroToken jwtShiroToken = new JwtShiroToken(authorization);
                //进行验证，这里可以捕获异常，然后返回对应信息
                subject.login(jwtShiroToken);
                //所属租户
                DefineTenantDto defineTenantDto = defineTenantMapper.selectOneDtoOfUserBelongTenant(userAccount.getFid());
                if (defineTenantDto != null) {
                    userAccountToken.setUserBelongTenantId(defineTenantDto.getFid());
                }
                //所属部门
                DefineDepartmentDto defineDepartmentDto = defineDepartmentMapper.selectOneDtoOfUserBelongDepartment(userAccount.getFid());
                if (defineDepartmentDto != null) {
                    userAccountToken.setUserBelongTenantId(defineDepartmentDto.getFid());
                }
                userAccountToken.setAuthorization(authorization);
                //redis30分钟过期
                this.dealSetTokenToRedis(loginUser, userAccountToken, result);
                //返回给前端 jwt jwt值
                result.setAuthorization(authorization);
            }
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }
}
