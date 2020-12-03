package com.egg.manager.baseservice.serviceimpl.obl.article.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.article.basic.OblArticleTagRelatedService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleTagRelatedEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleTagRelatedMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleTagRelatedDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblArticleTagRelatedTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleTagRelatedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 文章与标签关联表-ServiceImpl
 * @date 2020-11-30
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblArticleTagRelatedService.class)
public class OblArticleTagRelatedServiceImpl extends MyBaseMysqlServiceImpl<OblArticleTagRelatedMapper, OblArticleTagRelatedEntity, OblArticleTagRelatedVo>
        implements OblArticleTagRelatedService {

    @Autowired
    private OblArticleTagRelatedMapper oblArticleTagRelatedMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleTagRelatedDto> queryPage) {
        Page<OblArticleTagRelatedDto> mpPagination = queryPage.toMpPage();
        List<OblArticleTagRelatedDto> dtoList = oblArticleTagRelatedMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblArticleTagRelatedTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblArticleTagRelatedVo oblArticleTagRelatedVo) throws Exception {
        OblArticleTagRelatedEntity oblArticleTagRelatedEntity = OblArticleTagRelatedTransfer.transferVoToEntity(oblArticleTagRelatedVo);
        super.doBeforeCreate(loginUserInfo, oblArticleTagRelatedEntity);
        return oblArticleTagRelatedMapper.insert(oblArticleTagRelatedEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblArticleTagRelatedVo oblArticleTagRelatedVo) throws Exception {
        Integer changeCount = 0;
        OblArticleTagRelatedEntity oblArticleTagRelatedEntity = OblArticleTagRelatedTransfer.transferVoToEntity(oblArticleTagRelatedVo);
        oblArticleTagRelatedEntity = super.doBeforeUpdate(loginUserInfo, oblArticleTagRelatedEntity);
        changeCount = oblArticleTagRelatedMapper.updateById(oblArticleTagRelatedEntity);
        return changeCount;
    }


}