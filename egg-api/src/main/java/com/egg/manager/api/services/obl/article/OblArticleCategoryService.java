package com.egg.manager.api.services.obl.article;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleCategoryEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleCategoryMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleCategoryVo;

/**
 * @author zhoucj
 * @description 文章分类定义表-Service
 * @date 2020-11-30
 */
public interface OblArticleCategoryService extends MyBaseMysqlService<OblArticleCategoryEntity, OblArticleCategoryMapper, OblArticleCategoryVo> {

    /**
     * 分页查询-文章分类定义表 -dto列表
     * (查询的是 com.egg.manager.persistence.obl.article.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleCategoryDto> queryPageBean);


    /**
     * 新增-文章分类定义表
     * @param loginUserInfo        当前登录用户
     * @param oblArticleCategoryVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblArticleCategoryVo oblArticleCategoryVo) throws Exception;

    /**
     * 更新文章分类定义表
     * @param loginUserInfo        当前登录用户
     * @param oblArticleCategoryVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblArticleCategoryVo oblArticleCategoryVo) throws Exception;


}