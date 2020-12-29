package com.egg.manager.facade.api.services.em.user.basic;

import com.egg.manager.facade.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAppRelatedEntity;
import com.egg.manager.facade.persistence.em.user.db.mysql.mapper.EmUserAppRelatedMapper;
import com.egg.manager.facade.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.facade.persistence.em.user.pojo.dto.EmUserAppRelatedDto;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserAppRelatedVo;

/**
 * @author zhoucj
 * @description app用户关联表-Service
 * @date 2020-12-07
 */
public interface EmUserAppRelatedService extends MyBaseMysqlService<EmUserAppRelatedEntity, EmUserAppRelatedMapper, EmUserAppRelatedVo> {

    /**
     * 分页查询-app用户关联表 -dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmUserAppRelatedDto> queryPageBean);


    /**
     * 新增-app用户关联表
     * @param loginUserInfo      当前登录用户
     * @param emUserAppRelatedVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmUserAppRelatedVo emUserAppRelatedVo) throws Exception;

    /**
     * 更新app用户关联表
     * @param loginUserInfo      当前登录用户
     * @param emUserAppRelatedVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmUserAppRelatedVo emUserAppRelatedVo) throws Exception;


}