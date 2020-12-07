package com.egg.manager.api.services.em.define.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineTenantEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefineTenantMapper;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineTenantDto;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineTenantVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmDefineTenantService extends MyBaseMysqlService<EmDefineTenantEntity, EmDefineTenantMapper, EmDefineTenantVo> {


    /**
     * 分页查询 租户
     * (查询的是 com.egg.manager.persistence.obl.article.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean
     * @return
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmDefineTenantDto> queryPageBean);

    /**
     * 租户定义-新增
     * @param loginUserInfo  当前登录用户
     * @param emDefineTenantVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefineTenantVo emDefineTenantVo) throws Exception;

    /**
     * 租户定义-更新
     * @param loginUserInfo  当前登录用户
     * @param emDefineTenantVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmDefineTenantVo emDefineTenantVo) throws Exception;

    /**
     * 取得的结果 转为 枚举类型
     * @param loginUserInfo 当前登录用户
     * @param result
     * @return
     */
    WebResult dealResultListToEnums(CurrentLoginEmUserInfo loginUserInfo, WebResult result);

    /**
     * 租户设置管理员
     * @param loginUserInfo 当前登录用户
     * @param tenantId      租户id
     * @param checkIds      要设置的管理员id
     * @return
     * @throws Exception
     */
    Integer dealTenantSetupManager(CurrentLoginEmUserInfo loginUserInfo, String tenantId, String[] checkIds) throws Exception;
}
