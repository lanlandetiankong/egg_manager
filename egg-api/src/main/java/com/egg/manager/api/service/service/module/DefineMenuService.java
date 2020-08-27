package com.egg.manager.api.service.service.module;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.bean.tree.common.CommonMenuTree;
import com.egg.manager.persistence.bean.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.pojo.vo.define.DefineMenuVo;

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
public interface DefineMenuService extends IService<DefineMenu> {

    /**
     * 查询 用户 可访问的[菜单定义]
     * @param userAccountId
     * @return
     */
    List<DefineMenu> dealGetUserGrantedMenusByAccountId(String userAccountId);

    /**
     * 查询 用户 可访问的 菜单路径
     * @param userAccountId
     * @return
     */
    Set<String> dealGetUserVisitAbleUrl(String userAccountId);

    /**
     * 查询 用户 可访问的[菜单定义] Tree
     *
     * @param userAccountId
     * @return
     */
    List<CommonMenuTree> dealGetUserGrantedMenuTrees(String userAccountId);
    /**
     * 查询 所有[可用状态]的 [菜单定义]
     * @param defineMenuEntityWrapper
     * @return
     */
    List<DefineMenu> getAllEnableDefineMenus(EntityWrapper<DefineMenu> defineMenuEntityWrapper);
    /**
     * [菜单展示]的子节点 构建的树结构
     * @param rootId
     * @param allMenus
     * @return
     */
    List<CommonMenuTree> getMenuTreeChildNodes(String rootId, List<DefineMenu> allMenus) ;
    /**
     * [菜单展示]的子节点 构建的TreeSelect结构
     * @param rootId 最顶层的菜单id
     * @param allMenus
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodes(String rootId,List<DefineMenu> allMenus);

    /**
     * [菜单展示]的子节点 构建的TreeSelect结构(包含最顶层)
     * @param rootId 最顶层的菜单id
     * @param allMenus
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(String rootId,List<DefineMenu> allMenus);



    /**
     * 分页查询 菜单定义 列表
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetDefineMenuPages(MyCommonResult<DefineMenuVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                  List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 菜单定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetDefineMenuDtoPages(MyCommonResult<DefineMenuVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                List<AntdvSortBean> sortBeans);

    /**
     * 菜单定义-新增
     * @param defineMenuVo
     * @throws Exception
     */
    Integer dealAddDefineMenu(DefineMenuVo defineMenuVo, UserAccount loginUser) throws Exception ;

    /**
     * 菜单定义-更新
     * @param defineMenuVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateDefineMenu(DefineMenuVo defineMenuVo,UserAccount loginUser,boolean updateAll) throws Exception ;

    /**
     * 菜单定义-批量删除
     * @param delIds 要删除的菜单id 集合
     * @throws Exception
     */
    Integer dealDelDefineMenuByArr(String[] delIds,UserAccount loginUser) throws Exception;

    /**
     * 菜单定义-删除
     * @param delId 要删除的菜单id
     * @throws Exception
     */
    Integer dealDelDefineMenu(String delId,UserAccount loginUser) throws Exception;


    /**
     * 验证 数据库 中的唯一冲突
     * @param defineMenuVo
     * @param defineMenuWrapper
     * @return
     */
    MyVerifyDuplicateBean dealCheckDuplicateKey(DefineMenuVo defineMenuVo, Wrapper<DefineMenu> defineMenuWrapper);
}
