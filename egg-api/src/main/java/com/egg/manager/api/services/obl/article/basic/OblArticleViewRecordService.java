package com.egg.manager.api.services.obl.article.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblArticleViewRecordEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblArticleViewRecordMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblArticleViewRecordDto;
import com.egg.manager.persistence.obl.article.pojo.vo.OblArticleViewRecordVo;

/**
 * @author zhoucj
 * @description 文章查看记录-Service
 * @date 2020-12-04
 */
public interface OblArticleViewRecordService extends MyBaseMysqlService<OblArticleViewRecordEntity, OblArticleViewRecordMapper, OblArticleViewRecordVo> {

    /**
     * 分页查询-文章查看记录 -dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblArticleViewRecordDto> queryPageBean);


    /**
     * 新增-文章查看记录
     * @param loginUserInfo          当前登录用户
     * @param oblArticleViewRecordVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblArticleViewRecordVo oblArticleViewRecordVo) throws Exception;

    /**
     * 更新文章查看记录
     * @param loginUserInfo          当前登录用户
     * @param oblArticleViewRecordVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblArticleViewRecordVo oblArticleViewRecordVo) throws Exception;


}