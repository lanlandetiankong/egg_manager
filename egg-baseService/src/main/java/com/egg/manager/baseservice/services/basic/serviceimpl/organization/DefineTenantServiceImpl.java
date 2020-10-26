package com.egg.manager.baseservice.services.basic.serviceimpl.organization;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.organization.DefineTenantService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.beans.front.FrontEntitySelectBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.db.mysql.mapper.organization.DefineTenantMapper;
import com.egg.manager.persistence.db.mysql.mapper.user.UserTenantMapper;
import com.egg.manager.persistence.pojo.mysql.dto.organization.DefineTenantDto;
import com.egg.manager.persistence.pojo.mysql.initialize.user.UserTenantPojoInitialize;
import com.egg.manager.persistence.pojo.mysql.transfer.organization.DefineTenantTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.organization.DefineTenantVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = DefineTenantService.class)
public class DefineTenantServiceImpl extends MyBaseMysqlServiceImpl<DefineTenantMapper, DefineTenant, DefineTenantVo>
        implements DefineTenantService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private DefineTenantMapper defineTenantMapper;
    @Autowired
    private UserTenantMapper userTenantMapper;


    @Override
    public MyCommonResult<DefineTenantVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<DefineTenantVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineTenantDto> paginationBean,
                                                              List<AntdvSortBean> sortBeans) {
        Page<DefineTenantDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefineTenantDto> defineTenantDtoList = defineTenantMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(DefineTenantTransfer.transferDtoToVoList(defineTenantDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(UserAccount loginUser, DefineTenantVo defineTenantVo) throws Exception {
        DefineTenant defineTenant = DefineTenantTransfer.transferVoToEntity(defineTenantVo);
        defineTenant = super.doBeforeCreate(loginUser, defineTenant, true);
        Integer addCount = defineTenantMapper.insert(defineTenant);
        return addCount;
    }


    @Override
    public Integer dealUpdate(UserAccount loginUser, DefineTenantVo defineTenantVo) throws Exception {
        Integer changeCount = 0;
        DefineTenant defineTenant = DefineTenantTransfer.transferVoToEntity(defineTenantVo);
        defineTenant = super.doBeforeUpdate(loginUser, defineTenant);
        changeCount = defineTenantMapper.updateById(defineTenant);
        return changeCount;
    }

    @Override
    public Integer dealBatchDelete(UserAccount loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = defineTenantMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    @Override
    public Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception {
        Integer count = defineTenantMapper.fakeDeleteById(delId);
        return count;
    }


    @Override
    public MyCommonResult dealResultListToEnums(UserAccount loginUser, MyCommonResult result) {
        List<FrontEntitySelectBean> enumList = new ArrayList<>();
        List<DefineTenantVo> resultList = result.getResultList();
        if (resultList != null && resultList.isEmpty() == false) {
            for (DefineTenantVo defineTenantVo : resultList) {
                enumList.add(new FrontEntitySelectBean(defineTenantVo.getFid(), defineTenantVo.getName()));
            }
        }
        result.setEnumList(enumList);
        return result;
    }

    @Override
    public Integer dealTenantSetupManager(UserAccount loginUser, String tenantId, String[] checkIds) throws Exception {
        Integer changeCount = 0;
        if (checkIds == null || checkIds.length == 0) {
            //清空所有权限
            changeCount = defineTenantMapper.clearAllManagerByTenantId(tenantId, loginUser);
        } else {
            changeCount = checkIds.length;
            //取得曾勾选的用户id 集合
            List<String> oldCheckIds = defineTenantMapper.findAllManagerUserIdByTenantId(tenantId, false);
            if (oldCheckIds == null || oldCheckIds.isEmpty()) {
                List<UserTenant> addEntitys = new ArrayList<>();
                for (String checkId : checkIds) {
                    addEntitys.add(UserTenantPojoInitialize.generateInsertIsManagerEntity(tenantId, checkId, loginUser));
                }
                //批量新增行
                userTenantMapper.customBatchInsert(addEntitys);
            } else {
                List<String> checkIdList = new ArrayList<>(Arrays.asList(checkIds));
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
                if (enableIds.isEmpty() == false) {
                    //批量启用
                    userTenantMapper.batchUpdateManagerUserStateByTenantId(tenantId, enableIds, BaseStateEnum.ENABLED.getValue(), loginUser);
                }
                if (disabledIds.isEmpty() == false) {
                    //批量禁用
                    userTenantMapper.batchUpdateManagerUserStateByTenantId(tenantId, disabledIds, BaseStateEnum.DELETE.getValue(), loginUser);
                }
                if (checkIdList.isEmpty() == false) {
                    //有新勾选的权限，需要新增行
                    List<UserTenant> addEntitys = new ArrayList<>();
                    for (String checkId : checkIdList) {
                        addEntitys.add(UserTenantPojoInitialize.generateInsertIsManagerEntity(tenantId, checkId, loginUser));
                    }
                    //批量新增行
                    userTenantMapper.customBatchInsert(addEntitys);
                }
            }
        }
        return changeCount;
    }
}
