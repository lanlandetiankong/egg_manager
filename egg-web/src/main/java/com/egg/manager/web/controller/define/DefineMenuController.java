package com.egg.manager.web.controller.define;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.common.annotation.log.CurrentLoginUser;
import com.egg.manager.common.annotation.log.OperLog;
import com.egg.manager.api.services.redis.service.user.UserAccountRedisService;
import com.egg.manager.api.services.basic.CommonFuncService;
import com.egg.manager.api.services.basic.module.DefineMenuService;
import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.constant.define.DefineMenuConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineMenuMapper;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefineMenuTransfer;
import com.egg.manager.persistence.bean.tree.common.CommonMenuTree;
import com.egg.manager.persistence.bean.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineMenuVo;
import com.egg.manager.web.controller.BaseController;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API ==>>  DefineMenuController ",description = "菜单定义接口")
@RestController
@RequestMapping("/define/define_menu")
public class DefineMenuController extends BaseController{

    @Autowired
    private DefineMenuMapper defineMenuMapper ;

    @Reference
    private DefineMenuService defineMenuService ;
    @Reference
    private CommonFuncService commonFuncService ;
    @Reference
    private UserAccountRedisService userAccountRedisService;


    @OperLog(action="查询所有路由菜单TreeSelect",description = "查询所有路由菜单TreeSelect",fullPath = "/define/define_menu/getAllMenuTreeSelect")
    @ApiOperation(value = "查询所有路由菜单TreeSelect", notes = "查询所有路由菜单TreeSelect", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping("/getAllMenuTreeSelect")
    public MyCommonResult<DefineMenu> doGetAllMenuTreeSelect(Boolean withRoot) {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>() ;
        //查询 所有[可用状态]的 [菜单定义]
        List<DefineMenu> allMenus  = defineMenuService.getAllEnableDefineMenus(new EntityWrapper<DefineMenu>());
        List<CommonTreeSelect> treeList = null ;
        if(Boolean.TRUE.equals(withRoot)){
            treeList = defineMenuService.getTreeSelectChildNodesWithRoot(DefineMenuConstant.ROOT_ID,allMenus);
        }   else {
            treeList = defineMenuService.getTreeSelectChildNodes(DefineMenuConstant.ROOT_ID,allMenus);
        }
        result.setResultList(treeList);
        return result ;
    }

    @OperLog(action="查询被过滤的路由菜单TreeSelect",description = "查询被过滤路由菜单TreeSelect(过滤指定节点的所有子节点)",fullPath = "/define/define_menu/getMenuTreeSelectFilterChildrens")
    @ApiOperation(value = "查询被过滤的路由菜单TreeSelect", notes = "查询被过滤路由菜单TreeSelect(过滤指定节点的所有子节点)", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping("/getMenuTreeSelectFilterChildrens")
    public MyCommonResult<DefineMenu> doGetMenuTreeSelectFilterChildrens(String filterId) {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>() ;
        List<DefineMenu> allMenus  =  defineMenuMapper.getMenusFilterChildrens(filterId,true);
        List<CommonTreeSelect> treeList = defineMenuService.getTreeSelectChildNodesWithRoot(DefineMenuConstant.ROOT_ID,allMenus);
        result.setResultList(treeList);
        return result ;
    }

    @ApiOperation(value = "查询所有可以访问的路由菜单", notes = "查询所有可以访问的路由菜单", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="查询所有可以访问的路由菜单",description = "查询所有可以访问的路由菜单",fullPath = "/define/define_menu/user/getAllMenuTree")
    @PostMapping("/user/getAllMenuTree")
    public MyCommonResult<DefineMenu> doGetAllMenuTree() {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>() ;
        //筛选与排序
        EntityWrapper<DefineMenu> defineMenuEntityWrapper = new EntityWrapper<DefineMenu>();
        defineMenuEntityWrapper.eq("state",BaseStateEnum.ENABLED.getValue());
        defineMenuEntityWrapper.orderBy("level",true);
        defineMenuEntityWrapper.orderBy("order_num",true);
        defineMenuEntityWrapper.orderBy("create_time",false);
        List<DefineMenu> allMenus  = defineMenuService.selectList(defineMenuEntityWrapper);
        List<CommonMenuTree> treeList = defineMenuService.getMenuTreeChildNodes(DefineMenuConstant.ROOT_ID,allMenus);
        result.setResultList(treeList);
        return result ;
    }


    @ApiOperation(value = "查询用户可以访问的路由菜单", notes = "查询用户可以访问的路由菜单", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="查询用户可以访问的路由菜单",description = "查询用户可以访问的路由菜单",fullPath = "/define/define_menu/user/getGrantedMenuTree")
    @PostMapping("/user/getGrantedMenuTree")
    public MyCommonResult<DefineMenu> doGetGrantedMenuTree(@RequestHeader("authorization") String authorization, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>() ;
        List<CommonMenuTree> treeList = userAccountRedisService.dealGetCurrentUserFrontMenuTrees(authorization,loginUser.getFid(),false);
        result.setResultList(treeList);
        Map<String,CommonMenuTree> urlMap = CommonMenuTree.dealTreeListToUrlMap(treeList,Maps.newHashMap());
        result.setResultMap(urlMap);
        return result ;
    }







    @OperLog(action="查询菜单定义信息-Dto列表",description = "查询菜单定义信息-Dto列表",fullPath = "/define/define_menu/getAllDefineMenuDtos")
    @ApiOperation(value = "查询菜单定义信息-Dto列表", notes = "查询菜单定义信息-Dto列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = true,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineMenuDtos")
    public MyCommonResult<DefineMenuVo> doGetAllDefineMenuDtos(HttpServletRequest request,
                                                               String queryObj, String paginationObj, String sortObj,
                                                               @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineMenuVo> result = new MyCommonResult<DefineMenuVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = this.parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            result = defineMenuService.dealGetDefineMenuDtoPages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询菜单定义信息-Dto列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询菜单定义信息", notes = "根据菜单定义id查询菜单定义信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="查询菜单定义信息",description = "根据菜单定义id查询菜单定义信息",fullPath = "/define/define_menu/getDefineMenuById")
    @PostMapping(value = "/getDefineMenuById")
    public MyCommonResult<DefineMenuVo> doGetDefineMenuById(HttpServletRequest request, String defineMenuId) {
        MyCommonResult<DefineMenuVo> result = new MyCommonResult<DefineMenuVo>() ;
        try{
            DefineMenu defineMenu = defineMenuMapper.selectById(defineMenuId);
            result.setBean(DefineMenuTransfer.transferEntityToVo(defineMenu));
            dealCommonSuccessCatch(result,"查询菜单定义信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "新增菜单定义", notes = "表单方式新增菜单定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="新增菜单定义",description = "表单方式新增菜单定义",fullPath = "/define/define_menu/doAddDefineMenu")
    @PostMapping(value = "/doAddDefineMenu")
    public MyCommonResult<DefineMenuVo> doAddDefineMenu(HttpServletRequest request, DefineMenuVo defineMenuVo, @CurrentLoginUser UserAccount loginUser){
        MyCommonResult<DefineMenuVo> result = new MyCommonResult<DefineMenuVo>() ;
        Integer addCount = 0 ;
        try{
            if(defineMenuVo == null) {
                throw new Exception("未接收到有效的菜单定义！");
            }   else {
                addCount = defineMenuService.dealAddDefineMenu(defineMenuVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增菜单定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新菜单定义", notes = "表单方式更新菜单定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="更新菜单定义",description = "表单方式更新菜单定义",fullPath = "/define/define_menu/doUpdateDefineMenu")
    @PostMapping(value = "/doUpdateDefineMenu")
    public MyCommonResult doUpdateDefineMenu(HttpServletRequest request, DefineMenuVo defineMenuVo, @CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            if(defineMenuVo == null) {
                throw new Exception("未接收到有效的菜单定义！");
            }   else {
                changeCount = defineMenuService.dealUpdateDefineMenu(defineMenuVo,loginUser,false);
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新菜单定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新菜单对应的Excel模板", notes = "更新菜单对应的Excel模板", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="更新菜单对应的Excel模板",description = "更新菜单对应的Excel模板",fullPath = "/define/define_menu/doUpdateExcelModel")
    @PostMapping(value = "/doUpdateExcelModel")
    @RequiresRoles(value = {"Root","SuperRoot"},logical= Logical.OR)
    public MyCommonResult doUpdateExcelModelConf(HttpServletRequest request, String menuId, AntdFileUploadBean fileUploadBean,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
            DefineMenu defineMenu = defineMenuMapper.selectById(menuId);
            if(fileUploadBean != null){
                defineMenu.setExcelModelConf(JSONObject.toJSONString(fileUploadBean));
            }   else {
                defineMenu.setExcelModelConf(null);
            }
            defineMenu.setLastModifyerId(loginUser.getFid());
            defineMenu.setUpdateTime(new Date());
            Integer changeCount = defineMenuMapper.updateAllColumnById(defineMenu);
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新菜单对应的Excel模板:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "批量删除菜单定义", notes = "根据菜单定义id批量删除菜单定义", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(action="批量删除菜单定义",description = "根据菜单定义id批量删除菜单定义",fullPath = "/define/define_menu/batchDelDefineMenuByIds")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的菜单定义id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelDefineMenuByIds")
    public MyCommonResult doBatchDeleteDefineMenuById(HttpServletRequest request,String[] delIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(delIds != null && delIds.length > 0) {
                delCount = defineMenuService.dealDelDefineMenuByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除菜单定义:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @OperLog(action="删除菜单定义",description = "根据菜单id删除菜单定义",fullPath = "/define/define_menu/delOneDefineMenuById")
    @ApiOperation(value = "删除菜单定义", notes = "根据菜单id删除菜单定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的菜单定义id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneDefineMenuById")
    public MyCommonResult doDelOneDefineMenuById(HttpServletRequest request,String delId,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(org.apache.commons.lang3.StringUtils.isNotBlank(delId)){
                Integer delCount = defineMenuService.dealDelDefineMenu(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除菜单定义:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }



}
