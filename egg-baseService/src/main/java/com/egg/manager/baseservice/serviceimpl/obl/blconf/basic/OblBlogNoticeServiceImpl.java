package com.egg.manager.baseservice.serviceimpl.obl.blconf.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.blconf.basic.OblBlogNoticeService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.blconf.db.mysql.entity.OblBlogNoticeEntity;
import com.egg.manager.persistence.obl.blconf.db.mysql.mapper.OblBlogNoticeMapper;
import com.egg.manager.persistence.obl.blconf.pojo.dto.OblBlogNoticeDto;
import com.egg.manager.persistence.obl.blconf.pojo.transfer.OblBlogNoticeTransfer;
import com.egg.manager.persistence.obl.blconf.pojo.vo.OblBlogNoticeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 博客通知表-ServiceImpl
 * @date 2020-11-30
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblBlogNoticeService.class)
public class OblBlogNoticeServiceImpl extends MyBaseMysqlServiceImpl<OblBlogNoticeMapper, OblBlogNoticeEntity, OblBlogNoticeVo>
        implements OblBlogNoticeService {

    @Autowired
    private OblBlogNoticeMapper oblBlogNoticeMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblBlogNoticeDto> queryPage) {
        Page<OblBlogNoticeDto> mpPagination = queryPage.toMpPage();
        List<OblBlogNoticeDto> dtoList = oblBlogNoticeMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblBlogNoticeTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblBlogNoticeVo oblBlogNoticeVo) throws Exception {
        OblBlogNoticeEntity oblBlogNoticeEntity = OblBlogNoticeTransfer.transferVoToEntity(oblBlogNoticeVo);
        super.doBeforeCreate(loginUserInfo, oblBlogNoticeEntity);
        return oblBlogNoticeMapper.insert(oblBlogNoticeEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblBlogNoticeVo oblBlogNoticeVo) throws Exception {
        Integer changeCount = 0;
        OblBlogNoticeEntity oblBlogNoticeEntity = OblBlogNoticeTransfer.transferVoToEntity(oblBlogNoticeVo);
        oblBlogNoticeEntity = super.doBeforeUpdate(loginUserInfo, oblBlogNoticeEntity);
        changeCount = oblBlogNoticeMapper.updateById(oblBlogNoticeEntity);
        return changeCount;
    }


}