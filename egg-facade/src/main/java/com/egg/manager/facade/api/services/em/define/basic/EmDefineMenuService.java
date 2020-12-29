package com.egg.manager.facade.api.services.em.define.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.facade.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.beans.tree.common.CommonMenuTree;
import com.egg.manager.facade.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.facade.persistence.commons.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.facade.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineMenuEntity;
import com.egg.manager.facade.persistence.em.define.db.mysql.mapper.EmDefineMenuMapper;
import com.egg.manager.facade.persistence.em.define.pojo.dto.EmDefineMenuDto;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineMenuVo;
import com.egg.manager.facade.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;

import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmDefineMenuService extends MyBaseMysqlService<EmDefineMenuEntity, EmDefineMenuMapper, EmDefineMenuVo> {

    /**
     * 查询 用户 可访问的[菜单定义]
     * @param userAccountId
     * @return
     */
    List<EmDefineMenuEntity> dealGetUserGrantedMenusByAccountId(String userAccountId);

    /**
     * 查询 用户 可访问的 菜单路径
     * @param userAccountId
     * @return
     */
    Set<String> queryUserVisitAbleUrl(String userAccountId);

    /**
     * 查询并缓存 用户 可访问的 菜单路径
     * @param userAccountId
     * @return
     */
    Set<String> queryUserVisitAbleUrlToCacheable(String userAccountId);

    /**
     * 刷新缓存 用户 可访问的 菜单路径
     * @param userAccountId
     * @return
     */
    Set<String> queryUserVisitAbleUrlToCachePut(String userAccountId);


    /**
     * 查询 用户 可访问的[菜单定义] Tree
     * @param userAccountId
     * @return
     */
    List<CommonMenuTree> queryUserVisitAbleMenu(String userAccountId);

    /**
     * 查询并缓存 用户 可访问的[菜单定义] Tree
     * @param userAccountId
     * @return
     */
    List<CommonMenuTree> queryUserVisitAbleMenuToCacheable(String userAccountId);

    /**
     * 刷新缓存 用户 可访问的[菜单定义] Tree
     * @param userAccountId
     * @return
     */
    List<CommonMenuTree> queryUserVisitAbleMenuToCachePut(String userAccountId);


    /**
     * 查询 所有[可用状态]的 [菜单定义]
     * @return
     */
    List<EmDefineMenuEntity> getAllEnableList();

    /**
     * [菜单展示]的子节点 构建的树结构
     * @param rootId
     * @param allMenus
     * @return
     */
    List<CommonMenuTree> getMenuTreeChildNodes(String rootId, List<EmDefineMenuEntity> allMenus);

    /**
     * [菜单展示]的子节点 构建的TreeSelect结构
     * @param rootId   最顶层的菜单id
     * @param allMenus
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodes(String rootId, List<EmDefineMenuEntity> allMenus);

    /**
     * [菜单展示]的子节点 构建的TreeSelect结构(包含最顶层)
     * @param rootId   最顶层的菜单id
     * @param allMenus
     * @return
     */
    List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(String rootId, List<EmDefineMenuEntity> allMenus);


    /**
     * 分页查询 菜单定义 列表
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPage     查询分页配置
     * @return
     */
    WebResult dealQueryPageByEntitys(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean queryPage);

    /**
     * 分页查询 菜单定义 dto列表
     * (查询的 Dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmDefineMenuDto> queryPageBean);

    /**
     * 菜单定义-新增
     * @param loginUserInfo  当前登录用户
     * @param emDefineMenuVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefineMenuVo emDefineMenuVo) throws Exception;

    /**
     * 菜单定义-更新
     * @param loginUserInfo  当前登录用户
     * @param emDefineMenuVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmDefineMenuVo emDefineMenuVo) throws Exception;

    /**
     * 验证 数据库 中的唯一冲突
     * @param loginUserInfo     当前登录用户
     * @param emDefineMenuVo
     * @param defineMenuWrapper
     * @return
     */
    MyVerifyDuplicateBean dealCheckDuplicateKey(CurrentLoginEmUserInfo loginUserInfo, EmDefineMenuVo emDefineMenuVo, QueryWrapper<EmDefineMenuEntity> defineMenuWrapper);

    /**
     * 查询菜单(过滤指定节点下的所有节点
     * @param filterId
     * @param onlyEnable 是否只查询 状态为 可用 的数据
     * @return
     */
    List<EmDefineMenuEntity> getMenusFilterChildrens(String filterId, boolean onlyEnable);

    /**
     * 取得角色拥有的所有[菜单]集合(忽略 有子节点的菜单节点)
     * @param roleId
     * @param state
     * @return
     */
    List<EmDefineMenuEntity> findAllMenuByRoleIdFilterParentNode(String roleId, Short state);

    /**
     * 取得角色拥有的所有[菜单]集合
     * @param roleId
     * @param state
     * @return
     */
    List<EmDefineMenuEntity> findAllMenuByRoleId(String roleId, Short state);
}
