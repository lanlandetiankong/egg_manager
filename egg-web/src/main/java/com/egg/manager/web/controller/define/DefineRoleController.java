package com.egg.manager.web.controller.define;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.exception.BusinessException;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.persistence.entity.define.DefineMenu;
import com.egg.manager.persistence.entity.define.DefinePermission;
import com.egg.manager.persistence.entity.define.DefineRole;
import com.egg.manager.persistence.entity.role.RoleMenu;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mapper.define.DefineMenuMapper;
import com.egg.manager.persistence.mapper.define.DefinePermissionMapper;
import com.egg.manager.persistence.mapper.define.DefineRoleMapper;
import com.egg.manager.persistence.mapper.role.RoleMenuMapper;
import com.egg.manager.persistence.transfer.define.DefineRoleTransfer;
import com.egg.manager.persistence.vo.define.DefineRoleVo;
import com.egg.manager.service.annotation.log.CurrentLoginUser;
import com.egg.manager.service.annotation.log.OperLog;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.service.service.CommonFuncService;
import com.egg.manager.service.service.define.DefineRoleService;
import com.egg.manager.service.service.role.RoleMenuService;
import com.egg.manager.web.controller.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API ==>>  DefineRoleController ",description = "角色定义接口")
@RestController
@RequestMapping("/define/define_role")
public class DefineRoleController extends BaseController {

    @Autowired
    private DefineMenuMapper defineMenuMapper ;
    @Autowired
    private RoleMenuMapper roleMenuMapper ;
    @Autowired
    private RoleMenuService roleMenuService ;
    @Autowired
    private DefinePermissionMapper definePermissionMapper;
    @Autowired
    private DefineRoleMapper defineRoleMapper ;
    @Autowired
    private DefineRoleService defineRoleService;
    @Autowired
    private CommonFuncService commonFuncService ;


