package com.egg.manager.web.controller.common.binding;

import com.egg.manager.api.constants.funcModule.controllers.common.binding.PermissionCommonFuncModuleConstant;
import com.egg.manager.common.base.beans.front.FrontSelectBean;
import com.egg.manager.common.base.enums.permission.DefinePermissionCodePrefixEnum;
import com.egg.manager.common.base.enums.permission.DefinePermissionTypeEnum;
import com.egg.manager.common.base.enums.role.DefineRoleTypeEnum;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
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
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/4
 * \* Time: 19:08
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API ==>>  PermissionCommonController ", description = "通用接口 - 权限")
@RestController
@RequestMapping("/common_api/permission")
public class PermissionCommonController extends BaseController {

    @ApiOperation(value = "检索所有权限类型", notes = "检索所有权限类型", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/getAllPermissionTypeEnumList")
    public MyCommonResult<DefinePermission> doGetAllPermissionTypeEnumList(HttpServletRequest request) {
        MyCommonResult<DefinePermission> result = MyCommonResult.gainQueryResult(DefinePermission.class, PermissionCommonFuncModuleConstant.Success.queryEnumList);
        try {
            DefinePermissionTypeEnum[] enums = DefinePermissionTypeEnum.values();
            List<FrontSelectBean> beanList = new ArrayList<>();
            if (enums != null && enums.length > 0) {
                for (DefinePermissionTypeEnum enumObj : enums) {
                    beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getLabel()));
                }
            }
            result.setEnumList(beanList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "检索所有角色类型", notes = "检索所有角色类型", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/getAllRoleTypeEnumList")
    public MyCommonResult doGetAllRoleTypeEnumList(HttpServletRequest request) {
        MyCommonResult<FrontSelectBean> result = MyCommonResult.gainQueryResult(FrontSelectBean.class,PermissionCommonFuncModuleConstant.Success.queryEnumList);
        try {
            DefineRoleTypeEnum[] enums = DefineRoleTypeEnum.values();
            List<FrontSelectBean> beanList = new ArrayList<>();
            if (enums != null && enums.length > 0) {
                for (DefineRoleTypeEnum enumObj : enums) {
                    beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getLabel()));
                }
            }
            result.setEnumList(beanList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "检索所有权限Code前缀类型", notes = "检索所有权限Code前缀类型", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/getAllPermissionCodePrefixEnumList")
    public MyCommonResult doGetAllPermissionCodePrefixEnumList(HttpServletRequest request) {
        MyCommonResult<FrontSelectBean> result = MyCommonResult.gainQueryResult(FrontSelectBean.class,PermissionCommonFuncModuleConstant.Success.queryEnumList);
        try {
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
            result.setEnumList(beanList);
            result.setEnumDefaultCheckList(defaultCheckList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

}
