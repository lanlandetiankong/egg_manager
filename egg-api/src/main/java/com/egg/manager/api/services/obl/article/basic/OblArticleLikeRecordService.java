package com.egg.manager.api.services.obl.article.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleLikeRecordEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleLikeRecordMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleLikeRecordDto;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleLikeRecordVo;

/**
 * @author zhoucj
 * @description 文章点赞表-Service
 * @date 2020-12-02
 */
public interface OblArticleLikeRecordService extends MyBaseMysqlService<OblArticleLikeRecordEntity, OblArticleLikeRecordMapper, OblArticleLikeRecordVo> {

    /**
     * 分页查询-文章点赞表 -dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleLikeRecordDto> queryPageBean);


    /**
     * 新增-文章点赞表
     * @param loginUserInfo       当前登录用户
     * @param oblArticleLikeRecordVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, OblArticleLikeRecordVo oblArticleLikeRecordVo) throws Exception;

    /**
     * 更新文章点赞表
     * @param loginUserInfo       当前登录用户
     * @param oblArticleLikeRecordVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, OblArticleLikeRecordVo oblArticleLikeRecordVo) throws Exception;


}