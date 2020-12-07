package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.define.basic.EmDefineMenuService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonMenuTree;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelect;
import com.egg.manager.persistence.commons.base.beans.tree.common.CommonTreeSelectTranslate;
import com.egg.manager.persistence.commons.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineMenuEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineMenuDto;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineMenuVo;
import com.egg.manager.persistence.em.user.domain.constant.DefineMenuConstant;
import com.egg.manager.persistence.commons.base.constant.db.redis.RedisShiroKeyConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.em.define.domain.enums.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.persistence.em.user.domain.enums.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.commons.base.exception.MyDbException;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineMenuMapper;
import com.egg.manager.persistence.em.define.pojo.transfer.EmDefineMenuTransfer;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmUserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
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
@Service(interfaceClass = EmDefineMenuService.class)
public class EmDefineMenuServiceImpl extends MyBaseMysqlServiceImpl<EmDefineMenuMapper, EmDefineMenuEntity, EmDefineMenuVo> implements EmDefineMenuService {

    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private EmDefineMenuMapper emDefineMenuMapper;
    @Autowired
    private EmUserAccountMapper emUserAccountMapper;

    @Override
    public List<EmDefineMenuEntity> dealGetUserGrantedMenusByAccountId(String userAccountId) {
        if (StringUtils.isBlank(userAccountId)) {
            return new ArrayList<EmDefineMenuEntity>();
        }
        EmUserAccountEntity emUserAccountEntity = emUserAccountMapper.selectById(userAccountId);
        if (UserAccountBaseTypeEnum.SuperRoot.getValue().equals(emUserAccountEntity.getUserType())) {
            //如果是[超级管理员]的话可以访问全部菜单
            return getAllEnableList();
        } else {
            return emDefineMenuMapper.getUserGrantedMenusByAccountId(userAccountId);
        }
    }


    @Override
    @Cacheable(value = RedisShiroKeyConstant.KEY_USER_FRONT_ROUTER_URL, key = "#userAccountId", condition = "#userAccountId!=null")
    public Set<String> dealGetUserVisitAbleUrl(String userAccountId) {
        Set<String> urlSets = new HashSet<>();
        List<EmDefineMenuEntity> emDefineMenuEntityList = this.dealGetUserGrantedMenusByAccountId(userAccountId);
        if (CollectionUtil.isEmpty(emDefineMenuEntityList)) {
            return urlSets;
        }
        for (EmDefineMenuEntity emDefineMenuEntity : emDefineMenuEntityList) {
            if (emDefineMenuEntity == null) {
                continue;
            }
            //内部router跳转
            if (DefineMenuUrlJumpTypeEnum.RouterUrlJump.getValue().equals(emDefineMenuEntity.getUrlJumpType())) {
                if (StringUtils.isNotBlank(emDefineMenuEntity.getRouterUrl())) {
                    urlSets.add(emDefineMenuEntity.getRouterUrl());
                }
            }
        }
        return urlSets;
    }


    @Override
    @Cacheable(value = RedisShiroKeyConstant.KEY_USER_FRONT_MENUS, key = "#userAccountId", condition = "#userAccountId!=null")
    public List<CommonMenuTree> queryDbToCacheable(String userAccountId) {
        List<EmDefineMenuEntity> allMenus = this.dealGetUserGrantedMenusByAccountId(userAccountId);
        List<CommonMenuTree> treeList = this.getMenuTreeChildNodes(DefineMenuConstant.ROOT_ID, allMenus);
        return treeList != null ? treeList : new ArrayList<CommonMenuTree>();
    }

    @Override
    public List<EmDefineMenuEntity> getAllEnableList() {
        QueryWrapper<EmDefineMenuEntity> queryWrapper = new QueryWrapper<EmDefineMenuEntity>();
        //筛选与排序
        queryWrapper.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        queryWrapper.orderBy(true, true, "level");
        queryWrapper.orderBy(true, true, "order_num");
        queryWrapper.orderBy(true, true, FieldConst.COL_CREATE_TIME);
        return emDefineMenuMapper.selectList(queryWrapper);
    }


