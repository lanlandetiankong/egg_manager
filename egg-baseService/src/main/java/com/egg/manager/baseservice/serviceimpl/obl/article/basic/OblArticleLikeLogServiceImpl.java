package com.egg.manager.baseservice.serviceimpl.obl.article.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.article.basic.OblArticleLikeLogService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleLikeLogEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleLikeLogMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleLikeLogDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblArticleLikeLogTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleLikeLogVo;
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
@Service(interfaceClass = OblArticleLikeLogService.class)
public class OblArticleLikeLogServiceImpl extends MyBaseMysqlServiceImpl<OblArticleLikeLogMapper, OblArticleLikeLogEntity, OblArticleLikeLogVo>
        implements OblArticleLikeLogService {

    @Autowired
    private OblArticleLikeLogMapper oblArticleLikeLogMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleLikeLogDto> queryPage) {
        Page<OblArticleLikeLogDto> mpPagination = queryPage.toMpPage();
        List<OblArticleLikeLogDto> dtoList = oblArticleLikeLogMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblArticleLikeLogTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblArticleLikeLogVo oblArticleLikeLogVo) throws Exception {
        OblArticleLikeLogEntity oblArticleLikeLogEntity = OblArticleLikeLogTransfer.transferVoToEntity(oblArticleLikeLogVo);
        super.doBeforeCreate(loginUserInfo, oblArticleLikeLogEntity);
        return oblArticleLikeLogMapper.insert(oblArticleLikeLogEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblArticleLikeLogVo oblArticleLikeLogVo) throws Exception {
        Integer changeCount = 0;
        OblArticleLikeLogEntity oblArticleLikeLogEntity = OblArticleLikeLogTransfer.transferVoToEntity(oblArticleLikeLogVo);
        oblArticleLikeLogEntity = super.doBeforeUpdate(loginUserInfo, oblArticleLikeLogEntity);
        changeCount = oblArticleLikeLogMapper.updateById(oblArticleLikeLogEntity);
        return changeCount;
    }


}