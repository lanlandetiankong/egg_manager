package com.egg.manager.api.services.basic.organization;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.organization.DefineTenantMapper;
import com.egg.manager.persistence.pojo.mysql.dto.organization.DefineTenantDto;
import com.egg.manager.persistence.pojo.mysql.vo.organization.DefineTenantVo;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface DefineTenantService extends IService<DefineTenant>, MyBaseMysqlService<DefineTenant, DefineTenantMapper, DefineTenantVo> {


    /**
     * 分页查询 租户
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefineTenantVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<DefineTenantVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineTenantDto> paginationBean,
                                                       List<AntdvSortBean> sortBeans);

    /**
     * 租户定义-新增
     * @param loginUser      当前登录用户
     * @param defineTenantVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser, DefineTenantVo defineTenantVo) throws Exception;

    /**
     * 租户定义-更新
     * @param loginUser      当前登录用户
     * @param defineTenantVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser, DefineTenantVo defineTenantVo) throws Exception;

    /**
     * 租户定义-删除
     * @param loginUser 当前登录用户
     * @param delId     要删除的租户id
     * @return
     * @throws Exception
     */
    Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception;


    /**
     * 取得的结果 转为 枚举类型
     * @param loginUser 当前登录用户
     * @param result
     * @return
     */
    MyCommonResult dealResultListToEnums(UserAccount loginUser, MyCommonResult result);

    /**
     * 租户设置管理员
     * @param loginUser 当前登录用户
     * @param tenantId  租户id
     * @param checkIds  要设置的管理员id
     * @return
     * @throws Exception
     */
    Integer dealTenantSetupManager(UserAccount loginUser, String tenantId, String[] checkIds) throws Exception;
}
