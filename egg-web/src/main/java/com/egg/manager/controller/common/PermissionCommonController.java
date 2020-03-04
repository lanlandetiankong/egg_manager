package com.egg.manager.controller.common;

import com.egg.manager.common.base.beans.FrontSelectBean;
import com.egg.manager.common.base.enums.permission.DefinePermissionTypeEnum;
import com.egg.manager.common.base.enums.role.DefineRoleTypeEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.user.UserAccount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "API ==>>  PermissionCommonController ",description = "通用接口 - 权限")
@RestController
@RequestMapping("/common_api/permission")
public class PermissionCommonController extends BaseController{


    @ApiOperation(value = "检索所有权限类型", notes = "检索所有权限类型", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/getAllPermissionTypeEnumList")
    public MyCommonResult<DefinePermission> doGetAllPermissionTypeEnumList(HttpServletRequest request, HttpServletResponse response) {
        MyCommonResult<DefinePermission> result = new MyCommonResult<DefinePermission>() ;
        try{
            DefinePermissionTypeEnum[] enums = DefinePermissionTypeEnum.values();
            List<FrontSelectBean> beanList = new ArrayList<>();
            if(enums != null && enums.length > 0){
                for (DefinePermissionTypeEnum enumObj : enums){
                    beanList.add(new FrontSelectBean(enumObj.getValue(),enumObj.getLabel()));
                }
            }
            result.setEnumList(beanList);
        }   catch (Exception e){
            this.dealCommonErrorCatch(result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "检索所有角色类型", notes = "检索所有角色类型", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/getAllRoleTypeEnumList")
    public MyCommonResult doGetAllRoleTypeEnumList(HttpServletRequest request, HttpServletResponse response) {
        MyCommonResult result = new MyCommonResult() ;
        try{
            DefineRoleTypeEnum[] enums = DefineRoleTypeEnum.values();
            List<FrontSelectBean> beanList = new ArrayList<>();
            if(enums != null && enums.length > 0){
                for (DefineRoleTypeEnum enumObj : enums){
                    beanList.add(new FrontSelectBean(enumObj.getValue(),enumObj.getLabel()));
                }
            }
            result.setEnumList(beanList);
        }   catch (Exception e){
            this.dealCommonErrorCatch(result,e) ;
        }
        return  result;
    }
}
