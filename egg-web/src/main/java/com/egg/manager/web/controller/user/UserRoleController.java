package com.egg.manager.web.controller.user;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.funcmodule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcmodule.controllers.user.UserRoleFuncModuleConstant;
import com.egg.manager.api.services.basic.user.UserRoleService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.base.constant.web.api.WebApiConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.db.mysql.mapper.user.UserRoleMapper;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserRoleTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserRoleVo;
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


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API-用户_角色接口")
@RestController
@RequestMapping("/user/user_role")
public class UserRoleController extends BaseController {


    @Autowired
    private UserRoleMapper userRoleMapper;
    @Reference
    private UserRoleService userRoleService;


    @PcWebQueryLog(fullPath = "/user/user_role/getAllUserRoles")
    @ApiOperation(value = "分页查询->用户角色",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllUserRoles")
    public MyCommonResult<UserRoleVo> doGetAllUserRoles(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserRoleVo> result = MyCommonResult.gainQueryResult(UserRoleVo.class, UserRoleFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<UserRole> paginationBean = this.parsePaginationJsonToBean(paginationObj,UserRole.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = userRoleService.dealQueryPageByEntitys(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserRoleFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->用户角色",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/user/user_role/getUserRoleById")
    @PostMapping(value = "/getUserRoleById")
    public MyCommonResult<UserRoleVo> doGetUserRoleById(HttpServletRequest request, String roleId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserRoleVo> result = MyCommonResult.gainQueryResult(UserRoleVo.class, UserRoleFuncModuleConstant.Success.QUERY_ONE_BY_ID);
        try {
            Assert.notBlank(roleId, BaseRstMsgConstant.ErrorMsg.unknowId());
            UserRole vo = userRoleMapper.selectById(roleId);
            result.setBean(UserRoleTransfer.transferEntityToVo(vo));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserRoleFuncModuleConstant.Failure.QUERY_ONE_BY_ID);
        }
        return result;
    }


    @ApiOperation(value = "新增->用户角色",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/user/user_role/doAddUserRole")
    @PostMapping(value = "/doAddUserRole")
    public MyCommonResult doAddUserRole(HttpServletRequest request, UserRoleVo userRoleVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( UserRoleFuncModuleConstant.Success.CREATE_OPER);
        Integer addCount = 0;
        try {
            Assert.notNull(userRoleVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = userRoleService.dealCreate(loginUser, userRoleVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserRoleFuncModuleConstant.Failure.CREATE_OPER);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/user/user_role/batchDelUserRoleByIds")
    @ApiOperation(value = "批量伪删除->用户角色",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelUserRoleByIds")
    public MyCommonResult doBatchDeleteUserRoleById(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( UserRoleFuncModuleConstant.Success.UPDATE_OPER);
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = userRoleService.dealBatchDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserRoleFuncModuleConstant.Failure.UPDATE_OPER);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/user/user_role/delOneUserRoleByIds")
    @ApiOperation(value = "伪删除->用户角色",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "指定删除的id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneUserRoleByIds")
    public MyCommonResult doDelOneUserRoleById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( UserRoleFuncModuleConstant.Success.DELETE_BY_ID);
        Integer delCount = 0;
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            delCount = userRoleService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,UserRoleFuncModuleConstant.Failure.DELETE_BY_ID);
        }
        return result;
    }


}
