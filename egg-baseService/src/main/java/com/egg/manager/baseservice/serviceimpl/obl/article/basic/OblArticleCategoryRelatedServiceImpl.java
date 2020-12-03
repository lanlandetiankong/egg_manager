package com.egg.manager.baseservice.serviceimpl.obl.article.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.article.basic.OblArticleCategoryRelatedService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCategoryRelatedEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleCategoryRelatedMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCategoryRelatedDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblArticleCategoryRelatedTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleCategoryRelatedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 文章分类关联表-ServiceImpl
 * @date 2020-11-30
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblArticleCategoryRelatedService.class)
public class OblArticleCategoryRelatedServiceImpl extends MyBaseMysqlServiceImpl<OblArticleCategoryRelatedMapper, OblArticleCategoryRelatedEntity, OblArticleCategoryRelatedVo>
        implements OblArticleCategoryRelatedService {

    @Autowired
    private OblArticleCategoryRelatedMapper oblArticleCategoryRelatedMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleCategoryRelatedDto> queryPage) {
        Page<OblArticleCategoryRelatedDto> mpPagination = queryPage.toMpPage();
        List<OblArticleCategoryRelatedDto> dtoList = oblArticleCategoryRelatedMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblArticleCategoryRelatedTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblArticleCategoryRelatedVo oblArticleCategoryRelatedVo) throws Exception {
        OblArticleCategoryRelatedEntity oblArticleCategoryRelatedEntity = OblArticleCategoryRelatedTransfer.transferVoToEntity(oblArticleCategoryRelatedVo);
        super.doBeforeCreate(loginUserInfo, oblArticleCategoryRelatedEntity);
        return oblArticleCategoryRelatedMapper.insert(oblArticleCategoryRelatedEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblArticleCategoryRelatedVo oblArticleCategoryRelatedVo) throws Exception {
        Integer changeCount = 0;
        OblArticleCategoryRelatedEntity oblArticleCategoryRelatedEntity = OblArticleCategoryRelatedTransfer.transferVoToEntity(oblArticleCategoryRelatedVo);
        oblArticleCategoryRelatedEntity = super.doBeforeUpdate(loginUserInfo, oblArticleCategoryRelatedEntity);
        changeCount = oblArticleCategoryRelatedMapper.updateById(oblArticleCategoryRelatedEntity);
        return changeCount;
    }


}