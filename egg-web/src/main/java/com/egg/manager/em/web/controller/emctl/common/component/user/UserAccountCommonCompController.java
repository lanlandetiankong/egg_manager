package com.egg.manager.em.web.controller.emctl.common.component.user;

import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.user.basic.EmUserAccountService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserAccountDto;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Slf4j
@Api(value = "API-通用组件/用户账号接口")
@RestController
@RequestMapping("/emCtl/commmon/component/user/userAccount")
public class UserAccountCommonCompController extends BaseController {
    @Autowired
    private EmUserAccountService emUserAccountService;

    @EmPcWebOperationLog(fullPath = "/emCtl/commmon/component/user/userAccount/queryDtoPage", flag = false)
    @ApiOperation(value = "通用组件?分页查询(com.egg.manager.persistence.obl.article.pojo.dto)->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = EmUserAccountDto.class) QueryPageBean<EmUserAccountDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addEqNotDeleted();
        result = emUserAccountService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }
}
