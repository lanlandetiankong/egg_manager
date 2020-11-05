package com.egg.manager.api.services.basic.module;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonMenuTree;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenu;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineMenuMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineMenuDto;
import com.egg.manager.persistence.em.define.pojo.vo.DefineMenuVo;

import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface DefineMenuService extends IService<DefineMenu>, MyBaseMysqlService<DefineMenu, DefineMenuMapper, DefineMenuVo> {

    /**
     * 查询 用户 可访问的[菜单定义]
     * @param userAccountId
     * @return
     */
    List<DefineMenu> dealGetUserGrantedMenusByAccountId(Long userAccountId);

    /**
     * 查询 用户 可访问的 菜单路径
     * @param userAccountId
     * @return
     */
    Set<String> dealGetUserVisitAbleUrl(Long userAccountId);

    /**
     * 查询 用户 可访问的[菜单定义] Tree
     * @param userAccountId
     * @return
     */
    List<CommonMenuTree> dealGetUserGrantedMenuTrees(Long userAccountId);

    /**
     * 查询 所有[可用状态]的 [菜单定义]
     * @return
     */
    List<DefineMenu> getAllEnableList();

    /**
     * [菜单展示]的子节点 构建的树结构
     * @param rootId
     * @param allMenus
     * @return
     */
    List<CommonMenuTree> getMenuTreeChildNodes(Long rootId, List<DefineMenu> allMenus);

    /**
     * [菜单展示]的子节点 构建的TreeSelect结构
     * @param rootId   最顶层的菜单id
     * @param allMenus
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodes(Long rootId, List<DefineMenu> allMenus);

    /**
     * [菜单展示]的子节点 构建的TreeSelect结构(包含最顶层)
     * @param rootId   最顶层的菜单id
     * @param allMenus
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(Long rootId, List<DefineMenu> allMenus);


    /**
     * 分页查询 菜单定义 列表
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefineMenuVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<DefineMenuVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineMenu> paginationBean,
                                                        List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 菜单定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefineMenuVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<DefineMenuVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineMenuDto> paginationBean,
                                                     List<AntdvSortBean> sortBeans);

    /**
     * 菜单定义-新增
     * @param loginUser    当前登录用户
     * @param defineMenuVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser, DefineMenuVo defineMenuVo) throws Exception;

    /**
     * 菜单定义-更新
     * @param loginUser    当前登录用户
     * @param defineMenuVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser, DefineMenuVo defineMenuVo) throws Exception;

    /**
     * 验证 数据库 中的唯一冲突
     * @param loginUser         当前登录用户
     * @param defineMenuVo
     * @param defineMenuWrapper
     * @return
     */
    MyVerifyDuplicateBean dealCheckDuplicateKey(UserAccount loginUser, DefineMenuVo defineMenuVo, QueryWrapper<DefineMenu> defineMenuWrapper);
}
