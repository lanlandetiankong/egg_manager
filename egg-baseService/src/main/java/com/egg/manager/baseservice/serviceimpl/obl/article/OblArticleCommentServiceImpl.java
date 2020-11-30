package com.egg.manager.baseservice.serviceimpl.obl.article;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.article.OblArticleCommentService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.announcement.pojo.transfer.AnnouncementTagTransfer;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCommentEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleCommentMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCommentDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblArticleCommentTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleCommentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 文章评论表-ServiceImpl
 * @date 2020-11-30
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblArticleCommentService.class)
public class OblArticleCommentServiceImpl extends MyBaseMysqlServiceImpl<OblArticleCommentMapper, OblArticleCommentEntity, OblArticleCommentVo>
        implements OblArticleCommentService {

    @Autowired
    private OblArticleCommentMapper oblArticleCommentMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleCommentDto> queryPage) {
        Page<OblArticleCommentDto> mpPagination = queryPage.toMpPage();
        List<OblArticleCommentDto> dtoList = oblArticleCommentMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblArticleCommentTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblArticleCommentVo oblArticleCommentVo) throws Exception {
        OblArticleCommentEntity oblArticleCommentEntity = OblArticleCommentTransfer.transferVoToEntity(oblArticleCommentVo);
        super.doBeforeCreate(loginUserInfo, oblArticleCommentEntity);
        return oblArticleCommentMapper.insert(oblArticleCommentEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblArticleCommentVo oblArticleCommentVo) throws Exception {
        Integer changeCount = 0;
        OblArticleCommentEntity oblArticleCommentEntity = OblArticleCommentTransfer.transferVoToEntity(oblArticleCommentVo);
        oblArticleCommentEntity = super.doBeforeUpdate(loginUserInfo, oblArticleCommentEntity);
        changeCount = oblArticleCommentMapper.updateById(oblArticleCommentEntity);
        return changeCount;
    }


}