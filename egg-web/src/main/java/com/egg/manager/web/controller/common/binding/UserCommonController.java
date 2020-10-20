package com.egg.manager.web.controller.common.binding;

import com.egg.manager.api.constants.funcmodule.controllers.common.binding.UserCommonFuncModuleConstant;
import com.egg.manager.common.base.beans.front.FrontSelectBean;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.base.enums.define.DefineJobTypeEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/4
 * \* Time: 19:08
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API-通用接口(用户)")
@RestController
@RequestMapping("/commonApi/user")
public class UserCommonController extends BaseController {

    @ApiOperation(value = "查询枚举->用户类型",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getAllUserTypeEnumList")
    public MyCommonResult doGetAllUserTypeEnumList(HttpServletRequest request, HttpServletResponse response) {
        MyCommonResult result = MyCommonResult.gainEnumResult(UserCommonFuncModuleConstant.Success.QUERY_ENUM_LIST);
        try {
            UserAccountBaseTypeEnum[] enums = UserAccountBaseTypeEnum.values();
            List<FrontSelectBean> beanList = new ArrayList<>();
            if (enums != null && enums.length > 0) {
                for (UserAccountBaseTypeEnum enumObj : enums) {
                    beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getLabel()));
                }
            }
            result.setEnumList(beanList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserCommonFuncModuleConstant.Failure.QUERY_ENUM_LIST);
        }
        return result;
    }

    @ApiOperation(value = "查询枚举->用户锁类型",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getAllUserLockStateEnumList")
    public MyCommonResult doGetAllUserLockStateEnumList(HttpServletRequest request, HttpServletResponse response) {
        MyCommonResult result = MyCommonResult.gainEnumResult(UserCommonFuncModuleConstant.Success.QUERY_ENUM_LIST);
        try {
            List<FrontSelectBean> beanList = new ArrayList<>();
            beanList.add(new FrontSelectBean(0, "未锁定"));
            beanList.add(new FrontSelectBean(1, "已锁定"));
            result.setEnumList(beanList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserCommonFuncModuleConstant.Failure.QUERY_ENUM_LIST);
        }
        return result;
    }


    @ApiOperation(value = "查询枚举->职务类型",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getAllDefineJobTypeEnumList")
    public MyCommonResult doGetAllDefineJobTypeEnumList(HttpServletRequest request, HttpServletResponse response) {
        MyCommonResult result = MyCommonResult.gainEnumResult(UserCommonFuncModuleConstant.Success.QUERY_ENUM_LIST);
        try {
            DefineJobTypeEnum[] enums = DefineJobTypeEnum.values();
            List<FrontSelectBean> beanList = new ArrayList<>();
            if (enums != null && enums.length > 0) {
                for (DefineJobTypeEnum enumObj : enums) {
                    beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getLabel()));
                }
            }
            result.setEnumList(beanList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserCommonFuncModuleConstant.Failure.QUERY_ENUM_LIST);
        }
        return result;
    }
}
