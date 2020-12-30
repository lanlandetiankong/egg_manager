package com.egg.manager.api.services.serviceimpl.em.define.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.facade.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.facade.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.facade.api.services.em.define.basic.EmDefineModuleService;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineModuleEntity;
import com.egg.manager.facade.persistence.em.define.db.mysql.mapper.EmDefineModuleMapper;
import com.egg.manager.facade.persistence.em.define.pojo.dto.EmDefineModuleDto;
import com.egg.manager.facade.persistence.em.define.pojo.transfer.EmDefineModuleTransfer;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineModuleVo;
import com.egg.manager.facade.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = EmDefineModuleService.class)
public class EmDefineModuleServiceImpl extends MyBaseMysqlServiceImpl<EmDefineModuleMapper, EmDefineModuleEntity, EmDefineModuleVo> implements EmDefineModuleService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;


    @Autowired
    private EmDefineModuleMapper emDefineModuleMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmDefineModuleDto> queryPageBean) {
        Page<EmDefineModuleDto> mpPagination = queryPageBean.toMpPage();
        List<EmDefineModuleDto> emDefineModuleDtoList = emDefineModuleMapper.selectQueryPage(mpPagination, queryPageBean.getQuery(), queryPageBean.getSortMap());
        result.settingPage(queryPageBean, mpPagination);
        result.putGridList(EmDefineModuleTransfer.transferDtoToVoList(emDefineModuleDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefineModuleVo emDefineModuleVo) throws Exception {
        EmDefineModuleEntity emDefineModuleEntity = EmDefineModuleTransfer.transferVoToEntity(emDefineModuleVo);
        emDefineModuleEntity = super.doBeforeCreate(loginUserInfo, emDefineModuleEntity);
        return emDefineModuleMapper.insert(emDefineModuleEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmDefineModuleVo emDefineModuleVo) throws Exception {
        Integer changeCount = 0;
        EmDefineModuleEntity emDefineModuleEntity = EmDefineModuleTransfer.transferVoToEntity(emDefineModuleVo);
        emDefineModuleEntity = super.doBeforeUpdate(loginUserInfo, emDefineModuleEntity);
        changeCount = emDefineModuleMapper.updateById(emDefineModuleEntity);
        return changeCount;
    }

}
