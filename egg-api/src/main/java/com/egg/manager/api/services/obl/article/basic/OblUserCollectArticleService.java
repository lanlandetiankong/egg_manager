package com.egg.manager.api.services.obl.article.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserCollectArticleEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblUserCollectArticleMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserCollectArticleDto;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserCollectArticleVo;

/**
 * @author zhoucj
 * @description 用户收藏的文章-Service
 * @date 2020-12-03
 */
public interface OblUserCollectArticleService extends MyBaseMysqlService<OblUserCollectArticleEntity, OblUserCollectArticleMapper, OblUserCollectArticleVo> {

    /**
     * 分页查询-用户收藏的文章 -dto列表
     * (查询的是 Dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<OblUserCollectArticleDto> queryPageBean);


    /**
     * 新增-用户收藏的文章
     * @param loginUserInfo           当前登录用户
     * @param oblUserCollectArticleVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, OblUserCollectArticleVo oblUserCollectArticleVo) throws Exception;

    /**
     * 更新用户收藏的文章
     * @param loginUserInfo           当前登录用户
     * @param oblUserCollectArticleVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, OblUserCollectArticleVo oblUserCollectArticleVo) throws Exception;


}