    @OperLog(modelName="DefineRoleController",action="查询角色定义信息列表",description = "查询角色定义信息列表")
    @ApiOperation(value = "查询角色定义信息列表", notes = "查询角色定义信息列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineRoles")
    public MyCommonResult<DefineRoleVo> doGetAllDefineRoles(HttpServletRequest request,String queryObj, String paginationObj,String sortObj,
                                                            @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineRoleVo> result = new MyCommonResult<DefineRoleVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            defineRoleService.dealGetDefineRolePages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询角色定义信息列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @OperLog(modelName="DefineRoleController",action="查询角色定义信息-Dto列表",description = "查询角色定义信息-Dto列表")
    @ApiOperation(value = "查询角色定义信息-Dto列表", notes = "查询角色定义信息-Dto列表", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj",value = "字段查询配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "paginationObj",value = "分页配置 -> json格式", required = false,dataTypeClass=String.class),
            @ApiImplicitParam(name = "sortObj",value = "排序对象 -> json格式", required = false,dataTypeClass=String.class),
    })
    @PostMapping(value = "/getAllDefineRoleDtos")
    public MyCommonResult<DefineRoleVo> doGetAllDefineRoleDtos(HttpServletRequest request,String queryObj, String paginationObj,String sortObj,
                                                               @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineRoleVo> result = new MyCommonResult<DefineRoleVo>() ;
        try{
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj) ;
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue())) ;
            //取得 分页配置
            AntdvPaginationBean paginationBean = parsePaginationJsonToBean(paginationObj) ;
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj,true) ;
            defineRoleService.dealGetDefineRoleDtoPages(result,queryFieldBeanList,paginationBean,sortBeans) ;
            dealCommonSuccessCatch(result,"查询角色定义信息-Dto列表:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询角色定义信息", notes = "根据角色定义id查询角色定义信息", response = MyCommonResult.class,httpMethod = "POST")
    @OperLog(modelName="DefineRoleController",action="查询角色定义信息",description = "根据角色定义id查询角色定义信息")
    @PostMapping(value = "/getDefineRoleById")
    public MyCommonResult<DefineRoleVo> doGetDefineRoleById(HttpServletRequest request,String defineRoleId,@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineRoleVo> result = new MyCommonResult<DefineRoleVo>() ;
        try{
            DefineRole defineRole = defineRoleMapper.selectById(defineRoleId);
            result.setBean(DefineRoleTransfer.transferEntityToVo(defineRole));
            dealCommonSuccessCatch(result,"查询角色定义信息:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @ApiOperation(value = "查询角色所拥有的权限", notes = "根据角色定义id查询角色已有的权限", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/getAllPermissionByRoleId")
    public MyCommonResult<DefinePermission> doGetAllPermissionByRoleId(HttpServletRequest request,String defineRoleId,@CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermission> result = new MyCommonResult<DefinePermission>() ;
        try{
            List<DefinePermission> definePermissionList = definePermissionMapper.findAllPermissionByRoleId(defineRoleId);
            result.setResultList(definePermissionList);
            dealCommonSuccessCatch(result,"查询角色所拥有的权限:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "查询角色已获授权的菜单", notes = "根据角色定义id查询角色已获授权的菜单", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/getAllMenuByRoleId")
    public MyCommonResult<DefinePermission> doGetAllMenuByRoleId(HttpServletRequest request,String defineRoleId,Boolean filterParentNode,
                                                                 @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermission> result = new MyCommonResult<DefinePermission>() ;
        try{
            List<DefineMenu> defineMenuList = null ;
            if(Boolean.TRUE.equals(filterParentNode)){  //是否过滤掉 有子节点的 [菜单节点]
                defineMenuList = defineMenuMapper.findAllMenuByRoleIdFilterParentNode(defineRoleId,BaseStateEnum.ENABLED.getValue());
            }   else {
                defineMenuList = defineMenuMapper.findAllMenuByRoleId(defineRoleId,BaseStateEnum.ENABLED.getValue());
            }
            result.setResultList(defineMenuList);
            dealCommonSuccessCatch(result,"查询角色已获授权的菜单:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }



    @ApiOperation(value = "新增角色定义", notes = "表单方式新增角色定义", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/doAddDefineRole")
    public MyCommonResult<DefineRoleVo> doAddDefineRole(HttpServletRequest request,DefineRoleVo defineRoleVo,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult<DefineRoleVo> result = new MyCommonResult<DefineRoleVo>() ;
        Integer addCount = 0 ;
        try{
            if(defineRoleVo == null) {
                throw new Exception("未接收到有效的角色定义！");
            }   else {
                addCount = defineRoleService.dealAddDefineRole(defineRoleVo,loginUser) ;
            }
            result.setCount(addCount);
            dealCommonSuccessCatch(result,"新增角色定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "更新角色定义", notes = "表单方式更新角色定义", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/doUpdateDefineRole")
    public MyCommonResult doUpdateDefineRole(HttpServletRequest request,DefineRoleVo defineRoleVo,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer changeCount = 0 ;
        try{
            if(defineRoleVo == null) {
                throw new Exception("未接收到有效的角色定义！");
            }   else {
                changeCount = defineRoleService.dealUpdateDefineRole(defineRoleVo,loginUser,false);
            }
            result.setCount(changeCount);
            dealCommonSuccessCatch(result,"更新角色定义:"+actionSuccessMsg);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @OperLog(modelName="DefinePermissionController",action="批量删除角色定义",description = "根据角色id批量删除角色定义")
    @ApiOperation(value = "批量删除角色定义", notes = "根据角色id批量删除角色定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds",value = "要删除的权限定义id数组", required = true,dataTypeClass=String[].class),
    })
    @PostMapping(value = "/batchDelDefineRoleByIds")
    public MyCommonResult doBatchDeleteDefineRoleById(HttpServletRequest request,String[] delIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        Integer delCount = 0;
        try{
            if(delIds != null && delIds.length > 0) {
                delCount = defineRoleService.dealDelDefineRoleByArr(delIds,loginUser);
                dealCommonSuccessCatch(result,"批量删除角色定义:"+actionSuccessMsg);
            }
            result.setCount(delCount);
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }

    @OperLog(modelName="DefinePermissionController",action="删除角色定义",description = "根据角色id删除角色定义")
    @ApiOperation(value = "删除角色定义", notes = "根据角色id删除角色定义", response = MyCommonResult.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId",value = "要删除的角色定义id", required = true,dataTypeClass=String.class),
    })
    @PostMapping(value = "/delOneDefineRoleByIds")
    public MyCommonResult doDelOneDefineRoleByIds(HttpServletRequest request,String delId,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(StringUtils.isNotBlank(delId)){
                Integer delCount = defineRoleService.dealDelDefineRole(delId,loginUser);
                result.setCount(delCount);
                dealCommonSuccessCatch(result,"删除角色定义:"+actionSuccessMsg);
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "角色授权", notes = "为角色分配权限", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/grantPermissionToRole")
    public MyCommonResult doGrantPermissionToRole(HttpServletRequest request,String roleId,String[] checkIds,@CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(StringUtils.isNotBlank(roleId)){
                Integer grantCount = defineRoleService.dealGrantPermissionToRole(roleId,checkIds,loginUser);
                result.setCount(grantCount);
                dealCommonSuccessCatch(result,"角色授权:"+actionSuccessMsg);
            }   else {
                throw new BusinessException("未知要授权的角色id");
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }


    @ApiOperation(value = "[菜单-角色]授权", notes = "为角色设置可访问菜单", response = MyCommonResult.class,httpMethod = "POST")
    @PostMapping(value = "/grantMenusToRole")
    @Transactional
    public MyCommonResult doGrantMenusToRole(HttpServletRequest request,String roleId,String[] checkIds,String[] halfCheckIds,
                                             @CurrentLoginUser UserAccount loginUser){
        MyCommonResult result = new MyCommonResult() ;
        try{
            if(StringUtils.isNotBlank(roleId)){
                //取得前端所有 勾选的值
                checkIds = checkIds != null ? checkIds : new String[]{} ;
                halfCheckIds = halfCheckIds != null ? halfCheckIds : new String[]{} ;
                Set<String> checkIdAll = Sets.newHashSet(checkIds) ;
                checkIdAll.addAll(Lists.newArrayList(halfCheckIds));

                //在数据库中 关联分别为 enable、delete 状态
                Set<String>  oldEnableCheckIdSet = defineRoleService.dealGetMenuIdSetByRoleIdFromDb(roleId,BaseStateEnum.ENABLED.getValue());
                Set<String>  oldDelCheckIdSet = defineRoleService.dealGetMenuIdSetByRoleIdFromDb(roleId,BaseStateEnum.DELETE.getValue());
                //所有 已经在数据库 有的关联行
                Set<String> oldCheckIdAll = Sets.union(oldEnableCheckIdSet,oldDelCheckIdSet);

                //分别为 待添加数据行、待更新为 可用状态、待更新为 删除状态的 Set集合
                Sets.SetView  addSetView = Sets.difference(checkIdAll,oldEnableCheckIdSet);
                Sets.SetView  updateEnableIdSet = Sets.difference(checkIdAll,oldDelCheckIdSet);
                Sets.SetView  updateDelIdSet = Sets.difference(oldCheckIdAll,checkIdAll);

                if(addSetView != null && addSetView.isEmpty() == false){
                    List<RoleMenu> addRoleMenuList = Lists.newArrayList();
                    Iterator<String> addIter = addSetView.iterator();
                    while (addIter.hasNext()){
                        String diffNext = addIter.next();
                        addRoleMenuList.add(RoleMenu.generateSimpleInsertEntity(roleId,diffNext,BaseStateEnum.ENABLED.getValue(),loginUser));
                    }
                    boolean flag = roleMenuService.insertBatch(addRoleMenuList);
                }
                if(updateEnableIdSet != null && updateEnableIdSet.isEmpty() == false){
                    Iterator<String> enableIter = updateEnableIdSet.iterator();
                    List enableIdList = Lists.newArrayList(enableIter);
                    int count = roleMenuMapper.batchUpdateStateByRole(roleId,enableIdList,BaseStateEnum.ENABLED.getValue(),loginUser);
                }
                if(updateDelIdSet != null && updateDelIdSet.isEmpty() == false){
                    Iterator<String> delIter = updateDelIdSet.iterator();
                    List delIdList = Lists.newArrayList(delIter);
                    int count = roleMenuMapper.batchUpdateStateByRole(roleId,delIdList,BaseStateEnum.DELETE.getValue(),loginUser);
                }
                dealCommonSuccessCatch(result,"授权菜单:"+actionSuccessMsg);
            }   else {
                throw new BusinessException("未知要授权的角色id");
            }
        }   catch (Exception e){
            this.dealCommonErrorCatch(log,result,e) ;
        }
        return  result;
    }
}
