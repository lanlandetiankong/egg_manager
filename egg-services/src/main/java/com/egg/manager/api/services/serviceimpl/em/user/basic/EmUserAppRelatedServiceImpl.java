package com.egg.manager.api.services.serviceimpl.em.user.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.facade.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.facade.api.services.em.user.basic.EmUserAppRelatedService;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAppRelatedEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.EmUserAppRelatedMapper;
import com.egg.manager.facade.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.facade.persistence.em.user.pojo.dto.EmUserAppRelatedDto;
import com.egg.manager.facade.persistence.em.user.pojo.transfer.EmUserAppRelatedTransfer;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserAppRelatedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description app用户关联表-ServiceImpl
 * @date 2020-12-07
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = EmUserAppRelatedService.class)
public class EmUserAppRelatedServiceImpl extends MyBaseMysqlServiceImpl<EmUserAppRelatedMapper, EmUserAppRelatedEntity, EmUserAppRelatedVo>
        implements EmUserAppRelatedService {

    @Autowired
    private EmUserAppRelatedMapper emUserAppRelatedMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmUserAppRelatedDto> queryPage) {
        Page<EmUserAppRelatedDto> mpPagination = queryPage.toMpPage();
        List<EmUserAppRelatedDto> dtoList = emUserAppRelatedMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage, mpPagination);
        result.putGridList(EmUserAppRelatedTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmUserAppRelatedVo emUserAppRelatedVo) throws Exception {
        EmUserAppRelatedEntity emUserAppRelatedEntity = EmUserAppRelatedTransfer.transferVoToEntity(emUserAppRelatedVo);
        super.doBeforeCreate(loginUserInfo, emUserAppRelatedEntity);
        return emUserAppRelatedMapper.insert(emUserAppRelatedEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmUserAppRelatedVo emUserAppRelatedVo) throws Exception {
        Integer changeCount = 0;
        EmUserAppRelatedEntity emUserAppRelatedEntity = EmUserAppRelatedTransfer.transferVoToEntity(emUserAppRelatedVo);
        emUserAppRelatedEntity = super.doBeforeUpdate(loginUserInfo, emUserAppRelatedEntity);
        changeCount = emUserAppRelatedMapper.updateById(emUserAppRelatedEntity);
        return changeCount;
    }


}