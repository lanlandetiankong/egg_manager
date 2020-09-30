package com.egg.manager.web.controller.module;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.funcmodule.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcmodule.controllers.module.DefineModuleFuncModuleConstant;
import com.egg.manager.api.services.basic.module.DefineModuleService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
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
 * \* User: zhouchengjie
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


    @PcWebQueryLog(action = "查询模块定义信息-Dto列表", description = "查询模块定义信息-Dto列表", fullPath = "/module/define_module/getAllDefineModuleDtos")
    @ApiOperation(value = "查询模块定义信息-Dto列表", notes = "查询模块定义信息-Dto列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllDefineModuleDtos")
    public MyCommonResult<DefineModuleVo> doGetAllDefineModuleDtos(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
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


    @ApiOperation(value = "查询模块定义信息", notes = "根据模块定义id查询模块定义信息", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询模块定义信息", description = "根据模块定义id查询模块定义信息", fullPath = "/module/define_module/getDefineModuleById")
    @PostMapping(value = "/getDefineModuleById")
    public MyCommonResult<DefineModuleVo> doGetDefineModuleById(HttpServletRequest request, String defineModuleId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineModuleVo> result = MyCommonResult.gainQueryResult(DefineModuleVo.class, DefineModuleFuncModuleConstant.Success.QUERY_PAGE);
        try {
            DefineModule defineModule = defineModuleMapper.selectById(defineModuleId);
            result.setBean(DefineModuleTransfer.transferEntityToVo(defineModule));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineModuleFuncModuleConstant.Failure.QUERY_PAGE);
        }
        return result;
    }


    @ApiOperation(value = "新增模块定义", notes = "表单方式新增模块定义", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "新增模块定义", description = "表单方式新增模块定义", fullPath = "/module/define_module/doAddDefineModule")
    @PostMapping(value = "/doAddDefineModule")
    public MyCommonResult doAddDefineModule(HttpServletRequest request, DefineModuleVo defineModuleVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult(DefineModuleFuncModuleConstant.Success.CREATE);
        Integer addCount = 0;
        try {
            Assert.notNull(defineModuleVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = defineModuleService.dealCreate(loginUser, defineModuleVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineModuleFuncModuleConstant.Failure.CREATE);
        }
        return result;
    }


    @ApiOperation(value = "更新模块定义", notes = "表单方式更新模块定义", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "更新模块定义", description = "表单方式更新模块定义", fullPath = "/module/define_module/doUpdateDefineModule")
    @PostMapping(value = "/doUpdateDefineModule")
    public MyCommonResult doUpdateDefineModule(HttpServletRequest request, DefineModuleVo defineModuleVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult( DefineModuleFuncModuleConstant.Success.UPDATE);
        Integer changeCount = 0;
        try {
            Assert.notNull(defineModuleVo,BaseRstMsgConstant.ErrorMsg.emptyForm());
            changeCount = defineModuleService.dealUpdate(loginUser, defineModuleVo, false);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e,DefineModuleFuncModuleConstant.Failure.UPDATE);
        }
        return result;
    }


    @PcWebOperationLog(action = "批量删除模块定义", description = "根据菜单定义id批量删除模块定义", fullPath = "/module/define_module/batchDelDefineModuleByIds")
    @ApiOperation(value = "批量删除模块定义", notes = "根据菜单定义id批量删除模块定义", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的模块定义id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelDefineModuleByIds")
    public MyCommonResult doBatchDeleteDefineModuleById(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
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


    @PcWebOperationLog(action = "删除模块定义", description = "根据模块id删除模块定义", fullPath = "/module/define_module/delOneDefineModuleById")
    @ApiOperation(value = "删除模块定义", notes = "根据模块id删除模块定义", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的模块定义id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneDefineModuleById")
    public MyCommonResult doDelOneDefineModuleById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
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
