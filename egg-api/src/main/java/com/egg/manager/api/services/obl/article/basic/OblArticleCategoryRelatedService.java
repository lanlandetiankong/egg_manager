package com.egg.manager.api.services.obl.article.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCategoryRelatedEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleCategoryRelatedMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCategoryRelatedDto;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleCategoryRelatedVo;

/**
 * @author zhoucj
 * @description 文章分类关联表-Service
 * @date 2020-11-30
 */
public interface OblArticleCategoryRelatedService extends MyBaseMysqlService<OblArticleCategoryRelatedEntity, OblArticleCategoryRelatedMapper, OblArticleCategoryRelatedVo> {

    /**
     * 分页查询-文章分类关联表 -dto列表
     * (查询的是 com.egg.manager.persistence.obl.article.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleCategoryRelatedDto> queryPageBean);


    /**
     * 新增-文章分类关联表
     * @param loginUserInfo               当前登录用户
     * @param oblArticleCategoryRelatedVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, OblArticleCategoryRelatedVo oblArticleCategoryRelatedVo) throws Exception;

    /**
     * 更新文章分类关联表
     * @param loginUserInfo               当前登录用户
     * @param oblArticleCategoryRelatedVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, OblArticleCategoryRelatedVo oblArticleCategoryRelatedVo) throws Exception;


}