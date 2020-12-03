package com.egg.manager.baseservice.serviceimpl.obl.article;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.article.OblArticleTagService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleTagEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleTagMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleTagDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblArticleTagTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleTagVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 文章标签定义表-ServiceImpl
 * @date 2020-11-30
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblArticleTagService.class)
public class OblArticleTagServiceImpl extends MyBaseMysqlServiceImpl<OblArticleTagMapper, OblArticleTagEntity, OblArticleTagVo>
        implements OblArticleTagService {

    @Autowired
    private OblArticleTagMapper oblArticleTagMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleTagDto> queryPage) {
        Page<OblArticleTagDto> mpPagination = queryPage.toMpPage();
        List<OblArticleTagDto> dtoList = oblArticleTagMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblArticleTagTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblArticleTagVo oblArticleTagVo) throws Exception {
        OblArticleTagEntity oblArticleTagEntity = OblArticleTagTransfer.transferVoToEntity(oblArticleTagVo);
        super.doBeforeCreate(loginUserInfo, oblArticleTagEntity);
        return oblArticleTagMapper.insert(oblArticleTagEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblArticleTagVo oblArticleTagVo) throws Exception {
        Integer changeCount = 0;
        OblArticleTagEntity oblArticleTagEntity = OblArticleTagTransfer.transferVoToEntity(oblArticleTagVo);
        oblArticleTagEntity = super.doBeforeUpdate(loginUserInfo, oblArticleTagEntity);
        changeCount = oblArticleTagMapper.updateById(oblArticleTagEntity);
        return changeCount;
    }


}