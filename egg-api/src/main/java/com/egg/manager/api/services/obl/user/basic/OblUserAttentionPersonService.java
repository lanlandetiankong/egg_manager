package com.egg.manager.api.services.obl.user.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserAttentionPersonEntity;
import com.egg.manager.persistence.obl.user.db.mysql.mapper.OblUserAttentionPersonMapper;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserAttentionPersonDto;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserAttentionPersonVo;

/**
 * @author zhoucj
 * @description 用户的关注人关联-Service
 * @date 2020-12-03
 */
public interface OblUserAttentionPersonService extends MyBaseMysqlService<OblUserAttentionPersonEntity, OblUserAttentionPersonMapper, OblUserAttentionPersonVo> {

    /**
     * 分页查询-用户的关注人关联 -dto列表
     * (查询的是 com.egg.manager.persistence.obl.user.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblUserAttentionPersonDto> queryPageBean);


    /**
     * 新增-用户的关注人关联
     * @param loginUserInfo            当前登录用户
     * @param oblUserAttentionPersonVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblUserAttentionPersonVo oblUserAttentionPersonVo) throws Exception;

    /**
     * 更新用户的关注人关联
     * @param loginUserInfo            当前登录用户
     * @param oblUserAttentionPersonVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblUserAttentionPersonVo oblUserAttentionPersonVo) throws Exception;


}