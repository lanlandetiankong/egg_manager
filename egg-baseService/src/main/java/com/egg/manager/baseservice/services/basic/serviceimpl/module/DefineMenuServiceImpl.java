package com.egg.manager.baseservice.services.basic.serviceimpl.module;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.module.DefineMenuService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.common.base.constant.define.DefineMenuConstant;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.module.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.base.exception.MyDbException;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.util.LongUtils;
import com.egg.manager.persistence.commons.bean.helper.MyCommonResult;
import com.egg.manager.persistence.commons.bean.tree.common.CommonMenuTree;
import com.egg.manager.persistence.commons.bean.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.commons.bean.tree.common.CommonTreeSelectTranslate;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenu;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineMenuMapper;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserAccountMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineMenuDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineMenuTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineMenuVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = DefineMenuService.class)
public class DefineMenuServiceImpl extends MyBaseMysqlServiceImpl<DefineMenuMapper, DefineMenu, DefineMenuVo> implements DefineMenuService {

    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private DefineMenuMapper defineMenuMapper;
    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public List<DefineMenu> dealGetUserGrantedMenusByAccountId(Long userAccountId) {
        if (LongUtils.isBlank(userAccountId)) {
            return new ArrayList<DefineMenu>();
        }
        UserAccount userAccount = userAccountMapper.selectById(userAccountId);
        if (UserAccountBaseTypeEnum.SuperRoot.getValue().equals(userAccount.getUserTypeNum())) {
            //如果是[超级管理员]的话可以访问全部菜单
            return getAllEnableList();
        } else {
            return defineMenuMapper.getUserGrantedMenusByAccountId(userAccountId);
        }
    }


    @Override
    public Set<String> dealGetUserVisitAbleUrl(Long userAccountId) {
        Set<String> urlSets = new HashSet<>();
        List<DefineMenu> defineMenuList = this.dealGetUserGrantedMenusByAccountId(userAccountId);
        if (defineMenuList != null && defineMenuList.isEmpty() == false) {
            for (DefineMenu defineMenu : defineMenuList) {
                if (defineMenu != null) {
                    if (DefineMenuUrlJumpTypeEnum.RouterUrlJump.getValue().equals(defineMenu.getUrlJumpType())) {
                        //内部router跳转
                        if (StringUtils.isNotBlank(defineMenu.getRouterUrl())) {
                            urlSets.add(defineMenu.getRouterUrl());
                        }
                    }
                }
            }
        }
        return urlSets;
    }

    @Override
    public List<CommonMenuTree> dealGetUserGrantedMenuTrees(Long userAccountId) {
        List<DefineMenu> allMenus = this.dealGetUserGrantedMenusByAccountId(userAccountId);
        List<CommonMenuTree> treeList = this.getMenuTreeChildNodes(DefineMenuConstant.ROOT_ID, allMenus);
        return treeList != null ? treeList : new ArrayList<CommonMenuTree>();
    }

    @Override
    public List<DefineMenu> getAllEnableList() {
        QueryWrapper<DefineMenu> queryWrapper = new QueryWrapper<DefineMenu>();
        //筛选与排序
        queryWrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        queryWrapper.orderBy(true, true, "level");
        queryWrapper.orderBy(true, true, "order_num");
        queryWrapper.orderBy(true, true, "create_time");
        return defineMenuMapper.selectList(queryWrapper);
    }


    @Override
    public List<CommonMenuTree> getMenuTreeChildNodes(Long rootId, List<DefineMenu> allMenus) {
        if (allMenus == null || allMenus.size() == 0) {
            return null;
        }
        List<CommonMenuTree> childList = new ArrayList<CommonMenuTree>();
        CommonMenuTree tree = null;
        for (DefineMenu menu : allMenus) {
            if (LongUtils.isNotBlank(menu.getParentId())) {
                if (rootId != null) {
                    if (rootId.equals(menu.getParentId())) {
                        tree = new CommonMenuTree();
                        childList.add(CommonMenuTree.dealDefineMenuToTree(menu, tree));
                    }
                }
            }
        }
        if (childList.size() == 0) {
            return null;
        }
        for (CommonMenuTree treeItem : childList) {
            treeItem.setChildren(this.getMenuTreeChildNodes(treeItem.getId(), allMenus));
        }
        return childList;
    }


    @Override
    public List<CommonTreeSelect> getTreeSelectChildNodes(Long rootId, List<DefineMenu> allMenus) {
        if (allMenus == null || allMenus.size() == 0) {
            return null;
        }
        //添加最底层的根节点
        List<CommonTreeSelect> childList = new ArrayList<CommonTreeSelect>();
        CommonTreeSelect tree = null;
        for (DefineMenu menu : allMenus) {
            if (LongUtils.isNotBlank(menu.getParentId())) {
                if (rootId != null) {
                    if (rootId.equals(menu.getParentId())) {
                        tree = new CommonTreeSelect();
                        childList.add(CommonTreeSelectTranslate.setDefineMenuParamToTreeSelect(menu, tree));
                    }
                }
            }
        }
        if (childList.size() == 0) {
            return null;
        }
        for (CommonTreeSelect treeItem : childList) {
            treeItem.setChildren(this.getTreeSelectChildNodes(treeItem.getKey(), allMenus));
        }
        return childList;
    }


