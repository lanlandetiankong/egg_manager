package com.egg.manager.em.web.controller.common.binding;

import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.persistence.commons.base.beans.front.FrontSelectBean;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.enums.base.SwitchStateEnum;
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
@Api(value = "API-通用接口/参数绑定")
@RestController
@RequestMapping("/commonApi/binding")
public class CommonBindingController extends BaseController {
    @ApiOperation(value = "查询枚举->开关", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/getSwitchEnumList")
    public WebResult doGetSwitchEnumList(HttpServletRequest request, HttpServletResponse response) {
        WebResult result = WebResult.okEnums();
        SwitchStateEnum[] enums = SwitchStateEnum.values();
        List<FrontSelectBean> beanList = new ArrayList<>();
        if (enums != null && enums.length > 0) {
            for (SwitchStateEnum enumObj : enums) {
                beanList.add(new FrontSelectBean(enumObj.getValue(), enumObj.getName()));
            }
        }
        result.putEnumData(beanList);
        return result;
    }
}
