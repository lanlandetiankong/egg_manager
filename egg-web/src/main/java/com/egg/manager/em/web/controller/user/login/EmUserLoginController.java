package com.egg.manager.em.web.controller.user.login;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.exchange.helper.PasswordHelper;
import com.egg.manager.api.exchange.helper.redis.RedisHelper;
import com.egg.manager.api.services.em.define.basic.EmDefineMenuService;
import com.egg.manager.api.services.em.define.basic.EmDefinePermissionService;
import com.egg.manager.api.services.em.define.basic.EmDefineRoleService;
import com.egg.manager.api.services.em.user.basic.EmUserAccountService;
import com.egg.manager.em.web.config.shiro.JwtShiroToken;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.enums.db.RedisShiroCacheEnum;
import com.egg.manager.persistence.commons.base.exception.login.MyLoginFailureException;
import com.egg.manager.persistence.commons.util.jwt.JwtUtil;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineDepartmentMapper;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineTenantMapper;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineDepartmentDto;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineTenantDto;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.pojo.bean.UserAccountToken;
import com.egg.manager.persistence.em.user.pojo.dto.login.LoginAccountVo;
import com.egg.manager.persistence.em.user.pojo.verification.login.EmLoginAccountVerifyO;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebLoginLog;
import com.egg.manager.persistence.enhance.annotation.shiro.ShiroPass;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.exchange.verification.igroup.VerifyGroupOfDefault;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@RestController
@RequestMapping("user/login")
public class EmUserLoginController extends BaseController {
    @Value("${egg.conf.jwt.sso:true}")
    private boolean jwtSsoFlag;
    @Reference
    private RedisHelper redisHelper;
    @Autowired
    private EmDefineTenantMapper emDefineTenantMapper;
    @Autowired
    private EmDefineDepartmentMapper emDefineDepartmentMapper;
    @Autowired
    private EmUserAccountService emUserAccountService;
    @Reference
    public EmDefineRoleService emDefineRoleService;
    @Reference
    public EmDefinePermissionService emDefinePermissionService;
    @Reference
    public EmDefineMenuService emDefineMenuService;

