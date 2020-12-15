package com.egg.manager.obl.web.controller.user;


import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.user.basic.OblUserAttentionPersonService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserAttentionPersonEntity;
import com.egg.manager.persistence.obl.user.db.mysql.mapper.OblUserAttentionPersonMapper;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserAttentionPersonDto;
import com.egg.manager.persistence.obl.user.pojo.transfer.OblUserAttentionPersonTransfer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhoucj
 * @description 用户的关注人关联-Api
 * @date 2020-12-03
 */
@Slf4j
@Api(value = "API-用户的关注人关联")
@RestController
@RequestMapping("/oblUserAttentionPerson")
public class OblUserAttentionPersonController extends BaseController {

    @Autowired
    private OblUserAttentionPersonMapper oblUserAttentionPersonMapper;
    @Reference
    private OblUserAttentionPersonService oblUserAttentionPersonService;


    @ApiOperation(value = "分页查询(com.egg.manager.persistence.obl.user.pojo.dto)->用户的关注人关联", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblUserAttentionPerson/queryDtoPage")
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = OblUserAttentionPersonDto.class) QueryPageBean<OblUserAttentionPersonDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = oblUserAttentionPersonService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->用户的关注人关联", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/oblUserAttentionPerson/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String oblUserAttentionPersonId,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(oblUserAttentionPersonId, BaseRstMsgConstant.ErrorMsg.unknowId());
        OblUserAttentionPersonEntity oblUserAttentionPersonEntity = oblUserAttentionPersonMapper.selectById(oblUserAttentionPersonId);
        result.putBean(OblUserAttentionPersonTransfer.transferEntityToVo(oblUserAttentionPersonEntity));
        return result;
    }

}