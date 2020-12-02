package com.egg.manager.baseservice.serviceimpl.obl.article;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.article.OblContentLikeLogService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblContentLikeLogEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblContentLikeLogMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblContentLikeLogDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblContentLikeLogTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblContentLikeLogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 评论点赞表-ServiceImpl
 * @date 2020-12-02
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblContentLikeLogService.class)
public class OblContentLikeLogServiceImpl extends MyBaseMysqlServiceImpl<OblContentLikeLogMapper, OblContentLikeLogEntity, OblContentLikeLogVo>
        implements OblContentLikeLogService {

    @Autowired
    private OblContentLikeLogMapper oblContentLikeLogMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblContentLikeLogDto> queryPage) {
        Page<OblContentLikeLogDto> mpPagination = queryPage.toMpPage();
        List<OblContentLikeLogDto> dtoList = oblContentLikeLogMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblContentLikeLogTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblContentLikeLogVo oblContentLikeLogVo) throws Exception {
        OblContentLikeLogEntity oblContentLikeLogEntity = OblContentLikeLogTransfer.transferVoToEntity(oblContentLikeLogVo);
        super.doBeforeCreate(loginUserInfo, oblContentLikeLogEntity);
        return oblContentLikeLogMapper.insert(oblContentLikeLogEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblContentLikeLogVo oblContentLikeLogVo) throws Exception {
        Integer changeCount = 0;
        OblContentLikeLogEntity oblContentLikeLogEntity = OblContentLikeLogTransfer.transferVoToEntity(oblContentLikeLogVo);
        oblContentLikeLogEntity = super.doBeforeUpdate(loginUserInfo, oblContentLikeLogEntity);
        changeCount = oblContentLikeLogMapper.updateById(oblContentLikeLogEntity);
        return changeCount;
    }


}