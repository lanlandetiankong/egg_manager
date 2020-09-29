package com.egg.manager.web.controller.common.component.user;

import com.egg.manager.api.constants.funcModule.controllers.common.component.user.UserAccountCommonCompFuncModuleConstant;
import com.egg.manager.api.services.basic.user.UserAccountService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserAccountDto;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserAccountVo;
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
import java.util.List;

@Slf4j
@Api(value = "API-用户账号接口(通用组件) ")
@RestController
@RequestMapping("/commmon/component/user/user_account")
public class UserAccountCommonCompController extends BaseController {

    @Autowired
    private UserAccountService userAccountService;


    @PcWebOperationLog(action = "[通用组件]-查询用户信息-Dto列表", description = "[通用组件]-查询用户信息-Dto列表", fullPath = "/commmon/component/user/user_account/getAllUserAccountDtos", flag = false)
    @ApiOperation(value = "[通用组件]-查询用户信息-Dto列表", notes = "[通用组件]-查询用户信息-Dto列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllUserAccountDtos")
    public MyCommonResult<UserAccountVo> doGetAllUserAccountDtos(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                                 @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserAccountVo> result = MyCommonResult.gainQueryResult(UserAccountVo.class, UserAccountCommonCompFuncModuleConstant.Success.queryPage);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<UserAccountDto> paginationBean = this.parsePaginationJsonToBean(paginationObj,UserAccountDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = userAccountService.dealQueryPageByDtos(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserAccountCommonCompFuncModuleConstant.Failure.queryPage);
        }
        return result;
    }

}
