package com.egg.manager.em.web.controller.emctl.common.binding;

import com.egg.manager.facade.api.exchange.BaseController;
import com.egg.manager.facade.persistence.commons.base.beans.front.FrontSelectBean;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.facade.persistence.em.define.domain.enums.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.facade.persistence.em.define.domain.enums.DefineModuleTypeEnum;
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
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-通用接口(模块)")
@RestController
@RequestMapping("/emCtl/commonApi/module")
public class ModuleCommonController extends BaseController {
    @ApiOperation(value = "查询枚举->模块类型", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getAllModuleTypeEnumList")
    public WebResult doGetAllModuleTypeEnumList(HttpServletRequest request, HttpServletResponse response) {
        WebResult result = WebResult.okEnums();
        DefineModuleTypeEnum[] enums = DefineModuleTypeEnum.values();
        List<FrontSelectBean> beanList = new ArrayList<>();
        if (enums != null && enums.length > 0) {
            for (DefineModuleTypeEnum enumObj : enums) {
                beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getLabel()));
            }
        }
        result.putEnumData(beanList);
        return result;
    }

    @ApiOperation(value = "查询枚举->菜单跳转类型", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getAllMenuUrlJumpTypeEnumList")
    public WebResult doGetAllMenuTypeEnumList(HttpServletRequest request, HttpServletResponse response) {
        WebResult result = WebResult.okEnums();
        DefineMenuUrlJumpTypeEnum[] enums = DefineMenuUrlJumpTypeEnum.values();
        List<FrontSelectBean> beanList = new ArrayList<>();
        if (enums != null && enums.length > 0) {
            for (DefineMenuUrlJumpTypeEnum enumObj : enums) {
                beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getLabel()));
            }
        }
        result.putEnumData(beanList);
        return result;
    }
}
