package com.egg.manager.baseservice.services.basic.serviceimpl.define;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.define.DefinePermissionService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.base.exception.MyDbException;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.util.LongUtils;
import com.egg.manager.persistence.commons.bean.helper.MyCommonResult;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefinePermission;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefinePermissionMapper;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserAccountMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefinePermissionDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefinePermissionTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefinePermissionVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = DefinePermissionService.class)
public class DefinePermissionServiceImpl extends MyBaseMysqlServiceImpl<DefinePermissionMapper, DefinePermission, DefinePermissionVo> implements DefinePermissionService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private DefinePermissionMapper definePermissionMapper;
    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public List<DefinePermission> getAllEnableList(UserAccount loginUser, QueryWrapper<DefinePermission> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<DefinePermission>();
        //筛选与排序
        wrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        wrapper.orderBy(true, false, "create_time");
        return definePermissionMapper.selectList(wrapper);
    }

    @Override
    public MyCommonResult<DefinePermissionVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefinePermission> paginationBean,
                                                                     List<AntdvSortBean> sortBeans) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //解析 搜索条件
        QueryWrapper<DefinePermission> queryWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        //取得 总数
        Integer total = definePermissionMapper.selectCount(queryWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = definePermissionMapper.selectPage(page, queryWrapper);
        List<DefinePermission> definePermissions = iPage.getRecords();
        result.setResultList(DefinePermissionTransfer.transferEntityToVoList(definePermissions));
        return result;
    }

    @Override
    public MyCommonResult<DefinePermissionVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefinePermissionDto> paginationBean,
                                                                  List<AntdvSortBean> sortBeans) {
        Page<DefinePermissionDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefinePermissionDto> definePermissionDtos = definePermissionMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(DefinePermissionTransfer.transferDtoToVoList(definePermissionDtos));
        return result;
    }


    @Override
    public Integer dealCreate(UserAccount loginUser, DefinePermissionVo definePermissionVo) throws Exception {
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(definePermissionVo, new QueryWrapper());
        if (verifyDuplicateBean.isSuccessFlag() == false) {
            //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Date now = new Date();
        DefinePermission definePermission = DefinePermissionTransfer.transferVoToEntity(definePermissionVo);
        definePermission = super.doBeforeCreate(loginUser, definePermission, true);
        definePermission.setEnsure(BaseStateEnum.DISABLED.getValue());
        return definePermissionMapper.insert(definePermission);
    }


    @Override
    public Integer dealUpdate(UserAccount loginUser, DefinePermissionVo definePermissionVo) throws Exception {
        QueryWrapper<DefinePermission> uniWrapper = new QueryWrapper<DefinePermission>()
                .ne("fid", definePermissionVo.getFid());
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(definePermissionVo, uniWrapper);
        if (verifyDuplicateBean.isSuccessFlag() == false) {
            //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Integer changeCount = 0;
        DefinePermission updateEntity = DefinePermissionTransfer.transferVoToEntity(definePermissionVo);
        updateEntity = super.doBeforeUpdate(loginUser, updateEntity);
        DefinePermission oldEntity = definePermissionMapper.selectById(definePermissionVo.getFid());
        if (SwitchStateEnum.Open.getValue().equals(oldEntity.getEnsure())) {
            //如果已经启用
            DefinePermissionTransfer.handleSwitchOpenChangeFieldChange(updateEntity, oldEntity);
        }
        changeCount = definePermissionMapper.updateById(updateEntity);
        return changeCount;
    }

    @Override
    public Integer dealBatchEnsure(UserAccount loginUser, Long[] ensureIds) {
        Integer delCount = 0;
        if (ensureIds != null && ensureIds.length > 0) {
            List<Long> delIdList = Lists.newArrayList(ensureIds);
            //批量伪删除
            delCount = definePermissionMapper.batchEnsureByIds(delIdList, loginUser);
        }
        return delCount;
    }

    @Override
    public List<DefinePermission> dealGetListByAccountFromDb(UserAccount loginUser, Long userAccountId) {
        if (LongUtils.isBlank(userAccountId)) {
            return null;
        }
        UserAccount userAccount = userAccountMapper.selectById(userAccountId);
        if (UserAccountBaseTypeEnum.SuperRoot.getValue().equals(userAccount.getUserTypeNum())) {
            //如果是[超级管理员]的话可以访问全部菜单
            return getAllEnableList(loginUser, null);
        } else {
            return definePermissionMapper.findAllPermissionByUserAcccountId(userAccountId);
        }
    }


    @Override
    public Set<String> dealGetPermissionCodeSetByAccountFromDb(UserAccount loginUser, Long userAccountId) {
        Set<String> codeSet = Sets.newHashSet();
        List<DefinePermission> definePermissions = this.dealGetListByAccountFromDb(loginUser, userAccountId);
        if (definePermissions != null && definePermissions.isEmpty() == false) {
            for (DefinePermission definePermission : definePermissions) {
                String permissionCode = definePermission.getCode();
                if (StringUtils.isNotBlank(permissionCode)) {
                    codeSet.add(permissionCode);
                }
            }
        }
        return codeSet;
    }


    @Override
    public MyVerifyDuplicateBean dealCheckDuplicateKey(DefinePermissionVo definePermissionVo, QueryWrapper<DefinePermission> wrapper) {
        MyVerifyDuplicateBean verifyBean = new MyVerifyDuplicateBean();
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        wrapper.eq("code", definePermissionVo.getCode());
        wrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        boolean successFlag = definePermissionMapper.selectCount(wrapper) == 0;
        if (successFlag == false) {
            verifyBean.setErrorMsg("唯一键[编码]不允许重复！");
            verifyBean.dealAddColumn("code");
            verifyBean.dealAddFieldName("编码");
        }
        verifyBean.setSuccessFlag(successFlag);
        return verifyBean;
    }
}