    @Override
    public List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(Long rootId, List<DefineMenu> allMenus) {
        List<CommonTreeSelect> childList = this.getTreeSelectChildNodes(rootId, allMenus);
        CommonTreeSelect rootItem = CommonTreeSelect.builder().key(DefineMenuConstant.ROOT_ID).title("菜单首层项").value(DefineMenuConstant.ROOT_ID).children(childList).build();
        List<CommonTreeSelect> treeSelectListWithRoot = new ArrayList<CommonTreeSelect>();
        treeSelectListWithRoot.add(rootItem);
        return treeSelectListWithRoot;
    }


    @Override
    public MyCommonResult<DefineMenuVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<DefineMenuVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineMenu> paginationBean,
                                                               List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<DefineMenu> queryWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = defineMenuMapper.selectCount(queryWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = defineMenuMapper.selectPage(page, queryWrapper);
        List<DefineMenu> defineMenus = iPage.getRecords();
        result.setResultList(DefineMenuTransfer.transferEntityToVoList(defineMenus));
        return result;
    }


    @Override
    public MyCommonResult<DefineMenuVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<DefineMenuVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineMenuDto> paginationBean,
                                                            List<AntdvSortBean> sortBeans) {
        Page<DefineMenuDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineMenuDto> defineMenuDtoList = defineMenuMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(DefineMenuTransfer.transferDtoToVoList(defineMenuDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(UserAccount loginUser, DefineMenuVo defineMenuVo) throws Exception {
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(loginUser, defineMenuVo, new QueryWrapper<DefineMenu>());
        if (verifyDuplicateBean.isSuccessFlag() == false) {
            //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Date now = new Date();
        DefineMenu defineMenu = DefineMenuTransfer.transferVoToEntity(defineMenuVo);
        defineMenu = super.doBeforeCreate(loginUser, defineMenu, true);
        Long parentId = defineMenu.getParentId();
        //
        if (LongUtils.isBlank(parentId)) {
            defineMenu.setParentId(DefineMenuConstant.ROOT_ID);
            defineMenu.setLevel(DefineMenuConstant.ROOT_LEVEL);
        } else if (DefineMenuConstant.ROOT_ID.equals(parentId)) {
            //如果上级是 根级菜单
            defineMenu.setParentId(DefineMenuConstant.ROOT_ID);
            defineMenu.setLevel(DefineMenuConstant.ROOT_LEVEL);
        } else {
            DefineMenu parentMenu = defineMenuMapper.selectById(parentId);
            Integer parentMenuLevel = null;
            if (parentMenu != null) {
                parentMenuLevel = parentMenu.getLevel();
            }
            if (parentMenuLevel != null) {
                defineMenu.setLevel(parentMenuLevel + 1);
            } else {
                defineMenu.setLevel(parentMenuLevel);
            }
        }
        return defineMenuMapper.insert(defineMenu);
    }


    @Override
    public Integer dealUpdate(UserAccount loginUser, DefineMenuVo defineMenuVo) throws Exception {
        QueryWrapper<DefineMenu> uniWrapper = new QueryWrapper<DefineMenu>()
                .ne("fid", defineMenuVo.getFid());
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(loginUser, defineMenuVo, uniWrapper);
        if (verifyDuplicateBean.isSuccessFlag() == false) {
            //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Integer changeCount = 0;
        DefineMenu defineMenu = DefineMenuTransfer.transferVoToEntity(defineMenuVo);
        defineMenu = super.doBeforeUpdate(loginUser, defineMenu);
        Long parentId = defineMenu.getParentId();
        if (LongUtils.isNotBlank(parentId)) {
            DefineMenu parentMenu = defineMenuMapper.selectById(parentId);
            Integer parentMenuLevel = null;
            if (parentMenu != null) {
                parentMenuLevel = parentMenu.getLevel();
            }
            if (parentMenuLevel != null) {
                defineMenu.setLevel(parentMenuLevel + 1);
            } else {
                defineMenu.setLevel(parentMenuLevel);
            }
        } else {
            defineMenu.setParentId(DefineMenuConstant.ROOT_ID);
            defineMenu.setLevel(DefineMenuConstant.ROOT_LEVEL);
        }
        changeCount = defineMenuMapper.updateById(defineMenu);
        return changeCount;
    }


    @Override
    public MyVerifyDuplicateBean dealCheckDuplicateKey(UserAccount loginUser, DefineMenuVo defineMenuVo, QueryWrapper<DefineMenu> queryWrapper) {
        MyVerifyDuplicateBean verifyBean = new MyVerifyDuplicateBean();
        queryWrapper = queryWrapper != null ? queryWrapper : new QueryWrapper<>();
        queryWrapper.eq("router_url", defineMenuVo.getRouterUrl());
        queryWrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        boolean successFlag = defineMenuMapper.selectCount(queryWrapper) == 0;
        if (successFlag == false) {
            verifyBean.setErrorMsg("唯一键[路由]不允许重复！");
            verifyBean.dealAddColumn("router_url");
            verifyBean.dealAddFieldName("路由Url");
        }
        verifyBean.setSuccessFlag(successFlag);
        return verifyBean;
    }


}
