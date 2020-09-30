package com.egg.manager.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.funcmodule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcmodule.controllers.define.DefinePermissionFuncModuleConstant;
import com.egg.manager.api.services.basic.define.DefinePermissionService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.enums.PublicResultEnum;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefinePermissionMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefinePermissionDto;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefinePermissionTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefinePermissionVo;
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
@Api(value = "API-权限定义接口")
@RestController
@RequestMapping("/define/define_permission")
public class DefinePermissionController extends BaseController {

    @Autowired
    private DefinePermissionMapper definePermissionMapper;

    @Reference
    private DefinePermissionService definePermissionService;


    @PcWebQueryLog(action = "查询权限定义信息列表", description = "查询权限定义信息列表", fullPath = "/define/define_permission/getAllDefinePermissions")
    @ApiOperation(value = "查询权限定义信息列表", notes = "查询权限定义信息列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllDefinePermissions")
    public MyCommonResult<DefinePermissionVo> doGetAllDefinePermissions(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermissionVo> result = MyCommonResult.gainQueryResult(DefinePermissionVo.class, DefinePermissionFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("ensure", SwitchStateEnum.Open.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefinePermission> paginationBean = this.parsePaginationJsonToBean(paginationObj,DefinePermission.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = definePermissionService.dealQueryPageByEntitys(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }

    @PcWebQueryLog(action = "查询权限定义信息-Dto列表", description = "查询权限定义信息-Dto列表", fullPath = "/define/define_permission/getAllDefinePermissionDtos")
    @ApiOperation(value = "查询权限定义信息-Dto列表", notes = "查询权限定义信息-Dto列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllDefinePermissionDtos")
    public MyCommonResult<DefinePermissionVo> doGetAllDefinePermissionDtos(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermissionVo> result = MyCommonResult.gainQueryResult(DefinePermissionVo.class,DefinePermissionFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefinePermissionDto> paginationBean = this.parsePaginationJsonToBean(paginationObj,DefinePermissionDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = definePermissionService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }


    @ApiOperation(value = "查询权限定义信息", notes = "根据权限定义id查询权限定义信息", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询权限定义信息", description = "根据权限定义id查询权限定义信息", fullPath = "/define/define_permission/getDefinePermissionById")
    @PostMapping(value = "/getDefinePermissionById")
    public MyCommonResult<DefinePermissionVo> doGetDefinePermissionById(HttpServletRequest request, String definePermissionId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermissionVo> result = MyCommonResult.gainQueryResult(DefinePermissionVo.class,DefinePermissionFuncModuleConstant.Success.QUERY_ONE_BY_ID);
        try {
            DefinePermission definePermission = definePermissionMapper.selectById(definePermissionId);
            result.setBean(DefinePermissionTransfer.transferEntityToVo(definePermission));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.QUERY_ONE_BY_ID);
        }
        return result;
    }


    @ApiOperation(value = "新增权限定义", notes = "表单方式新增权限定义", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "新增权限定义", description = "表单方式新增权限定义", fullPath = "/define/define_permission/doAddDefinePermission")
    @PostMapping(value = "/doAddDefinePermission")
    public MyCommonResult doAddDefinePermission(HttpServletRequest request, DefinePermissionVo definePermissionVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefinePermissionFuncModuleConstant.Success.CREATE);
        Integer addCount = 0;
        try {
            Assert.notNull(definePermissionVo, BaseRstMsgConstant.ErrorMsg.emptyForm());

            addCount = definePermissionService.dealCreate(loginUser, definePermissionVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.CREATE);
        }
        return result;
    }


    @ApiOperation(value = "更新权限定义", notes = "表单方式更新权限定义", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "更新权限定义", description = "表单方式更新权限定义", fullPath = "/define/define_permission/doUpdateDefinePermission")
    @PostMapping(value = "/doUpdateDefinePermission")
    public MyCommonResult doUpdateDefinePermission(HttpServletRequest request, DefinePermissionVo definePermissionVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefinePermissionFuncModuleConstant.Success.UPDATE);
        Integer changeCount = 0;
        try {
            Assert.notNull(definePermissionVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = definePermissionService.dealUpdate(loginUser, definePermissionVo, false);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.UPDATE);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量启用权限定义", description = "根据权限id批量启用权限定义", fullPath = "/define/define_permission/batchEnsureDefinePermissionByIds")
    @ApiOperation(value = "批量启用权限", notes = "根据权限id批量启用权限定义", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要启用的权限定义id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchEnsureDefinePermissionByIds")
    public MyCommonResult doBatchEnsureDefinePermissionById(HttpServletRequest request, String[] ensureIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefinePermissionFuncModuleConstant.Success.BATCH_ENSURE);
        Integer delCount = 0;
        try {
            Assert.notEmpty(ensureIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = definePermissionService.dealBatchEnsure(loginUser, ensureIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.BATCH_ENSURE);
        }
        return result;
    }


    @PcWebOperationLog(action = "删除权限定义", description = "根据权限id删除权限定义", fullPath = "/define/define_permission/delOneDefinePermissionByIds")
    @ApiOperation(value = "删除权限定义", notes = "根据权限id删除权限定义", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的权限定义id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneDefinePermissionByIds")
    public MyCommonResult doDelOneDefinePermissionByIds(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefinePermissionFuncModuleConstant.Success.DELETE_BY_ID);
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer delCount = definePermissionService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
            if (new Integer(0).equals(delCount)) {
                //如果删除的是 [已启用的]，则抛出异常
                throw new BusinessException("删除权限定义:" + actionFailMsg + PublicResultEnum.SwitchOpenChangeLimit.getLabel());
            }
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.DELETE_BY_ID);
        }
        return result;
    }
    @PcWebOperationLog(action = "批量删除权限定义", description = "根据权限id批量删除权限定义", fullPath = "/define/define_permission/batchDelDefinePermissionByIds")
    @ApiOperation(value = "批量删除权限定义", notes = "根据权限id批量删除权限定义", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的权限定义id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelDefinePermissionByIds")
    public MyCommonResult doBatchDeleteDefinePermissionById(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefinePermissionFuncModuleConstant.Success.BATCH_DELETE_BY_IDS);
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            delCount = definePermissionService.dealBatchDelete(loginUser, delIds);
            if (delIds.length > delCount) {
                result.setInfo("由于部分权限已经确认启用后无法删除！预计删除" + delIds.length + "条数据，实际删除" + delCount + "条数据。");
            }
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result,e,DefinePermissionFuncModuleConstant.Failure.BATCH_DELETE_BY_IDS);
        }
        return result;
    }
}
