package com.egg.manager.baseservice.serviceimpl.obl.article.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.article.basic.OblArticleCategoryService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCategoryEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleCategoryMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblArticleCategoryTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleCategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 文章分类定义表-ServiceImpl
 * @date 2020-11-30
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblArticleCategoryService.class)
public class OblArticleCategoryServiceImpl extends MyBaseMysqlServiceImpl<OblArticleCategoryMapper, OblArticleCategoryEntity, OblArticleCategoryVo>
        implements OblArticleCategoryService {

    @Autowired
    private OblArticleCategoryMapper oblArticleCategoryMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleCategoryDto> queryPage) {
        Page<OblArticleCategoryDto> mpPagination = queryPage.toMpPage();
        List<OblArticleCategoryDto> dtoList = oblArticleCategoryMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblArticleCategoryTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, OblArticleCategoryVo oblArticleCategoryVo) throws Exception {
        OblArticleCategoryEntity oblArticleCategoryEntity = OblArticleCategoryTransfer.transferVoToEntity(oblArticleCategoryVo);
        super.doBeforeCreate(loginUserInfo, oblArticleCategoryEntity);
        return oblArticleCategoryMapper.insert(oblArticleCategoryEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, OblArticleCategoryVo oblArticleCategoryVo) throws Exception {
        Integer changeCount = 0;
        OblArticleCategoryEntity oblArticleCategoryEntity = OblArticleCategoryTransfer.transferVoToEntity(oblArticleCategoryVo);
        oblArticleCategoryEntity = super.doBeforeUpdate(loginUserInfo, oblArticleCategoryEntity);
        changeCount = oblArticleCategoryMapper.updateById(oblArticleCategoryEntity);
        return changeCount;
    }


}