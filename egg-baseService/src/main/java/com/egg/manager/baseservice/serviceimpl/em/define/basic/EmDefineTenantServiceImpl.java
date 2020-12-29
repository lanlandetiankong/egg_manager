package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.facade.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.facade.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.facade.api.services.em.define.basic.EmDefineTenantService;
import com.egg.manager.facade.api.services.em.user.basic.EmUserTenantService;
import com.egg.manager.facade.persistence.commons.base.beans.front.FrontSelectBean;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.facade.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineTenantEntity;
import com.egg.manager.facade.persistence.em.define.db.mysql.mapper.EmDefineTenantMapper;
import com.egg.manager.facade.persistence.em.define.pojo.dto.EmDefineTenantDto;
import com.egg.manager.facade.persistence.em.define.pojo.transfer.EmDefineTenantTransfer;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineTenantVo;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserTenantEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.EmUserTenantMapper;
import com.egg.manager.facade.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.facade.persistence.em.user.pojo.initialize.EmUserTenantPojoInitialize;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = EmDefineTenantService.class)
public class EmDefineTenantServiceImpl extends MyBaseMysqlServiceImpl<EmDefineTenantMapper, EmDefineTenantEntity, EmDefineTenantVo>
        implements EmDefineTenantService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private EmDefineTenantMapper emDefineTenantMapper;
    @Autowired
    private EmUserTenantMapper emUserTenantMapper;

    @Autowired
    private EmUserTenantService emUserTenantService;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmDefineTenantDto> queryPageBean) {
        Page<EmDefineTenantDto> mpPagination = queryPageBean.toMpPage();
        List<EmDefineTenantDto> emDefineTenantDtoList = emDefineTenantMapper.selectQueryPage(mpPagination, queryPageBean.getQuery(), queryPageBean.getSortMap());
        result.settingPage(queryPageBean, mpPagination);
        result.putGridList(EmDefineTenantTransfer.transferDtoToVoList(emDefineTenantDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefineTenantVo emDefineTenantVo) throws Exception {
        EmDefineTenantEntity emDefineTenantEntity = EmDefineTenantTransfer.transferVoToEntity(emDefineTenantVo);
        emDefineTenantEntity = super.doBeforeCreate(loginUserInfo, emDefineTenantEntity);
        Integer addCount = emDefineTenantMapper.insert(emDefineTenantEntity);
        return addCount;
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmDefineTenantVo emDefineTenantVo) throws Exception {
        Integer changeCount = 0;
        EmDefineTenantEntity emDefineTenantEntity = EmDefineTenantTransfer.transferVoToEntity(emDefineTenantVo);
        emDefineTenantEntity = super.doBeforeUpdate(loginUserInfo, emDefineTenantEntity);
        changeCount = emDefineTenantMapper.updateById(emDefineTenantEntity);
        return changeCount;
    }


    @Override
    public WebResult dealResultListToEnums(CurrentLoginEmUserInfo loginUserInfo, WebResult result) {
        List<FrontSelectBean> enumList = new ArrayList<>();
        List<EmDefineTenantVo> resultList = result.getGridList();
        if (CollectionUtil.isNotEmpty(resultList)) {
            for (EmDefineTenantVo emDefineTenantVo : resultList) {
                enumList.add(new FrontSelectBean<String>(emDefineTenantVo.getFid(), emDefineTenantVo.getName()));
            }
        }
        result.putEnumData(enumList);
        return result;
    }

    @Override
    public Integer dealTenantSetupManager(CurrentLoginEmUserInfo loginUserInfo, String tenantId, String[] checkIds) throws Exception {
        Integer changeCount = 0;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = emDefineTenantMapper.clearAllManagerByTenantId(tenantId, loginUserInfo);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的用户id 集合
            List<String> oldCheckIds = emDefineTenantMapper.findAllManagerUserIdByTenantId(tenantId, false);
            if (CollectionUtil.isEmpty(oldCheckIds)) {
                List<EmUserTenantEntity> addEntitys = new ArrayList<>();
                for (String checkId : checkIds) {
                    addEntitys.add(EmUserTenantPojoInitialize.generateInsertIsManagerEntity(tenantId, checkId, loginUserInfo));
                }
                //批量新增行
                emUserTenantService.saveBatch(addEntitys);
            } else {
                List<String> checkIdList = new ArrayList<>(Lists.newArrayList(checkIds));
                List<String> enableIds = new ArrayList<>();
                List<String> disabledIds = new ArrayList<>();
                Iterator<String> oldCheckIter = oldCheckIds.iterator();
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
                    emUserTenantMapper.batchUpdateManagerUserStateByTenantId(tenantId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(disabledIds)) {
                    //批量禁用
                    emUserTenantMapper.batchUpdateManagerUserStateByTenantId(tenantId, disabledIds, BaseStateEnum.DISABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(checkIdList)) {
                    //有新勾选的权限，需要新增行
                    List<EmUserTenantEntity> addEntitys = new ArrayList<>();
                    for (String checkId : checkIdList) {
                        addEntitys.add(EmUserTenantPojoInitialize.generateInsertIsManagerEntity(tenantId, checkId, loginUserInfo));
                    }
                    //批量新增行
                    emUserTenantService.saveBatch(addEntitys);
                }
            }
        }
        return changeCount;
    }

    @Override
    public EmDefineTenantEntity selectOneOfUserBelongTenant(String userAccountId, Short tenantState) {
        return this.baseMapper.selectOneOfUserBelongTenant(userAccountId, tenantState);
    }

    @Override
    public EmDefineTenantDto selectOneDtoOfUserBelongTenant(String userAccountId) {
        return this.baseMapper.selectOneDtoOfUserBelongTenant(userAccountId);
    }

}
