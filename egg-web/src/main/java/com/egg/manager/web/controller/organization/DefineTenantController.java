package com.egg.manager.web.controller.organization;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.common.annotation.log.CurrentLoginUser;
import com.egg.manager.common.annotation.log.OperLog;
import com.egg.manager.api.services.basic.CommonFuncService;
import com.egg.manager.api.services.basic.organization.DefineTenantService;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.mapper.organization.DefineTenantMapper;
import com.egg.manager.persistence.pojo.transfer.mysql.organization.DefineTenantMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.organization.DefineTenantMysqlVo;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
@Api(value = "API ==>>  DefineTenantController ",description = "租户定义接口")
@RestController
@RequestMapping("/organization/define_tenant")
public class DefineTenantController extends BaseController{

    @Autowired
    private DefineTenantMapper defineTenantMapper ;
    @Reference
    private DefineTenantService defineTenantService;
    @Reference
    private CommonFuncService commonFuncService ;



    @OperLog(action="查询租户定义信息-Dto列表",description = "查询租户定义信息-Dto列表",fullPath = "/organization/define_tenant/getAllDefineTenantDtos")
    @ApiOperation(value = "查询租户定义信息-Dto列表", notes = "查询租户定义信息-Dto列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineTenantDtos")
    public MyCommonResult<DefineTenantMysqlVo> doGetAllDefineTenantDtos(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineTenantMysqlVo> result = new MyCommonResult<DefineTenantMysqlVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            result = defineTenantService.dealGetDefineTenantDtoPages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询租户定义信息-Dto列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询租户定义信息", notes = "根据租户定义id查询租户定义信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="查询租户定义信息",description = "根据租户定义id查询租户定义信息",fullPath = "/organization/define_tenant/getDefineTenantById")
    @PostMapping(value = "/getDefineTenantById")
    public MyCommonResult<DefineTenantMysqlVo> doGetDefineTenantById(HttpServletRequest request, String defineTenantId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineTenantMysqlVo> result = new MyCommonResult<DefineTenantMysqlVo>() ;
        try{
            DefineTenant defineTenant = defineTenantMapper.selectById(defineTenantId);
            result.setBean(DefineTenantMysqlTransfer.transferEntityToVo(defineTenant));
            dealCommonSuccessCatch(result,"查询租户定义信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(action="查询租户定义信息-Enum列表",description = "查询租户定义信息-Enum列表",fullPath = "/organization/define_tenant/getAllDefineTenantEnums")
    @ApiOperation(value = "查询租户定义信息-Enum列表", notes = "查询租户定义信息-Enum列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineTenantEnums")
    public MyCommonResult<DefineTenantMysqlVo> doGetAllDefineTenantEnums(HttpServletRequest request, String queryObj, String paginationObj, String sortObj, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineTenantMysqlVo> result = new MyCommonResult<DefineTenantMysqlVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = new ArrayList<QueryFormFieldBean>() ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            result = defineTenantService.dealGetDefineTenantDtoPages(result,queryFieldBeanList,null,sortBeans); ;
            result = defineTenantService.dealResultListSetToEntitySelect(result) ;
            dealCommonSuccessCatch(result,"查询租户定义信息Select列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }




    @ApiOperation(value = "新增租户定义", notes = "表单方式新增租户定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="新增租户定义",description = "表单方式新增租户定义",fullPath = "/organization/define_tenant/doAddDefineTenant")
    @PostMapping(value = "/doAddDefineTenant")
    public MyCommonResult<DefineTenantMysqlVo> doAddDefineTenant(HttpServletRequest request, DefineTenantMysqlVo defineTenantVo, @CurrentLoginUser UserAccount loginUser){
        MyCommonResult<DefineTenantMysqlVo> result = new MyCommonResult<DefineTenantMysqlVo>() ;
        Integer addCount = 0 ;
        try{
            if(defineTenantVo == null) {
                throw new Exception("未接收到有效的租户定义！");
            }   else {
                addCount = defineTenantService.dealAddDefineTenant(defineTenantVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增租户定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新租户定义", notes = "表单方式更新租户定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="更新租户定义",description = "表单方式更新租户定义",fullPath = "/organization/define_tenant/doUpdateDefineTenant")
    @PostMapping(value = "/doUpdateDefineTenant")
    public MyCommonResult doUpdateDefineTenant(HttpServletRequest request, DefineTenantMysqlVo defineTenantVo, @CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            if(defineTenantVo == null) {
                throw new Exception("未接收到有效的租户定义！");
            }   else {
                changeCount = defineTenantService.dealUpdateDefineTenant(defineTenantVo,loginUser,false);
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新租户定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(action="批量删除租户定义",description = "根据租户定义id批量删除租户定义",fullPath = "/organization/define_tenant/batchDelDefineTenantByIds")
    @ApiOperation(value = "批量删除租户定义", notes = "根据租户定义id批量删除租户定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的租户定义id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelDefineTenantByIds")
    public MyCommonResult doBatchDeleteDefineTenantByIds(HttpServletRequest request,String[] delIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(delIds != null && delIds.length > 0) {
                delCount = defineTenantService.dealDelDefineTenantByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除租户定义:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(action="删除租户定义",description = "根据租户id删除租户定义",fullPath = "/organization/define_tenant/delOneDefineTenantById")
    @ApiOperation(value = "删除租户定义", notes = "根据租户id删除租户定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的租户定义id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneDefineTenantById")
    public MyCommonResult doDelOneDefineTenantById(HttpServletRequest request,String delId,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = defineTenantService.dealDelDefineTenant(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除租户定义:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

}
