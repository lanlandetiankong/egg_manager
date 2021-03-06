package com.egg.manager.em.web.controller.emctl.define;

import cn.hutool.core.lang.Assert;
import org.apache.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.define.basic.EmDefineMenuService;
import com.egg.manager.persistence.commons.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonMenuTree;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.constant.shiro.ShiroRoleConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineMenuEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineMenuDto;
import com.egg.manager.persistence.em.define.pojo.transfer.EmDefineMenuTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineMenuVo;
import com.egg.manager.persistence.em.user.domain.constant.DefineMenuConstant;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-菜单定义接口")
@RestController
@RequestMapping("/emCtl/define/defineMenu")
public class EmDefineMenuController extends BaseController {

    @Reference
    private EmDefineMenuService emDefineMenuService;

    @EmPcWebQueryLog(fullPath = "/emCtl/define/defineMenu/queryTreeSelect")
    @ApiOperation(value = "查询下拉树->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping("/queryTreeSelect")
    public WebResult queryTreeSelect(Boolean withRoot) {
        WebResult result = WebResult.okQuery();
        //查询 所有[可用状态]的 [菜单定义]
        List<EmDefineMenuEntity> allMenus = emDefineMenuService.getAllEnableList();
        List<CommonTreeSelect> treeList = null;
        if (Boolean.TRUE.equals(withRoot)) {
            treeList = emDefineMenuService.getTreeSelectChildNodesWithRoot(DefineMenuConstant.ROOT_ID, allMenus);
        } else {
            treeList = emDefineMenuService.getTreeSelectChildNodes(DefineMenuConstant.ROOT_ID, allMenus);
        }
        result.putGridList(treeList);
        return result;
    }

    @EmPcWebQueryLog(description = "查询被过滤路由菜单TreeSelect(过滤指定节点的所有子节点)", fullPath = "/emCtl/define/defineMenu/queryFilteredTreeSelect")
    @ApiOperation(value = "筛选查询下拉树->菜单定义", notes = "查询被过滤路由菜单TreeSelect(过滤指定节点的所有子节点)", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping("/queryFilteredTreeSelect")
    public WebResult queryFilteredTreeSelect(String filterId) {
        WebResult result = WebResult.okQuery();
        List<EmDefineMenuEntity> allMenus = emDefineMenuService.getMenusFilterChildrens(filterId, true);
        List<CommonTreeSelect> treeList = emDefineMenuService.getTreeSelectChildNodesWithRoot(DefineMenuConstant.ROOT_ID, allMenus);
        result.putGridList(treeList);
        return result;
    }

    @ApiOperation(value = "权限筛选查询下拉树->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/emCtl/define/defineMenu/user/gainGrantTree")
    @PostMapping("/user/gainGrantTree")
    public WebResult doGetGrantedMenuTree(@RequestHeader("authorization") String authorization,
                                          @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        List<CommonMenuTree> treeList = emDefineMenuService.queryUserVisitAbleMenuToCacheable(loginUserInfo.getFid());
        result.putGridList(treeList);
        Map<String, CommonMenuTree> urlMap = CommonMenuTree.dealTreeListToUrlMap(treeList, Maps.newHashMap());
        result.putDataMap(urlMap);
        return result;
    }

    @ApiOperation(value = "刷新缓存-权限筛选查询下拉树->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/emCtl/define/defineMenu/user/reflushCacheAndGainGrantTree")
    @PostMapping("/user/reflushCacheAndGainGrantTree")
    public WebResult reflushCacheAndGainGrantTree(@RequestHeader("authorization") String authorization,
                                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        List<CommonMenuTree> treeList = emDefineMenuService.queryUserVisitAbleMenuToCachePut(loginUserInfo.getFid());
        result.putGridList(treeList);
        Map<String, CommonMenuTree> urlMap = CommonMenuTree.dealTreeListToUrlMap(treeList, Maps.newHashMap());
        result.putDataMap(urlMap);
        return result;
    }

    @EmPcWebQueryLog(fullPath = "/emCtl/define/defineMenu/queryDtoPage")
    @ApiOperation(value = "分页查询(Dto)->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = EmDefineMenuDto.class) QueryPageBean<EmDefineMenuDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addEqNotDeleted();
        result = emDefineMenuService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebQueryLog(fullPath = "/emCtl/define/defineMenu/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String defineMenuId) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(defineMenuId, BaseRstMsgConstant.ErrorMsg.unknowId());
        EmDefineMenuEntity entity = emDefineMenuService.getById(defineMenuId);
        result.putBean(EmDefineMenuTransfer.transferEntityToVo(entity));
        return result;
    }

    @ApiOperation(value = "新增->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineMenu/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, EmDefineMenuVo vo, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(vo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = emDefineMenuService.dealCreate(loginUserInfo, vo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineMenu/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, EmDefineMenuVo vo, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(vo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = emDefineMenuService.dealUpdate(loginUserInfo, vo);
        result.putCount(changeCount);
        return result;
    }

    @ApiOperation(value = "更新->菜单定义/excel模板", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineMenu/updateExcelModel")
    @PostMapping(value = "/updateExcelModel")
    @RequiresRoles(value = {ShiroRoleConstant.ROOT, ShiroRoleConstant.SUPER_ROOT}, logical = Logical.OR)
    public WebResult updateExcelModel(HttpServletRequest request, String menuId, AntdFileUploadBean fileUploadBean, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(menuId, BaseRstMsgConstant.ErrorMsg.unknowId());
        EmDefineMenuEntity entity = emDefineMenuService.getById(menuId);
        if (fileUploadBean != null) {
            entity.setExcelModelConf(JSONObject.toJSONString(fileUploadBean));
        } else {
            entity.setExcelModelConf(null);
        }
        entity.setLastModifyerId(loginUserInfo.getFid());
        entity.setUpdateTime(new Date());
        boolean flag = emDefineMenuService.updateById(entity);
        result.putCount(flag ? 1 : 0);
        return result;
    }

    @ApiOperation(value = "批量逻辑删除->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineMenu/batchDeleteByIds")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = emDefineMenuService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineMenu/deleteById")
    @ApiOperation(value = "逻辑删除->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = emDefineMenuService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}
