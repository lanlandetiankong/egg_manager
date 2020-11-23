package com.egg.manager.web.controller.common.component.user;

import com.egg.manager.api.services.em.user.basic.UserAccountService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryField;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.commons.util.page.PageUtil;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.em.user.pojo.dto.UserAccountDto;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.web.controller.BaseController;
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
@RequestMapping("/commmon/component/user/userAccount")
public class UserAccountCommonCompController extends BaseController {
    @Autowired
    private UserAccountService userAccountService;

    @PcWebOperationLog(fullPath = "/commmon/component/user/userAccount/queryDtoPage", flag = false)
    @ApiOperation(value = "通用组件?分页查询(dto)->用户账号", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        //解析 搜索条件
        QueryFieldArr queryFieldArr = PageUtil.parseQueryJsonToBeanList(queryObj);
        queryFieldArr.add(QueryField.gainEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue()));
        //取得 分页配置
        AntdvPage<UserAccountDto> vpage = PageUtil.parsePaginationJsonToBean(paginationObj, UserAccountDto.class);
        //取得 排序配置
        AntdvSortMap sortMap = PageUtil.parseSortJsonToBean(sortObj, true);
        result = userAccountService.dealQueryPageByDtos(loginUserInfo, result, queryFieldArr, vpage, sortMap);
        return result;
    }
}
