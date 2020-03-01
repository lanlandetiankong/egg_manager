package com.egg.manager.service.module;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.common.web.pagination.AntdvSortBean;
import com.egg.manager.common.web.tree.CommonMenuTree;
import com.egg.manager.common.web.tree.CommonTreeSelect;
import com.egg.manager.entity.module.DefineMenu;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.module.DefineMenuVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;

import java.util.List;

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
     * [菜单展示]的子节点 构建的树结构
     * @param rootId
     * @param allMenus
     * @return
     */
    List<CommonMenuTree> getMenuTreeChildNodes(String rootId, List<DefineMenu> allMenus) ;
    /**
     * [菜单展示]的子节点 构建的TreeSelect结构
     * @param rootId
     * @param allMenus
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodes(String rootId, List<DefineMenu> allMenus);



    /**
     * 分页查询 菜单
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetDefineMenuPages(MyCommonResult<DefineMenuVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
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
}
