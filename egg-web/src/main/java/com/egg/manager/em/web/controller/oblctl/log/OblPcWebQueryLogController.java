package com.egg.manager.em.web.controller.oblctl.log;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.obl.log.mongo.OblPcWebQueryLogMgoService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.db.MongoFieldConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.obl.OblPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebQueryLogMgo;
import com.egg.manager.persistence.obl.log.db.mongo.repository.OblPcWebQueryLogRepository;
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
 * @description 查询日志
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-OolongBlog_PcWeb查询日志")
@RestController
@RequestMapping("/oblCtl/log/obl/pc/web/queryLog")
public class OblPcWebQueryLogController extends BaseController {
    @Autowired
    private OblPcWebQueryLogRepository pcWebQueryLogRepository;
    @Reference
    private OblPcWebQueryLogMgoService pcWebQueryLogMgoService;

    @OblPcWebQueryLog(fullPath = "/oblCtl/log/obl/pc/web/queryLog/getDataPage", flag = false)
    @ApiOperation(value = "分页查询->PcWeb查询接口日志", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public WebResult doGetDataPage(HttpServletRequest request, @QueryPage(tClass = OblPcWebQueryLogMgo.class) QueryPageBean<OblPcWebQueryLogMgo> queryPageBean,
                                   @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        //添加状态过滤,时间倒序排序
        queryPageBean.operateQuery().addNotEq(MongoFieldConstant.FIELD_ISDELETED, SwitchStateEnum.Close.getValue());
        queryPageBean.operateSortMap().putDesc(MongoFieldConstant.FIELD_CREATETIME);
        AntdvPage<OblPcWebQueryLogMgo> pageBean = pcWebQueryLogMgoService.doFindPage(loginUserInfo, queryPageBean);
        result.putPage(pageBean);
        return result;
    }
}
