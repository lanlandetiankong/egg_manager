package com.egg.manager.em.enhance.advice;

import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.enums.PublicResultEnum;
import com.egg.manager.persistence.commons.base.exception.BusinessException;
import com.egg.manager.persistence.commons.base.exception.MyParamJsonException;
import com.egg.manager.persistence.commons.base.exception.MyUnauthorizedException;
import com.egg.manager.persistence.commons.base.helper.MyResponseHelper;
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
 * @author zhoucj
 * @description
 * @date 2019/9/14
 */
@Slf4j
@ControllerAdvice
public class MyControllerAdvice {
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
    public void addAttributes(Model model) {
        //nothing
    }

    /**
     * 全局异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public WebResult errorHandle(Exception ex) {
        log.error("全局异常：", ex);
        return MyResponseHelper.handleRequestFailure(ex, "");
    }

    /**
     * 拦截UnauthorizedException处理
     * 用户没有权限的异常
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = MyUnauthorizedException.class)
    @ResponseBody
    public WebResult handleUnauthorized(MyUnauthorizedException e) {
        log.error(String.format("执行异常(%d)--->", PublicResultEnum.UnauthorizedLoginUser.getLabel()), e);
        return MyResponseHelper.handleRequestFailure(PublicResultEnum.UnauthorizedLoginUser);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public WebResult handleShiroException(ShiroException e) {
        log.error(String.format("执行异常(%d)--->" + PublicResultEnum.NoPermissionOfUser.getLabel()), e);
        return MyResponseHelper.handleRequestFailure(PublicResultEnum.NoPermissionOfUser);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public WebResult handleBusinessException(BusinessException e) {
        log.error(String.format("执行异常(%d)--->" + PublicResultEnum.ErrorOfDb.getLabel()), e);
        return MyResponseHelper.handleRequestFailure(PublicResultEnum.ErrorOfDb);
    }


    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = MyParamJsonException.class)
    @ResponseBody
    public WebResult handleParamJsonException(MyParamJsonException e) {
        log.error(String.format("执行异常(%d)--->" + PublicResultEnum.ErrorOfParam.getLabel()), e);
        return MyResponseHelper.handleRequestFailure(PublicResultEnum.ErrorOfParam);
    }

    /**
     * 字段验证不通过处理
     * @param ex
     * @param request
     * @param response
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public WebResult handleBindException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        BindException c = (BindException) ex;
        List<ObjectError> errors = c.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer("表单验证错误信息:");
        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()));
        log.error(errorMsg.toString());
        return WebResult.error(errorMsg.toString());
    }

    /**
     * 请求参数绑定异常 处理
     * @param ex
     * @param request
     * @param response
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseBody
    public WebResult handleMethodArgumentNotValidException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
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
        log.error(errorMsg.toString(), extException);
        return WebResult.error(errorMsg.toString());
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
