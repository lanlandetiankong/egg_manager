package com.egg.manager.api.services.obl.user;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserCalculateInfoEntity;
import com.egg.manager.persistence.obl.user.db.mysql.mapper.OblUserCalculateInfoMapper;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserCalculateInfoDto;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserCalculateInfoVo;

/**
 * @author zhoucj
 * @description 用户的计算信息-Service
 * @date 2020-12-03
 */
public interface OblUserCalculateInfoService extends MyBaseMysqlService<OblUserCalculateInfoEntity, OblUserCalculateInfoMapper, OblUserCalculateInfoVo> {

    /**
     * 分页查询-用户的计算信息 -dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return WebResult
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryPageBean<OblUserCalculateInfoDto> queryPageBean);


    /**
     * 新增-用户的计算信息
     * @param loginUserInfo          当前登录用户
     * @param oblUserCalculateInfoVo
     * @return Integer
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, OblUserCalculateInfoVo oblUserCalculateInfoVo) throws Exception;

    /**
     * 更新用户的计算信息
     * @param loginUserInfo          当前登录用户
     * @param oblUserCalculateInfoVo
     * @return Integer
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, OblUserCalculateInfoVo oblUserCalculateInfoVo) throws Exception;


}