package com.egg.manager.web.controller.define;

import cn.hutool.core.lang.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.constants.controllers.BaseRstMsgConstant;
import com.egg.manager.api.services.basic.define.DefineRoleService;
import com.egg.manager.api.services.basic.role.RoleMenuService;
import com.egg.manager.common.annotation.log.pc.web.PcWebOperationLog;
import com.egg.manager.common.annotation.user.CurrentLoginUser;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.exception.BusinessException;
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
@Api(value = "API ==>>  DefineRoleController ", description = "角色定义接口")
@RestController
@RequestMapping("/define/define_role")
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


    @PcWebOperationLog(action = "查询角色定义信息列表", description = "查询角色定义信息列表", fullPath = "/define/define_role/getAllDefineRoles")
    @ApiOperation(value = "查询角色定义信息列表", notes = "查询角色定义信息列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllDefineRoles")
    public MyCommonResult<DefineRoleVo> doGetAllDefineRoles(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                            @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineRoleVo> result = new MyCommonResult<DefineRoleVo>();
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineRole> paginationBean = this.parsePaginationJsonToBean(paginationObj,DefineRole.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineRoleService.dealQueryPageByEntitys(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
            dealCommonSuccessCatch(result, "查询角色定义信息列表:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebOperationLog(action = "查询角色定义信息-Dto列表", description = "查询角色定义信息-Dto列表", fullPath = "/define/define_role/getAllDefineRoleDtos")
    @ApiOperation(value = "查询角色定义信息-Dto列表", notes = "查询角色定义信息-Dto列表", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryObj", value = "字段查询配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "paginationObj", value = "分页配置 -> json格式", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "sortObj", value = "排序对象 -> json格式", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/getAllDefineRoleDtos")
    public MyCommonResult<DefineRoleVo> doGetAllDefineRoleDtos(HttpServletRequest request, String queryObj, String paginationObj, String sortObj,
                                                               @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineRoleVo> result = new MyCommonResult<DefineRoleVo>();
        try {
            //解析 搜索条件
            List<QueryFormFieldBean> queryFieldBeanList = this.parseQueryJsonToBeanList(queryObj);
            queryFieldBeanList.add(QueryFormFieldBean.dealGetEqualsBean("state", BaseStateEnum.ENABLED.getValue()));
            //取得 分页配置
            AntdvPaginationBean<DefineRoleDto> paginationBean = this.parsePaginationJsonToBean(paginationObj,DefineRoleDto.class);
            //取得 排序配置
            List<AntdvSortBean> sortBeans = parseSortJsonToBean(sortObj, true);
            result = defineRoleService.dealQueryPageByDtos(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
            dealCommonSuccessCatch(result, "查询角色定义信息-Dto列表:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "查询角色定义信息", notes = "根据角色定义id查询角色定义信息", response = MyCommonResult.class, httpMethod = "POST")
    @PcWebOperationLog(action = "查询角色定义信息", description = "根据角色定义id查询角色定义信息", fullPath = "/define/define_role/getDefineRoleById")
    @PostMapping(value = "/getDefineRoleById")
    public MyCommonResult<DefineRoleVo> doGetDefineRoleById(HttpServletRequest request, String defineRoleId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineRoleVo> result = new MyCommonResult<DefineRoleVo>();
        try {
            DefineRole defineRole = defineRoleMapper.selectById(defineRoleId);
            result.setBean(DefineRoleTransfer.transferEntityToVo(defineRole));
            dealCommonSuccessCatch(result, "查询角色定义信息:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @ApiOperation(value = "查询角色所拥有的权限", notes = "根据角色定义id查询角色已有的权限", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/getAllPermissionByRoleId")
    public MyCommonResult<DefinePermission> doGetAllPermissionByRoleId(HttpServletRequest request, String defineRoleId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermission> result = new MyCommonResult<DefinePermission>();
        try {
            List<DefinePermission> definePermissionList = definePermissionMapper.findAllPermissionByRoleId(defineRoleId);
            result.setResultList(definePermissionList);
            dealCommonSuccessCatch(result, "查询角色所拥有的权限:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "查询角色已获授权的菜单", notes = "根据角色定义id查询角色已获授权的菜单", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/getAllMenuByRoleId")
    public MyCommonResult<DefinePermission> doGetAllMenuByRoleId(HttpServletRequest request, String defineRoleId, Boolean filterParentNode,
                                                                 @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefinePermission> result = new MyCommonResult<DefinePermission>();
        try {
            List<DefineMenu> defineMenuList = null;
            if (Boolean.TRUE.equals(filterParentNode)) {  //是否过滤掉 有子节点的 [菜单节点]
                defineMenuList = defineMenuMapper.findAllMenuByRoleIdFilterParentNode(defineRoleId, BaseStateEnum.ENABLED.getValue());
            } else {
                defineMenuList = defineMenuMapper.findAllMenuByRoleId(defineRoleId, BaseStateEnum.ENABLED.getValue());
            }
            result.setResultList(defineMenuList);
            dealCommonSuccessCatch(result, "查询角色已获授权的菜单:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "新增角色定义", notes = "表单方式新增角色定义", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/doAddDefineRole")
    public MyCommonResult<DefineRoleVo> doAddDefineRole(HttpServletRequest request, DefineRoleVo defineRoleVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult<DefineRoleVo> result = new MyCommonResult<DefineRoleVo>();
        Integer addCount = 0;
        try {
            Assert.notNull(defineRoleVo, BaseRstMsgConstant.ErrorMsg.emptyForm());
            addCount = defineRoleService.dealCreate(loginUser, defineRoleVo);
            result.setCount(addCount);
            dealCommonSuccessCatch(result, "新增角色定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "更新角色定义", notes = "表单方式更新角色定义", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/doUpdateDefineRole")
    public MyCommonResult doUpdateDefineRole(HttpServletRequest request, DefineRoleVo defineRoleVo, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer changeCount = 0;
        try {
            Assert.notNull(defineRoleVo,BaseRstMsgConstant.ErrorMsg.emptyForm());

            changeCount = defineRoleService.dealUpdate(loginUser, defineRoleVo, false);
            result.setCount(changeCount);
            dealCommonSuccessCatch(result, "更新角色定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebOperationLog(action = "批量删除角色定义", description = "根据角色id批量删除角色定义", fullPath = "/define/define_role/batchDelDefineRoleByIds")
    @ApiOperation(value = "批量删除角色定义", notes = "根据角色id批量删除角色定义", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delIds", value = "要删除的权限定义id数组", required = true, dataTypeClass = String[].class),
    })
    @PostMapping(value = "/batchDelDefineRoleByIds")
    public MyCommonResult doBatchDeleteDefineRoleById(HttpServletRequest request, String[] delIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        Integer delCount = 0;
        try {
            Assert.notEmpty(delIds,BaseRstMsgConstant.ErrorMsg.unknowIdCollection());

            delCount = defineRoleService.dealBatchDelete(loginUser, delIds);
            dealCommonSuccessCatch(result, "批量删除角色定义:" + actionSuccessMsg);
            result.setCount(delCount);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }

    @PcWebOperationLog(action = "删除角色定义", description = "根据角色id删除角色定义", fullPath = "/define/define_role/delOneDefineRoleByIds")
    @ApiOperation(value = "删除角色定义", notes = "根据角色id删除角色定义", response = MyCommonResult.class, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "delId", value = "要删除的角色定义id", required = true, dataTypeClass = String.class),
    })
    @PostMapping(value = "/delOneDefineRoleByIds")
    public MyCommonResult doDelOneDefineRoleByIds(HttpServletRequest request, String delId, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        try {
            Assert.notBlank(delId,BaseRstMsgConstant.ErrorMsg.unknowId());

            Integer delCount = defineRoleService.dealDeleteById(loginUser, delId);
            result.setCount(delCount);
            dealCommonSuccessCatch(result, "删除角色定义:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "角色授权", notes = "为角色分配权限", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/grantPermissionToRole")
    public MyCommonResult doGrantPermissionToRole(HttpServletRequest request, String roleId, String[] checkIds, @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        try {
            Assert.notBlank(roleId,"未知角色id:"+actionFailMsg);
            Integer count = defineRoleService.dealGrantPermissionToRole(loginUser, roleId, checkIds);
            result.setCount(count);
            dealCommonSuccessCatch(result, "角色授权:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }


    @ApiOperation(value = "[菜单-角色]授权", notes = "为角色设置可访问菜单", response = MyCommonResult.class, httpMethod = "POST")
    @PostMapping(value = "/grantMenusToRole")
    @Transactional
    public MyCommonResult doGrantMenusToRole(HttpServletRequest request, String roleId, String[] checkIds, String[] halfCheckIds,
                                             @CurrentLoginUser UserAccount loginUser) {
        MyCommonResult result = new MyCommonResult();
        try {
            Assert.notBlank(roleId,"未知角色id:"+actionFailMsg);

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
            dealCommonSuccessCatch(result, "授权菜单:" + actionSuccessMsg);
        } catch (Exception e) {
            this.dealCommonErrorCatch(log, result, e);
        }
        return result;
    }
}
