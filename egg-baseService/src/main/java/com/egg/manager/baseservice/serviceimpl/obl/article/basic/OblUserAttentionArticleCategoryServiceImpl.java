package com.egg.manager.baseservice.serviceimpl.obl.article.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.article.basic.OblUserAttentionArticleCategoryService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserAttentionArticleCategoryEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblUserAttentionArticleCategoryMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserAttentionArticleCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblUserAttentionArticleCategoryTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserAttentionArticleCategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 用户关注的文章收藏类别-ServiceImpl
 * @date 2020-12-03
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblUserAttentionArticleCategoryService.class)
public class OblUserAttentionArticleCategoryServiceImpl extends MyBaseMysqlServiceImpl<OblUserAttentionArticleCategoryMapper, OblUserAttentionArticleCategoryEntity, OblUserAttentionArticleCategoryVo>
        implements OblUserAttentionArticleCategoryService {

    @Autowired
    private OblUserAttentionArticleCategoryMapper oblUserAttentionArticleCategoryMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblUserAttentionArticleCategoryDto> queryPage) {
        Page<OblUserAttentionArticleCategoryDto> mpPagination = queryPage.toMpPage();
        List<OblUserAttentionArticleCategoryDto> dtoList = oblUserAttentionArticleCategoryMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblUserAttentionArticleCategoryTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblUserAttentionArticleCategoryVo oblUserAttentionArticleCategoryVo) throws Exception {
        OblUserAttentionArticleCategoryEntity oblUserAttentionArticleCategoryEntity = OblUserAttentionArticleCategoryTransfer.transferVoToEntity(oblUserAttentionArticleCategoryVo);
        super.doBeforeCreate(loginUserInfo, oblUserAttentionArticleCategoryEntity);
        return oblUserAttentionArticleCategoryMapper.insert(oblUserAttentionArticleCategoryEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblUserAttentionArticleCategoryVo oblUserAttentionArticleCategoryVo) throws Exception {
        Integer changeCount = 0;
        OblUserAttentionArticleCategoryEntity oblUserAttentionArticleCategoryEntity = OblUserAttentionArticleCategoryTransfer.transferVoToEntity(oblUserAttentionArticleCategoryVo);
        oblUserAttentionArticleCategoryEntity = super.doBeforeUpdate(loginUserInfo, oblUserAttentionArticleCategoryEntity);
        changeCount = oblUserAttentionArticleCategoryMapper.updateById(oblUserAttentionArticleCategoryEntity);
        return changeCount;
    }


}