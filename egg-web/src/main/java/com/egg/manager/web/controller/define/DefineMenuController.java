package com.egg.manager.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.api.constants.controllers.BaseRstMsgConstant;
import com.egg.manager.api.constants.funcModule.define.DefineMenuFuncModuleConstant;
import com.egg.manager.api.services.basic.module.DefineMenuService;
import com.egg.manager.api.services.redis.service.user.UserAccountRedisService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.constant.define.DefineMenuConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.bean.tree.common.CommonMenuTree;
import com.egg.manager.persistence.bean.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineMenuMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineMenuDto;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefineMenuTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineMenuVo;
import com.egg.manager.web.controller.BaseController;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;
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
@Api(value = "API ==>>  DefineMenuController ", description = "菜单定义接口")
@RestController
@RequestMapping("/define/define_menu")
public class DefineMenuController extends BaseController {

    @Autowired
    private DefineMenuMapper defineMenuMapper;

    @Reference
    private DefineMenuService defineMenuService;
    @Reference
    private UserAccountRedisService userAccountRedisService;


    @PcWebQueryLog(action = "查询所有路由菜单TreeSelect", description = "查询所有路由菜单TreeSelect", fullPath = "/define/define_menu/getAllMenuTreeSelect")
    @ApiOperation(value = "查询所有路由菜单TreeSelect", notes = "查询所有路由菜单TreeSelect", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping("/getAllMenuTreeSelect")
    public MyCommonResult<DefineMenu> doGetAllMenuTreeSelect(Boolean withRoot) {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>();
        //查询 所有[可用状态]的 [菜单定义]
        List<DefineMenu> allMenus = defineMenuService.getAllEnableList(new QueryWrapper<DefineMenu>());
        List<CommonTreeSelect> treeList = null;
        if (Boolean.TRUE.equals(withRoot)) {
            treeList = defineMenuService.getTreeSelectChildNodesWithRoot(DefineMenuConstant.ROOT_ID, allMenus);
        } else {
            treeList = defineMenuService.getTreeSelectChildNodes(DefineMenuConstant.ROOT_ID, allMenus);
        }
        result.setResultList(treeList);
        return result;
    }

    @PcWebQueryLog(action = "查询被过滤的路由菜单TreeSelect", description = "查询被过滤路由菜单TreeSelect(过滤指定节点的所有子节点)", fullPath = "/define/define_menu/getMenuTreeSelectFilterChildrens")
    @ApiOperation(value = "查询被过滤的路由菜单TreeSelect", notes = "查询被过滤路由菜单TreeSelect(过滤指定节点的所有子节点)", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping("/getMenuTreeSelectFilterChildrens")
    public MyCommonResult<DefineMenu> doGetMenuTreeSelectFilterChildrens(String filterId) {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>();
        List<DefineMenu> allMenus = defineMenuMapper.getMenusFilterChildrens(filterId, true);
        List<CommonTreeSelect> treeList = defineMenuService.getTreeSelectChildNodesWithRoot(DefineMenuConstant.ROOT_ID, allMenus);
        result.setResultList(treeList);
        return result;
    }

    @ApiOperation(value = "查询所有可以访问的路由菜单", notes = "查询所有可以访问的路由菜单", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询所有可以访问的路由菜单", description = "查询所有可以访问的路由菜单", fullPath = "/define/define_menu/user/getAllMenuTree")
    @PostMapping("/user/getAllMenuTree")
    public MyCommonResult<DefineMenu> doGetAllMenuTree() {
        MyCommonResult<DefineMenu> result = new MyCommonResult<DefineMenu>();
        //筛选与排序
        QueryWrapper<DefineMenu> queryWrapper = new QueryWrapper<DefineMenu>();
        queryWrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        queryWrapper.orderBy(true,true,"level");
        queryWrapper.orderBy(true,true,"order_num");
        queryWrapper.orderBy(true,true,"create_time");
        List<DefineMenu> allMenus = defineMenuMapper.selectList(queryWrapper);
        List<CommonMenuTree> treeList = defineMenuService.getMenuTreeChildNodes(DefineMenuConstant.ROOT_ID, allMenus);
        result.setResultList(treeList);
        return result;
    }


    @ApiOperation(value = "查询用户可以访问的路由菜单", notes = "查询用户可以访问的路由菜单", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询用户可以访问的路由菜单", description = "查询用户可以访问的路由菜单", fullPath = "/define/define_menu/user/getGrantedMenuTree")
    @PostMapping("/user/getGrantedMenuTree")
    public MyCommonResult<DefineMenu> doGetGrantedMenuTree(@RequestHeader("authorization") String authorization, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineMenu> result = new MyCommonResult();
        List<CommonMenuTree> treeList = userAccountRedisService.dealGetCurrentUserFrontMenuTrees(loginUser, authorization, loginUser.getFid(), false);
        result.setResultList(treeList);
        Map<String, CommonMenuTree> urlMap = CommonMenuTree.dealTreeListToUrlMap(treeList, Maps.newHashMap());
        result.setResultMap(urlMap);
        return result;
    }


    @PcWebQueryLog(action = "查询菜单定义信息-Dto列表", description = "查询菜单定义信息-Dto列表", fullPath = "/define/define_menu/getAllDefineMenuDtos")
    @ApiOperation(value = "查询菜单定义信息-Dto列表", notes = "查询菜单定义信息-Dto列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllDefineMenuDtos")
    public MyCommonResult<DefineMenuVo> doGetAllDefineMenuDtos(HttpServletRequest request,
                                                               String queryObj, String paginationObj, String sortObj,
                                                               @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineMenuVo> result = new MyCommonResult<DefineMenuVo>();
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineMenuDto> paginationBean = this.parsePaginationJsonToBean(paginationObj,DefineMenuDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineMenuService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
            dealCommonSuccessCatch(result, "查询菜单定义信息-Dto列表:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "查询菜单定义信息", notes = "根据菜单定义id查询菜单定义信息", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebQueryLog(action = "查询菜单定义信息", description = "根据菜单定义id查询菜单定义信息", fullPath = "/define/define_menu/getDefineMenuById")
    @PostMapping(value = "/getDefineMenuById")
    public MyCommonResult<DefineMenuVo> doGetDefineMenuById(HttpServletRequest request, String defineMenuId) {
        MyCommonResult<DefineMenuVo> result = new MyCommonResult();
        try {
            Assert.notBlank(defineMenuId, BaseRstMsgConstant.ErrorMsg.unknowId());

            DefineMenu entity = defineMenuMapper.selectById(defineMenuId);
            result.setBean(DefineMenuTransfer.transferEntityToVo(entity));
            dealCommonSuccessCatch(result, "查询菜单定义信息:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增菜单定义", notes = "表单方式新增菜单定义", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "新增菜单定义", description = "表单方式新增菜单定义", fullPath = "/define/define_menu/doAddDefineMenu")
    @PostMapping(value = "/doAddDefineMenu")
    public MyCommonResult<DefineMenuVo> doAddDefineMenu(HttpServletRequest request, DefineMenuVo vo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineMenuVo> result = new MyCommonResult();
        Integer addCount = 0;
        try {
            Assert.notNull(vo,BaseRstMsgConstant.ErrorMsg.emptyForm());

            addCount = defineMenuService.dealCreate(loginUser, vo);
            result.setCount(addCount);
            dealCommonSuccessCatch(result, DefineMenuFuncModuleConstant.Success.create);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新菜单定义", notes = "表单方式更新菜单定义", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "更新菜单定义", description = "表单方式更新菜单定义", fullPath = "/define/define_menu/doUpdateDefineMenu")
    @PostMapping(value = "/doUpdateDefineMenu")
    public MyCommonResult doUpdateDefineMenu(HttpServletRequest request, DefineMenuVo vo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer changeCount = 0;
        try {
            Assert.notNull(vo,BaseRstMsgConstant.ErrorMsg.emptyForm());

            changeCount = defineMenuService.dealUpdate(loginUser, vo, false);
            result.setCount(changeCount);
            dealCommonSuccessCatch(result, DefineMenuFuncModuleConstant.Success.update);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新菜单对应的Excel模板", notes = "更新菜单对应的Excel模板", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "更新菜单对应的Excel模板", description = "更新菜单对应的Excel模板", fullPath = "/define/define_menu/doUpdateExcelModel")
    @PostMapping(value = "/doUpdateExcelModel")
    @RequiresRoles(value = {"Root", "SuperRoot"}, logical = Logical.OR)
    public MyCommonResult doUpdateExcelModelConf(HttpServletRequest request, String menuId, AntdFileUploadBean fileUploadBean, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        try {
            Assert.notBlank(menuId,BaseRstMsgConstant.ErrorMsg.unknowId());

            DefineMenu entity = defineMenuMapper.selectById(menuId);
            if (fileUploadBean != null) {
                entity.setExcelModelConf(JSONObject.toJSONString(fileUploadBean));
            } else {
                entity.setExcelModelConf(null);
            }
            entity.setLastModifyerId(loginUser.getFid());
            entity.setUpdateTime(new Date());
            Integer changeCount = defineMenuMapper.updateById(entity);
            result.setCount(changeCount);
            dealCommonSuccessCatch(result, "更新菜单对应的Excel模板:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "批量删除菜单定义", notes = "根据菜单定义id批量删除菜单定义", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "批量删除菜单定义", description = "根据菜单定义id批量删除菜单定义", fullPath = "/define/define_menu/batchDelDefineMenuByIds")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的菜单定义id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelDefineMenuByIds")
    public MyCommonResult doBatchDeleteDefineMenuById(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            delCount = defineMenuService.dealBatchDelete(loginUser, delIds);
            dealCommonSuccessCatch(result, DefineMenuFuncModuleConstant.Success.batchDeleteByIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(action = "删除菜单定义", description = "根据菜单id删除菜单定义", fullPath = "/define/define_menu/delOneDefineMenuById")
    @ApiOperation(value = "删除菜单定义", notes = "根据菜单id删除菜单定义", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的菜单定义id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneDefineMenuById")
    public MyCommonResult doDelOneDefineMenuById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer delCount = defineMenuService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
            dealCommonSuccessCatch(result, DefineMenuFuncModuleConstant.Success.deleteById);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


}
