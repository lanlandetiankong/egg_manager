package com.egg.manager.web.controller.log.pc.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.mongodb.mservices.service.log.pc.web.PcWebLoginLogMgoService;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.base.constant.web.api.WebApiConstant;
import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.common.base.enums.query.mongo.MyMongoCommonSortFieldEnum;
import com.egg.manager.common.base.query.mongo.MongoQueryBean;
import com.egg.manager.common.base.query.mongo.MyMongoQueryBuffer;
import com.egg.manager.common.base.query.mongo.MyMongoQueryPageBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebLoginLogMgo;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebLoginLogRepository;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
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
 * @description: 登录日志
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-PcWeb登录接口日志")
@RestController
@RequestMapping("/log/pc/web/loginLog")
public class PcWebLoginLogController extends BaseController {

    @Autowired
    private PcWebLoginLogRepository pcWebLoginLogRepository;

    @Reference
    private PcWebLoginLogMgoService pcWebLoginLogMgoService;

    @PcWebQueryLog(fullPath = "/log/pc/web/loginLog/getDataPage",flag=false)
    @ApiOperation(value = "分页查询->PcWeb登录接口日志", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public MyCommonResult<PcWebLoginLogMgo> doGetDataPage(HttpServletRequest request, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<PcWebLoginLogMgo> result = MyCommonResult.gainQueryResult(PcWebLoginLogMgo.class, "分页查询");
        try {
            //添加状态过滤,时间倒序排序
            MyMongoQueryBuffer mongoQueryBuffer = new MyMongoQueryBuffer(MyMongoCommonQueryFieldEnum.Status_NotEq_Delete)
                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                    .getRefreshedSelf();
            mongoQueryBuffer = MongoQueryBean.getMongoQueryBeanFromRequest(request, mongoQueryBuffer);
            MyMongoQueryPageBean<PcWebLoginLogMgo> pageBean = pcWebLoginLogMgoService.doFindPage(loginUser, mongoQueryBuffer);
            dealSetMongoPageResult(result, pageBean);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e, "分页查询-操作失败");
        }
        return result;
    }
}