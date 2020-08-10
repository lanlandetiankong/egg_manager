package com.egg.manager.web.controller;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.vo.organization.DefineTenantVo;
import com.egg.manager.service.annotation.log.CurrentLoginUser;
import com.egg.manager.service.annotation.log.OperLog;
import com.egg.manager.service.helper.MyCommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/7/23
 * \* Time: 22:26
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API ==>>  CommonController ",description = "通用功能接口")
@RestController
@RequestMapping("/role/role_menus")
public class CommonController extends BaseController {

    @OperLog(modelName="CommonController",action="根据枚举key查询对应枚举下拉",description = "根据枚举key查询对应枚举下拉")
    @ApiOperation(value = "根据枚举key查询对应枚举下拉", notes = "根据枚举key查询对应枚举下拉", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enumKey",value = "枚举的key值 -> json格式", required = true,dataTypeClass=String.class)
    })
    @PostMapping(value = "/getEnumListByKey")
    public MyCommonResult<DefineTenantVo> doGetAllDefineTenantDtos(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineTenantVo> result = new MyCommonResult<DefineTenantVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            dealCommonSuccessCatch(result,"根据枚举key查询对应枚举下拉:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


}
