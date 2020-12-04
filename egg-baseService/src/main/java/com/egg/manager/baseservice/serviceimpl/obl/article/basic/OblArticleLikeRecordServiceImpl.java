package com.egg.manager.baseservice.serviceimpl.obl.article.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.article.basic.OblArticleLikeRecordService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleLikeRecordEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleLikeRecordMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleLikeRecordDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblArticleLikeRecordTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleLikeRecordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 文章点赞表-ServiceImpl
 * @date 2020-12-02
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblArticleLikeRecordService.class)
public class OblArticleLikeRecordServiceImpl extends MyBaseMysqlServiceImpl<OblArticleLikeRecordMapper, OblArticleLikeRecordEntity, OblArticleLikeRecordVo>
        implements OblArticleLikeRecordService {

    @Autowired
    private OblArticleLikeRecordMapper oblArticleLikeRecordMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleLikeRecordDto> queryPage) {
        Page<OblArticleLikeRecordDto> mpPagination = queryPage.toMpPage();
        List<OblArticleLikeRecordDto> dtoList = oblArticleLikeRecordMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblArticleLikeRecordTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblArticleLikeRecordVo oblArticleLikeRecordVo) throws Exception {
        OblArticleLikeRecordEntity oblArticleLikeRecordEntity = OblArticleLikeRecordTransfer.transferVoToEntity(oblArticleLikeRecordVo);
        super.doBeforeCreate(loginUserInfo, oblArticleLikeRecordEntity);
        return oblArticleLikeRecordMapper.insert(oblArticleLikeRecordEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblArticleLikeRecordVo oblArticleLikeRecordVo) throws Exception {
        Integer changeCount = 0;
        OblArticleLikeRecordEntity oblArticleLikeRecordEntity = OblArticleLikeRecordTransfer.transferVoToEntity(oblArticleLikeRecordVo);
        oblArticleLikeRecordEntity = super.doBeforeUpdate(loginUserInfo, oblArticleLikeRecordEntity);
        changeCount = oblArticleLikeRecordMapper.updateById(oblArticleLikeRecordEntity);
        return changeCount;
    }


}