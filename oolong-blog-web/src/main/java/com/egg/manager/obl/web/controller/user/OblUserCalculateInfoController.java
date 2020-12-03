package com.egg.manager.obl.web.controller.user;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.obl.user.db.mysql.mapper.OblUserCalculateInfoMapper;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserCalculateInfoDto;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserCalculateInfoEntity;
import com.egg.manager.api.services.obl.user.OblUserCalculateInfoService;
import com.egg.manager.persistence.obl.user.pojo.transfer.OblUserCalculateInfoTransfer;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserCalculateInfoVo;
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
 * @description 用户的计算信息-Api
 * @date 2020-12-03
 */
@Slf4j
@Api(value = "API-用户的计算信息")
@RestController
@RequestMapping("/oblUserCalculateInfo")
public class OblUserCalculateInfoController extends BaseController {

    @Autowired
    private OblUserCalculateInfoMapper oblUserCalculateInfoMapper;
    @Reference
    private OblUserCalculateInfoService oblUserCalculateInfoService;


    @ApiOperation(value = "分页查询(dto)->用户的计算信息", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/oblUserCalculateInfo/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblUserCalculateInfoDto.class) QueryPageBean<OblUserCalculateInfoDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = oblUserCalculateInfoService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->用户的计算信息", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/oblUserCalculateInfo/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblUserCalculateInfoId,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblUserCalculateInfoId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblUserCalculateInfoEntity oblUserCalculateInfoEntity = oblUserCalculateInfoMapper.selectById(oblUserCalculateInfoId);
        result.putBean(OblUserCalculateInfoTransfer.transferEntityToVo(oblUserCalculateInfoEntity));
        return result;
    }

    @ApiOperation(value = "新增->用户的计算信息", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/oblUserCalculateInfo/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, OblUserCalculateInfoVo oblUserCalculateInfoVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(oblUserCalculateInfoVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = oblUserCalculateInfoService.dealCreate(loginUserInfo, oblUserCalculateInfoVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->用户的计算信息", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/oblUserCalculateInfo/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, OblUserCalculateInfoVo oblUserCalculateInfoVo,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(oblUserCalculateInfoVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = oblUserCalculateInfoService.dealUpdate(loginUserInfo, oblUserCalculateInfoVo);
        result.putCount(changeCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/oblUserCalculateInfo/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->用户的计算信息", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
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
        delCount = oblUserCalculateInfoService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/oblUserCalculateInfo/deleteById")
    @ApiOperation(value = "伪删除->用户的计算信息", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId,
                                @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = oblUserCalculateInfoService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}