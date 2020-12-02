package com.egg.manager.api.services.obl.article;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleLikeLogEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleLikeLogMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleLikeLogDto;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleLikeLogVo;

/**
 * @author zhoucj
 * @description 文章点赞表-Service
 * @date 2020-12-02
 */
public interface OblArticleLikeLogService extends MyBaseMysqlService<OblArticleLikeLogEntity, OblArticleLikeLogMapper, OblArticleLikeLogVo> {

    /**
     * 分页查询-文章点赞表 -dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleLikeLogDto> queryPageBean);


    /**
     * 新增-文章点赞表
     * @param loginUserInfo       当前登录用户
     * @param oblArticleLikeLogVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblArticleLikeLogVo oblArticleLikeLogVo) throws Exception;

    /**
     * 更新文章点赞表
     * @param loginUserInfo       当前登录用户
     * @param oblArticleLikeLogVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblArticleLikeLogVo oblArticleLikeLogVo) throws Exception;


}