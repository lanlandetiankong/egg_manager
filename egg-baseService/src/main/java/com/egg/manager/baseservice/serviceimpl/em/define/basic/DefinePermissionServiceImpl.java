package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.define.basic.DefinePermissionService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.persistence.commons.base.constant.redis.RedisShiroKeyConstant;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.base.SwitchStateEnum;
import com.egg.manager.persistence.commons.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.commons.base.exception.MyDbException;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.form.QueryFieldArr;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefinePermissionEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefinePermissionMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefinePermissionDto;
import com.egg.manager.persistence.em.define.pojo.transfer.DefinePermissionTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefinePermissionVo;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = DefinePermissionService.class)
public class DefinePermissionServiceImpl extends MyBaseMysqlServiceImpl<DefinePermissionMapper, DefinePermissionEntity, DefinePermissionVo> implements DefinePermissionService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private DefinePermissionMapper definePermissionMapper;
    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public List<DefinePermissionEntity> getAllEnableList(QueryWrapper<DefinePermissionEntity> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<DefinePermissionEntity>();
        //筛选与排序
        wrapper.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        wrapper.orderBy(true, false, FieldConst.COL_CREATE_TIME);
        return definePermissionMapper.selectList(wrapper);
    }

    @Override
    public WebResult dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryFieldArr queryFieldArr, AntdvPage<DefinePermissionEntity> vpage,
                                            AntdvSortMap sortMap) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(vpage);
        //解析 搜索条件
        QueryWrapper<DefinePermissionEntity> queryWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryFieldArr, vpage, sortMap);
        //取得 总数
        Integer total = definePermissionMapper.selectCount(queryWrapper);
        result.settingPage(vpage, Long.valueOf(total));
        IPage iPage = definePermissionMapper.selectPage(page, queryWrapper);
        List<DefinePermissionEntity> definePermissionEntities = iPage.getRecords();
        result.putResultList(DefinePermissionTransfer.transferEntityToVoList(definePermissionEntities));
        return result;
    }

    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryFieldArr queryFieldArr, AntdvPage<DefinePermissionDto> vpage,
                                         AntdvSortMap sortMap) {
        Page<DefinePermissionDto> mpPagination = super.dealAntvPageToPagination(vpage);
        List<DefinePermissionDto> definePermissionDtos = definePermissionMapper.selectQueryPage(mpPagination, queryFieldArr, sortMap);
        result.settingPage(vpage, mpPagination.getTotal());
        result.putResultList(DefinePermissionTransfer.transferDtoToVoList(definePermissionDtos));
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, DefinePermissionVo definePermissionVo) throws Exception {
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(definePermissionVo, new QueryWrapper());
        if (verifyDuplicateBean.isSuccessFlag() == false) {
            //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Date now = new Date();
        DefinePermissionEntity definePermissionEntity = DefinePermissionTransfer.transferVoToEntity(definePermissionVo);
        definePermissionEntity = super.doBeforeCreate(loginUserInfo, definePermissionEntity);
        definePermissionEntity.setEnsure(BaseStateEnum.DISABLED.getValue());
        return definePermissionMapper.insert(definePermissionEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, DefinePermissionVo definePermissionVo) throws Exception {
        QueryWrapper<DefinePermissionEntity> uniWrapper = new QueryWrapper<DefinePermissionEntity>()
                .ne(FieldConst.COL_FID, definePermissionVo.getFid());
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(definePermissionVo, uniWrapper);
        if (verifyDuplicateBean.isSuccessFlag() == false) {
            //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Integer changeCount = 0;
        DefinePermissionEntity updateEntity = DefinePermissionTransfer.transferVoToEntity(definePermissionVo);
        updateEntity = super.doBeforeUpdate(loginUserInfo, updateEntity);
        DefinePermissionEntity oldEntity = definePermissionMapper.selectById(definePermissionVo.getFid());
        if (SwitchStateEnum.Open.getValue().equals(oldEntity.getEnsure())) {
            //如果已经启用
            DefinePermissionTransfer.handleSwitchOpenChangeFieldChange(updateEntity, oldEntity);
        }
        changeCount = definePermissionMapper.updateById(updateEntity);
        return changeCount;
    }

    @Override
    public Integer dealBatchEnsure(CurrentLoginUserInfo loginUserInfo, String[] ensureIds) {
        Integer delCount = 0;
        if (ensureIds != null && ensureIds.length > 0) {
            List<String> delIdList = Lists.newArrayList(ensureIds);
            //批量伪删除
            delCount = definePermissionMapper.batchEnsureByIds(delIdList, loginUserInfo);
        }
        return delCount;
    }

    @Override
    public List<DefinePermissionEntity> dealGetListByAccountFromDb(String userAccountId) {
        if (StringUtils.isBlank(userAccountId)) {
            return null;
        }
        UserAccountEntity userAccountEntity = userAccountMapper.selectById(userAccountId);
        if (UserAccountBaseTypeEnum.SuperRoot.getValue().equals(userAccountEntity.getUserTypeNum())) {
            //如果是[超级管理员]的话可以访问全部菜单
            return getAllEnableList(null);
        } else {
            return definePermissionMapper.findAllPermissionByUserAcccountId(userAccountId);
        }
    }


    @Override
    @Cacheable(value = RedisShiroKeyConstant.KEY_USER_PERMISSION, key = "#userAccountId", condition = "#userAccountId!=null")
    public Set<String> queryDbToCacheable(String userAccountId) {
        Set<String> codeSet = Sets.newHashSet();
        List<DefinePermissionEntity> definePermissionEntities = this.dealGetListByAccountFromDb(userAccountId);
        if (CollectionUtil.isNotEmpty(definePermissionEntities)) {
            for (DefinePermissionEntity definePermissionEntity : definePermissionEntities) {
                String permissionCode = definePermissionEntity.getCode();
                if (StringUtils.isNotBlank(permissionCode)) {
                    codeSet.add(permissionCode);
                }
            }
        }
        return codeSet;
    }


    @Override
    public MyVerifyDuplicateBean dealCheckDuplicateKey(DefinePermissionVo definePermissionVo, QueryWrapper<DefinePermissionEntity> wrapper) {
        MyVerifyDuplicateBean verifyBean = new MyVerifyDuplicateBean();
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        wrapper.eq("code", definePermissionVo.getCode());
        wrapper.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
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
