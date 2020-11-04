package com.egg.manager.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.egg.manager.common.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.api.services.basic.module.DefineMenuService;
import com.egg.manager.api.services.redis.service.user.UserAccountRedisService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.log.pc.web.PcWebQueryLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.base.constant.define.DefineMenuConstant;
import com.egg.manager.common.base.constant.shiro.ShiroRoleConstant;
import com.egg.manager.common.base.constant.web.api.WebApiConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.bean.helper.MyCommonResult;
import com.egg.manager.persistence.commons.bean.tree.common.CommonMenuTree;
import com.egg.manager.persistence.commons.bean.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenu;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineMenuMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineMenuDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineMenuTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineMenuVo;
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
 * @description:
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
    @Reference
    private UserAccountRedisService userAccountRedisService;


    @PcWebQueryLog(fullPath = "/define/defineMenu/queryTreeSelect")
    @ApiOperation(value = "查询下拉树->菜单定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping("/queryTreeSelect")
    public MyCommonResult<CommonTreeSelect> queryTreeSelect(Boolean withRoot) {
        MyCommonResult<CommonTreeSelect> result = MyCommonResult.gainQueryResult(CommonTreeSelect.class);
        //查询 所有[可用状态]的 [菜单定义]
        List<DefineMenu> allMenus = defineMenuService.getAllEnableList();
        List<CommonTreeSelect> treeList = null;
        if (Boolean.TRUE.equals(withRoot)) {
            treeList = defineMenuService.getTreeSelectChildNodesWithRoot(DefineMenuConstant.ROOT_ID, allMenus);
        } else {
            treeList = defineMenuService.getTreeSelectChildNodes(DefineMenuConstant.ROOT_ID, allMenus);
        }
        result.setResultList(treeList);
        return result;
    }

    @PcWebQueryLog(description = "查询被过滤路由菜单TreeSelect(过滤指定节点的所有子节点)", fullPath = "/define/defineMenu/queryFilteredTreeSelect")
    @ApiOperation(value = "筛选查询下拉树->菜单定义", notes = "查询被过滤路由菜单TreeSelect(过滤指定节点的所有子节点)", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping("/queryFilteredTreeSelect")
    public MyCommonResult<CommonTreeSelect> queryFilteredTreeSelect(String filterId) {
        MyCommonResult<CommonTreeSelect> result = MyCommonResult.gainQueryResult(CommonTreeSelect.class);
        List<DefineMenu> allMenus = defineMenuMapper.getMenusFilterChildrens(filterId, true);
        List<CommonTreeSelect> treeList = defineMenuService.getTreeSelectChildNodesWithRoot(DefineMenuConstant.ROOT_ID, allMenus);
        result.setResultList(treeList);
        return result;
    }

    @ApiOperation(value = "权限筛选查询下拉树->菜单定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/define/defineMenu/user/gainGrantTree")
    @PostMapping("/user/gainGrantTree")
    public MyCommonResult<CommonMenuTree> doGetGrantedMenuTree(@RequestHeader("authorization") String authorization, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<CommonMenuTree> result = MyCommonResult.gainQueryResult(CommonMenuTree.class);
        List<CommonMenuTree> treeList = userAccountRedisService.dealGetCurrentUserFrontMenuTrees(loginUser, authorization, loginUser.getFid(), false);
        result.setResultList(treeList);
        Map<String, CommonMenuTree> urlMap = CommonMenuTree.dealTreeListToUrlMap(treeList, Maps.newHashMap());
        result.setResultMap(urlMap);
        return result;
    }


    @PcWebQueryLog(fullPath = "/define/defineMenu/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->菜单定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public MyCommonResult<DefineMenuVo> queryDtoPage(HttpServletRequest request,
                                                     String queryObj, String paginationObj, String sortObj,
                                                     @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineMenuVo> result = MyCommonResult.gainQueryResult(DefineMenuVo.class);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineMenuDto> paginationBean = this.parsePaginationJsonToBean(paginationObj, DefineMenuDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineMenuService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->菜单定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebQueryLog(fullPath = "/define/defineMenu/queryOneById")
    @PostMapping(value = "/queryOneById")
    public MyCommonResult<DefineMenuVo> queryOneById(HttpServletRequest request, String defineMenuId) {
        MyCommonResult<DefineMenuVo> result = MyCommonResult.gainQueryResult(DefineMenuVo.class);
        try {
            Assert.notBlank(defineMenuId, BaseRstMsgConstant.ErrorMsg.unknowId());

            DefineMenu entity = defineMenuMapper.selectById(defineMenuId);
            result.setBean(DefineMenuTransfer.transferEntityToVo(entity));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增->菜单定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineMenu/createByForm")
    @PostMapping(value = "/createByForm")
    public MyCommonResult createByForm(HttpServletRequest request, DefineMenuVo vo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer addCount = 0;
        try {
            Assert.notNull(vo, BaseRstMsgConstant.ErrorMsg.emptyForm());

            addCount = defineMenuService.dealCreate(loginUser, vo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新->菜单定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineMenu/updateByForm")
    @PostMapping(value = "/updateByForm")
    public MyCommonResult updateByForm(HttpServletRequest request, DefineMenuVo vo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer changeCount = 0;
        try {
            Assert.notNull(vo, BaseRstMsgConstant.ErrorMsg.emptyForm());

            changeCount = defineMenuService.dealUpdate(loginUser, vo);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新->菜单定义/excel模板", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineMenu/updateExcelModel")
    @PostMapping(value = "/updateExcelModel")
    @RequiresRoles(value = {ShiroRoleConstant.ROOT, ShiroRoleConstant.SUPER_ROOT}, logical = Logical.OR)
    public MyCommonResult updateExcelModel(HttpServletRequest request, String menuId, AntdFileUploadBean fileUploadBean, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notBlank(menuId, BaseRstMsgConstant.ErrorMsg.unknowId());

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
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "批量伪删除->菜单定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineMenu/batchDeleteByIds")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", required = true, dataTypeClass = Long[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public MyCommonResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            delCount = defineMenuService.dealBatchLogicDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @PcWebOperationLog(fullPath = "/define/defineMenu/deleteById")
    @ApiOperation(value = "伪删除->菜单定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public MyCommonResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer delCount = defineMenuService.dealLogicDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


}
