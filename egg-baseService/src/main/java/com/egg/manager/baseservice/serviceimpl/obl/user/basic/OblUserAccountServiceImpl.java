package com.egg.manager.baseservice.serviceimpl.obl.user.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.user.basic.OblUserAccountService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserAccountEntity;
import com.egg.manager.persistence.obl.user.db.mysql.mapper.OblUserAccountMapper;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserAccountDto;
import com.egg.manager.persistence.obl.user.pojo.transfer.OblUserAccountTransfer;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserAccountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 用户表-ServiceImpl
 * @date 2020-12-07
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblUserAccountService.class)
public class OblUserAccountServiceImpl extends MyBaseMysqlServiceImpl<OblUserAccountMapper, OblUserAccountEntity, OblUserAccountVo>
        implements OblUserAccountService {

    @Autowired
    private OblUserAccountMapper oblUserAccountMapper;

    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<OblUserAccountDto> queryPage) {
        Page<OblUserAccountDto> mpPagination = queryPage.toMpPage();
        List<OblUserAccountDto> dtoList = oblUserAccountMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage,mpPagination);
        result.putGridList(OblUserAccountTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, OblUserAccountVo oblUserAccountVo) throws Exception {
        OblUserAccountEntity oblUserAccountEntity = OblUserAccountTransfer.transferVoToEntity(oblUserAccountVo);
        super.doBeforeCreate(loginUserInfo, oblUserAccountEntity);
        return oblUserAccountMapper.insert(oblUserAccountEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, OblUserAccountVo oblUserAccountVo) throws Exception {
        Integer changeCount = 0;
        OblUserAccountEntity oblUserAccountEntity = OblUserAccountTransfer.transferVoToEntity(oblUserAccountVo);
        oblUserAccountEntity = super.doBeforeUpdate(loginUserInfo, oblUserAccountEntity);
        changeCount = oblUserAccountMapper.updateById(oblUserAccountEntity);
        return changeCount;
    }


}