package com.egg.manager.web.controller.user;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.controllers.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcModule.user.UserDepartmentFuncModuleConstant;
import com.egg.manager.api.constants.funcModule.user.UserRoleFuncModuleConstant;
import com.egg.manager.api.services.basic.user.UserRoleService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
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
import org.apache.commons.lang3.StringUtils;
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
@Api(value = "API ==>>  UserRoleController ", description = "用户角色接口")
@RestController
@RequestMapping("/user/user_role")
public class UserRoleController extends BaseController {


    @Autowired
    private UserRoleMapper userRoleMapper;
    @Reference
    private UserRoleService userRoleService;


    @PcWebQueryLog(action = "查询用户角色列表", description = "查询用户角色列表", fullPath = "/user/user_role/getAllUserRoles")
    @ApiOperation(value = "查询用户角色列表", notes = "查询用户角色列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllUserRoles")
    public MyCommonResult<UserRoleVo> doGetAllUserRoles(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserRoleVo> result = new MyCommonResult<UserRoleVo>();
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFormFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFormFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<UserRole> paginationBean = this.parsePaginationJsonToBean(paginationObj,UserRole.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = userRoleService.dealQueryPageByEntitys(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
            dealCommonSuccessCatch(result, "查询用户角色信息列表:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "查询用户角色信息", notes = "根据用户角色id查询用户角色信息", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询用户角色信息", description = "根据用户角色id查询用户角色信息", fullPath = "/user/user_role/getUserRoleById")
    @PostMapping(value = "/getUserRoleById")
    public MyCommonResult<UserRoleVo> doGetUserRoleById(HttpServletRequest request, String roleId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<UserRoleVo> result = new MyCommonResult<UserRoleVo>();
        try {
            Assert.notBlank(roleId, BaseRstMsgConstant.ErrorMsg.unknowId());
            UserRole vo = userRoleMapper.selectById(roleId);
            result.setBean(UserRoleTransfer.transferEntityToVo(vo));
            dealCommonSuccessCatch(result, "查询用户角色信息:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增用户角色", notes = "表单方式新增用户角色", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "新增用户角色", description = "表单方式新增用户角色", fullPath = "/user/user_role/doAddUserRole")
    @PostMapping(value = "/doAddUserRole")
    public MyCommonResult doAddUserRole(HttpServletRequest request, UserRoleVo userRoleVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer addCount = 0;
        try {
            Assert.notNull(userRoleVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = userRoleService.dealCreate(loginUser, userRoleVo);
            result.setCount(addCount);
            dealCommonSuccessCatch(result, UserRoleFuncModuleConstant.Success.create);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量删除用户角色", description = "根据用户角色id批量删除用户角色", fullPath = "/user/user_role/batchDelUserRoleByIds")
    @ApiOperation(value = "批量删除用户角色", notes = "根据用户角色id批量删除用户角色", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的用户角色id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelUserRoleByIds")
    public MyCommonResult doBatchDeleteUserRoleById(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = userRoleService.dealBatchDelete(loginUser, delIds);
            dealCommonSuccessCatch(result, UserRoleFuncModuleConstant.Success.batchDeleteByIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(action = "删除用户角色", description = "根据用户角色id删除用户角色", fullPath = "/user/user_role/delOneUserRoleByIds")
    @ApiOperation(value = "删除用户角色", notes = "根据用户角色id删除用户角色", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的用户角色id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneUserRoleByIds")
    public MyCommonResult doDelOneUserRoleById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer delCount = 0;
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            delCount = userRoleService.dealDeleteById(loginUser, delId);
            dealCommonSuccessCatch(result, UserRoleFuncModuleConstant.Success.deleteById);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


}
