package com.egg.manager.web.controller.log.pc.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.log.basic.pc.web.PcWebQueryLogMgoService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.mongodb.MongoFieldConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.SwitchStateEnum;
import com.egg.manager.persistence.commons.base.query.mongo.MongoQueryBean;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebQueryLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.repository.pc.web.PcWebQueryLogRepository;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
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

/**
 * @author zhoucj
 * @description 查询日志
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-PcWeb查询日志")
@RestController
@RequestMapping("/log/pc/web/queryLog")
public class PcWebQueryLogController extends BaseController {
    @Autowired
    private PcWebQueryLogRepository pcWebQueryLogRepository;
    @Reference
    private PcWebQueryLogMgoService pcWebQueryLogMgoService;

    @PcWebQueryLog(fullPath = "/log/pc/web/queryLog/getDataPage", flag = false)
    @ApiOperation(value = "分页查询->PcWeb查询接口日志", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public WebResult doGetDataPage(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        //添加状态过滤,时间倒序排序
        QueryPageBean mongoQueryPage = new QueryPageBean();
        mongoQueryPage.operateQuery().addNotEq(MongoFieldConstant.FIELD_ISDELETED, SwitchStateEnum.Close.getValue());
        mongoQueryPage.operateSortMap().putDesc(MongoFieldConstant.FIELD_CREATETIME);
        mongoQueryPage = MongoQueryBean.getMongoQueryBeanFromRequest(request, mongoQueryPage);
        AntdvPage<PcWebQueryLogMgo> pageBean = pcWebQueryLogMgoService.doFindPage(loginUserInfo, mongoQueryPage);
        result.putPage(pageBean);
        return result;
    }
}
