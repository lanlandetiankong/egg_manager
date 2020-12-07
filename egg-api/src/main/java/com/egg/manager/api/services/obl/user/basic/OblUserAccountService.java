package com.egg.manager.api.services.obl.user.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserAccountEntity;
import com.egg.manager.persistence.obl.user.db.mysql.mapper.OblUserAccountMapper;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserAccountDto;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserAccountVo;

/**
 * @author zhoucj
 * @description 用户表-Service
 * @date 2020-12-07
 */
public interface OblUserAccountService extends MyBaseMysqlService<OblUserAccountEntity, OblUserAccountMapper, OblUserAccountVo> {

    /**
     * 分页查询-用户表 -dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblUserAccountDto> queryPageBean);


    /**
     * 新增-用户表
     * @param loginUserInfo    当前登录用户
     * @param oblUserAccountVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblUserAccountVo oblUserAccountVo) throws Exception;

    /**
     * 更新用户表
     * @param loginUserInfo    当前登录用户
     * @param oblUserAccountVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblUserAccountVo oblUserAccountVo) throws Exception;


}