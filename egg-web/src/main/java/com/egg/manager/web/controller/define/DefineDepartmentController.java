package com.egg.manager.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.api.services.em.define.basic.DefineDepartmentService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.define.DefineDepartmentConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryField;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineDepartmentEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineDepartmentMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineDepartmentDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineDepartmentTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineDepartmentVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
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
public class DefineDepartmentController extends BaseController {
    @Autowired
    private DefineDepartmentMapper defineDepartmentMapper;
    @Reference
    private DefineDepartmentService defineDepartmentService;

    @PcWebQueryLog(fullPath = "/define/defineDepartment/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = DefineDepartmentDto.class) QueryPageBean<DefineDepartmentDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = defineDepartmentService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/define/defineDepartment/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String defineDepartmentId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(defineDepartmentId, BaseRstMsgConstant.ErrorMsg.unknowId());
        DefineDepartmentEntity defineDepartmentEntity = defineDepartmentService.getById(defineDepartmentId);
        result.putBean(DefineDepartmentTransfer.transferEntityToVo(defineDepartmentEntity));
        return result;
    }

    @PcWebQueryLog(fullPath = "/define/defineDepartment/queryTreeSelect")
    @ApiOperation(value = "查询下拉树->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping("/queryTreeSelect")
    public WebResult queryTreeSelect(@CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        //筛选与排序
        QueryWrapper<DefineDepartmentEntity> queryWrapper = new QueryWrapper<DefineDepartmentEntity>();
        queryWrapper.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryWrapper.orderBy(true, true, "level");
        queryWrapper.orderBy(true, true, "order_num");
        queryWrapper.orderBy(true, true, FieldConst.COL_CREATE_TIME);
        List<DefineDepartmentEntity> allDepartments = defineDepartmentMapper.selectList(queryWrapper);
        List<CommonTreeSelect> treeList = defineDepartmentService.getTreeSelectChildNodesWithRoot(loginUserInfo, DefineDepartmentConstant.ROOT_DEPARTMENT_ID, allDepartments);
        result.putResultList(treeList);
        return result;
    }

    @PcWebQueryLog(description = "查询被过滤部门定义TreeSelect(过滤指定节点的所有子节点)", fullPath = "/define/defineDepartment/queryFilteredTreeSelect")
    @ApiOperation(value = "筛选查询下拉树->部门定义", notes = "查询被过滤部门定义TreeSelect(过滤指定节点的所有子节点)", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping("/queryFilteredTreeSelect")
    public WebResult queryFilteredTreeSelect(String filterId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        List<DefineDepartmentEntity> allDepartment = defineDepartmentMapper.getDepartmentFilterChildrens(filterId, true);
        List<CommonTreeSelect> treeList = defineDepartmentService.getTreeSelectChildNodesWithRoot(loginUserInfo, DefineDepartmentConstant.ROOT_DEPARTMENT_ID, allDepartment);
        result.putResultList(treeList);
        return result;
    }

    @ApiOperation(value = "新增->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineDepartment/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, DefineDepartmentVo defineDepartmentVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(defineDepartmentVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = defineDepartmentService.dealCreate(loginUserInfo, defineDepartmentVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineDepartment/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, DefineDepartmentVo defineDepartmentVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(defineDepartmentVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = defineDepartmentService.dealUpdate(loginUserInfo, defineDepartmentVo);
        result.putCount(changeCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/define/defineDepartment/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = defineDepartmentService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/define/defineDepartment/deleteById")
    @ApiOperation(value = "伪删除->部门定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = defineDepartmentService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}
