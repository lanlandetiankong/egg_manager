package com.egg.manager.em.web.controller.emctl.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.define.basic.EmDefineDepartmentService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineDepartmentEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineDepartmentDto;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineDepartmentVo;
import com.egg.manager.persistence.em.user.domain.constant.DefineDepartmentConstant;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineDepartmentMapper;
import com.egg.manager.persistence.em.define.pojo.transfer.EmDefineDepartmentTransfer;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
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
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-部门定义接口")
@RestController
@RequestMapping("/define/defineDepartment")
public class EmDefineDepartmentController extends BaseController {
    @Autowired
    private EmDefineDepartmentMapper emDefineDepartmentMapper;
    @Reference
    private EmDefineDepartmentService emDefineDepartmentService;

    @EmPcWebQueryLog(fullPath = "/define/defineDepartment/queryDtoPage")
    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.article.pojo.dto)->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = EmDefineDepartmentDto.class) QueryPageBean<EmDefineDepartmentDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addEqNotDeleted();
        result = emDefineDepartmentService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/define/defineDepartment/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String defineDepartmentId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(defineDepartmentId, BaseRstMsgConstant.ErrorMsg.unknowId());
        EmDefineDepartmentEntity emDefineDepartmentEntity = emDefineDepartmentService.getById(defineDepartmentId);
        result.putBean(EmDefineDepartmentTransfer.transferEntityToVo(emDefineDepartmentEntity));
        return result;
    }

    @EmPcWebQueryLog(fullPath = "/define/defineDepartment/queryTreeSelect")
    @ApiOperation(value = "查询下拉树->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping("/queryTreeSelect")
    public WebResult queryTreeSelect(@CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        //筛选与排序
        QueryWrapper<EmDefineDepartmentEntity> queryWrapper = new QueryWrapper<EmDefineDepartmentEntity>();
        queryWrapper.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryWrapper.orderBy(true, true, "level");
        queryWrapper.orderBy(true, true, "weights");
        queryWrapper.orderBy(true, true, FieldConst.COL_CREATE_TIME);
        List<EmDefineDepartmentEntity> allDepartments = emDefineDepartmentMapper.selectList(queryWrapper);
        List<CommonTreeSelect> treeList = emDefineDepartmentService.getTreeSelectChildNodesWithRoot(loginUserInfo, DefineDepartmentConstant.ROOT_DEPARTMENT_ID, allDepartments);
        result.putGridList(treeList);
        return result;
    }

    @EmPcWebQueryLog(description = "查询被过滤部门定义TreeSelect(过滤指定节点的所有子节点)", fullPath = "/define/defineDepartment/queryFilteredTreeSelect")
    @ApiOperation(value = "筛选查询下拉树->部门定义", notes = "查询被过滤部门定义TreeSelect(过滤指定节点的所有子节点)", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping("/queryFilteredTreeSelect")
    public WebResult queryFilteredTreeSelect(String filterId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        List<EmDefineDepartmentEntity> allDepartment = emDefineDepartmentMapper.getDepartmentFilterChildrens(filterId, true);
        List<CommonTreeSelect> treeList = emDefineDepartmentService.getTreeSelectChildNodesWithRoot(loginUserInfo, DefineDepartmentConstant.ROOT_DEPARTMENT_ID, allDepartment);
        result.putGridList(treeList);
        return result;
    }

    @ApiOperation(value = "新增->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/define/defineDepartment/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, EmDefineDepartmentVo emDefineDepartmentVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(emDefineDepartmentVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = emDefineDepartmentService.dealCreate(loginUserInfo, emDefineDepartmentVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/define/defineDepartment/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, EmDefineDepartmentVo emDefineDepartmentVo,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(emDefineDepartmentVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = emDefineDepartmentService.dealUpdate(loginUserInfo, emDefineDepartmentVo);
        result.putCount(changeCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/define/defineDepartment/batchDeleteByIds")
    @ApiOperation(value = "批量逻辑删除->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = emDefineDepartmentService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/define/defineDepartment/deleteById")
    @ApiOperation(value = "逻辑删除->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = emDefineDepartmentService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}