    @EmPcWebLoginLog(fullPath = "/user/login/loginByForm")
    @ApiOperation(value = "用户登录接口", notes = "账号密码方式登录接口", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginAccountVo", value = "要登录用户的相关信息", required = true, dataTypeClass = LoginAccountVo.class),
    })
    @ShiroPass
    @PostMapping(value = "/loginByForm")
    public WebResult doLoginCheckByAccount(HttpServletRequest request, LoginAccountVo loginAccountVo,
                                           @Validated({VerifyGroupOfDefault.class}) EmLoginAccountVerifyO emLoginAccountVerifyO
            , @CurrentLoginUser(required = false) EmUserAccountEntity loginUser
    ) throws Exception {
        PasswordHelper passwordHelper = new PasswordHelper();
        WebResult result = WebResult.okQuery();
        try {
            Assert.notNull(loginAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            Assert.notEmpty(loginAccountVo.getAccount(), BaseRstMsgConstant.ErrorMsg.emptyLoginAccount());
            Assert.notNull(loginAccountVo.getPassword(), BaseRstMsgConstant.ErrorMsg.emptyLoginPassword());
            //取得用户
            EmUserAccountEntity emUserAccountEntity = emUserAccountService.dealGetEntityByDTO(LoginAccountVo.transferToLoginAccountDTO(loginAccountVo));
            Assert.notNull(emUserAccountEntity, BaseRstMsgConstant.ErrorMsg.nullLoginAccount());
            //取得 form的password+数据库的salt 进行md5加密后的值
            String saltedPwd = SecureUtil.md5(loginAccountVo.getPassword() + (StringUtils.isBlank(emUserAccountEntity.getSalt()) ? "" : emUserAccountEntity.getSalt()));
            //判断: 数据库存储的md5(密码+salt) == (form的password+数据库salt)的值，匹配才能算验证成功
            //Assert.isTrue(emUserAccountEntity.getPassword().equals(saltedPwd), BaseRstMsgConstant.ErrorMsg.notMatchaccountPassword());
            Assert.isTrue(passwordHelper.isPasswordMatch(loginAccountVo.getPassword(), emUserAccountEntity));
            UserAccountToken userAccountToken = UserAccountToken.gainByUserAccount(emUserAccountEntity);
            //账号密码验证通过
            result.putAccountToken(userAccountToken);
            //用户登录信息验证成功，在shiro进行一些登录处理
            //添加用户认证信息
            Subject subject = SecurityUtils.getSubject();
            String authorization = JwtUtil.sign(emUserAccountEntity.getFid());
            JwtShiroToken jwtShiroToken = new JwtShiroToken(authorization);
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(jwtShiroToken);
            //所属租户
            EmDefineTenantDto emDefineTenantDto = emDefineTenantMapper.selectOneDtoOfUserBelongTenant(emUserAccountEntity.getFid());
            if (emDefineTenantDto != null) {
                userAccountToken.setUserBelongTenantId(emDefineTenantDto.getFid());
            }
            //所属部门
            EmDefineDepartmentDto emDefineDepartmentDto = emDefineDepartmentMapper.selectOneDtoOfUserBelongDepartment(emUserAccountEntity.getFid());
            if (emDefineDepartmentDto != null) {
                userAccountToken.setUserBelongTenantId(emDefineDepartmentDto.getFid());
            }
            userAccountToken.setAuthorization(authorization);
            //redis30分钟过期
            this.dealSetTokenToRedis(loginUser, userAccountToken, result);
            //返回给前端 jwt jwt值
            result.putAuthorization(authorization);
        } catch (Exception e) {
            throw new MyLoginFailureException();
        }
        return result;
    }

    /**
     * 设置/刷新 用户信息缓存到redis
     * @param userAccountToken
     * @param result
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void dealSetTokenToRedis(EmUserAccountEntity loginUser, UserAccountToken userAccountToken, WebResult result) throws InvocationTargetException, IllegalAccessException {
        //将用户 token 分别存入到redis
        String userAccountId = userAccountToken.getUserAccountId();
        if (userAccountToken != null && userAccountId != null && StringUtils.isNotBlank(userAccountToken.getAuthorization())) {
            //通过当前用户id 取得原先的 authorization(如果在ttl期间重新登录的话
            Object oldAuthorization = redisHelper.hashGet(RedisShiroCacheEnum.userAuthorization.getKey(), userAccountId);
            if (oldAuthorization != null && jwtSsoFlag) {
                //根据用户id取得 当前用户的 Authorization 值，清理之前的缓存，删除后就类似于[单点登录] ,jwtSsoFlag由application.properties 配置取得
                String userAuthorization = (String) oldAuthorization;
                redisHelper.hashRemove(RedisShiroCacheEnum.userAuthorization.getKey(), userAuthorization);
                //清除 authorization 缓存
                redisHelper.hashRemove(RedisShiroCacheEnum.authorization.getKey(), userAuthorization);
                redisHelper.hashRemove(RedisShiroCacheEnum.userAccount.getKey(), userAuthorization);
                redisHelper.hashRemove(RedisShiroCacheEnum.userPermissions.getKey(), userAuthorization);
                redisHelper.hashRemove(RedisShiroCacheEnum.userRoles.getKey(), userAuthorization);
                redisHelper.hashRemove(RedisShiroCacheEnum.userFrontButtons.getKey(), userAuthorization);
                redisHelper.hashRemove(RedisShiroCacheEnum.userFrontRouterUrl.getKey(), userAuthorization);
            }
            String authorization = userAccountToken.getAuthorization();
            //设置 用户id指向当前 的 authorization
            redisHelper.hashTtlPut(RedisShiroCacheEnum.userAuthorization.getKey(), userAccountId, authorization, RedisShiroCacheEnum.userAuthorization.getTtl());
            //设置 authorization 缓存 当前用户的token
            redisHelper.hashTtlPut(RedisShiroCacheEnum.authorization.getKey(), authorization, userAccountToken, RedisShiroCacheEnum.authorization.getTtl());
            //设置到缓存,hashKey 都是 authorization
            emUserAccountService.queryDbToCacheable(userAccountId);
            Set<String> permissionSet = emDefinePermissionService.queryDbToCacheable(userAccountId);
            emDefineRoleService.queryDbToCacheable(userAccountId);
            emDefineRoleService.queryDbToCacheable(userAccountId);
            Set<String> routerUrlSet = emDefineMenuService.dealGetUserVisitAbleUrl(userAccountId);
            if (result != null) {
                result.putRouterUrlSet(routerUrlSet);
                result.putPermissionSet(permissionSet);
            }
        } else {
            log.error("未能成功缓存用户信息到Redis");
        }
    }
}
