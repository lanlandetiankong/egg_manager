package com.egg.manager.web.controller.module;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.funcmodule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcmodule.controllers.module.DefineModuleFuncModuleConstant;
import com.egg.manager.api.services.basic.module.DefineModuleService;
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
import com.egg.manager.persistence.db.mysql.entity.module.DefineModule;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.module.DefineModuleMapper;
import com.egg.manager.persistence.pojo.mysql.dto.module.DefineModuleDto;
import com.egg.manager.persistence.pojo.mysql.transfer.module.DefineModuleTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.module.DefineModuleVo;
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
 * @author: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API-模块定义接口")
@RestController
@RequestMapping("/module/define_module")
public class DefineModuleController extends BaseController {

    @Autowired
    private DefineModuleMapper defineModuleMapper;
    @Reference
    private DefineModuleService defineModuleService;


    @PcWebQueryLog(fullPath = "/module/define_module/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->模块定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public MyCommonResult<DefineModuleVo> queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                                   @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineModuleVo> result = MyCommonResult.gainQueryResult(DefineModuleVo.class, DefineModuleFuncModuleConstant.Success.QUERY_PAGE);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineModuleDto> paginationBean = this.parsePaginationJsonToBean(paginationObj,DefineModuleDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineModuleService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineModuleFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->模块定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/module/define_module/queryOneById")
    @PostMapping(value = "/queryOneById")
    public MyCommonResult<DefineModuleVo> queryOneById(HttpServletRequest request, String defineModuleId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineModuleVo> result = MyCommonResult.gainQueryResult(DefineModuleVo.class, DefineModuleFuncModuleConstant.Success.QUERY_PAGE);
        try {
            DefineModule defineModule = defineModuleMapper.selectById(defineModuleId);
            result.setBean(DefineModuleTransfer.transferEntityToVo(defineModule));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineModuleFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }


    @ApiOperation(value = "新增->模块定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/module/define_module/createByForm")
    @PostMapping(value = "/createByForm")
    public MyCommonResult createByForm(HttpServletRequest request, DefineModuleVo defineModuleVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineModuleFuncModuleConstant.Success.CREATE_OPER);
        Integer addCount = 0;
        try {
            Assert.notNull(defineModuleVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = defineModuleService.dealCreate(loginUser, defineModuleVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineModuleFuncModuleConstant.Failure.CREATE_OPER);
        }
        return result;
    }


    @ApiOperation(value = "更新->模块定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/module/define_module/updateByForm")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult updateByForm(HttpServletRequest request, DefineModuleVo defineModuleVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( DefineModuleFuncModuleConstant.Success.UPDATE_OPER);
        Integer changeCount = 0;
        try {
            Assert.notNull(defineModuleVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = defineModuleService.dealUpdate(loginUser, defineModuleVo);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineModuleFuncModuleConstant.Failure.UPDATE_OPER);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/module/define_module/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->模块定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public MyCommonResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( DefineModuleFuncModuleConstant.Success.BATCH_DELETE_BY_IDS);
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
            delCount = defineModuleService.dealBatchDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineModuleFuncModuleConstant.Failure.BATCH_DELETE_BY_IDS);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/module/define_module/deleteById")
    @ApiOperation(value = "伪删除->模块定义",response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public MyCommonResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( DefineModuleFuncModuleConstant.Success.DELETE_BY_ID);
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());
            Integer delCount = defineModuleService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineModuleFuncModuleConstant.Failure.DELETE_BY_ID);
        }
        return result;
    }

}
