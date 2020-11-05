package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserTenant;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserTenantMapper;
import com.egg.manager.persistence.em.user.pojo.dto.UserTenantDto;
import com.egg.manager.persistence.em.user.pojo.vo.UserTenantVo;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface UserTenantService extends IService<UserTenant>, MyBaseMysqlService<UserTenant, UserTenantMapper, UserTenantVo> {
    /**
     * 取得当前用户关联的 UserTenant
     * @param userAccount
     * @return
     */
    List<UserTenant> dealGetAllByAccount(UserAccount userAccount);

    /**
     * 从数据库是中取得当前用户关联的 UserTenant
     * @param userAccount
     * @return
     */
    List<UserTenant> dealGetAllByAccountFromDb(UserAccount userAccount);

    /**
     * 从Redis中取得当前用户关联的 UserTenant
     * @param userAccount
     * @return
     */
    List<UserTenant> dealGetAllByAccountFromRedis(UserAccount userAccount);


    /**
     * 分页查询 用户与租户关联列表
     * @param loginUser              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserTenantVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<UserTenantVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserTenant> paginationBean,
                                                        List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 用户与租户关联 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserTenantVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<UserTenantVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserTenantDto> paginationBean,
                                                     List<AntdvSortBean> sortBeans);


    /**
     * 用户与租户关联-新增
     * @param loginUser    当前登录用户
     * @param userTenantVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser, UserTenantVo userTenantVo) throws Exception;

    /**
     * 用户与租户关联-更新
     * @param loginUser    当前登录用户
     * @param userTenantVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser, UserTenantVo userTenantVo) throws Exception;

}
