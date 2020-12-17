package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.helper.redis.RedisHelper;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.define.basic.EmDefineRoleService;
import com.egg.manager.api.services.em.user.basic.EmRolePermissionService;
import com.egg.manager.api.services.em.user.basic.EmUserRoleService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.constant.db.redis.RedisShiroKeyConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineRoleEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineRoleDto;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmRolePermissionEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserRoleEntity;
import com.egg.manager.persistence.em.user.domain.enums.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineMenuEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineMenuMapper;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefinePermissionMapper;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineRoleMapper;
import com.egg.manager.persistence.em.define.pojo.transfer.EmDefineRoleTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineRoleVo;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmRolePermissionMapper;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmUserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.em.user.pojo.initialize.EmRolePermissionPojoInitialize;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
@Service(interfaceClass = EmDefineRoleService.class)
public class EmDefineRoleServiceImpl extends MyBaseMysqlServiceImpl<EmDefineRoleMapper, EmDefineRoleEntity, EmDefineRoleVo> implements EmDefineRoleService {

    @Reference
    private RedisHelper redisHelper;
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private EmDefineRoleMapper emDefineRoleMapper;
    @Autowired
    private EmDefineMenuMapper emDefineMenuMapper;
    @Autowired
    private EmDefinePermissionMapper emDefinePermissionMapper;
    @Autowired
    private EmRolePermissionMapper emRolePermissionMapper;
    @Autowired
    private EmUserAccountMapper emUserAccountMapper;

    @Reference
    private EmUserRoleService emUserRoleService;
    @Reference
    private EmRolePermissionService emRolePermissionService;

    @Override
    public List<EmDefineRoleEntity> dealGetRolesByAccountFromDb(String userAccountId, Short stateVal) {
        if (StringUtils.isBlank(userAccountId)) {
            return null;
        }
        EmUserAccountEntity emUserAccountEntity = emUserAccountMapper.selectById(userAccountId);
        if (UserAccountBaseTypeEnum.SuperRoot.getValue().equals(emUserAccountEntity.getUserType())) {
            //如果是[超级管理员]的话可以访问全部菜单
            return queryAllEnableList(null);
        } else {
            return emDefineRoleMapper.findAllRoleByUserAcccountId(userAccountId, stateVal);
        }
    }


    @Override
    @Cacheable(value = RedisShiroKeyConstant.KEY_USER_ROLE, key = "#userAccountId", condition = "#userAccountId!=null")
    public Set<String> queryDbToCacheable(String userAccountId) {
        Set<String> codeSet = Sets.newHashSet();
        List<EmDefineRoleEntity> defineRoleEntities = this.dealGetRolesByAccountFromDb(userAccountId, BaseStateEnum.ENABLED.getValue());
        if (CollectionUtil.isNotEmpty(defineRoleEntities)) {
            for (EmDefineRoleEntity emDefineRoleEntity : defineRoleEntities) {
                String roleCode = emDefineRoleEntity.getCode();
                if (StringUtils.isNotBlank(roleCode)) {
                    codeSet.add(roleCode);
                }
            }
        }
        return codeSet;
    }


    @Override
    public List<EmDefineMenuEntity> dealGetMenusByRoleIdFromDb(String roleId, Short stateVal) {
        if (StringUtils.isBlank(roleId)) {
            return null;
        } else {
            return emDefineMenuMapper.findAllMenuByRoleId(roleId, stateVal);
        }
    }


    @Override
    public List<EmDefineRoleEntity> queryAllEnableList(QueryWrapper<EmDefineRoleEntity> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<EmDefineRoleEntity>();
        //筛选与排序
        wrapper.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        wrapper.orderBy(true, false, FieldConst.COL_CREATE_TIME);
        return emDefineRoleMapper.selectList(wrapper);
    }


    @Override
    public Set<String> dealGetMenuIdSetByRoleIdFromDb(String roleId, Short stateVal) {
        Set<String> idSet = Sets.newHashSet();
        List<EmDefineMenuEntity> emDefineMenuEntityList = this.dealGetMenusByRoleIdFromDb(roleId, stateVal);
        if (CollectionUtil.isNotEmpty(emDefineMenuEntityList)) {
            for (EmDefineMenuEntity emDefineMenuEntity : emDefineMenuEntityList) {
                String menuFid = emDefineMenuEntity.getFid();
                if (StringUtils.isNotBlank(menuFid)) {
                    idSet.add(menuFid);
                }
            }
        }
        return idSet;
    }


