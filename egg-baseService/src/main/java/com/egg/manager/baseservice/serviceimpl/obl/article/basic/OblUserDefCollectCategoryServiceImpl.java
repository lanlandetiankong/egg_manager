package com.egg.manager.baseservice.serviceimpl.obl.article.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.article.basic.OblUserDefCollectCategoryService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserDefCollectCategoryEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblUserDefCollectCategoryMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserDefCollectCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.transfer.OblUserDefCollectCategoryTransfer;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserDefCollectCategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 用户定义的收藏类别-ServiceImpl
 * @date 2020-12-03
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblUserDefCollectCategoryService.class)
public class OblUserDefCollectCategoryServiceImpl extends MyBaseMysqlServiceImpl<OblUserDefCollectCategoryMapper, OblUserDefCollectCategoryEntity, OblUserDefCollectCategoryVo>
        implements OblUserDefCollectCategoryService {

    @Autowired
    private OblUserDefCollectCategoryMapper oblUserDefCollectCategoryMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblUserDefCollectCategoryDto> queryPage) {
        Page<OblUserDefCollectCategoryDto> mpPagination = queryPage.toMpPage();
        List<OblUserDefCollectCategoryDto> dtoList = oblUserDefCollectCategoryMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblUserDefCollectCategoryTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblUserDefCollectCategoryVo oblUserDefCollectCategoryVo) throws Exception {
        OblUserDefCollectCategoryEntity oblUserDefCollectCategoryEntity = OblUserDefCollectCategoryTransfer.transferVoToEntity(oblUserDefCollectCategoryVo);
        super.doBeforeCreate(loginUserInfo, oblUserDefCollectCategoryEntity);
        return oblUserDefCollectCategoryMapper.insert(oblUserDefCollectCategoryEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblUserDefCollectCategoryVo oblUserDefCollectCategoryVo) throws Exception {
        Integer changeCount = 0;
        OblUserDefCollectCategoryEntity oblUserDefCollectCategoryEntity = OblUserDefCollectCategoryTransfer.transferVoToEntity(oblUserDefCollectCategoryVo);
        oblUserDefCollectCategoryEntity = super.doBeforeUpdate(loginUserInfo, oblUserDefCollectCategoryEntity);
        changeCount = oblUserDefCollectCategoryMapper.updateById(oblUserDefCollectCategoryEntity);
        return changeCount;
    }


}