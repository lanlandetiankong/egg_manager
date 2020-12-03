package com.egg.manager.api.services.obl.article.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.article.db.mysql.entity.OblUserDefCollectCategoryEntity;
import com.egg.manager.persistence.obl.article.db.mysql.mapper.OblUserDefCollectCategoryMapper;
import com.egg.manager.persistence.obl.article.pojo.dto.OblUserDefCollectCategoryDto;
import com.egg.manager.persistence.obl.article.pojo.vo.OblUserDefCollectCategoryVo;

/**
 * @author zhoucj
 * @description 用户定义的收藏类别-Service
 * @date 2020-12-03
 */
public interface OblUserDefCollectCategoryService extends MyBaseMysqlService<OblUserDefCollectCategoryEntity, OblUserDefCollectCategoryMapper, OblUserDefCollectCategoryVo> {

    /**
     * 分页查询-用户定义的收藏类别 -dto列表
     * (查询的是 com.egg.manager.persistence.obl.user.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblUserDefCollectCategoryDto> queryPageBean);


    /**
     * 新增-用户定义的收藏类别
     * @param loginUserInfo               当前登录用户
     * @param oblUserDefCollectCategoryVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblUserDefCollectCategoryVo oblUserDefCollectCategoryVo) throws Exception;

    /**
     * 更新用户定义的收藏类别
     * @param loginUserInfo               当前登录用户
     * @param oblUserDefCollectCategoryVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblUserDefCollectCategoryVo oblUserDefCollectCategoryVo) throws Exception;


}