package com.egg.manager.api.services.em.define.basic;

import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineJobEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineJobMapper;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineJobDto;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineJobVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmDefineJobService extends MyBaseMysqlService<EmDefineJobEntity, EmDefineJobMapper, EmDefineJobVo> {


    /**
     * 分页查询 职务定义 列表
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return
     */
    WebResult dealQueryPageByEntitys(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmDefineJobEntity> queryPageBean);

    /**
     * 分页查询 职务定义 dto列表
     * (查询的 Dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmDefineJobDto> queryPageBean);

    /**
     * 职务账号-新增
     * @param loginUserInfo 当前登录用户
     * @param emDefineJobVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefineJobVo emDefineJobVo) throws Exception;

    /**
     * 职务账号-更新
     * @param loginUserInfo 当前登录用户
     * @param emDefineJobVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmDefineJobVo emDefineJobVo) throws Exception;

    /**
     * 查询指定用户的 用户-职务 关联表
     * @param userAccountId
     * @param stateVal      指定state的值
     * @return
     */
    List<EmDefineJobEntity> findAllByUserAcccountId(String userAccountId, Short stateVal);
}
