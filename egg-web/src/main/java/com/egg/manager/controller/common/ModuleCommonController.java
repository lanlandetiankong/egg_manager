package com.egg.manager.controller.common;

import com.egg.manager.common.base.beans.FrontSelectBean;
import com.egg.manager.common.base.enums.module.DefineModuleTypeEnum;
import com.egg.manager.common.base.enums.permission.DefinePermissionTypeEnum;
import com.egg.manager.common.base.enums.role.DefineRoleTypeEnum;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.controller.BaseController;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.vo.module.DefineModuleVo;
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
@RestController
@RequestMapping("/common_api/module")
public class ModuleCommonController extends BaseController{


    @ApiOperation(value = "检索所有模块类型", notes = "检索所有模块类型", response = String.class)
    @PostMapping(value = "/getAllModuleTypeEnumList")
    public MyCommonResult<DefineModuleVo> doGetAllModuleTypeEnumList(HttpServletRequest request, HttpServletResponse response) {
        MyCommonResult<DefineModuleVo> result = new MyCommonResult<DefineModuleVo>() ;
        try{
            DefineModuleTypeEnum[] enums = DefineModuleTypeEnum.values();
            List<FrontSelectBean> beanList = new ArrayList<>();
            if(enums != null && enums.length > 0){
                for (DefineModuleTypeEnum enumObj : enums){
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
