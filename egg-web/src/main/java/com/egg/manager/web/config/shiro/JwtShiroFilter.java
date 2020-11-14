package com.egg.manager.web.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.persistence.commons.base.constant.Constant;
import com.egg.manager.persistence.commons.base.enums.PublicResultEnum;
import com.egg.manager.persistence.commons.base.helper.MyResponseHelper;
import com.egg.manager.persistence.commons.util.jwt.JwtUtil;
import com.egg.manager.persistence.commons.util.spring.SpringContextBeanUtil;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.pojo.initialize.UserAccountPojoInitialize;
import com.egg.manager.persistence.em.user.pojo.transfer.UserAccountTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author zhoucj
 * @description 代码的执行流程 preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 * @date 2020/10/21
 */
@Slf4j
public class JwtShiroFilter extends BasicHttpAuthenticationFilter {

    private UserAccountService userAccountService;

    /**
     * 检验用户是否想要登录
     * 检测header里面是否包含 authorization 字段
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("authorization");
        return StringUtils.isNotBlank(authorization);
    }


    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("authorization");
        Long userAccountId = JwtUtil.getUserAccountId(authorization);
        JwtShiroToken jwtShiroToken = new JwtShiroToken(authorization);
        // 触发 Relam.doGetAuthenticationInfo
        getSubject(request, response).login(jwtShiroToken);
        //handleSetUserAccountBean(request,response,jwtShiroToken);
        // 触发 Relam.doGetAuthorizationInfo
        return super.executeLogin(request, response);
    }

    /**
     * 目前只需要 head 包含
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                e.getMessage();
                responseError(request, response);
            }
        }
        return true;
    }

    /**
     * 跨域支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        String token = httpServletRequest.getHeader("token");
        //当前请求是否有@ShiroPass并且可直接放行，会注入一个 游客信息
        if (handleVerificationPassAnnotation(request, response, httpServletRequest, token)) {
            return true;
        }
        return super.preHandle(request, response);
    }

    /**
     * 放行有 @ShiroPass 的请求方法
     * (项目启动时，加载所有带有 @ShiroPass 的RequestMapping 到 Constant.METHOD_URL_SET 中)
     * @param request
     * @param response
     * @param httpServletRequest
     * @param authorization
     * @return
     * @throws Exception
     */
    private boolean handleVerificationPassAnnotation(ServletRequest request, ServletResponse response, HttpServletRequest httpServletRequest, String authorization) throws Exception {
        for (String urlMethod : Constant.METHOD_URL_SET) {
            String[] urlSplit = urlMethod.split(":--:");
            boolean eqUriFlag = urlSplit[0].equals(httpServletRequest.getRequestURI());
            boolean eqMethodFlag = (urlSplit[1].equals(httpServletRequest.getMethod()) || "RequestMapping".equals(urlSplit[1]));
            if (eqUriFlag && eqMethodFlag) {
                //Constant.isPass = true ;
                if (StringUtils.isBlank(authorization)) {
                    //如果前端没传递 authorization的话，按游客处理，添加游客信息
                    httpServletRequest.setAttribute("currentLoginUser", UserAccountPojoInitialize.dealGetVisitor());
                    return true;
                } else {
                    super.preHandle(request, response);
                }
            }
            if (StringUtils.countMatches(urlMethod, "{") > 0) {
                if (StringUtils.countMatches(urlMethod, Constant.SYMBOL_SLASH) == StringUtils.countMatches(urlSplit[0], Constant.SYMBOL_SLASH)) {
                    if (urlSplit[1].equals(httpServletRequest.getMethod()) || "RequestMapping".equals(urlSplit[1])) {
                        //路径url跟控制器的url一致时
                        if (checkIsSameUrl(urlSplit[0], httpServletRequest.getRequestURI())) {
                            //Constant.isPass=true;
                            if (StringUtils.isBlank(authorization)) {
                                httpServletRequest.setAttribute("currentLoginUser", new UserAccountEntity());
                                return true;
                            } else {
                                super.preHandle(request, response);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    /**
     * 设置用户信息到request
     * @param request
     * @param response
     * @param token
     */
    private void handleSetUserAccountBean(ServletRequest request, ServletResponse response, JwtShiroToken token) {
        if (this.userAccountService == null) {
            this.userAccountService = SpringContextBeanUtil.getBean(UserAccountService.class);
        }
        //取得用户id
        Long userId = JwtUtil.getUserAccountId(token.getPrincipal().toString());
        UserAccountEntity userAccountEntity = userAccountService.getById(userId);
        if (userAccountEntity != null) {
            request.setAttribute("currentLoginUser", UserAccountTransfer.transferEntityToVo(userAccountEntity));
        }
    }

    private void responseError(ServletRequest request, ServletResponse response) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("utf-8");
            out = response.getWriter();
            response.setContentType("application/json; charset=utf-8");
            out.print(JSONObject.toJSONString(MyResponseHelper.handleRequestFailure(PublicResultEnum.UnauthorizedLoginUser)));
        } catch (Exception e) {
            log.error("执行异常--->",e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 判断路径url是否和controller方法的url一致
     * @param localUrl
     * @param requestUrl
     * @return
     */
    private boolean checkIsSameUrl(String localUrl, String requestUrl) {
        String[] tempLocalUrls = localUrl.split(Constant.SYMBOL_SLASH);
        String[] tempRequestUrls = requestUrl.split(Constant.SYMBOL_SLASH);
        if (tempLocalUrls.length != tempLocalUrls.length) {
            return false;
        }
        StringBuilder localUrlBuilder = new StringBuilder();
        StringBuilder requestUrlBuilder = new StringBuilder();
        for (int i = 0; i < tempLocalUrls.length; i++) {
            if (StringUtils.countMatches(tempLocalUrls[i], "{") > 0) {
                tempLocalUrls[i] = "*";
                tempRequestUrls[i] = "*";
            }
            localUrlBuilder.append(tempLocalUrls[i] + Constant.SYMBOL_SLASH);
            requestUrlBuilder.append(tempRequestUrls[i] + Constant.SYMBOL_SLASH);
        }
        return localUrlBuilder.toString().trim().equals(requestUrlBuilder.toString().trim());
    }
}
