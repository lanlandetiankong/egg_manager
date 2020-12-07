package com.egg.manager.baseservice.serviceimpl.obl.user.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.user.basic.OblUserCalculateInfoService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserCalculateInfoEntity;
import com.egg.manager.persistence.obl.user.db.mysql.mapper.OblUserCalculateInfoMapper;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserCalculateInfoDto;
import com.egg.manager.persistence.obl.user.pojo.transfer.OblUserCalculateInfoTransfer;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserCalculateInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 用户的计算信息-ServiceImpl
 * @date 2020-12-03
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblUserCalculateInfoService.class)
public class OblUserCalculateInfoServiceImpl extends MyBaseMysqlServiceImpl<OblUserCalculateInfoMapper, OblUserCalculateInfoEntity, OblUserCalculateInfoVo>
        implements OblUserCalculateInfoService {

    @Autowired
    private OblUserCalculateInfoMapper oblUserCalculateInfoMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<OblUserCalculateInfoDto> queryPage) {
        Page<OblUserCalculateInfoDto> mpPagination = queryPage.toMpPage();
        List<OblUserCalculateInfoDto> dtoList = oblUserCalculateInfoMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblUserCalculateInfoTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, OblUserCalculateInfoVo oblUserCalculateInfoVo) throws Exception {
        OblUserCalculateInfoEntity oblUserCalculateInfoEntity = OblUserCalculateInfoTransfer.transferVoToEntity(oblUserCalculateInfoVo);
        super.doBeforeCreate(loginUserInfo, oblUserCalculateInfoEntity);
        return oblUserCalculateInfoMapper.insert(oblUserCalculateInfoEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, OblUserCalculateInfoVo oblUserCalculateInfoVo) throws Exception {
        Integer changeCount = 0;
        OblUserCalculateInfoEntity oblUserCalculateInfoEntity = OblUserCalculateInfoTransfer.transferVoToEntity(oblUserCalculateInfoVo);
        oblUserCalculateInfoEntity = super.doBeforeUpdate(loginUserInfo, oblUserCalculateInfoEntity);
        changeCount = oblUserCalculateInfoMapper.updateById(oblUserCalculateInfoEntity);
        return changeCount;
    }


}