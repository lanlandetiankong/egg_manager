package com.egg.manager.api.services.obl.article.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserAttentionArticleCategoryEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblUserAttentionArticleCategoryMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserAttentionArticleCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserAttentionArticleCategoryVo;

/**
 * @author zhoucj
 * @description 用户关注的文章收藏类别-Service
 * @date 2020-12-03
 */
public interface OblUserAttentionArticleCategoryService extends MyBaseMysqlService<OblUserAttentionArticleCategoryEntity, OblUserAttentionArticleCategoryMapper, OblUserAttentionArticleCategoryVo> {

    /**
     * 分页查询-用户关注的文章收藏类别 -dto列表
     * (查询的是 Dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<OblUserAttentionArticleCategoryDto> queryPageBean);


    /**
     * 新增-用户关注的文章收藏类别
     * @param loginUserInfo                     当前登录用户
     * @param oblUserAttentionArticleCategoryVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, OblUserAttentionArticleCategoryVo oblUserAttentionArticleCategoryVo) throws Exception;

    /**
     * 更新用户关注的文章收藏类别
     * @param loginUserInfo                     当前登录用户
     * @param oblUserAttentionArticleCategoryVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, OblUserAttentionArticleCategoryVo oblUserAttentionArticleCategoryVo) throws Exception;


}