    @Override
    public List<EmDefineRoleEntity> dealGetListFormRedisByAccount(EmUserAccountEntity emUserAccountEntity) {
        List<EmUserRoleEntity> userRoleEntities = emUserRoleService.dealGetAllByAccount(emUserAccountEntity);
        List<EmDefineRoleEntity> defineRoleEntities = null;
        if (CollectionUtil.isEmpty(userRoleEntities)) {
            return defineRoleEntities;
        } else {
            Set<String> roleIds = new HashSet<String>();
            for (EmUserRoleEntity emUserRoleEntity : userRoleEntities) {
                if (StringUtils.isNotBlank(emUserRoleEntity.getDefineRoleId())) {
                    roleIds.add(emUserRoleEntity.getDefineRoleId());
                }
            }
            QueryWrapper<EmDefineRoleEntity> defineRoleEntityWrapper = new QueryWrapper<EmDefineRoleEntity>();
            defineRoleEntityWrapper.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue())
                    .in(true, "define_role_id", roleIds);
            defineRoleEntities = emDefineRoleMapper.selectList(defineRoleEntityWrapper);
        }
        return defineRoleEntities;
    }


    @Override
    public WebResult dealQueryPageByEntitys(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean queryPageBean) {
        //解析 搜索条件
        QueryWrapper<EmDefineRoleEntity> defineRoleEntityWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryPageBean);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(queryPageBean.getPageConf());
        //取得 总数
        Integer total = emDefineRoleMapper.selectCount(defineRoleEntityWrapper);
        result.settingPage(queryPageBean.getPageConf(), new Long(total));
        IPage iPage = emDefineRoleMapper.selectPage(page, defineRoleEntityWrapper);
        List<EmDefineRoleEntity> defineRoleEntities = iPage.getRecords();
        result.putGridList(EmDefineRoleTransfer.transferEntityToVoList(defineRoleEntities));
        return result;
    }


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean queryPageBean) {
        Page<EmDefineRoleDto> mpPagination = queryPageBean.toMpPage();
        List<EmDefineRoleDto> emDefineRoleDtoList = emDefineRoleMapper.selectQueryPage(mpPagination, queryPageBean.getQuery(), queryPageBean.getSortMap());
        result.settingPage(queryPageBean, mpPagination);
        result.putGridList(EmDefineRoleTransfer.transferDtoToVoList(emDefineRoleDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefineRoleVo emDefineRoleVo) throws Exception {
        Date now = new Date();
        EmDefineRoleEntity emDefineRoleEntity = EmDefineRoleTransfer.transferVoToEntity(emDefineRoleVo);
        emDefineRoleEntity = super.doBeforeCreate(loginUserInfo, emDefineRoleEntity);
        return emDefineRoleMapper.insert(emDefineRoleEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmDefineRoleVo emDefineRoleVo) throws Exception {
        Integer changeCount = 0;
        EmDefineRoleEntity emDefineRoleEntity = EmDefineRoleTransfer.transferVoToEntity(emDefineRoleVo);
        emDefineRoleEntity = super.doBeforeUpdate(loginUserInfo, emDefineRoleEntity);
        changeCount = emDefineRoleMapper.updateById(emDefineRoleEntity);
        return changeCount;
    }


    @Override
    public Integer dealGrantPermissionToRole(CurrentLoginEmUserInfo loginUserInfo, String roleId, String[] checkIds) throws Exception {
        Integer changeCount = 0;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = emDefinePermissionMapper.clearAllPermissionByRoleId(roleId, loginUserInfo);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的权限id 集合
            List<String> oldCheckPermIds = emDefinePermissionMapper.findAllPermissionIdByRoleId(roleId, false);
            if (CollectionUtil.isEmpty(oldCheckPermIds)) {
                List<EmRolePermissionEntity> addEntitys = new ArrayList<>();
                for (String checkId : checkIds) {
                    addEntitys.add(EmRolePermissionPojoInitialize.generateSimpleInsertEntity(roleId, checkId, loginUserInfo));
                }
                //批量新增行
                emRolePermissionService.saveBatch(addEntitys);
            } else {
                List<String> checkIdList = new ArrayList<>(Lists.newArrayList(checkIds));
                List<String> enableIds = new ArrayList<>();
                List<String> disabledIds = new ArrayList<>();
                Iterator<String> oldCheckIter = oldCheckPermIds.iterator();
                while (oldCheckIter.hasNext()) {
                    String oldCheckId = oldCheckIter.next();
                    boolean isOldRow = checkIdList.contains(oldCheckId);
                    if (isOldRow) {
                        //原本有的数据行
                        enableIds.add(oldCheckId);
                        checkIdList.remove(oldCheckId);
                    } else {
                        disabledIds.add(oldCheckId);
                    }
                }
                if (CollectionUtil.isNotEmpty(enableIds)) {
                    //批量启用
                    emRolePermissionMapper.batchUpdateStateByRole(roleId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(disabledIds)) {
                    //批量禁用
                    emRolePermissionMapper.batchUpdateStateByRole(roleId, disabledIds, BaseStateEnum.DISABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(checkIdList)) {
                    //有新勾选的权限，需要新增行
                    List<EmRolePermissionEntity> addEntitys = new ArrayList<>();
                    for (String checkId : checkIdList) {
                        addEntitys.add(EmRolePermissionPojoInitialize.generateSimpleInsertEntity(roleId, checkId, loginUserInfo));
                    }
                    //批量新增行
                    emRolePermissionService.saveBatch(addEntitys);
                }
            }
        }
        return changeCount;
    }
}
