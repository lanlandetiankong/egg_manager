package com.egg.manager.service.serviceimpl.module;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.constant.define.DefineMenuConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.module.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.service.helper.MyCommonResult;
import com.egg.manager.persistence.tree.CommonMenuTree;
import com.egg.manager.persistence.tree.CommonTreeSelect;
import com.egg.manager.persistence.tree.CommonTreeSelectTranslate;
import com.egg.manager.persistence.dto.define.DefineMenuDto;
import com.egg.manager.persistence.entity.define.DefineMenu;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.mapper.define.DefineMenuMapper;
import com.egg.manager.service.service.CommonFuncService;
import com.egg.manager.service.service.module.DefineMenuService;
import com.egg.manager.persistence.vo.define.DefineMenuVo;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service
public class DefineMenuServiceImpl extends ServiceImpl<DefineMenuMapper,DefineMenu> implements DefineMenuService {

    @Autowired
    private DefineMenuMapper defineMenuMapper ;
    @Autowired
    private CommonFuncService commonFuncService ;

    /**
     * 查询 用户 可访问的[菜单定义]
     *
     * @param userAccountId
     * @return
     */
    @Override
    public List<DefineMenu> dealGetUserGrantedMenusByAccountId(String userAccountId) {
        if(StringUtils.isBlank(userAccountId)){
            return new ArrayList<DefineMenu>() ;
        }
        return defineMenuMapper.getUserGrantedMenusByAccountId(userAccountId);
    }

    /**
     * 查询 用户 可访问的 菜单路径
     *
     * @param userAccountId
     * @return
     */
    @Override
    public Set<String> dealGetUserVisitAbleUrl(String userAccountId) {
        Set<String> urlSets = new HashSet<>();
        List<DefineMenu> defineMenuList = this.dealGetUserGrantedMenusByAccountId(userAccountId);
        if(defineMenuList != null && defineMenuList.isEmpty() == false){
            for(DefineMenu defineMenu : defineMenuList){
                if(defineMenu != null){
                    if(DefineMenuUrlJumpTypeEnum.RouterUrlJump.getValue().equals(defineMenu.getUrlJumpType())){ //内部router跳转
                        if(StringUtils.isNotBlank(defineMenu.getRouterUrl())){
                            urlSets.add(defineMenu.getRouterUrl());
                        }
                    }
                }
            }
        }
        return urlSets;
    }

    /**
     * 查询 所有[可用状态]的 [菜单定义]
     * @param defineMenuEntityWrapper
     * @return
     */
    @Override
    public List<DefineMenu> getAllEnableDefineMenus(EntityWrapper<DefineMenu> defineMenuEntityWrapper){
        defineMenuEntityWrapper = defineMenuEntityWrapper != null ? defineMenuEntityWrapper : new EntityWrapper<DefineMenu>();
        //筛选与排序
        defineMenuEntityWrapper.eq("state",BaseStateEnum.ENABLED.getValue());
        defineMenuEntityWrapper.orderBy("level",true);
        defineMenuEntityWrapper.orderBy("order_num",true);
        defineMenuEntityWrapper.orderBy("create_time",false);
        return this.selectList(defineMenuEntityWrapper);
    }

    /**
     * [菜单展示]的子节点 构建的树结构
     * @param rootId
     * @param allMenus
     * @return
     */
    @Override
    public List<CommonMenuTree> getMenuTreeChildNodes(String rootId, List<DefineMenu> allMenus) {
        if(allMenus == null || allMenus.size() == 0){
            return null ;
        }
        List<CommonMenuTree> childList = new ArrayList<CommonMenuTree>() ;
        CommonMenuTree tree = null ;
        for (DefineMenu menu : allMenus) {
            if(StringUtils.isNotBlank(menu.getParentId())){
                if(rootId != null){
                    if(rootId.equals(menu.getParentId())){
                        tree = new CommonMenuTree() ;
                        childList.add(setDefineMenuParamToTree(menu,tree)) ;
                    }
                }
            }
        }
        if(childList.size() == 0) {
            return null ;
        }
        for (CommonMenuTree treeItem : childList) {
            treeItem.setChildren(this.getMenuTreeChildNodes(treeItem.getId(),allMenus));
        }
        return childList ;
    }

    /**
     * 菜单树->entity转CommonMenuTree
     * @param menu
     * @param tree
     * @return
     */
    private CommonMenuTree setDefineMenuParamToTree(DefineMenu menu, CommonMenuTree tree) {
        tree.setId(menu.getFid());
        tree.setPid(menu.getParentId());
        tree.setName(menu.getMenuName());
        tree.setIconName(menu.getIconName());
        tree.setLabel(menu.getLabel());
        tree.setKey(menu.getFid());
        tree.setUrlJumpType(menu.getUrlJumpType());
        tree.setRouterUrl(menu.getRouterUrl());
        tree.setHrefUrl(menu.getHrefUrl());
        return tree;
    }



