package com.egg.manager.web.controller.define;

import com.egg.manager.common.base.enums.PublicResultEnum;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.persistence.entity.define.DefinePermission;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mapper.define.DefinePermissionMapper;
import com.egg.manager.persistence.transfer.define.DefinePermissionTransfer;
import com.egg.manager.persistence.vo.define.DefinePermissionVo;
import com.egg.manager.service.annotation.log.CurrentLoginUser;
import com.egg.manager.service.annotation.log.OperLog;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.service.service.CommonFuncService;
import com.egg.manager.service.service.define.DefinePermissionService;
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
@Api(value = "API ==>>  DefinePermissionController ",description = "权限定义接口")
@RestController
@RequestMapping("/define/define_permission")
public class DefinePermissionController  extends BaseController{

    @Autowired
    private DefinePermissionMapper definePermissionMapper ;
    @Autowired
    private DefinePermissionService definePermissionService;
    @Autowired
    private CommonFuncService commonFuncService ;


    @OperLog(modelName="DefinePermissionController",action="查询权限定义信息列表",description = "查询权限定义信息列表")
    @ApiOperation(value = "查询权限定义信息列表", notes = "查询权限定义信息列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefinePermissions")
    public MyCommonResult<DefinePermissionVo> doGetAllDefinePermissions(HttpServletRequest request,String queryObj, String paginationObj,String sortObj,@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermissionVo> result = new MyCommonResult<DefinePermissionVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("ensure", SwitchStateEnum.Open.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            definePermissionService.dealGetDefinePermissionPages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询权限定义信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @OperLog(modelName="DefinePermissionController",action="查询权限定义信息-Dto列表",description = "查询权限定义信息-Dto列表")
    @ApiOperation(value = "查询权限定义信息-Dto列表", notes = "查询权限定义信息-Dto列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefinePermissionDtos")
    public MyCommonResult<DefinePermissionVo> doGetAllDefinePermissionDtos(HttpServletRequest request,String queryObj, String paginationObj,String sortObj,@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermissionVo> result = new MyCommonResult<DefinePermissionVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            definePermissionService.dealGetDefinePermissionDtoPages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询权限定义信息-Dto列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询权限定义信息", notes = "根据权限定义id查询权限定义信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefinePermissionController",action="查询权限定义信息",description = "根据权限定义id查询权限定义信息")
    @PostMapping(value = "/getDefinePermissionById")
    public MyCommonResult<DefinePermissionVo> doGetDefinePermissionById(HttpServletRequest request,String definePermissionId,@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermissionVo> result = new MyCommonResult<DefinePermissionVo>() ;
        try{
            DefinePermission definePermission = definePermissionMapper.selectById(definePermissionId);
            result.setBean(DefinePermissionTransfer.transferEntityToVo(definePermission));
            dealCommonSuccessCatch(result,"查询权限定义信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "新增权限定义", notes = "表单方式新增权限定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefinePermissionController",action="新增权限定义",description = "表单方式新增权限定义")
    @PostMapping(value = "/doAddDefinePermission")
    public MyCommonResult<DefinePermissionVo> doAddDefinePermission(HttpServletRequest request,DefinePermissionVo definePermissionVo,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult<DefinePermissionVo> result = new MyCommonResult<DefinePermissionVo>() ;
        Integer addCount = 0 ;
        try{
            if(definePermissionVo == null) {
                throw new Exception("未接收到有效的权限定义！");
            }   else {
                addCount = definePermissionService.dealAddDefinePermission(definePermissionVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增权限定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新权限定义", notes = "表单方式更新权限定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefinePermissionController",action="更新权限定义",description = "表单方式更新权限定义")
    @PostMapping(value = "/doUpdateDefinePermission")
    public MyCommonResult doUpdateDefinePermission(HttpServletRequest request,DefinePermissionVo definePermissionVo,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            if(definePermissionVo == null) {
                throw new Exception("未接收到有效的权限定义！");
            }   else {
                changeCount = definePermissionService.dealUpdateDefinePermission(definePermissionVo,loginUser,false);
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新权限定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefinePermissionController",action="批量删除权限定义",description = "根据权限id批量删除权限定义")
    @ApiOperation(value = "批量删除权限定义", notes = "根据权限id批量删除权限定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的权限定义id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelDefinePermissionByIds")
    public MyCommonResult doBatchDeleteDefinePermissionById(HttpServletRequest request,String[] delIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(delIds != null && delIds.length > 0) {
                delCount = definePermissionService.dealDelDefinePermissionByArr(delIds,loginUser);
                StringBuffer respMsg = new StringBuffer("批量删除权限定义:"+actionSuccessMsg) ;
                if(delIds.length > delCount){
                    respMsg.append("由于部分权限已经确认启用后无法删除！预计删除"+delIds.length+"条数据，实际删除"+delCount+"条数据。");
                }
                dealCommonSuccessCatch(result,respMsg.toString());
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @OperLog(modelName="DefinePermissionController",action="批量启用权限定义",description = "根据权限id批量启用权限定义")
    @ApiOperation(value = "批量启用权限", notes = "根据权限id批量启用权限定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要启用的权限定义id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchEnsureDefinePermissionByIds")
    public MyCommonResult doBatchEnsureDefinePermissionById(HttpServletRequest request,String[] ensureIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(ensureIds != null && ensureIds.length > 0) {
                delCount = definePermissionService.dealEnsureDefinePermissionByArr(ensureIds,loginUser);
                dealCommonSuccessCatch(result,"批量启用权限定义:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(modelName="DefinePermissionController",action="删除权限定义",description = "根据权限id删除权限定义")
    @ApiOperation(value = "删除权限定义", notes = "根据权限id删除权限定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的权限定义id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneDefinePermissionByIds")
    public MyCommonResult doDelOneDefinePermissionByIds(HttpServletRequest request,String delId,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = definePermissionService.dealDelDefinePermission(delId,loginUser);
                result.setCount(delCount);
                if(new Integer(0).equals(delCount)){    //如果删除的是 [已启用的]，则抛出异常
                    throw new BusinessException("删除权限定义:"+actionFailMsg+ PublicResultEnum.SwitchOpenChangeLimit.getLabel()) ;
                }
                dealCommonSuccessCatch(result,"删除权限定义:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

}