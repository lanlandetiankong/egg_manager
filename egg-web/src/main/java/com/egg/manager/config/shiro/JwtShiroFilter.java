package com.egg.manager.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.common.base.constant.Constant;
import com.egg.manager.common.base.constant.PublicResultConstant;
import com.egg.manager.common.base.enums.PublicResultEnum;
import com.egg.manager.common.util.jwt.JWTUtil;
import com.egg.manager.common.web.helper.MyResponseHelper;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.service.SpringContextBeanService;
import com.egg.manager.service.user.UserAccountService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:11
 * \* Description:
 * \
 */
public class JwtShiroFilter extends BasicHttpAuthenticationFilter {

    private UserAccountService userAccountService ;

    /**
     * 检验用户是否想要登录
     * 检测header里面是否包含Authorization 字段
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response){
        HttpServletRequest req = (HttpServletRequest) request ;
        String authorization = req.getHeader("Authorization") ;
        return StringUtils.isNotBlank(authorization);
    }


    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization =httpServletRequest.getHeader("Authorization");
        JwtShiroToken token = new JwtShiroToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request,response).login(token);
        handleSetUserAccountBean(request,response,token);
        return super.executeLogin(request, response);
    }

    /**
     * doc by liugh
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request,ServletResponse response,Object mappedValue) {
        if(isLoginAttempt(request,response)){
            try{
                executeLogin(request,response) ;
            }   catch (Exception e){
                e.getMessage();
                responseError(request,response) ;
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
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if(httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false ;
        }
        String authorization = httpServletRequest.getHeader("Authorization");
        if(handleVerificationPassAnnotation(request,response,httpServletRequest,authorization)){
            return  true;
        }
        if(StringUtils.isBlank(authorization)){
            responseError(request,response);
            return false ;
        }
        return super.preHandle(request,response);
    }

    /**
     * 放行有@ShiroPass 的请求方法
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
            if(urlSplit[0].equals(httpServletRequest.getRequestURI())
                    && (urlSplit[1].equals(httpServletRequest.getMethod()) || urlSplit[1].equals("RequestMapping")
            )){
                Constant.isPass = true ;
                if(StringUtils.isNotBlank(authorization)){
                    //TODO 需求：没有登录时生成一个 [唯一的游客账号]
                    UserAccount visitorUser = new UserAccount() ;
                    httpServletRequest.setAttribute("currentUser",visitorUser);
                    return true ;
                }   else {
                    super.preHandle(request,response);
                }
            }
            if(StringUtils.countMatches(urlMethod,"{") > 0 ){
                if(StringUtils.countMatches(urlMethod,"/") == StringUtils.countMatches(urlSplit[0], "/")) {
                    if(urlSplit[1].equals(httpServletRequest.getMethod()) || urlSplit[1].equals("RequestMapping")){
                        //路径url跟控制器的url一致时
                        if(checkIsSameUrl(urlSplit[0],httpServletRequest.getRequestURI())){
                            Constant.isPass=true;
                            if(StringUtils.isBlank(authorization)){
                                httpServletRequest.setAttribute("currentUser",new UserAccount());
                                return true ;
                            }   else {
                                super.preHandle(request,response);
                            }
                        }
                    }
                }
            }
        }
        return false ;
    }


    /**
     * 设置用户信息到request
     * TODO 目前是vo，改为bean
     * @param request
     * @param response
     * @param token
     */
    private void handleSetUserAccountBean(ServletRequest request,ServletResponse response,JwtShiroToken token) {
         if(this.userAccountService == null) {
             this.userAccountService = SpringContextBeanService.getBean(UserAccountService.class) ;
         }
         //取得用户id
         String userId = JWTUtil.getUserAccountId(token.getPrincipal().toString());
         UserAccount userAccount = userAccountService.selectById(userId);
         request.setAttribute("currentUser",userAccount);
    }

    private void responseError(ServletRequest request,ServletResponse response) {
        PrintWriter out = null ;
        try {
            response.setCharacterEncoding("utf-8");
            out= response.getWriter() ;
            response.setContentType("application/json; charset=utf-8");
            out.print(JSONObject.toJSONString(MyResponseHelper.handleRequestFailure(PublicResultEnum.Unauthorized)));
        }   catch (Exception e){
            e.printStackTrace();
        }   finally {
            if(out != null) {
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
    private boolean checkIsSameUrl(String localUrl,String requestUrl){
        String[] tempLocalUrls = localUrl.split("/");
        String[] tempRequestUrls = requestUrl.split("/");
        if(tempLocalUrls.length != tempLocalUrls.length){
            return  false;
        }
        StringBuilder localUrlBuilder = new StringBuilder() ;
        StringBuilder requestUrlBuilder = new StringBuilder() ;
        for (int i = 0; i < tempLocalUrls.length; i++) {
            if(StringUtils.countMatches(tempLocalUrls[i],"{") > 0){
                tempLocalUrls[i] = "*";
                tempRequestUrls[i] = "*" ;
            }
            localUrlBuilder.append(tempLocalUrls[i]+"/");
            requestUrlBuilder.append(tempRequestUrls[i]+"/");
        }
        return localUrlBuilder.toString().trim().equals(requestUrlBuilder.toString().trim()) ;
    }
}
