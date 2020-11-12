package com.egg.manager.web.controller.log.pc.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.log.basic.pc.web.PcWebQueryLogMgoService;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.query.mongo.MyMongoCommonQueryFieldEnum;
import com.egg.manager.persistence.commons.base.enums.query.mongo.MyMongoCommonSortFieldEnum;
import com.egg.manager.persistence.commons.base.query.mongo.MongoQueryBean;
import com.egg.manager.persistence.commons.base.query.mongo.MyMongoQueryBuffer;
import com.egg.manager.persistence.commons.base.query.mongo.MyMongoQueryPageBean;
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


    @PcWebQueryLog(fullPath = "/log/pc/web/queryLog/getDataPage",flag=false)
    @ApiOperation(value = "分页查询->PcWeb查询接口日志", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getDataPage")
    public MyCommonResult<PcWebQueryLogMgo> doGetDataPage(HttpServletRequest request, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        MyCommonResult<PcWebQueryLogMgo> result = MyCommonResult.gainQueryResult(PcWebQueryLogMgo.class);
        try {
            //添加状态过滤,时间倒序排序
            MyMongoQueryBuffer mongoQueryBuffer = new MyMongoQueryBuffer(MyMongoCommonQueryFieldEnum.IsDeleted_Eq_Not)
                    .addBehindSortItem(MyMongoCommonSortFieldEnum.CreateTime_Desc)
                    .getRefreshedSelf();
            mongoQueryBuffer = MongoQueryBean.getMongoQueryBeanFromRequest(request, mongoQueryBuffer);
            MyMongoQueryPageBean<PcWebQueryLogMgo> pageBean = pcWebQueryLogMgoService.doFindPage(loginUserInfo, mongoQueryBuffer);
            dealSetMongoPageResult(result, pageBean);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }
}