    @Override
    public List<CommonMenuTree> getMenuTreeChildNodes(String rootId, List<EmDefineMenuEntity> allMenus) {
        if (allMenus == null || allMenus.size() == 0) {
            return null;
        }
        List<CommonMenuTree> childList = new ArrayList<CommonMenuTree>();
        CommonMenuTree tree = null;
        for (EmDefineMenuEntity menu : allMenus) {
            if (StringUtils.isNotBlank(menu.getPid())) {
                if (rootId != null) {
                    if (rootId.equals(menu.getPid())) {
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
    public List<CommonTreeSelect> getTreeSelectChildNodes(String rootId, List<EmDefineMenuEntity> allMenus) {
        if (allMenus == null || allMenus.size() == 0) {
            return null;
        }
        //添加最底层的根节点
        List<CommonTreeSelect> childList = new ArrayList<CommonTreeSelect>();
        CommonTreeSelect tree = null;
        for (EmDefineMenuEntity menu : allMenus) {
            if (StringUtils.isNotBlank(menu.getPid())) {
                if (rootId != null) {
                    if (rootId.equals(menu.getPid())) {
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
    public List<CommonTreeSelect> getTreeSelectChildNodesWithRoot(String rootId, List<EmDefineMenuEntity> allMenus) {
        List<CommonTreeSelect> childList = this.getTreeSelectChildNodes(rootId, allMenus);
        CommonTreeSelect rootItem = CommonTreeSelect.builder().key(DefineMenuConstant.ROOT_ID).title("菜单首层项").value(DefineMenuConstant.ROOT_ID).children(childList).build();
        List<CommonTreeSelect> treeSelectListWithRoot = new ArrayList<CommonTreeSelect>();
        treeSelectListWithRoot.add(rootItem);
        return treeSelectListWithRoot;
    }


    @Override
    public WebResult dealQueryPageByEntitys(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean queryPage) {
        //解析 搜索条件
        QueryWrapper<EmDefineMenuEntity> queryWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryPage);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(queryPage.getPageConf());
        //取得 总数
        Integer total = emDefineMenuMapper.selectCount(queryWrapper);
        result.settingPage(queryPage.getPageConf(), Long.valueOf(total));
        IPage iPage = emDefineMenuMapper.selectPage(page, queryWrapper);
        List<EmDefineMenuEntity> defineMenuEntities = iPage.getRecords();
        result.putGridList(EmDefineMenuTransfer.transferEntityToVoList(defineMenuEntities));
        return result;
    }


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmDefineMenuDto> queryPageBean) {
        Page<EmDefineMenuDto> mpPagination = queryPageBean.toMpPage();
        List<EmDefineMenuDto> emDefineMenuDtoList = emDefineMenuMapper.selectQueryPage(mpPagination, queryPageBean.getQuery(), queryPageBean.getSortMap());
        result.settingPage(queryPageBean.getPageConf(), mpPagination.getTotal());
        result.putGridList(EmDefineMenuTransfer.transferDtoToVoList(emDefineMenuDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefineMenuVo emDefineMenuVo) throws Exception {
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(loginUserInfo, emDefineMenuVo, new QueryWrapper<EmDefineMenuEntity>());
        if (verifyDuplicateBean.isSuccessFlag() == false) {
            //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Date now = new Date();
        EmDefineMenuEntity emDefineMenuEntity = EmDefineMenuTransfer.transferVoToEntity(emDefineMenuVo);
        emDefineMenuEntity = super.doBeforeCreate(loginUserInfo, emDefineMenuEntity);
        String pid = emDefineMenuEntity.getPid();
        //
        if (StringUtils.isBlank(pid)) {
            emDefineMenuEntity.setPid(DefineMenuConstant.ROOT_ID);
            emDefineMenuEntity.setLevel(DefineMenuConstant.ROOT_LEVEL);
        } else if (DefineMenuConstant.ROOT_ID.equals(pid)) {
            //如果上级是 根级菜单
            emDefineMenuEntity.setPid(DefineMenuConstant.ROOT_ID);
            emDefineMenuEntity.setLevel(DefineMenuConstant.ROOT_LEVEL);
        } else {
            EmDefineMenuEntity parentMenu = emDefineMenuMapper.selectById(pid);
            Integer parentMenuLevel = null;
            if (parentMenu != null) {
                parentMenuLevel = parentMenu.getLevel();
            }
            if (parentMenuLevel != null) {
                emDefineMenuEntity.setLevel(parentMenuLevel + 1);
            } else {
                emDefineMenuEntity.setLevel(parentMenuLevel);
            }
        }
        return emDefineMenuMapper.insert(emDefineMenuEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmDefineMenuVo emDefineMenuVo) throws Exception {
        QueryWrapper<EmDefineMenuEntity> uniWrapper = new QueryWrapper<EmDefineMenuEntity>()
                .ne(FieldConst.COL_FID, emDefineMenuVo.getFid());
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(loginUserInfo, emDefineMenuVo, uniWrapper);
        if (verifyDuplicateBean.isSuccessFlag() == false) {
            //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Integer changeCount = 0;
        EmDefineMenuEntity emDefineMenuEntity = EmDefineMenuTransfer.transferVoToEntity(emDefineMenuVo);
        emDefineMenuEntity = super.doBeforeUpdate(loginUserInfo, emDefineMenuEntity);
        String pid = emDefineMenuEntity.getPid();
        if (StringUtils.isNotBlank(pid)) {
            EmDefineMenuEntity parentMenu = emDefineMenuMapper.selectById(pid);
            Integer parentMenuLevel = null;
            if (parentMenu != null) {
                parentMenuLevel = parentMenu.getLevel();
            }
            if (parentMenuLevel != null) {
                emDefineMenuEntity.setLevel(parentMenuLevel + 1);
            } else {
                emDefineMenuEntity.setLevel(parentMenuLevel);
            }
        } else {
            emDefineMenuEntity.setPid(DefineMenuConstant.ROOT_ID);
            emDefineMenuEntity.setLevel(DefineMenuConstant.ROOT_LEVEL);
        }
        changeCount = emDefineMenuMapper.updateById(emDefineMenuEntity);
        return changeCount;
    }


    @Override
    public MyVerifyDuplicateBean dealCheckDuplicateKey(CurrentLoginEmUserInfo loginUserInfo, EmDefineMenuVo emDefineMenuVo, QueryWrapper<EmDefineMenuEntity> queryWrapper) {
        MyVerifyDuplicateBean verifyBean = new MyVerifyDuplicateBean();
        queryWrapper = queryWrapper != null ? queryWrapper : new QueryWrapper<>();
        queryWrapper.eq("router_url", emDefineMenuVo.getRouterUrl());
        queryWrapper.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        boolean successFlag = emDefineMenuMapper.selectCount(queryWrapper) == 0;
        if (successFlag == false) {
            verifyBean.setErrorMsg("唯一键[路由]不允许重复！");
            verifyBean.dealAddColumn("router_url");
            verifyBean.dealAddFieldName("路由Url");
        }
        verifyBean.setSuccessFlag(successFlag);
        return verifyBean;
    }


}
