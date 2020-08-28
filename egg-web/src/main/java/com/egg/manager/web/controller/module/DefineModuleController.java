package com.egg.manager.web.controller.module;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.common.annotation.log.CurrentLoginUser;
import com.egg.manager.common.annotation.log.OperLog;
import com.egg.manager.api.services.basic.module.DefineModuleService;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.module.DefineModule;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.mapper.module.DefineModuleMapper;
import com.egg.manager.persistence.pojo.transfer.module.DefineModuleTransfer;
import com.egg.manager.persistence.pojo.vo.module.DefineModuleVo;
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
@Api(value = "API ==>>  DefineModuleController ",description = "模块定义接口")
@RestController
@RequestMapping("/module/define_module")
public class DefineModuleController extends BaseController{

    @Autowired
    private DefineModuleMapper defineModuleMapper ;
    @Reference
    private DefineModuleService defineModuleService;
 



    @OperLog(action="查询模块定义信息-Dto列表",description = "查询模块定义信息-Dto列表",fullPath = "/module/define_module/getAllDefineModuleDtos")
    @ApiOperation(value = "查询模块定义信息-Dto列表", notes = "查询模块定义信息-Dto列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineModuleDtos")
    public MyCommonResult<DefineModuleVo> doGetAllDefineModuleDtos(HttpServletRequest request,String queryObj, String paginationObj, String sortObj,
                                                                   @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineModuleVo> result = new MyCommonResult<DefineModuleVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            defineModuleService.dealGetDefineModuleDtoPages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询模块定义信息-Dto列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询模块定义信息", notes = "根据模块定义id查询模块定义信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="查询模块定义信息",description = "根据模块定义id查询模块定义信息",fullPath = "/module/define_module/getDefineModuleById")
    @PostMapping(value = "/getDefineModuleById")
    public MyCommonResult<DefineModuleVo> doGetDefineModuleById(HttpServletRequest request,String defineModuleId,@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineModuleVo> result = new MyCommonResult<DefineModuleVo>() ;
        try{
            DefineModule defineModule = defineModuleMapper.selectById(defineModuleId);
            result.setBean(DefineModuleTransfer.transferEntityToVo(defineModule));
            dealCommonSuccessCatch(result,"查询模块定义信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "新增模块定义", notes = "表单方式新增模块定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="新增模块定义",description = "表单方式新增模块定义",fullPath = "/module/define_module/doAddDefineModule")
    @PostMapping(value = "/doAddDefineModule")
    public MyCommonResult<DefineModuleVo> doAddDefineModule(HttpServletRequest request,DefineModuleVo defineModuleVo,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult<DefineModuleVo> result = new MyCommonResult<DefineModuleVo>() ;
        Integer addCount = 0 ;
        try{
            if(defineModuleVo == null) {
                throw new Exception("未接收到有效的模块定义！");
            }   else {
                addCount = defineModuleService.dealAddDefineModule(defineModuleVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增模块定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新模块定义", notes = "表单方式更新模块定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="更新模块定义",description = "表单方式更新模块定义",fullPath = "/module/define_module/doUpdateDefineModule")
    @PostMapping(value = "/doUpdateDefineModule")
    public MyCommonResult doUpdateDefineModule(HttpServletRequest request,DefineModuleVo defineModuleVo,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            if(defineModuleVo == null) {
                throw new Exception("未接收到有效的模块定义！");
            }   else {
                changeCount = defineModuleService.dealUpdateDefineModule(defineModuleVo,loginUser,false);
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新模块定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(action="批量删除模块定义",description = "根据菜单定义id批量删除模块定义",fullPath = "/module/define_module/batchDelDefineModuleByIds")
    @ApiOperation(value = "批量删除模块定义", notes = "根据菜单定义id批量删除模块定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的模块定义id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelDefineModuleByIds")
    public MyCommonResult doBatchDeleteDefineModuleById(HttpServletRequest request,String[] delIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(delIds != null && delIds.length > 0) {
                delCount = defineModuleService.dealDelDefineModuleByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除模块定义:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(action="删除模块定义",description = "根据模块id删除模块定义",fullPath = "/module/define_module/delOneDefineModuleById")
    @ApiOperation(value = "删除模块定义", notes = "根据模块id删除模块定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的模块定义id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneDefineModuleById")
    public MyCommonResult doDelOneDefineModuleById(HttpServletRequest request,String delId,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = defineModuleService.dealDelDefineModule(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除模块定义:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

}
