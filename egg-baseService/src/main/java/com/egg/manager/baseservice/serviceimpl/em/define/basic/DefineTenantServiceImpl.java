package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.define.basic.DefineTenantService;
import com.egg.manager.api.services.em.user.basic.UserTenantService;
import com.egg.manager.persistence.commons.base.beans.front.FrontEntitySelectBean;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineTenantEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserTenantEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineTenantMapper;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserTenantMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.em.define.pojo.dto.DefineTenantDto;
import com.egg.manager.persistence.em.user.pojo.initialize.UserTenantPojoInitialize;
import com.egg.manager.persistence.em.define.pojo.transfer.DefineTenantTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineTenantVo;
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
@Service(interfaceClass = DefineTenantService.class)
public class DefineTenantServiceImpl extends MyBaseMysqlServiceImpl<DefineTenantMapper, DefineTenantEntity, DefineTenantVo>
        implements DefineTenantService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private DefineTenantMapper defineTenantMapper;
    @Autowired
    private UserTenantMapper userTenantMapper;

    @Autowired
    private UserTenantService userTenantService;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineTenantDto> paginationBean,
                                         List<AntdvSortBean> sortBeans) {
        Page<DefineTenantDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineTenantDto> defineTenantDtoList = defineTenantMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.putResultList(DefineTenantTransfer.transferDtoToVoList(defineTenantDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, DefineTenantVo defineTenantVo) throws Exception {
        DefineTenantEntity defineTenantEntity = DefineTenantTransfer.transferVoToEntity(defineTenantVo);
        defineTenantEntity = super.doBeforeCreate(loginUserInfo, defineTenantEntity, true);
        Integer addCount = defineTenantMapper.insert(defineTenantEntity);
        return addCount;
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, DefineTenantVo defineTenantVo) throws Exception {
        Integer changeCount = 0;
        DefineTenantEntity defineTenantEntity = DefineTenantTransfer.transferVoToEntity(defineTenantVo);
        defineTenantEntity = super.doBeforeUpdate(loginUserInfo, defineTenantEntity);
        changeCount = defineTenantMapper.updateById(defineTenantEntity);
        return changeCount;
    }


    @Override
    public WebResult dealResultListToEnums(CurrentLoginUserInfo loginUserInfo, WebResult result) {
        List<FrontEntitySelectBean> enumList = new ArrayList<>();
        List<DefineTenantVo> resultList = result.getResultList();
        if (CollectionUtil.isNotEmpty(resultList)) {
            for (DefineTenantVo defineTenantVo : resultList) {
                enumList.add(new FrontEntitySelectBean<Long>(defineTenantVo.getFid(), defineTenantVo.getName()));
            }
        }
        result.putEnumList(enumList);
        return result;
    }

    @Override
    public Integer dealTenantSetupManager(CurrentLoginUserInfo loginUserInfo, Long tenantId, Long[] checkIds) throws Exception {
        Integer changeCount = 0;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = defineTenantMapper.clearAllManagerByTenantId(tenantId, loginUserInfo);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的用户id 集合
            List<Long> oldCheckIds = defineTenantMapper.findAllManagerUserIdByTenantId(tenantId, false);
            if (CollectionUtil.isEmpty(oldCheckIds)) {
                List<UserTenantEntity> addEntitys = new ArrayList<>();
                for (Long checkId : checkIds) {
                    addEntitys.add(UserTenantPojoInitialize.generateInsertIsManagerEntity(tenantId, checkId, loginUserInfo));
                }
                //批量新增行
                userTenantService.saveBatch(addEntitys);
            } else {
                List<Long> checkIdList = new ArrayList<>(Lists.newArrayList(checkIds));
                List<Long> enableIds = new ArrayList<>();
                List<Long> disabledIds = new ArrayList<>();
                Iterator<Long> oldCheckIter = oldCheckIds.iterator();
                while (oldCheckIter.hasNext()) {
                    Long oldCheckId = oldCheckIter.next();
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
                    userTenantMapper.batchUpdateManagerUserStateByTenantId(tenantId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(disabledIds)) {
                    //批量禁用
                    userTenantMapper.batchUpdateManagerUserStateByTenantId(tenantId, disabledIds, BaseStateEnum.DISABLED.getValue(), loginUserInfo);
                }
                if (CollectionUtil.isNotEmpty(checkIdList)) {
                    //有新勾选的权限，需要新增行
                    List<UserTenantEntity> addEntitys = new ArrayList<>();
                    for (Long checkId : checkIdList) {
                        addEntitys.add(UserTenantPojoInitialize.generateInsertIsManagerEntity(tenantId, checkId, loginUserInfo));
                    }
                    //批量新增行
                    userTenantService.saveBatch(addEntitys);
                }
            }
        }
        return changeCount;
    }
}
