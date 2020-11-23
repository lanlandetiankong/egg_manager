package com.egg.manager.api.services.em.define.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvSortMap;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.QueryFieldArr;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineTenantEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineTenantMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineTenantDto;
import com.egg.manager.persistence.em.define.pojo.vo.DefineTenantVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface DefineTenantService extends IService<DefineTenantEntity>, MyBaseMysqlService<DefineTenantEntity, DefineTenantMapper, DefineTenantVo> {


    /**
     * 分页查询 租户
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo  当前登录用户
     * @param result
     * @param queryFieldArr
     * @param vpage
     * @param sortMap
     * @return
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, QueryFieldArr queryFieldArr, AntdvPage<DefineTenantDto> vpage,
                                  AntdvSortMap sortMap);

    /**
     * 租户定义-新增
     * @param loginUserInfo  当前登录用户
     * @param defineTenantVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, DefineTenantVo defineTenantVo) throws Exception;

    /**
     * 租户定义-更新
     * @param loginUserInfo  当前登录用户
     * @param defineTenantVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, DefineTenantVo defineTenantVo) throws Exception;

    /**
     * 取得的结果 转为 枚举类型
     * @param loginUserInfo 当前登录用户
     * @param result
     * @return
     */
    WebResult dealResultListToEnums(CurrentLoginUserInfo loginUserInfo, WebResult result);

    /**
     * 租户设置管理员
     * @param loginUserInfo 当前登录用户
     * @param tenantId      租户id
     * @param checkIds      要设置的管理员id
     * @return
     * @throws Exception
     */
    Integer dealTenantSetupManager(CurrentLoginUserInfo loginUserInfo, String tenantId, String[] checkIds) throws Exception;
}
