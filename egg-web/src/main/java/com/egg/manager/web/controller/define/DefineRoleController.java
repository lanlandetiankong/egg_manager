package com.egg.manager.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.common.base.constant.rst.BaseRstMsgConstant;
import com.egg.manager.api.services.basic.define.DefineRoleService;
import com.egg.manager.api.services.basic.role.RoleMenuService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.constant.commons.http.HttpMethodConstant;
import com.egg.manager.common.base.constant.web.api.WebApiConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineMenuMapper;
import com.egg.manager.persistence.db.mysql.mapper.define.DefinePermissionMapper;
import com.egg.manager.persistence.db.mysql.mapper.define.DefineRoleMapper;
import com.egg.manager.persistence.db.mysql.mapper.role.RoleMenuMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineRoleDto;
import com.egg.manager.persistence.pojo.mysql.initialize.role.RoleMenuPojoInitialize;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefineRoleTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineRoleVo;
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
 * @description:
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
    @ApiOperation(value = "分页查询->角色定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryPage")
    public MyCommonResult<DefineRoleVo> queryPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                  @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineRoleVo> result = MyCommonResult.gainQueryResult(DefineRoleVo.class);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineRole> paginationBean = this.parsePaginationJsonToBean(paginationObj, DefineRole.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineRoleService.dealQueryPageByEntitys(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebOperationLog(fullPath = "/define/defineRole/queryDtoPage")
    @ApiOperation(value = "分页查询(dto)->角色定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_QUERY_OBJ, value = WebApiConstant.QUERY_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_PAGINATION_OBJ, value = WebApiConstant.PAGINATION_OBJ_LABEL, required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = WebApiConstant.FIELDNAME_SORT_OBJ, value = WebApiConstant.SORT_OBJ_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/queryDtoPage")
    public MyCommonResult<DefineRoleVo> queryDtoPage(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                     @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineRoleVo> result = MyCommonResult.gainQueryResult(DefineRoleVo.class);
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineRoleDto> paginationBean = this.parsePaginationJsonToBean(paginationObj, DefineRoleDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineRoleService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "根据id查询->角色定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PcWebOperationLog(fullPath = "/define/defineRole/queryOneById")
    @PostMapping(value = "/queryOneById")
    public MyCommonResult<DefineRoleVo> queryOneById(HttpServletRequest request, String defineRoleId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineRoleVo> result = MyCommonResult.gainQueryResult(DefineRoleVo.class);
        try {
            DefineRole defineRole = defineRoleMapper.selectById(defineRoleId);
            result.setBean(DefineRoleTransfer.transferEntityToVo(defineRole));
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "查询已获授权/角色->权限定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/gainAllPermissionByRoleId")
    public MyCommonResult<DefinePermission> gainAllPermissionByRoleId(HttpServletRequest request, String defineRoleId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermission> result = MyCommonResult.gainQueryResult(DefinePermission.class);
        try {
            List<DefinePermission> definePermissionList = definePermissionMapper.findAllPermissionByRoleId(defineRoleId);
            result.setResultList(definePermissionList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "查询已获授权/角色->菜单定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/gainAllMenuByRoleId")
    public MyCommonResult<DefineMenu> gainAllMenuByRoleId(HttpServletRequest request, String defineRoleId, Boolean filterParentNode,
                                                          @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineMenu> result = MyCommonResult.gainQueryResult(DefineMenu.class);
        try {
            List<DefineMenu> defineMenuList = null;
            if (Boolean.TRUE.equals(filterParentNode)) {
                //是否过滤掉 有子节点的 [菜单节点]
                defineMenuList = defineMenuMapper.findAllMenuByRoleIdFilterParentNode(defineRoleId, BaseStateEnum.ENABLED.getValue());
            } else {
                defineMenuList = defineMenuMapper.findAllMenuByRoleId(defineRoleId, BaseStateEnum.ENABLED.getValue());
            }
            result.setResultList(defineMenuList);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增->角色定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/createByForm")
    public MyCommonResult createByForm(HttpServletRequest request, DefineRoleVo defineRoleVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer addCount = 0;
        try {
            Assert.notNull(defineRoleVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = defineRoleService.dealCreate(loginUser, defineRoleVo);
            result.setCount(addCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新->角色定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/updateByForm")
    public MyCommonResult updateByForm(HttpServletRequest request, DefineRoleVo defineRoleVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer changeCount = 0;
        try {
            Assert.notNull(defineRoleVo, BaseRstMsgConstant.ErrorMsg.emptyForm());

            changeCount = defineRoleService.dealUpdate(loginUser, defineRoleVo);
            result.setCount(changeCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebOperationLog(fullPath = "/define/defineRole/batchDeleteByIds")
    @ApiOperation(value = "批量伪删除->角色定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = WebApiConstant.DELETE_ID_ARRAY_LABEL, required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDeleteByIds")
    public MyCommonResult batchDeleteByIds(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds, BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            delCount = defineRoleService.dealBatchLogicDelete(loginUser, delIds);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebOperationLog(fullPath = "/define/defineRole/deleteById")
    @ApiOperation(value = "伪删除->角色定义", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = WebApiConstant.DELETE_ID_LABEL, required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/deleteById")
    public MyCommonResult deleteById(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notBlank(delId, BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer delCount = defineRoleService.dealLogicDeleteById(loginUser, delId);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新授权->角色+权限", notes = "为角色分配权限", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/grantPermissionToRole")
    public MyCommonResult doGrantPermissionToRole(HttpServletRequest request, String roleId, String[] checkIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notBlank(roleId, "未知角色id:" + actionFailMsg);
            Integer count = defineRoleService.dealGrantPermissionToRole(loginUser, roleId, checkIds);
            result.setCount(count);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新授权->角色+菜单", notes = "为角色设置可访问菜单", response = MyCommonResult.class, httpMethod = HttpMethodConstant.POST)
    @PostMapping(value = "/grantMenusToRole")
    public MyCommonResult doGrantMenusToRole(HttpServletRequest request, String roleId, String[] checkIds, String[] halfCheckIds,
                                             @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = MyCommonResult.gainOperationResult();
        try {
            Assert.notBlank(roleId, "未知角色id:" + actionFailMsg);

            //取得前端所有 勾选的值
            checkIds = checkIds != null ? checkIds : new String[]{};
            halfCheckIds = halfCheckIds != null ? halfCheckIds : new String[]{};
            Set<String> checkIdAll = Sets.newHashSet(checkIds);
            checkIdAll.addAll(Lists.newArrayList(halfCheckIds));

            //在数据库中 关联分别为 enable、delete 状态
            Set<String> oldEnableCheckIdSet = defineRoleService.dealGetMenuIdSetByRoleIdFromDb(roleId, BaseStateEnum.ENABLED.getValue());
            Set<String> oldDelCheckIdSet = defineRoleService.dealGetMenuIdSetByRoleIdFromDb(roleId, BaseStateEnum.DELETE.getValue());
            //所有 已经在数据库 有的关联行
            Set<String> oldCheckIdAll = Sets.union(oldEnableCheckIdSet, oldDelCheckIdSet);

            //分别为 待添加数据行、待更新为 可用状态、待更新为 删除状态的 Set集合
            Sets.SetView addSetView = Sets.difference(checkIdAll, oldEnableCheckIdSet);
            Sets.SetView updateEnableIdSet = Sets.difference(checkIdAll, oldDelCheckIdSet);
            Sets.SetView updateDelIdSet = Sets.difference(oldCheckIdAll, checkIdAll);

            if (addSetView != null && addSetView.isEmpty() == false) {
                List<RoleMenu> addRoleMenuList = Lists.newArrayList();
                Iterator<String> addIter = addSetView.iterator();
                while (addIter.hasNext()) {
                    String diffNext = addIter.next();
                    addRoleMenuList.add(RoleMenuPojoInitialize.generateSimpleInsertEntity(roleId, diffNext, BaseStateEnum.ENABLED.getValue(), loginUser));
                }
                boolean flag = roleMenuService.saveBatch(addRoleMenuList);
            }
            if (updateEnableIdSet != null && updateEnableIdSet.isEmpty() == false) {
                Iterator<String> enableIter = updateEnableIdSet.iterator();
                List enableIdList = Lists.newArrayList(enableIter);
                int count = roleMenuMapper.batchUpdateStateByRole(roleId, enableIdList, BaseStateEnum.ENABLED.getValue(), loginUser);
            }
            if (updateDelIdSet != null && updateDelIdSet.isEmpty() == false) {
                Iterator<String> delIter = updateDelIdSet.iterator();
                List delIdList = Lists.newArrayList(delIter);
                int count = roleMenuMapper.batchUpdateStateByRole(roleId, delIdList, BaseStateEnum.DELETE.getValue(), loginUser);
            }
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }
}
