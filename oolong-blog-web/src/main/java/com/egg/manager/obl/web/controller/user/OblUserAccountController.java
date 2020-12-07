package com.egg.manager.obl.web.controller.user;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.user.basic.OblUserAccountService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserAccountEntity;
import com.egg.manager.persistence.obl.user.db.mysql.mapper.OblUserAccountMapper;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserAccountDto;
import com.egg.manager.persistence.obl.user.pojo.transfer.OblUserAccountTransfer;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserAccountVo;
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
 * @description 用户表-Api
 * @date 2020-12-07
 */
@Slf4j
@Api(value = "API-用户表")
@RestController
@RequestMapping("/oblUserAccount")
public class OblUserAccountController extends BaseController {

    @Autowired
    private OblUserAccountMapper oblUserAccountMapper;
    @Reference
    private OblUserAccountService oblUserAccountService;


    @ApiOperation(value = "分页查询(dto)->用户表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebQueryLog(fullPath = "/oblUserAccount/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblUserAccountDto.class) QueryPageBean<OblUserAccountDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = oblUserAccountService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->用户表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebQueryLog(fullPath = "/oblUserAccount/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblUserAccountId,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblUserAccountId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblUserAccountEntity oblUserAccountEntity = oblUserAccountMapper.selectById(oblUserAccountId);
        result.putBean(OblUserAccountTransfer.transferEntityToVo(oblUserAccountEntity));
        return result;
    }

    @ApiOperation(value = "新增->用户表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblUserAccount/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, OblUserAccountVo oblUserAccountVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(oblUserAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = oblUserAccountService.dealCreate(loginUserInfo, oblUserAccountVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->用户表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @OblPcWebOperationLog(fullPath = "/oblUserAccount/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, OblUserAccountVo oblUserAccountVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(oblUserAccountVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = oblUserAccountService.dealUpdate(loginUserInfo, oblUserAccountVo);
        result.putCount(changeCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblUserAccount/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->用户表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds,
                                      @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = oblUserAccountService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @OblPcWebOperationLog(fullPath = "/oblUserAccount/deleteById")
    @ApiOperation(value = "伪删除->用户表", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = oblUserAccountService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}