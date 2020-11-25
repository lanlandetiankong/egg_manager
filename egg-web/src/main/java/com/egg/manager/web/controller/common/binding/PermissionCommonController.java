package com.egg.manager.web.controller.common.binding;

import com.egg.manager.persistence.commons.base.beans.front.FrontSelectBean;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.enums.permission.DefinePermissionCodePrefixEnum;
import com.egg.manager.persistence.commons.base.enums.permission.DefinePermissionTypeEnum;
import com.egg.manager.persistence.commons.base.enums.role.DefineRoleTypeEnum;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-通用接口->权限")
@RestController
@RequestMapping("/commonApi/permission")
public class PermissionCommonController extends BaseController {
    @ApiOperation(value = "查询枚举->权限类型", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getAllPermissionTypeEnumList")
    public WebResult doGetAllPermissionTypeEnumList(HttpServletRequest request) {
        WebResult result = WebResult.okEnums();
        DefinePermissionTypeEnum[] enums = DefinePermissionTypeEnum.values();
        List<FrontSelectBean> beanList = new ArrayList<>();
        if (enums != null && enums.length > 0) {
            for (DefinePermissionTypeEnum enumObj : enums) {
                beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getLabel()));
            }
        }
        result.putEnumData(beanList);
        return result;
    }

    @ApiOperation(value = "查询枚举->角色类型", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getAllRoleTypeEnumList")
    public WebResult doGetAllRoleTypeEnumList(HttpServletRequest request) {
        WebResult result = WebResult.okEnums();
        DefineRoleTypeEnum[] enums = DefineRoleTypeEnum.values();
        List<FrontSelectBean> beanList = new ArrayList<>();
        if (enums != null && enums.length > 0) {
            for (DefineRoleTypeEnum enumObj : enums) {
                beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getLabel()));
            }
        }
        result.putEnumData(beanList);
        return result;
    }

    @ApiOperation(value = "查询枚举->权限Code前缀类型", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getAllPermissionCodePrefixEnumList")
    public WebResult doGetAllPermissionCodePrefixEnumList(HttpServletRequest request) {
        WebResult result = WebResult.okEnums();
        DefinePermissionCodePrefixEnum[] enums = DefinePermissionCodePrefixEnum.values();
        List<FrontSelectBean> beanList = new ArrayList<>();
        List<String> defaultCheckList = new ArrayList<>();
        if (enums != null && enums.length > 0) {
            for (DefinePermissionCodePrefixEnum enumObj : enums) {
                beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getLabel()));
                if (enumObj.isDefaultCheck()) {
                    defaultCheckList.add(enumObj.getValue());
                }
            }
        }
        result.putEnumData(beanList,defaultCheckList);
        return result;
    }
}
