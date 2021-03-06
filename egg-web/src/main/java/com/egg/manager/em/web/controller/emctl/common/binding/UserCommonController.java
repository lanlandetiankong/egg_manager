package com.egg.manager.em.web.controller.emctl.common.binding;

import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.persistence.commons.base.beans.front.FrontSelectBean;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.em.define.domain.enums.DefineJobTypeEnum;
import com.egg.manager.persistence.em.user.domain.enums.UserAccountBaseTypeEnum;
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
@Api(value = "API-通用接口(用户)")
@RestController
@RequestMapping("/emCtl/commonApi/user")
public class UserCommonController extends BaseController {
    @ApiOperation(value = "查询枚举->用户类型", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getAllUserTypeEnumList")
    public WebResult doGetAllUserTypeEnumList(HttpServletRequest request, HttpServletResponse response) {
        WebResult result = WebResult.okEnums();
        UserAccountBaseTypeEnum[] enums = UserAccountBaseTypeEnum.values();
        List<FrontSelectBean> beanList = new ArrayList<>();
        if (enums != null && enums.length > 0) {
            for (UserAccountBaseTypeEnum enumObj : enums) {
                beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getLabel()));
            }
        }
        result.putEnumData(beanList);
        return result;
    }

    @ApiOperation(value = "查询枚举->用户锁类型", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getAllUserLockStateEnumList")
    public WebResult doGetAllUserLockStateEnumList(HttpServletRequest request, HttpServletResponse response) {
        WebResult result = WebResult.okEnums();
        List<FrontSelectBean> beanList = new ArrayList<>();
        beanList.add(new FrontSelectBean(0, "未锁定"));
        beanList.add(new FrontSelectBean(1, "已锁定"));
        result.putEnumData(beanList);
        return result;
    }

    @ApiOperation(value = "查询枚举->职务类型", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getAllDefineJobTypeEnumList")
    public WebResult doGetAllDefineJobTypeEnumList(HttpServletRequest request, HttpServletResponse response) {
        WebResult result = WebResult.okEnums();
        DefineJobTypeEnum[] enums = DefineJobTypeEnum.values();
        List<FrontSelectBean> beanList = new ArrayList<>();
        if (enums != null && enums.length > 0) {
            for (DefineJobTypeEnum enumObj : enums) {
                beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getLabel()));
            }
        }
        result.putEnumData(beanList);
        return result;
    }
}
