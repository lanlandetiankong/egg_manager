package com.egg.manager.baseservice.serviceimpl.obl.article.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.article.basic.OblUserCollectArticleService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserCollectArticleEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblUserCollectArticleMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserCollectArticleDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblUserCollectArticleTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserCollectArticleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 用户收藏的文章-ServiceImpl
 * @date 2020-12-03
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblUserCollectArticleService.class)
public class OblUserCollectArticleServiceImpl extends MyBaseMysqlServiceImpl<OblUserCollectArticleMapper, OblUserCollectArticleEntity, OblUserCollectArticleVo>
        implements OblUserCollectArticleService {

    @Autowired
    private OblUserCollectArticleMapper oblUserCollectArticleMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<OblUserCollectArticleDto> queryPage) {
        Page<OblUserCollectArticleDto> mpPagination = queryPage.toMpPage();
        List<OblUserCollectArticleDto> dtoList = oblUserCollectArticleMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage,mpPagination);
        result.putGridList(OblUserCollectArticleTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, OblUserCollectArticleVo oblUserCollectArticleVo) throws Exception {
        OblUserCollectArticleEntity oblUserCollectArticleEntity = OblUserCollectArticleTransfer.transferVoToEntity(oblUserCollectArticleVo);
        super.doBeforeCreate(loginUserInfo, oblUserCollectArticleEntity);
        return oblUserCollectArticleMapper.insert(oblUserCollectArticleEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, OblUserCollectArticleVo oblUserCollectArticleVo) throws Exception {
        Integer changeCount = 0;
        OblUserCollectArticleEntity oblUserCollectArticleEntity = OblUserCollectArticleTransfer.transferVoToEntity(oblUserCollectArticleVo);
        oblUserCollectArticleEntity = super.doBeforeUpdate(loginUserInfo, oblUserCollectArticleEntity);
        changeCount = oblUserCollectArticleMapper.updateById(oblUserCollectArticleEntity);
        return changeCount;
    }


}