    /**
     * [菜单展示]的子节点 构建的 TreeSelect 结构
     * 用于 a-tree-select
     * @param rootId
     * @param allMenus
     * @return
     */
    @Override
    public List<CommonTreeSelect> getTreeSelectChildNodes(String rootId,List<DefineMenu> allMenus) {
        if(allMenus == null || allMenus.size() == 0){
            return null ;
        }
        //添加最底层的根节点
        List<CommonTreeSelect> childList = new ArrayList<CommonTreeSelect>() ;
        CommonTreeSelect tree = null ;
        for (DefineMenu menu : allMenus) {
            if(StringUtils.isNotBlank(menu.getParentId())){
                if(rootId != null){
                    if(rootId.equals(menu.getParentId())){
                        tree = new CommonTreeSelect() ;
                        childList.add(CommonTreeSelectTranslate.setDefineMenuParamToTreeSelect(menu,tree)) ;
                    }
                }
            }
        }
        if(childList.size() == 0) {
            return null ;
        }
        for (CommonTreeSelect treeItem : childList) {
            treeItem.setChildren(this.getTreeSelectChildNodes(treeItem.getKey(),allMenus));
        }
        return childList ;
    }
    /**
     * [菜单展示]的子节点 构建的 TreeSelect 结构(包含最顶层)
     * 用于 a-tree-select
     * @param rootId
     * @param allMenus
     * @return
     */
    @Override
    public List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(String rootId,List<DefineMenu> allMenus) {
        List<CommonTreeSelect> childList = this.getTreeSelectChildNodes(rootId,allMenus);
        CommonTreeSelect rootItem = CommonTreeSelect.builder().key(DefineMenuConstant.ROOT_ID).title("菜单首层项").value(DefineMenuConstant.ROOT_ID).children(childList).build();
        List<CommonTreeSelect> treeSelectListWithRoot = new ArrayList<CommonTreeSelect>() ;
        treeSelectListWithRoot.add(rootItem);
        return treeSelectListWithRoot ;
    }




    /**
     * 分页查询 模块
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetDefineMenuPages(MyCommonResult<DefineMenuVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                       List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        EntityWrapper<DefineMenu> defineMenuEntityWrapper = new EntityWrapper<DefineMenu>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到 defineMenuEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(defineMenuEntityWrapper,queryFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                defineMenuEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        //取得 总数
        Integer total = defineMenuMapper.selectCount(defineMenuEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<DefineMenu> defineMenus = defineMenuMapper.selectPage(rowBounds,defineMenuEntityWrapper) ;
        result.setResultList(DefineMenuVo.transferEntityToVoList(defineMenus));
    }


    /**
     * 分页查询 菜单定义
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetDefineMenuDtoPages(MyCommonResult<DefineMenuVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                       List<AntdvSortBean> sortBeans) {
        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<DefineMenuDto> defineMenuDtoList = defineMenuMapper.selectQueryPage(mpPagination, queryFieldBeanList,sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(DefineMenuVo.transferDtoToVoList(defineMenuDtoList));
    }




    /**
     * 模块定义-新增
     * @param defineMenuVo
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealAddDefineMenu(DefineMenuVo defineMenuVo,UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        DefineMenu defineMenu = DefineMenuVo.transferVoToEntity(defineMenuVo);
        String parentId = defineMenu.getParentId() ;
        if(StringUtils.isNotBlank(parentId)){
            DefineMenu parentMenu =defineMenuMapper.selectById(parentId);
            Integer parentMenuLevel = null ;
            if(parentMenu != null){
                parentMenuLevel = parentMenu.getLevel();
            }
            if(parentMenuLevel != null){
                defineMenu.setLevel(parentMenuLevel+1);
            }   else {
                defineMenu.setLevel(parentMenuLevel);
            }
        }   else {
            defineMenu.setParentId(DefineMenuConstant.ROOT_ID);
            defineMenu.setLevel(DefineMenuConstant.ROOT_LEVEL);
        }
        defineMenu.setFid(MyUUIDUtil.renderSimpleUUID());
        defineMenu.setState(BaseStateEnum.ENABLED.getValue());
        defineMenu.setCreateTime(now);
        defineMenu.setUpdateTime(now);
        if(loginUser != null){
            defineMenu.setCreateUserId(loginUser.getFid());
            defineMenu.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = defineMenuMapper.insert(defineMenu) ;
        return addCount ;
    }




    /**
     * 模块定义-更新
     * @param defineMenuVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealUpdateDefineMenu(DefineMenuVo defineMenuVo,UserAccount loginUser,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        defineMenuVo.setUpdateTime(now);
        DefineMenu defineMenu = DefineMenuVo.transferVoToEntity(defineMenuVo);
        String parentId = defineMenu.getParentId() ;
        if(StringUtils.isNotBlank(parentId)){
            DefineMenu parentMenu =defineMenuMapper.selectById(parentId);
            Integer parentMenuLevel = null ;
            if(parentMenu != null){
                parentMenuLevel = parentMenu.getLevel();
            }
            if(parentMenuLevel != null){
                defineMenu.setLevel(parentMenuLevel+1);
            }   else {
                defineMenu.setLevel(parentMenuLevel);
            }
        }   else {
            defineMenu.setParentId(DefineMenuConstant.ROOT_ID);
            defineMenu.setLevel(DefineMenuConstant.ROOT_LEVEL);
        }
        if(loginUser != null){
            defineMenu.setLastModifyerId(loginUser.getFid());
        }
        if(updateAll){  //是否更新所有字段
            changeCount = defineMenuMapper.updateAllColumnById(defineMenu) ;
        }   else {
            changeCount = defineMenuMapper.updateById(defineMenu) ;
        }
        return changeCount ;
    }

    /**
     * 模块定义-删除
     * @param delIds 要删除的模块id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelDefineMenuByArr(String[] delIds,UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = defineMenuMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 模块定义-删除
     * @param delId 要删除的模块id
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public Integer dealDelDefineMenu(String delId,UserAccount loginUser) throws Exception{
        DefineMenu defineMenu = DefineMenu.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            defineMenu.setLastModifyerId(loginUser.getFid());
        }
        Integer delCount = defineMenuMapper.updateById(defineMenu);
        return delCount ;
    }
}
