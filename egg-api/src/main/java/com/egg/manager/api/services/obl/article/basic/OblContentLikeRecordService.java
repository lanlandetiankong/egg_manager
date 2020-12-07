package com.egg.manager.api.services.obl.article.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblContentLikeRecordEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblContentLikeRecordMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblContentLikeRecordDto;
import com.egg.manager.persistence.obl.article.pojo.vo.OblContentLikeRecordVo;

/**
 * @author zhoucj
 * @description 评论点赞表-Service
 * @date 2020-12-02
 */
public interface OblContentLikeRecordService extends MyBaseMysqlService<OblContentLikeRecordEntity, OblContentLikeRecordMapper, OblContentLikeRecordVo> {

    /**
     * 分页查询-评论点赞表 -dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<OblContentLikeRecordDto> queryPageBean);


    /**
     * 新增-评论点赞表
     * @param loginUserInfo       当前登录用户
     * @param oblContentLikeRecordVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, OblContentLikeRecordVo oblContentLikeRecordVo) throws Exception;

    /**
     * 更新评论点赞表
     * @param loginUserInfo       当前登录用户
     * @param oblContentLikeRecordVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, OblContentLikeRecordVo oblContentLikeRecordVo) throws Exception;


}