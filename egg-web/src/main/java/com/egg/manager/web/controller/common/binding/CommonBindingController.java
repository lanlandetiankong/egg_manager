package com.egg.manager.web.controller.common.binding;

import com.egg.manager.api.constants.funcmodule.controllers.common.binding.CommonBindingFuncModuleConstant;
import com.egg.manager.common.base.beans.front.FrontSelectBean;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
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
 * @author: zhouchengjie
 * \* Date: 2020/2/4
 * \* Time: 19:08
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API-通用接口/参数绑定")
@RestController
@RequestMapping("/commonApi/binding")
public class CommonBindingController extends BaseController {

    @ApiOperation(value = "查询枚举->开关",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getSwitchEnumList")
    public MyCommonResult doGetSwitchEnumList(HttpServletRequest request, HttpServletResponse response) {
        MyCommonResult result = MyCommonResult.gainEnumResult(CommonBindingFuncModuleConstant.Success.QUERY_ENUM_LIST);
        try {
            SwitchStateEnum[] enums = SwitchStateEnum.values();
            List<FrontSelectBean> beanList = new ArrayList<>();
            if (enums != null && enums.length > 0) {
                for (SwitchStateEnum enumObj : enums) {
                    beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getName()));
                }
            }
            result.setEnumList(beanList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e, CommonBindingFuncModuleConstant.Failure.QUERY_ENUM_LIST);
        }
        return result;
    }


}
