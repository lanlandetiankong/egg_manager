package com.egg.manager.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.api.constants.controllers.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcModule.define.DefineDepartmentFuncModuleConstant;
import com.egg.manager.api.services.basic.define.DefineDepartmentService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.constant.define.DefineDepartmentConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.bean.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineDepartmentMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineDepartmentDto;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefineDepartmentTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineDepartmentVo;
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
@Api(value = "API ==>>  DefineDepartmentController ", description = "部门定义接口")
@RestController
@RequestMapping("/define/define_department")
public class DefineDepartmentController extends BaseController {

    @Autowired
    private DefineDepartmentMapper defineDepartmentMapper;
    @Reference
    private DefineDepartmentService defineDepartmentService;


    @PcWebQueryLog(action = "查询部门定义信息-Dto列表", description = "查询部门定义信息-Dto列表", fullPath = "/define/define_department/getAllDefineDepartmentDtos")
    @ApiOperation(value = "查询部门定义信息-Dto列表", notes = "查询部门定义信息-Dto列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllDefineDepartmentDtos")
    public MyCommonResult<DefineDepartmentVo> doGetAllDefineDepartmentDtos(HttpServletRequest request, String queryObj, String paginationObj, String sortObj
            , @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineDepartmentVo> result = new MyCommonResult<DefineDepartmentVo>();
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineDepartmentDto> paginationBean = this.parsePaginationJsonToBean(paginationObj, DefineDepartmentDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineDepartmentService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
            dealCommonSuccessCatch(result, "查询部门定义信息-Dto列表:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "查询部门定义信息", notes = "根据部门定义id查询部门定义信息", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询部门定义信息", description = "根据部门定义id查询部门定义信息", fullPath = "/define/define_department/getDefineDepartmentById")
    @PostMapping(value = "/getDefineDepartmentById")
    public MyCommonResult<DefineDepartmentVo> doGetDefineDepartmentById(HttpServletRequest request, String defineDepartmentId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineDepartmentVo> result = new MyCommonResult<DefineDepartmentVo>();
        try {
            Assert.notBlank(defineDepartmentId,BaseRstMsgConstant.ErrorMsg.unknowId());

            DefineDepartment defineDepartment = defineDepartmentService.getById(defineDepartmentId);
            result.setBean(DefineDepartmentTransfer.transferEntityToVo(defineDepartment));
            dealCommonSuccessCatch(result, "查询部门定义信息:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebQueryLog(action = "查询部门TreeSelect", description = "查询部门TreeSelect", fullPath = "/define/define_department/getAllDepartmentTreeSelect")
    @ApiOperation(value = "查询部门TreeSelect", notes = "查询部门TreeSelect", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping("/getAllDepartmentTreeSelect")
    public MyCommonResult<DefineDepartment> doGetAllDepartmentTreeSelect(@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineDepartment> result = new MyCommonResult<DefineDepartment>();
        //筛选与排序
        QueryWrapper<DefineDepartment> queryWrapper = new QueryWrapper<DefineDepartment>();
        queryWrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        queryWrapper.orderBy(true,true,"level");
        queryWrapper.orderBy(true,true,"order_num");
        queryWrapper.orderBy(true,true,"create_time");
        List<DefineDepartment> allDepartments = defineDepartmentMapper.selectList(queryWrapper);
        List<CommonTreeSelect> treeList = defineDepartmentService.getTreeSelectChildNodesWithRoot(loginUser, DefineDepartmentConstant.ROOT_DEPARTMENT_ID, allDepartments);
        result.setResultList(treeList);
        return result;
    }

    @PcWebQueryLog(action = "查询被过滤的部门定义TreeSelect", description = "查询被过滤部门定义TreeSelect(过滤指定节点的所有子节点)", fullPath = "/define/define_department/getDepartmentTreeSelectFilterChildrens")
    @ApiOperation(value = "查询被过滤的部门定义TreeSelect", notes = "查询被过滤部门定义TreeSelect(过滤指定节点的所有子节点)", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping("/getDepartmentTreeSelectFilterChildrens")
    public MyCommonResult<DefineDepartment> doGetDepartmentTreeSelectFilterChildrens(String filterId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineDepartment> result = new MyCommonResult<DefineDepartment>();
        List<DefineDepartment> allDepartment = defineDepartmentMapper.getDepartmentFilterChildrens(filterId, true);
        List<CommonTreeSelect> treeList = defineDepartmentService.getTreeSelectChildNodesWithRoot(loginUser, DefineDepartmentConstant.ROOT_DEPARTMENT_ID, allDepartment);
        result.setResultList(treeList);
        return result;
    }

    @ApiOperation(value = "新增部门定义", notes = "表单方式新增部门定义", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "新增部门定义", description = "表单方式新增部门定义", fullPath = "/define/define_department/doAddDefineDepartment")
    @PostMapping(value = "/doAddDefineDepartment")
    public MyCommonResult<DefineDepartmentVo> doAddDefineDepartment(HttpServletRequest request, DefineDepartmentVo defineDepartmentVo,
                                                                    @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineDepartmentVo> result = new MyCommonResult<DefineDepartmentVo>();
        Integer addCount = 0;
        try {
            Assert.notNull(defineDepartmentVo, BaseRstMsgConstant.ErrorMsg.emptyForm());

            addCount = defineDepartmentService.dealCreate(loginUser, defineDepartmentVo);
            result.setCount(addCount);
            dealCommonSuccessCatch(result, DefineDepartmentFuncModuleConstant.Success.create);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新部门定义", notes = "表单方式更新部门定义", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "更新部门定义", description = "表单方式更新部门定义", fullPath = "/define/define_department/doUpdateDefineDepartment")
    @PostMapping(value = "/doUpdateDefineDepartment")
    public MyCommonResult doUpdateDefineDepartment(HttpServletRequest request, DefineDepartmentVo defineDepartmentVo,
                                                   @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer changeCount = 0;
        try {
            Assert.notNull(defineDepartmentVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = defineDepartmentService.dealUpdate(loginUser, defineDepartmentVo, false);
            result.setCount(changeCount);
            dealCommonSuccessCatch(result, DefineDepartmentFuncModuleConstant.Success.update);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量删除部门定义", description = "根据部门id批量删除部门定义", fullPath = "/define/define_department/batchDelDefineDepartmentByIds")
    @ApiOperation(value = "批量删除部门定义", notes = "根据部门id批量删除部门定义", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的部门定义id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelDefineDepartmentByIds")
    public MyCommonResult doBatchDeleteDefineDepartmentById(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = defineDepartmentService.dealBatchDelete(loginUser, delIds);
            dealCommonSuccessCatch(result, DefineDepartmentFuncModuleConstant.Success.deleteById);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(action = "删除部门定义", description = "根据部门id删除部门定义", fullPath = "/define/define_department/delOneDefineDepartmentById")
    @ApiOperation(value = "删除部门定义", notes = "根据部门id删除部门定义", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的部门定义id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneDefineDepartmentById")
    public MyCommonResult doDelOneDefineDepartmentById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            Integer delCount = defineDepartmentService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
            dealCommonSuccessCatch(result, DefineDepartmentFuncModuleConstant.Success.batchDeleteByIds);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

}
