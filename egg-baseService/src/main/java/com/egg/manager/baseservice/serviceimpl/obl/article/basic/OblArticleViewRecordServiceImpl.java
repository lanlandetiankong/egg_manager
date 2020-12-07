package com.egg.manager.baseservice.serviceimpl.obl.article.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.article.basic.OblArticleViewRecordService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleViewRecordEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleViewRecordMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleViewRecordDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblArticleViewRecordTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleViewRecordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 文章查看记录-ServiceImpl
 * @date 2020-12-04
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblArticleViewRecordService.class)
public class OblArticleViewRecordServiceImpl extends MyBaseMysqlServiceImpl<OblArticleViewRecordMapper, OblArticleViewRecordEntity, OblArticleViewRecordVo>
        implements OblArticleViewRecordService {

    @Autowired
    private OblArticleViewRecordMapper oblArticleViewRecordMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleViewRecordDto> queryPage) {
        Page<OblArticleViewRecordDto> mpPagination = queryPage.toMpPage();
        List<OblArticleViewRecordDto> dtoList = oblArticleViewRecordMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblArticleViewRecordTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, OblArticleViewRecordVo oblArticleViewRecordVo) throws Exception {
        OblArticleViewRecordEntity oblArticleViewRecordEntity = OblArticleViewRecordTransfer.transferVoToEntity(oblArticleViewRecordVo);
        super.doBeforeCreate(loginUserInfo, oblArticleViewRecordEntity);
        return oblArticleViewRecordMapper.insert(oblArticleViewRecordEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, OblArticleViewRecordVo oblArticleViewRecordVo) throws Exception {
        Integer changeCount = 0;
        OblArticleViewRecordEntity oblArticleViewRecordEntity = OblArticleViewRecordTransfer.transferVoToEntity(oblArticleViewRecordVo);
        oblArticleViewRecordEntity = super.doBeforeUpdate(loginUserInfo, oblArticleViewRecordEntity);
        changeCount = oblArticleViewRecordMapper.updateById(oblArticleViewRecordEntity);
        return changeCount;
    }


}