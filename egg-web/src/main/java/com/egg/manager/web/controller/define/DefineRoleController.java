package com.egg.manager.web.controller.define;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.services.em.define.basic.DefineRoleService;
import com.egg.manager.api.services.em.user.basic.RoleMenuService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.persistence.commons.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.persistence.commons.base.constant.web.api.WebApiConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.form.QueryField;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenuEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefinePermissionEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineRoleEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineMenuMapper;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefinePermissionMapper;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineRoleMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineRoleDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineRoleTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineRoleVo;
import com.egg.manager.persistence.em.user.db.mysql.entity.RoleMenuEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.RoleMenuMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.em.user.pojo.initialize.RoleMenuPojoInitialize;
import com.egg.manager.persistence.enhance.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.persistence.enhance.annotation.user.CurrentLoginUser;
import com.egg.manager.web.controller.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/define/defineRole")
public class DefineRoleController extends BaseController {

    @Autowired
    private DefineMenuMapper defineMenuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private DefinePermissionMapper definePermissionMapper;
    @Autowired
    private DefineRoleMapper defineRoleMapper;

    @Reference
    private RoleMenuService roleMenuService;

    @Reference
    private DefineRoleService defineRoleService;


    @PcWebOperationLog(fullPath = "/define/defineRole/queryPage")
    @ApiOperation(value = "分页查询->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryPage")
    public WebResult queryPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                               @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            //解析 搜索条件
            List<QueryField> queryFieldList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldList.add(QueryField.gainEq("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPage<DefineRoleEntity> vpage = this.parsePaginationJsonToBean(paginationObj, DefineRoleEntity.class);
            //取得 排序配置
            AntdvSortMap sortMap = parseSortJsonToBean(sortObj, true);
            result = defineRoleService.dealQueryPageByEntitys(loginUserInfo, result, queryFieldList, vpage, sortMap);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebOperationLog(fullPath = "/define/defineRole/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public WebResult queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                  @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            //解析 搜索条件
            List<QueryField> queryFieldList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldList.add(QueryField.gainEq("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPage<DefineRoleDto> vpage = this.parsePaginationJsonToBean(paginationObj, DefineRoleDto.class);
            //取得 排序配置
            AntdvSortMap sortMap = parseSortJsonToBean(sortObj, true);
            result = defineRoleService.dealQueryPageByDtos(loginUserInfo, result, queryFieldList, vpage, sortMap);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineRole/queryOneById")
    @PostMapping(value = "/queryOneById")
    public WebResult queryOneById(HttpServletRequest request, String defineRoleId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            DefineRoleEntity defineRoleEntity = defineRoleMapper.selectById(defineRoleId);
            result.putBean(DefineRoleTransfer.transferEntityToVo(defineRoleEntity));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "查询已获授权/角色->权限定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/gainAllPermissionByRoleId")
    public WebResult gainAllPermissionByRoleId(HttpServletRequest request, String defineRoleId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            List<DefinePermissionEntity> definePermissionEntityList = definePermissionMapper.findAllPermissionByRoleId(defineRoleId);
            result.putResultList(definePermissionEntityList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "查询已获授权/角色->菜单定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/gainAllMenuByRoleId")
    public WebResult gainAllMenuByRoleId(HttpServletRequest request, String defineRoleId, Boolean filterParentNode,
                                         @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okQuery();
        try {
            List<DefineMenuEntity> defineMenuEntityList = null;
            if (Boolean.TRUE.equals(filterParentNode)) {
                //是否过滤掉 有子节点的 [菜单节点]
                defineMenuEntityList = defineMenuMapper.findAllMenuByRoleIdFilterParentNode(defineRoleId, BaseStateEnum.ENABLED.getValue());
            } else {
                defineMenuEntityList = defineMenuMapper.findAllMenuByRoleId(defineRoleId, BaseStateEnum.ENABLED.getValue());
            }
            result.putResultList(defineMenuEntityList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/createByForm")
    public WebResult createByForm(HttpServletRequest request, DefineRoleVo defineRoleVo, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer addCount = 0;
        try {
            Assert.notNull(defineRoleVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = defineRoleService.dealCreate(loginUserInfo, defineRoleVo);
            result.putCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/updateByForm")
    public WebResult updateByForm(HttpServletRequest request, DefineRoleVo defineRoleVo, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer changeCount = 0;
        try {
            Assert.notNull(defineRoleVo, BaseRstMsgConstant.ErrorMsg.emptyForm());

            changeCount = defineRoleService.dealUpdate(loginUserInfo, defineRoleVo);
            result.putCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebOperationLog(fullPath = "/define/defineRole/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public WebResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            delCount = defineRoleService.dealBatchLogicDelete(loginUserInfo, delIds);
            result.putCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebOperationLog(fullPath = "/define/defineRole/deleteById")
    @ApiOperation(value = "伪删除->角色定义", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public WebResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        try {
            Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer delCount = defineRoleService.dealLogicDeleteById(loginUserInfo, delId);
            result.putCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新授权->角色+权限", notes = "为角色分配权限", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/grantPermissionToRole")
    public WebResult doGrantPermissionToRole(HttpServletRequest request, String roleId, String[] checkIds, @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        try {
            Assert.notNull(roleId, "未知角色id:" + actionFailMsg);
            Integer count = defineRoleService.dealGrantPermissionToRole(loginUserInfo, roleId, checkIds);
            result.putCount(count);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新授权->角色+菜单", notes = "为角色设置可访问菜单", response = WebResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/grantMenusToRole")
    public WebResult doGrantMenusToRole(HttpServletRequest request, String roleId, String[] checkIds, String[] halfCheckIds,
                                        @CurrentLoginUser CurrentLoginUserInfo loginUserInfo) {
        WebResult result = WebResult.okOperation();
        try {
            Assert.notNull(roleId, "未知角色id:" + actionFailMsg);

            //取得前端所有 勾选的值
            checkIds = checkIds != null ? checkIds : new String[]{};
            halfCheckIds = halfCheckIds != null ? halfCheckIds : new String[]{};
            Set<String> checkIdAll = Sets.newHashSet(checkIds);
            checkIdAll.addAll(Lists.newArrayList(halfCheckIds));

            //在数据库中 关联分别为 enable、delete 状态
            Set<String> oldEnableCheckIdSet = defineRoleService.dealGetMenuIdSetByRoleIdFromDb(roleId, BaseStateEnum.ENABLED.getValue());
            //TODO 是否可能被强制限制
            Set<String> oldDelCheckIdSet = defineRoleService.dealGetMenuIdSetByRoleIdFromDb(roleId, BaseStateEnum.DISABLED.getValue());
            //所有 已经在数据库 有的关联行
            Set<String> oldCheckIdAll = Sets.union(oldEnableCheckIdSet, oldDelCheckIdSet);

            //分别为 待添加数据行、待更新为 可用状态、待更新为 删除状态的 Set集合
            Sets.SetView addSetView = Sets.difference(checkIdAll, oldEnableCheckIdSet);
            Sets.SetView updateEnableIdSet = Sets.difference(checkIdAll, oldDelCheckIdSet);
            Sets.SetView updateDelIdSet = Sets.difference(oldCheckIdAll, checkIdAll);

            if (CollectionUtil.isNotEmpty(addSetView)) {
                List<RoleMenuEntity> addRoleMenuEntityList = Lists.newArrayList();
                Iterator<String> addIter = addSetView.iterator();
                while (addIter.hasNext()) {
                    String diffNext = addIter.next();
                    addRoleMenuEntityList.add(RoleMenuPojoInitialize.generateSimpleInsertEntity(roleId, diffNext, BaseStateEnum.ENABLED.getValue(), loginUserInfo));
                }
                boolean flag = roleMenuService.saveBatch(addRoleMenuEntityList);
            }
            if (CollectionUtil.isNotEmpty(updateEnableIdSet)) {
                Iterator<String> enableIter = updateEnableIdSet.iterator();
                List enableIdList = Lists.newArrayList(enableIter);
                int count = roleMenuMapper.batchUpdateStateByRole(roleId, enableIdList, BaseStateEnum.ENABLED.getValue(), loginUserInfo);
            }
            if (CollectionUtil.isNotEmpty(updateDelIdSet)) {
                Iterator<String> delIter = updateDelIdSet.iterator();
                List delIdList = Lists.newArrayList(delIter);
                int count = roleMenuMapper.batchUpdateStateByRole(roleId, delIdList, BaseStateEnum.DISABLED.getValue(), loginUserInfo);
            }
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }
}
