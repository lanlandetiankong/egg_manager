package com.egg.manager.em.web.controller.emctl.define;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.exchange.BaseController;
import com.egg.manager.api.services.em.define.basic.EmDefineMenuService;
import com.egg.manager.api.services.em.define.basic.EmDefinePermissionService;
import com.egg.manager.api.services.em.define.basic.EmDefineRoleService;
import com.egg.manager.api.services.em.user.basic.EmRoleMenuService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.basic.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.basic.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineMenuEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefinePermissionEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineRoleEntity;
import com.egg.manager.persistence.em.define.pojo.transfer.EmDefineRoleTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineRoleVo;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmRoleMenuEntity;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserAccountDto;
import com.egg.manager.persistence.em.user.pojo.initialize.EmRoleMenuPojoInitialize;
import com.egg.manager.persistence.enhance.annotation.log.em.EmPcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.query.QueryPage;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Api(value = "API-角色定义接口")
@RestController
@RequestMapping("/emCtl/define/defineRole")
public class EmDefineRoleController extends BaseController {
    @Reference
    private EmDefineMenuService emDefineMenuService;
    @Reference
    private EmDefinePermissionService emDefinePermissionService;

    @Reference
    private EmRoleMenuService emRoleMenuService;
    @Reference
    private EmDefineRoleService emDefineRoleService;

    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineRole/queryPage")
    @ApiOperation(value = "分页查询->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryPage")
    public WebResult queryPage(HttpServletRequest request, @QueryPage(tClass = EmUserAccountDto.class) QueryPageBean<EmUserAccountDto> queryPageBean,
                               @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        result = emDefineRoleService.dealQueryPageByEntitys(loginUserInfo, result, queryPageBean);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineRole/queryDtoPage")
    @ApiOperation(value = "分页查询(Dto)->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, @QueryPage(tClass = EmUserAccountDto.class) QueryPageBean<EmUserAccountDto> queryPageBean,
                                  @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        queryPageBean.operateQuery().addEq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryPageBean.operateQuery().addEqNotDeleted();
        result = emDefineRoleService.dealQueryPageByDtos(loginUserInfo, result, queryPageBean);
        return result;
    }

    @ApiOperation(value = "根据id查询->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineRole/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String defineRoleId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        EmDefineRoleEntity emDefineRoleEntity = emDefineRoleService.getById(defineRoleId);
        result.putBean(EmDefineRoleTransfer.transferEntityToVo(emDefineRoleEntity));
        return result;
    }

    @ApiOperation(value = "查询已获授权/角色->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/gainAllPermissionByRoleId")
    public WebResult gainAllPermissionByRoleId(HttpServletRequest request, String defineRoleId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        List<EmDefinePermissionEntity> emDefinePermissionEntityList = emDefinePermissionService.findAllPermissionByRoleId(defineRoleId);
        result.putGridList(emDefinePermissionEntityList);
        return result;
    }

    @ApiOperation(value = "查询已获授权/角色->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/gainAllMenuByRoleId")
    public WebResult gainAllMenuByRoleId(HttpServletRequest request, String defineRoleId, Boolean filterParentNode,
                                         @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        List<EmDefineMenuEntity> emDefineMenuEntityList = null;
        if (Boolean.TRUE.equals(filterParentNode)) {
            //是否过滤掉 有子节点的 [菜单节点]
            emDefineMenuEntityList = emDefineMenuService.findAllMenuByRoleIdFilterParentNode(defineRoleId, BaseStateEnum.ENABLED.getValue());
        } else {
            emDefineMenuEntityList = emDefineMenuService.findAllMenuByRoleId(defineRoleId, BaseStateEnum.ENABLED.getValue());
        }
        result.putGridList(emDefineMenuEntityList);
        return result;
    }

    @ApiOperation(value = "新增->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, EmDefineRoleVo emDefineRoleVo, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        Assert.notNull(emDefineRoleVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        addCount = emDefineRoleService.dealCreate(loginUserInfo, emDefineRoleVo);
        result.putCount(addCount);
        return result;
    }

    @ApiOperation(value = "更新->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, EmDefineRoleVo emDefineRoleVo, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        Assert.notNull(emDefineRoleVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
        changeCount = emDefineRoleService.dealUpdate(loginUserInfo, emDefineRoleVo);
        result.putCount(changeCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineRole/batchDeleteByIds")
    @ApiOperation(value = "批量逻辑删除->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());
        delCount = emDefineRoleService.dealBatchLogicDelete(loginUserInfo, delIds);
        result.putCount(delCount);
        return result;
    }

    @EmPcWebOperationLog(fullPath = "/emCtl/define/defineRole/deleteById")
    @ApiOperation(value = "逻辑删除->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer delCount = emDefineRoleService.dealLogicDeleteById(loginUserInfo, delId);
        result.putCount(delCount);
        return result;
    }

    @ApiOperation(value = "更新授权->角色+权限", notes = "为角色分配权限", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/grantPermissionToRole")
    public WebResult doGrantPermissionToRole(HttpServletRequest request, String roleId, String[] checkIds, @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo)
            throws Exception {
        WebResult result = WebResult.okOperation();
        Assert.notNull(roleId, BaseRstMsgConstant.ErrorMsg.unknowId());
        Integer count = emDefineRoleService.dealGrantPermissionToRole(loginUserInfo, roleId, checkIds);
        result.putCount(count);
        return result;
    }

    @ApiOperation(value = "更新授权->角色+菜单", notes = "为角色设置可访问菜单", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/grantMenusToRole")
    public WebResult doGrantMenusToRole(HttpServletRequest request, String roleId, String[] checkIds, String[] halfCheckIds,
                                        @CurrentLoginUser CurrentLoginEmUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Assert.notNull(roleId, BaseRstMsgConstant.ErrorMsg.unknowId());
        //取得前端所有 勾选的值
        checkIds = checkIds != null ? checkIds : new String[]{};
        halfCheckIds = halfCheckIds != null ? halfCheckIds : new String[]{};
        Set<String> checkIdAll = Sets.newHashSet(checkIds);
        checkIdAll.addAll(Lists.newArrayList(halfCheckIds));
        //在数据库中 关联分别为 enable、delete 状态
        Set<String> oldEnableCheckIdSet = emDefineRoleService.dealGetMenuIdSetByRoleIdFromDb(roleId, BaseStateEnum.ENABLED.getValue());
        //TODO 是否可能被强制限制
        Set<String> oldDelCheckIdSet = emDefineRoleService.dealGetMenuIdSetByRoleIdFromDb(roleId, BaseStateEnum.DISABLED.getValue());
        //所有 已经在数据库 有的关联行
        Set<String> oldCheckIdAll = Sets.union(oldEnableCheckIdSet, oldDelCheckIdSet);
        //分别为 待添加数据行、待更新为 可用状态、待更新为 删除状态的 Set集合
        Sets.SetView addSetView = Sets.difference(checkIdAll, oldEnableCheckIdSet);
        Sets.SetView updateEnableIdSet = Sets.difference(checkIdAll, oldDelCheckIdSet);
        Sets.SetView updateDelIdSet = Sets.difference(oldCheckIdAll, checkIdAll);
        if (CollectionUtil.isNotEmpty(addSetView)) {
            List<EmRoleMenuEntity> addEmRoleMenuEntityList = Lists.newArrayList();
            Iterator<String> addIter = addSetView.iterator();
            while (addIter.hasNext()) {
                String diffNext = addIter.next();
                addEmRoleMenuEntityList.add(EmRoleMenuPojoInitialize.generateSimpleInsertEntity(roleId, diffNext, BaseStateEnum.ENABLED.getValue(), loginUserInfo));
            }
            boolean flag = emRoleMenuService.saveBatch(addEmRoleMenuEntityList);
        }
        if (CollectionUtil.isNotEmpty(updateEnableIdSet)) {
            Iterator<String> enableIter = updateEnableIdSet.iterator();
            List enableIdList = Lists.newArrayList(enableIter);
            int count = emRoleMenuService.batchUpdateStateByRole(roleId, enableIdList, BaseStateEnum.ENABLED.getValue(), loginUserInfo);
        }
        if (CollectionUtil.isNotEmpty(updateDelIdSet)) {
            Iterator<String> delIter = updateDelIdSet.iterator();
            List delIdList = Lists.newArrayList(delIter);
            int count = emRoleMenuService.batchUpdateStateByRole(roleId, delIdList, BaseStateEnum.DISABLED.getValue(), loginUserInfo);
        }
        return result;
    }
}
