package com.egg.manager.baseservice.serviceimpl.obl.user.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.user.basic.OblUserAttentionPersonService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserAttentionPersonEntity;
import com.egg.manager.persistence.obl.user.db.mysql.mapper.OblUserAttentionPersonMapper;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserAttentionPersonDto;
import com.egg.manager.persistence.obl.user.pojo.transfer.OblUserAttentionPersonTransfer;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserAttentionPersonVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 用户的关注人关联-ServiceImpl
 * @date 2020-12-03
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblUserAttentionPersonService.class)
public class OblUserAttentionPersonServiceImpl extends MyBaseMysqlServiceImpl<OblUserAttentionPersonMapper, OblUserAttentionPersonEntity, OblUserAttentionPersonVo>
        implements OblUserAttentionPersonService {

    @Autowired
    private OblUserAttentionPersonMapper oblUserAttentionPersonMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<OblUserAttentionPersonDto> queryPage) {
        Page<OblUserAttentionPersonDto> mpPagination = queryPage.toMpPage();
        List<OblUserAttentionPersonDto> dtoList = oblUserAttentionPersonMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage,mpPagination);
        result.putGridList(OblUserAttentionPersonTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, OblUserAttentionPersonVo oblUserAttentionPersonVo) throws Exception {
        OblUserAttentionPersonEntity oblUserAttentionPersonEntity = OblUserAttentionPersonTransfer.transferVoToEntity(oblUserAttentionPersonVo);
        super.doBeforeCreate(loginUserInfo, oblUserAttentionPersonEntity);
        return oblUserAttentionPersonMapper.insert(oblUserAttentionPersonEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, OblUserAttentionPersonVo oblUserAttentionPersonVo) throws Exception {
        Integer changeCount = 0;
        OblUserAttentionPersonEntity oblUserAttentionPersonEntity = OblUserAttentionPersonTransfer.transferVoToEntity(oblUserAttentionPersonVo);
        oblUserAttentionPersonEntity = super.doBeforeUpdate(loginUserInfo, oblUserAttentionPersonEntity);
        changeCount = oblUserAttentionPersonMapper.updateById(oblUserAttentionPersonEntity);
        return changeCount;
    }


}