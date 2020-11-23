package com.egg.manager.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.api.services.em.define.basic.DefineMenuService;
import com.egg.manager.persistence.commons.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonMenuTree;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.define.DefineMenuConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.shiro.ShiroRoleConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryField;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenuEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineMenuMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineMenuDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineMenuTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineMenuVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
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
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-菜单定义接口")
@RestController
@RequestMapping("/define/defineMenu")
public class DefineMenuController extends BaseController {
    @Autowired
    private DefineMenuMapper defineMenuMapper;
    @Reference
    private DefineMenuService defineMenuService;

    @PcWebQueryLog(fullPath = "/define/defineMenu/queryTreeSelect")
    @ApiOperation(value = "查询下拉树->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping("/queryTreeSelect")
    public WebResult queryTreeSelect(Boolean withRoot) {
        WebResult result = WebResult.okQuery();
        //查询 所有[可用状态]的 [菜单定义]
        List<DefineMenuEntity> allMenus = defineMenuService.getAllEnableList();
        List<CommonTreeSelect> treeList = null;
        if (Boolean.TRUE.equals(withRoot)) {
            treeList = defineMenuService.getTreeSelectChildNodesWithRoot(DefineMenuConstant.ROOT_ID, allMenus);
        } else {
            treeList = defineMenuService.getTreeSelectChildNodes(DefineMenuConstant.ROOT_ID, allMenus);
        }
        result.putResultList(treeList);
        return result;
    }

    @PcWebQueryLog(description = "查询被过滤路由菜单TreeSelect(过滤指定节点的所有子节点)", fullPath = "/define/defineMenu/queryFilteredTreeSelect")
    @ApiOperation(value = "筛选查询下拉树->菜单定义", notes = "查询被过滤路由菜单TreeSelect(过滤指定节点的所有子节点)", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping("/queryFilteredTreeSelect")
    public WebResult queryFilteredTreeSelect(String filterId) {
        WebResult result = WebResult.okQuery();
        List<DefineMenuEntity> allMenus = defineMenuMapper.getMenusFilterChildrens(filterId, true);
        List<CommonTreeSelect> treeList = defineMenuService.getTreeSelectChildNodesWithRoot(DefineMenuConstant.ROOT_ID, allMenus);
        result.putResultList(treeList);
        return result;
    }

    @ApiOperation(value = "权限筛选查询下拉树->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/define/defineMenu/user/gainGrantTree")
    @PostMapping("/user/gainGrantTree")
    public WebResult doGetGrantedMenuTree(@RequestHeader("authorization") String authorization, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        List<CommonMenuTree> treeList = defineMenuService.queryDbToCacheable(loginUserInfo.getFid());
        result.putResultList(treeList);
        Map<String, CommonMenuTree> urlMap = CommonMenuTree.dealTreeListToUrlMap(treeList, Maps.newHashMap());
        result.putResultMap(urlMap);
        return result;
    }

    @PcWebQueryLog(fullPath = "/define/defineMenu/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = DefineMenuDto.class) QueryPageBean<DefineMenuDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().add(QueryField.gainEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue()));
        result = defineMenuService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/define/defineMenu/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String defineMenuId) {
        WebResult result = WebResult.okQuery();
        Assert.notBlank(defineMenuId, BaseRstMsgConstant.ErrorMsg.unknowId());
        DefineMenuEntity entity = defineMenuMapper.selectById(defineMenuId);
        result.putBean(DefineMenuTransfer.transferEntityToVo(entity));
        return result;
    }

    @ApiOperation(value = "新增->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineMenu/createByForm")
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, DefineMenuVo vo, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(vo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = defineMenuService.dealCreate(loginUserInfo, vo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineMenu/updateByForm")
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, DefineMenuVo vo, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(vo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = defineMenuService.dealUpdate(loginUserInfo, vo);
        result.putCount(changeCount);
        return result;
    }

    @ApiOperation(value = "更新->菜单定义/excel模板", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineMenu/updateExcelModel")
    @PostMapping(value = "/updateExcelModel")
    @RequiresRoles(value = {ShiroRoleConstant.ROOT, ShiroRoleConstant.SUPER_ROOT}, logical = Logical.OR)
    public WebResult updateExcelModel(HttpServletRequest request, String menuId, AntdFileUploadBean fileUploadBean, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(menuId, BaseRstMsgConstant.ErrorMsg.unknowId());
        DefineMenuEntity entity = defineMenuMapper.selectById(menuId);
        if (fileUploadBean != null) {
            entity.setExcelModelConf(JSONObject.toJSONString(fileUploadBean));
        } else {
            entity.setExcelModelConf(null);
        }
        entity.setLastModifyerId(loginUserInfo.getFid());
        entity.setUpdateTime(new Date());
        Integer changeCount = defineMenuMapper.updateById(entity);
        result.putCount(changeCount);
        return result;
    }

    @ApiOperation(value = "批量伪删除->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineMenu/batchDeleteByIds")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = defineMenuService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @PcWebOperationLog(fullPath = "/define/defineMenu/deleteById")
    @ApiOperation(value = "伪删除->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = defineMenuService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }
}
