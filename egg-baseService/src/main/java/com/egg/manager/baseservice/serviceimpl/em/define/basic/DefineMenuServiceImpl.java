package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.define.basic.DefineMenuService;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonMenuTree;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelectTranslate;
import com.egg.manager.persistence.commons.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.persistence.commons.base.constant.define.DefineMenuConstant;
import com.egg.manager.persistence.commons.base.constant.redis.RedisShiroKeyConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.module.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.persistence.commons.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.commons.base.exception.MyDbException;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.util.LongUtils;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenuEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineMenuMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineMenuDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineMenuTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineMenuVo;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = DefineMenuService.class)
public class DefineMenuServiceImpl extends MyBaseMysqlServiceImpl<DefineMenuMapper, DefineMenuEntity, DefineMenuVo> implements DefineMenuService {

    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private DefineMenuMapper defineMenuMapper;
    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public List<DefineMenuEntity> dealGetUserGrantedMenusByAccountId(Long userAccountId) {
        if (LongUtils.isBlank(userAccountId)) {
            return new ArrayList<DefineMenuEntity>();
        }
        UserAccountEntity userAccountEntity = userAccountMapper.selectById(userAccountId);
        if (UserAccountBaseTypeEnum.SuperRoot.getValue().equals(userAccountEntity.getUserTypeNum())) {
            //如果是[超级管理员]的话可以访问全部菜单
            return getAllEnableList();
        } else {
            return defineMenuMapper.getUserGrantedMenusByAccountId(userAccountId);
        }
    }



    @Override
    @Cacheable(value = RedisShiroKeyConstant.KEY_USER_FRONT_ROUTER_URL,key = "#userAccountId",condition = "#userAccountId!=null")
    public Set<String> dealGetUserVisitAbleUrl(Long userAccountId) {
        Set<String> urlSets = new HashSet<>();
        List<DefineMenuEntity> defineMenuEntityList = this.dealGetUserGrantedMenusByAccountId(userAccountId);
        if(CollectionUtil.isEmpty(defineMenuEntityList)){
            return urlSets ;
        }
        for (DefineMenuEntity defineMenuEntity : defineMenuEntityList) {
            if (defineMenuEntity == null) {
                continue;
            }
            //内部router跳转
            if (DefineMenuUrlJumpTypeEnum.RouterUrlJump.getValue().equals(defineMenuEntity.getUrlJumpType())) {
                if (StringUtils.isNotBlank(defineMenuEntity.getRouterUrl())) {
                    urlSets.add(defineMenuEntity.getRouterUrl());
                }
            }
        }
        return urlSets;
    }


    @Override
    @Cacheable(value = RedisShiroKeyConstant.KEY_USER_FRONT_MENUS,key = "#userAccountId",condition = "#userAccountId!=null")
    public List<CommonMenuTree> queryDbToCacheable(Long userAccountId) {
        List<DefineMenuEntity> allMenus = this.dealGetUserGrantedMenusByAccountId(userAccountId);
        List<CommonMenuTree> treeList = this.getMenuTreeChildNodes(DefineMenuConstant.ROOT_ID, allMenus);
        return treeList != null ? treeList : new ArrayList<CommonMenuTree>();
    }

    @Override
    public List<DefineMenuEntity> getAllEnableList() {
        QueryWrapper<DefineMenuEntity> queryWrapper = new QueryWrapper<DefineMenuEntity>();
        //筛选与排序
        queryWrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        queryWrapper.orderBy(true, true, "level");
        queryWrapper.orderBy(true, true, "order_num");
        queryWrapper.orderBy(true, true, "create_time");
        return defineMenuMapper.selectList(queryWrapper);
    }


