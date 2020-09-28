package com.egg.manager.web.config.advice;

import com.egg.manager.api.trait.helper.MyResponseHelper;
import com.egg.manager.common.base.enums.PublicResultEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.exception.MyParamJsonException;
import com.egg.manager.common.exception.MyUnauthorizedException;
import com.egg.manager.common.exception.form.LoginFormFieldDeficiencyException;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 14:53
 * \* Description:
 * \
 */
@Slf4j
@ControllerAdvice
public class MyControllerAdvice {
    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //nothing
    }

    /**
     * 把值绑定到model中，那么全局@RequestMapping可以获取到该值
     *
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        //nothing
    }

    /**
     * 全局异常
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public MyCommonResult errorHandle(Exception ex) {
        ex.printStackTrace();
        log.error("接口异常：{}", ex.getMessage());
        return MyResponseHelper.handleRequestFailure(ex, "");
    }

    /**
     * 拦截UnauthorizedException处理
     * 用户没有权限的异常
     *
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = MyUnauthorizedException.class)
    @ResponseBody
    public MyCommonResult handleUnauthorized(MyUnauthorizedException e) {
        return MyResponseHelper.handleRequestFailure(PublicResultEnum.UnauthorizedLoginUser);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public MyCommonResult handleShiroException(ShiroException e) {
        e.printStackTrace();
        return MyResponseHelper.handleRequestFailure(PublicResultEnum.NoPermissionOfUser);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public MyCommonResult handleBusinessException(BusinessException e) {
        if (e instanceof BusinessException) {
            log.error("数据操作失败：" + e.getMessage());
            return MyResponseHelper.handleRequestFailure(PublicResultEnum.ErrorOfDb);
        }
        return MyResponseHelper.handleRequestFailure(PublicResultEnum.Error);
    }


    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = MyParamJsonException.class)
    @ResponseBody
    public MyCommonResult handleParamJsonException(Exception e) {
        if (e instanceof MyParamJsonException) {
            log.info("参数错误：" + e.getMessage());
            return MyResponseHelper.handleRequestFailure(e, "参数错误");
        }
        return MyResponseHelper.handleRequestFailure(PublicResultEnum.ErrorOfParam);
    }

    /**
     * 字段验证不通过处理
     *
     * @param ex
     * @param request
     * @param response
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public MyCommonResult handleBindException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        BindException c = (BindException) ex;
        List<ObjectError> errors = c.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer("表单验证错误信息:");
        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()));
        log.error(errorMsg.toString());
        return MyCommonResult.gainErrorResult(errorMsg.toString());
    }

    /**
     * 请求参数绑定异常 处理
     *
     * @param ex
     * @param request
     * @param response
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseBody
    public MyCommonResult MethodArgumentNotValidException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ServletRequestBindingException extException = (ServletRequestBindingException) ex;
        StringBuffer errorMsg = new StringBuffer("参数异常信息：");
        //参数值为空
        if (extException instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException exception = (MissingServletRequestParameterException) extException;
            errorMsg.append("必填参数[" + exception.getParameterName() + "]不能为空");
        } else if (extException instanceof MissingMatrixVariableException) {
            MissingMatrixVariableException exception = (MissingMatrixVariableException) extException;
            errorMsg.append("Missing matrix variable '" + exception.getVariableName() + "' for method parameter of type " + exception.getParameter().getNestedParameterType().getSimpleName());
        }
        log.error(errorMsg.toString());
        return MyCommonResult.gainErrorResult(errorMsg.toString());
    }


    /**
     * 登录表单字段缺失异常-处理
     *
     * @param ex
     * @param request
     * @param response
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(LoginFormFieldDeficiencyException.class)
    @ResponseBody
    public MyCommonResult handleLoginFormFieldDeficiencyException(LoginFormFieldDeficiencyException ex, HttpServletRequest request, HttpServletResponse response) {
        LoginFormFieldDeficiencyException c = (LoginFormFieldDeficiencyException) ex;
        StringBuffer errorMsg = new StringBuffer("表单验证错误信息:" + c.getMessage());
        log.error(errorMsg.toString());
        return MyCommonResult.gainErrorResult(errorMsg.toString());
    }

    public Integer getStatusCodeByException(Exception ex) {
        Integer statusCode = null;
        if (ex == null) {
            return statusCode;
        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            statusCode = HttpServletResponse.SC_METHOD_NOT_ALLOWED;
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            statusCode = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            statusCode = HttpServletResponse.SC_NOT_ACCEPTABLE;
        } else if (ex instanceof MissingServletRequestParameterException) {
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        } else if (ex instanceof ServletRequestBindingException) {
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        } else if (ex instanceof ConversionNotSupportedException) {
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        } else if (ex instanceof TypeMismatchException) {
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        } else if (ex instanceof HttpMessageNotReadableException) {
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        } else if (ex instanceof HttpMessageNotWritableException) {
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        } else if (ex instanceof MethodArgumentNotValidException) {
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        } else if (ex instanceof MissingServletRequestPartException) {
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        } else if (ex instanceof BindException) {
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        } else if (ex instanceof NoHandlerFoundException) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
        } else {
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        return statusCode;
    }


}
