package com.egg.manager.web.controller.user.login;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.helper.PasswordHelper;
import com.egg.manager.api.exchange.helper.redis.RedisHelper;
import com.egg.manager.api.services.em.define.basic.DefineMenuService;
import com.egg.manager.api.services.em.define.basic.DefinePermissionService;
import com.egg.manager.api.services.em.define.basic.DefineRoleService;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.enums.redis.RedisShiroCacheEnum;
import com.egg.manager.persistence.commons.util.jwt.JwtUtil;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineDepartmentMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineDepartmentDto;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineTenantMapper;
import com.egg.manager.persistence.em.user.pojo.bean.UserAccountToken;
import com.egg.manager.persistence.em.define.pojo.dto.DefineTenantDto;
import com.egg.manager.persistence.em.user.pojo.dto.login.LoginAccountVo;
import com.egg.manager.persistence.em.user.pojo.verification.login.LoginAccountVerifyO;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebLoginLog;
import com.egg.manager.persistence.enhance.annotation.shiro.ShiroPass;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.exchange.verification.igroup.VerifyGroupOfDefault;
import com.egg.manager.web.config.shiro.JwtShiroToken;
import com.egg.manager.web.controller.BaseController;
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
public class UserLoginController extends BaseController {


    @Value("${egg.conf.jwt.sso:true}")
    private boolean jwtSsoFlag;
    @Reference
    private RedisHelper redisHelper;

    @Autowired
    private DefineTenantMapper defineTenantMapper;
    @Autowired
    private DefineDepartmentMapper defineDepartmentMapper;
    @Autowired
    private UserAccountService userAccountService;


    @Reference
    public DefineRoleService defineRoleService;
    @Reference
    public DefinePermissionService definePermissionService;
    @Reference
    public DefineMenuService defineMenuService;



    @PcWebLoginLog(fullPath = "/user/login/loginByForm")
    @ApiOperation(value = "用户登录接口", notes = "账号密码方式登录接口", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginAccountVo", value = "要登录用户的相关信息", required = true, dataTypeClass = LoginAccountVo.class),
    })
    @ShiroPass
    @PostMapping(value = "/loginByForm")
    public WebResult doLoginCheckByAccount(HttpServletRequest request, LoginAccountVo loginAccountVo,
                                           @Validated({VerifyGroupOfDefault.class}) LoginAccountVerifyO loginAccountVerifyO
            , @CurrentLoginUser(required = false) UserAccountEntity loginUser
    ) {
        PasswordHelper passwordHelper = new PasswordHelper() ;
        WebResult result = WebResult.okQuery();
        try {
            Assert.notNull(loginAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            Assert.notEmpty(loginAccountVo.getAccount(), BaseRstMsgConstant.ErrorMsg.emptyLoginAccount());
            Assert.notNull(loginAccountVo.getPassword(), BaseRstMsgConstant.ErrorMsg.emptyLoginPassword());
            //取得用户
            UserAccountEntity userAccountEntity = userAccountService.dealGetEntityByDTO(LoginAccountVo.transferToLoginAccountDTO(loginAccountVo));
            Assert.notNull(userAccountEntity, BaseRstMsgConstant.ErrorMsg.nullLoginAccount());
            //取得 form的password+数据库的salt 进行md5加密后的值
            String saltedPwd = SecureUtil.md5(loginAccountVo.getPassword()+ (StringUtils.isBlank(userAccountEntity.getSalt()) ? "" : userAccountEntity.getSalt())) ;
            //判断: 数据库存储的md5(密码+salt) == (form的password+数据库salt)的值，匹配才能算验证成功
            //Assert.isTrue(userAccountEntity.getPassword().equals(saltedPwd), BaseRstMsgConstant.ErrorMsg.notMatchaccountPassword());
            Assert.isTrue(passwordHelper.isPasswordMatch(loginAccountVo.getPassword(),userAccountEntity));
            UserAccountToken userAccountToken = UserAccountToken.gainByUserAccount(userAccountEntity);
            //账号密码验证通过
            result.putAccountToken(userAccountToken);
            //用户登录信息验证成功，在shiro进行一些登录处理
            //添加用户认证信息
            Subject subject = SecurityUtils.getSubject();
            String authorization = JwtUtil.sign(userAccountEntity.getFid());
            JwtShiroToken jwtShiroToken = new JwtShiroToken(authorization);
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(jwtShiroToken);
            //所属租户
            DefineTenantDto defineTenantDto = defineTenantMapper.selectOneDtoOfUserBelongTenant(userAccountEntity.getFid());
            if (defineTenantDto != null) {
                userAccountToken.setUserBelongTenantId(defineTenantDto.getFid());
            }
            //所属部门
            DefineDepartmentDto defineDepartmentDto = defineDepartmentMapper.selectOneDtoOfUserBelongDepartment(userAccountEntity.getFid());
            if (defineDepartmentDto != null) {
                userAccountToken.setUserBelongTenantId(defineDepartmentDto.getFid());
            }
            userAccountToken.setAuthorization(authorization);
            //redis30分钟过期
            this.dealSetTokenToRedis(loginUser, userAccountToken, result);
            //返回给前端 jwt jwt值
            result.putAuthorization(authorization);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
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
    public void dealSetTokenToRedis(UserAccountEntity loginUser, UserAccountToken userAccountToken, WebResult result) throws InvocationTargetException, IllegalAccessException {
        //将用户 token 分别存入到redis
        Long userAccountId = userAccountToken.getUserAccountId();
        if (userAccountToken != null && userAccountId != null && StringUtils.isNotBlank(userAccountToken.getAuthorization())) {
            //通过当前用户id 取得原先的 authorization(如果在ttl期间重新登录的话
            Object oldAuthorization = redisHelper.hashGet(RedisShiroCacheEnum.userAuthorization.getKey(), String.valueOf(userAccountId));
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
            redisHelper.hashTtlPut(RedisShiroCacheEnum.userAuthorization.getKey(), String.valueOf(userAccountId), authorization, RedisShiroCacheEnum.userAuthorization.getTtl());
            //设置 authorization 缓存 当前用户的token
            redisHelper.hashTtlPut(RedisShiroCacheEnum.authorization.getKey(), authorization, userAccountToken, RedisShiroCacheEnum.authorization.getTtl());

            //设置到缓存,hashKey 都是 authorization
            userAccountService.queryDbToCacheable(userAccountId);
            Set<String> permissionSet = definePermissionService.queryDbToCacheable(userAccountId);
            defineRoleService.queryDbToCacheable(userAccountId);
            defineRoleService.queryDbToCacheable(userAccountId);
            Set<String> routerUrlSet = defineMenuService.dealGetUserVisitAbleUrl(userAccountId);
            if (result != null) {
                result.putRouterUrlSet(routerUrlSet);
                result.putPermissionSet(permissionSet);
            }
        } else {
            log.error("未能成功缓存用户信息到Redis");
        }
    }
}
