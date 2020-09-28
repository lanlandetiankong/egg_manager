package com.egg.manager.web.controller.common.binding;

import com.egg.manager.api.constants.funcModule.controllers.common.binding.ModuleCommonFuncModuleConstant;
import com.egg.manager.common.base.beans.front.FrontSelectBean;
import com.egg.manager.common.base.enums.module.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.common.base.enums.module.DefineModuleTypeEnum;
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
@Api(value = "API ==>>  ModuleCommonController ", description = "通用接口 - 模块")
@RestController
@RequestMapping("/common_api/module")
public class ModuleCommonController extends BaseController {


    @ApiOperation(value = "检索所有模块类型", notes = "检索所有模块类型", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/getAllModuleTypeEnumList")
    public MyCommonResult doGetAllModuleTypeEnumList(HttpServletRequest request, HttpServletResponse response) {
        MyCommonResult result = MyCommonResult.gainEnumResult(ModuleCommonFuncModuleConstant.Success.queryEnumList);
        try {
            DefineModuleTypeEnum[] enums = DefineModuleTypeEnum.values();
            List<FrontSelectBean> beanList = new ArrayList<>();
            if (enums != null && enums.length > 0) {
                for (DefineModuleTypeEnum enumObj : enums) {
                    beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getLabel()));
                }
            }
            result.setEnumList(beanList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,ModuleCommonFuncModuleConstant.Failure.queryEnumList);
        }
        return result;
    }


    @ApiOperation(value = "检索所有菜单跳转类型", notes = "检索所有菜单跳转类型", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/getAllMenuUrlJumpTypeEnumList")
    public MyCommonResult doGetAllMenuTypeEnumList(HttpServletRequest request, HttpServletResponse response) {
        MyCommonResult result = MyCommonResult.gainEnumResult( ModuleCommonFuncModuleConstant.Success.queryEnumList);
        try {
            DefineMenuUrlJumpTypeEnum[] enums = DefineMenuUrlJumpTypeEnum.values();
            List<FrontSelectBean> beanList = new ArrayList<>();
            if (enums != null && enums.length > 0) {
                for (DefineMenuUrlJumpTypeEnum enumObj : enums) {
                    beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getLabel()));
                }
            }
            result.setEnumList(beanList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,ModuleCommonFuncModuleConstant.Failure.queryEnumList);
        }
        return result;
    }

}
