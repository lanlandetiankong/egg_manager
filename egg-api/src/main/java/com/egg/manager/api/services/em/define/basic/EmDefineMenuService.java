package com.egg.manager.api.services.em.define.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonMenuTree;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.commons.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineMenuEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineMenuMapper;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineMenuDto;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineMenuVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;

import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmDefineMenuService extends IService<EmDefineMenuEntity>, MyBaseMysqlService<EmDefineMenuEntity, EmDefineMenuMapper, EmDefineMenuVo> {

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
    Set<String> dealGetUserVisitAbleUrl(String userAccountId);

    /**
     * 查询 用户 可访问的[菜单定义] Tree
     * @param userAccountId
     * @return
     */
    List<CommonMenuTree> queryDbToCacheable(String userAccountId);

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
     * (查询的是 com.egg.manager.persistence.obl.article.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmDefineMenuDto> queryPageBean);

    /**
     * 菜单定义-新增
     * @param loginUserInfo 当前登录用户
     * @param emDefineMenuVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefineMenuVo emDefineMenuVo) throws Exception;

    /**
     * 菜单定义-更新
     * @param loginUserInfo 当前登录用户
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
}
