package com.egg.manager.api.services.obl.article.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCommentEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleCommentMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCommentDto;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleCommentVo;

/**
 * @author zhoucj
 * @description 文章评论表-Service
 * @date 2020-11-30
 */
public interface OblArticleCommentService extends MyBaseMysqlService<OblArticleCommentEntity, OblArticleCommentMapper, OblArticleCommentVo> {

    /**
     * 分页查询-文章评论表 -dto列表
     * (查询的是 com.egg.manager.persistence.obl.article.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleCommentDto> queryPageBean);


    /**
     * 新增-文章评论表
     * @param loginUserInfo       当前登录用户
     * @param oblArticleCommentVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblArticleCommentVo oblArticleCommentVo) throws Exception;

    /**
     * 更新文章评论表
     * @param loginUserInfo       当前登录用户
     * @param oblArticleCommentVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblArticleCommentVo oblArticleCommentVo) throws Exception;


}