    @Override
    public List<CommonMenuTree> getMenuTreeChildNodes(Long rootId, List<DefineMenuEntity> allMenus) {
        if (allMenus == null || allMenus.size() == 0) {
            return null;
        }
        List<CommonMenuTree> childList = new ArrayList<CommonMenuTree>();
        CommonMenuTree tree = null;
        for (DefineMenuEntity menu : allMenus) {
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
    public List<CommonTreeSelect> getTreeSelectChildNodes(Long rootId, List<DefineMenuEntity> allMenus) {
        if (allMenus == null || allMenus.size() == 0) {
            return null;
        }
        //添加最底层的根节点
        List<CommonTreeSelect> childList = new ArrayList<CommonTreeSelect>();
        CommonTreeSelect tree = null;
        for (DefineMenuEntity menu : allMenus) {
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
    public List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(Long rootId, List<DefineMenuEntity> allMenus) {
        List<CommonTreeSelect> childList = this.getTreeSelectChildNodes(rootId, allMenus);
        CommonTreeSelect rootItem = CommonTreeSelect.builder().key(DefineMenuConstant.ROOT_ID).title("菜单首层项").value(DefineMenuConstant.ROOT_ID).children(childList).build();
        List<CommonTreeSelect> treeSelectListWithRoot = new ArrayList<CommonTreeSelect>();
        treeSelectListWithRoot.add(rootItem);
        return treeSelectListWithRoot;
    }


    @Override
    public MyCommonResult<DefineMenuVo> dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, MyCommonResult<DefineMenuVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineMenuEntity> paginationBean,
                                                               List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<DefineMenuEntity> queryWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = defineMenuMapper.selectCount(queryWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = defineMenuMapper.selectPage(page, queryWrapper);
        List<DefineMenuEntity> defineMenuEntities = iPage.getRecords();
        result.setResultList(DefineMenuTransfer.transferEntityToVoList(defineMenuEntities));
        return result;
    }


    @Override
    public MyCommonResult<DefineMenuVo> dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, MyCommonResult<DefineMenuVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineMenuDto> paginationBean,
                                                            List<AntdvSortBean> sortBeans) {
        Page<DefineMenuDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineMenuDto> defineMenuDtoList = defineMenuMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(DefineMenuTransfer.transferDtoToVoList(defineMenuDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, DefineMenuVo defineMenuVo) throws Exception {
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(loginUserInfo, defineMenuVo, new QueryWrapper<DefineMenuEntity>());
        if (verifyDuplicateBean.isSuccessFlag() == false) {
            //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Date now = new Date();
        DefineMenuEntity defineMenuEntity = DefineMenuTransfer.transferVoToEntity(defineMenuVo);
        defineMenuEntity = super.doBeforeCreate(loginUserInfo, defineMenuEntity, true);
        Long parentId = defineMenuEntity.getParentId();
        //
        if (LongUtils.isBlank(parentId)) {
            defineMenuEntity.setParentId(DefineMenuConstant.ROOT_ID);
            defineMenuEntity.setLevel(DefineMenuConstant.ROOT_LEVEL);
        } else if (DefineMenuConstant.ROOT_ID.equals(parentId)) {
            //如果上级是 根级菜单
            defineMenuEntity.setParentId(DefineMenuConstant.ROOT_ID);
            defineMenuEntity.setLevel(DefineMenuConstant.ROOT_LEVEL);
        } else {
            DefineMenuEntity parentMenu = defineMenuMapper.selectById(parentId);
            Integer parentMenuLevel = null;
            if (parentMenu != null) {
                parentMenuLevel = parentMenu.getLevel();
            }
            if (parentMenuLevel != null) {
                defineMenuEntity.setLevel(parentMenuLevel + 1);
            } else {
                defineMenuEntity.setLevel(parentMenuLevel);
            }
        }
        return defineMenuMapper.insert(defineMenuEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, DefineMenuVo defineMenuVo) throws Exception {
        QueryWrapper<DefineMenuEntity> uniWrapper = new QueryWrapper<DefineMenuEntity>()
                .ne("fid", defineMenuVo.getFid());
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(loginUserInfo, defineMenuVo, uniWrapper);
        if (verifyDuplicateBean.isSuccessFlag() == false) {
            //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Integer changeCount = 0;
        DefineMenuEntity defineMenuEntity = DefineMenuTransfer.transferVoToEntity(defineMenuVo);
        defineMenuEntity = super.doBeforeUpdate(loginUserInfo, defineMenuEntity);
        Long parentId = defineMenuEntity.getParentId();
        if (LongUtils.isNotBlank(parentId)) {
            DefineMenuEntity parentMenu = defineMenuMapper.selectById(parentId);
            Integer parentMenuLevel = null;
            if (parentMenu != null) {
                parentMenuLevel = parentMenu.getLevel();
            }
            if (parentMenuLevel != null) {
                defineMenuEntity.setLevel(parentMenuLevel + 1);
            } else {
                defineMenuEntity.setLevel(parentMenuLevel);
            }
        } else {
            defineMenuEntity.setParentId(DefineMenuConstant.ROOT_ID);
            defineMenuEntity.setLevel(DefineMenuConstant.ROOT_LEVEL);
        }
        changeCount = defineMenuMapper.updateById(defineMenuEntity);
        return changeCount;
    }


    @Override
    public MyVerifyDuplicateBean dealCheckDuplicateKey(CurrentLoginUserInfo loginUserInfo, DefineMenuVo defineMenuVo, QueryWrapper<DefineMenuEntity> queryWrapper) {
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
