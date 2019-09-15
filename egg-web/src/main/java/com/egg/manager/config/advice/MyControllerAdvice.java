package com.egg.manager.config.advice;

import com.baomidou.mybatisplus.activerecord.Model;
import com.egg.manager.common.base.enums.PublicResultEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.helper.MyResponseHelper;
import com.egg.manager.exception.MyUnauthorizedException;
import com.egg.manager.exception.MyParamJsonException;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 14:53
 * \* Description:
 * \
 */
@ControllerAdvice
public class MyControllerAdvice {
    private static Logger logger = LoggerFactory.getLogger(MyControllerAdvice.class);

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //nothing
    }

    /**
     * 把值绑定到model中，那么全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model){
        //nothing
    }

    /**
     * 全局异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public MyCommonResult errorHandle(Exception ex) {
        ex.printStackTrace();
        logger.error("接口异常：{}",ex.getMessage());
        return MyResponseHelper.handleRequestFailure(ex,"");
    }

    /**
     * 拦截UnauthorizedException处理
     * 用户没有权限的异常
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = MyUnauthorizedException.class)
    @ResponseBody
    public MyCommonResult handleUnauthorized(MyUnauthorizedException e) {
        return MyResponseHelper.handleRequestFailure(PublicResultEnum.NoPermissionOfUser);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public MyCommonResult handleShiroException(ShiroException e) {
        return MyResponseHelper.handleRequestFailure(PublicResultEnum.NoPermissionOfUser);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public MyCommonResult handleBusinessException(BusinessException e){
        if(e instanceof BusinessException) {
            logger.error("数据操作失败："+e.getMessage());
            return MyResponseHelper.handleRequestFailure(PublicResultEnum.ErrorOfDb);
        }
        return MyResponseHelper.handleRequestFailure(PublicResultEnum.Error);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(TemplateInputException.class)
    @ResponseBody
    public MyCommonResult handleTemplateInputException(TemplateInputException e) {
        return MyResponseHelper.handleRequestFailure(PublicResultEnum.NoPermissionOfUser);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = MyParamJsonException.class)
    @ResponseBody
    public MyCommonResult handleParamJsonException(Exception e) {
        if(e instanceof MyParamJsonException) {
            logger.info("参数错误："+e.getMessage());
            return MyResponseHelper.handleRequestFailure(e,"参数错误");
        }
        return MyResponseHelper.handleRequestFailure(PublicResultEnum.ErrorOfParam);
    }

}
