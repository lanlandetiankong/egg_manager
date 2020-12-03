package com.egg.manager.baseservice.serviceimpl.obl.blconf.basic;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.obl.blconf.basic.OblBlogMenuConfService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.blconf.db.mysql.entity.OblBlogMenuConfEntity;
import com.egg.manager.persistence.obl.blconf.db.mysql.mapper.OblBlogMenuConfMapper;
import com.egg.manager.persistence.obl.blconf.pojo.dto.OblBlogMenuConfDto;
import com.egg.manager.persistence.obl.blconf.pojo.transfer.OblBlogMenuConfTransfer;
import com.egg.manager.persistence.obl.blconf.pojo.vo.OblBlogMenuConfVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description 博客菜单定义表-ServiceImpl
 * @date 2020-11-30
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = OblBlogMenuConfService.class)
public class OblBlogMenuConfServiceImpl extends MyBaseMysqlServiceImpl<OblBlogMenuConfMapper, OblBlogMenuConfEntity, OblBlogMenuConfVo>
        implements OblBlogMenuConfService {

    @Autowired
    private OblBlogMenuConfMapper oblBlogMenuConfMapper;


    @Override
    public WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblBlogMenuConfDto> queryPage) {
        Page<OblBlogMenuConfDto> mpPagination = queryPage.toMpPage();
        List<OblBlogMenuConfDto> dtoList = oblBlogMenuConfMapper.selectQueryPage(mpPagination, queryPage.getQuery(), queryPage.getSortMap());
        result.settingPage(queryPage.getPageConf(), mpPagination.getTotal());
        result.putGridList(OblBlogMenuConfTransfer.transferDtoToVoList(dtoList));
        return result;
    }

    @Override
    public Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblBlogMenuConfVo oblBlogMenuConfVo) throws Exception {
        OblBlogMenuConfEntity oblBlogMenuConfEntity = OblBlogMenuConfTransfer.transferVoToEntity(oblBlogMenuConfVo);
        super.doBeforeCreate(loginUserInfo, oblBlogMenuConfEntity);
        return oblBlogMenuConfMapper.insert(oblBlogMenuConfEntity);
    }


    @Override
    public Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblBlogMenuConfVo oblBlogMenuConfVo) throws Exception {
        Integer changeCount = 0;
        OblBlogMenuConfEntity oblBlogMenuConfEntity = OblBlogMenuConfTransfer.transferVoToEntity(oblBlogMenuConfVo);
        oblBlogMenuConfEntity = super.doBeforeUpdate(loginUserInfo, oblBlogMenuConfEntity);
        changeCount = oblBlogMenuConfMapper.updateById(oblBlogMenuConfEntity);
        return changeCount;
    }


}