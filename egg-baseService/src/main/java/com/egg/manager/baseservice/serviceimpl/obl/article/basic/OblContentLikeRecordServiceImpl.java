package com.egg.manager.baseservice.serviceimpl.obl.article.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.article.basic.OblContentLikeRecordService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblContentLikeRecordEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblContentLikeRecordMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblContentLikeRecordDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblContentLikeRecordTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblContentLikeRecordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 评论点赞表-ServiceImpl
 * @date 2020-12-02
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblContentLikeRecordService.class)
public class OblContentLikeRecordServiceImpl extends MyBaseMysqlServiceImpl<OblContentLikeRecordMapper, OblContentLikeRecordEntity, OblContentLikeRecordVo>
        implements OblContentLikeRecordService {

    @Autowired
    private OblContentLikeRecordMapper oblContentLikeRecordMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<OblContentLikeRecordDto> queryPage) {
        Page<OblContentLikeRecordDto> mpPagination = queryPage.toMpPage();
        List<OblContentLikeRecordDto> dtoList = oblContentLikeRecordMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage,mpPagination);
        result.putGridList(OblContentLikeRecordTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, OblContentLikeRecordVo oblContentLikeRecordVo) throws Exception {
        OblContentLikeRecordEntity oblContentLikeRecordEntity = OblContentLikeRecordTransfer.transferVoToEntity(oblContentLikeRecordVo);
        super.doBeforeCreate(loginUserInfo, oblContentLikeRecordEntity);
        return oblContentLikeRecordMapper.insert(oblContentLikeRecordEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, OblContentLikeRecordVo oblContentLikeRecordVo) throws Exception {
        Integer changeCount = 0;
        OblContentLikeRecordEntity oblContentLikeRecordEntity = OblContentLikeRecordTransfer.transferVoToEntity(oblContentLikeRecordVo);
        oblContentLikeRecordEntity = super.doBeforeUpdate(loginUserInfo, oblContentLikeRecordEntity);
        changeCount = oblContentLikeRecordMapper.updateById(oblContentLikeRecordEntity);
        return changeCount;
    }


}