package com.egg.manager.api.services.em.define.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineModuleEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineModuleMapper;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineModuleDto;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineModuleVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmDefineModuleService extends MyBaseMysqlService<EmDefineModuleEntity, EmDefineModuleMapper, EmDefineModuleVo> {

    /**
     * 分页查询 模块 dto列表
     * (查询的是 com.egg.manager.persistence.obl.article.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmDefineModuleDto> queryPageBean);

    /**
     * 模块定义-新增
     * @param loginUserInfo  当前登录用户
     * @param emDefineModuleVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefineModuleVo emDefineModuleVo) throws Exception;

    /**
     * 模块定义-更新
     * @param loginUserInfo  当前登录用户
     * @param emDefineModuleVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmDefineModuleVo emDefineModuleVo) throws Exception;

}
