package com.egg.manager.api.services.obl.blconf.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.blconf.db.mysql.entity.OblBlogNoticeEntity;
import com.egg.manager.persistence.obl.blconf.db.mysql.mapper.OblBlogNoticeMapper;
import com.egg.manager.persistence.obl.blconf.pojo.dto.OblBlogNoticeDto;
import com.egg.manager.persistence.obl.blconf.pojo.vo.OblBlogNoticeVo;

/**
 * @author zhoucj
 * @description 博客通知表-Service
 * @date 2020-11-30
 */
public interface OblBlogNoticeService extends MyBaseMysqlService<OblBlogNoticeEntity, OblBlogNoticeMapper, OblBlogNoticeVo> {

    /**
     * 分页查询-博客通知表 -dto列表
     * (查询的是 com.egg.manager.persistence.obl.article.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblBlogNoticeDto> queryPageBean);


    /**
     * 新增-博客通知表
     * @param loginUserInfo   当前登录用户
     * @param oblBlogNoticeVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblBlogNoticeVo oblBlogNoticeVo) throws Exception;

    /**
     * 更新博客通知表
     * @param loginUserInfo   当前登录用户
     * @param oblBlogNoticeVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblBlogNoticeVo oblBlogNoticeVo) throws Exception;


}