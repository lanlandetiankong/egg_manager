package com.egg.manager.baseservice.serviceimpl.em.define.basic;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.define.basic.EmDefinePermissionService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.persistence.commons.base.constant.db.redis.RedisShiroKeyConstant;
import com.egg.manager.persistence.commons.base.enums.basic.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.basic.SwitchStateEnum;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefinePermissionEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefinePermissionDto;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefinePermissionVo;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.domain.enums.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.commons.base.exception.MyDbException;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefinePermissionMapper;
import com.egg.manager.persistence.em.define.pojo.transfer.EmDefinePermissionTransfer;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmUserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
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
@Service(interfaceClass = EmDefinePermissionService.class)
public class EmDefinePermissionServiceImpl extends MyBaseMysqlServiceImpl<EmDefinePermissionMapper, EmDefinePermissionEntity, EmDefinePermissionVo> implements EmDefinePermissionService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private EmDefinePermissionMapper emDefinePermissionMapper;
    @Autowired
    private EmUserAccountMapper emUserAccountMapper;

    @Override
    public List<EmDefinePermissionEntity> getAllEnableList(QueryWrapper<EmDefinePermissionEntity> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<EmDefinePermissionEntity>();
        //筛选与排序
        wrapper.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        wrapper.orderBy(true, false, FieldConst.COL_CREATE_TIME);
        return emDefinePermissionMapper.selectList(wrapper);
    }

    @Override
    public WebResult dealQueryPageByEntitys(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean queryPageBean) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(queryPageBean.getPageConf());
        //解析 搜索条件
        QueryWrapper<EmDefinePermissionEntity> queryWrapper = super.doGetPageQueryWrapper(loginUserInfo, result, queryPageBean);
        //取得 总数
        Integer total = emDefinePermissionMapper.selectCount(queryWrapper);
        result.settingPage(queryPageBean.getPageConf(), Long.valueOf(total));
        IPage iPage = emDefinePermissionMapper.selectPage(page, queryWrapper);
        List<EmDefinePermissionEntity> definePermissionEntities = iPage.getRecords();
        result.putGridList(EmDefinePermissionTransfer.transferEntityToVoList(definePermissionEntities));
        return result;
    }

    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean queryPageBean) {
        try{
            Page<EmDefinePermissionDto> mpPagination = queryPageBean.toMpPage();
            int count = emDefinePermissionMapper.querytest();
            List<EmDefinePermissionDto> emDefinePermissionDtos = emDefinePermissionMapper.selectQueryPage(mpPagination, queryPageBean.getQuery(), queryPageBean.getSortMap());
            result.settingPage(queryPageBean.getPageConf(), mpPagination.getTotal());
            result.putGridList(EmDefinePermissionTransfer.transferDtoToVoList(emDefinePermissionDtos));
        }   catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefinePermissionVo emDefinePermissionVo) throws Exception {
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(emDefinePermissionVo, new QueryWrapper());
        if (verifyDuplicateBean.isSuccessFlag() == false) {
            //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Date now = new Date();
        EmDefinePermissionEntity emDefinePermissionEntity = EmDefinePermissionTransfer.transferVoToEntity(emDefinePermissionVo);
        emDefinePermissionEntity = super.doBeforeCreate(loginUserInfo, emDefinePermissionEntity);
        emDefinePermissionEntity.setEnsure(BaseStateEnum.DISABLED.getValue());
        return emDefinePermissionMapper.insert(emDefinePermissionEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmDefinePermissionVo emDefinePermissionVo) throws Exception {
        QueryWrapper<EmDefinePermissionEntity> uniWrapper = new QueryWrapper<EmDefinePermissionEntity>()
                .ne(FieldConst.COL_FID, emDefinePermissionVo.getFid());
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(emDefinePermissionVo, uniWrapper);
        if (verifyDuplicateBean.isSuccessFlag() == false) {
            //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Integer changeCount = 0;
        EmDefinePermissionEntity updateEntity = EmDefinePermissionTransfer.transferVoToEntity(emDefinePermissionVo);
        updateEntity = super.doBeforeUpdate(loginUserInfo, updateEntity);
        EmDefinePermissionEntity oldEntity = emDefinePermissionMapper.selectById(emDefinePermissionVo.getFid());
        if (SwitchStateEnum.Open.getValue().equals(oldEntity.getEnsure())) {
            //如果已经启用
            EmDefinePermissionTransfer.handleSwitchOpenChangeFieldChange(updateEntity, oldEntity);
        }
        changeCount = emDefinePermissionMapper.updateById(updateEntity);
        return changeCount;
    }

    @Override
    public Integer dealBatchEnsure(CurrentLoginEmUserInfo loginUserInfo, String[] ensureIds) {
        Integer delCount = 0;
        if (ensureIds != null && ensureIds.length > 0) {
            List<String> delIdList = Lists.newArrayList(ensureIds);
            //批量逻辑删除
            delCount = emDefinePermissionMapper.batchEnsureByIds(delIdList, loginUserInfo);
        }
        return delCount;
    }

    @Override
    public List<EmDefinePermissionEntity> dealGetListByAccountFromDb(String userAccountId) {
        if (StringUtils.isBlank(userAccountId)) {
            return null;
        }
        EmUserAccountEntity emUserAccountEntity = emUserAccountMapper.selectById(userAccountId);
        if (UserAccountBaseTypeEnum.SuperRoot.getValue().equals(emUserAccountEntity.getUserType())) {
            //如果是[超级管理员]的话可以访问全部菜单
            return getAllEnableList(null);
        } else {
            return emDefinePermissionMapper.findAllPermissionByUserAcccountId(userAccountId);
        }
    }


    @Override
    @Cacheable(value = RedisShiroKeyConstant.KEY_USER_PERMISSION, key = "#userAccountId", condition = "#userAccountId!=null")
    public Set<String> queryDbToCacheable(String userAccountId) {
        Set<String> codeSet = Sets.newHashSet();
        List<EmDefinePermissionEntity> definePermissionEntities = this.dealGetListByAccountFromDb(userAccountId);
        if (CollectionUtil.isNotEmpty(definePermissionEntities)) {
            for (EmDefinePermissionEntity emDefinePermissionEntity : definePermissionEntities) {
                String permissionCode = emDefinePermissionEntity.getCode();
                if (StringUtils.isNotBlank(permissionCode)) {
                    codeSet.add(permissionCode);
                }
            }
        }
        return codeSet;
    }


    @Override
    public MyVerifyDuplicateBean dealCheckDuplicateKey(EmDefinePermissionVo emDefinePermissionVo, QueryWrapper<EmDefinePermissionEntity> wrapper) {
        MyVerifyDuplicateBean verifyBean = new MyVerifyDuplicateBean();
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        wrapper.eq("code", emDefinePermissionVo.getCode());
        wrapper.eq(FieldConst.COL_STATE, BaseStateEnum.ENABLED.getValue());
        boolean successFlag = emDefinePermissionMapper.selectCount(wrapper) == 0;
        if (successFlag == false) {
            verifyBean.setErrorMsg("唯一键[编码]不允许重复！");
            verifyBean.dealAddColumn("code");
            verifyBean.dealAddFieldName("编码");
        }
        verifyBean.setSuccessFlag(successFlag);
        return verifyBean;
    }
}
