package com.egg.manager.api.services.obl.article;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleTagEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleTagMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleTagDto;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleTagVo;

/**
 * @author zhoucj
 * @description 文章标签定义表-Service
 * @date 2020-11-30
 */
public interface OblArticleTagService extends MyBaseMysqlService<OblArticleTagEntity, OblArticleTagMapper, OblArticleTagVo> {

    /**
     * 分页查询-文章标签定义表 -dto列表
     * (查询的是 com.egg.manager.persistence.obl.article.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleTagDto> queryPageBean);


    /**
     * 新增-文章标签定义表
     * @param loginUserInfo   当前登录用户
     * @param oblArticleTagVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblArticleTagVo oblArticleTagVo) throws Exception;

    /**
     * 更新文章标签定义表
     * @param loginUserInfo   当前登录用户
     * @param oblArticleTagVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblArticleTagVo oblArticleTagVo) throws Exception;


}