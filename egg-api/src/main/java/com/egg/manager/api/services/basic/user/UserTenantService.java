package com.egg.manager.api.services.basic.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.db.mysql.mapper.user.UserTenantMapper;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserTenantDto;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserTenantVo;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface UserTenantService extends IService<UserTenant>,MyBaseMysqlService<UserTenantMapper,UserTenant,UserTenantVo> {
    /**
     * 取得当前用户关联的 UserTenant
     *
     * @return
     */
    List<UserTenant> dealGetAllByAccount(UserAccount account);
    /**
     * 从数据库是中取得当前用户关联的 UserTenant
     *
     * @return
     */
    List<UserTenant> dealGetAllByAccountFromDb(UserAccount userAccount);
    /**
     * 从Redis中取得当前用户关联的 UserTenant
     *
     * @return
     */
    List<UserTenant> dealGetAllByAccountFromRedis(UserAccount userAccount);


    /**
     * 分页查询 用户与租户关联列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<UserTenantVo> dealQueryPageByEntitys(UserAccount loginUser,MyCommonResult<UserTenantVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserTenant> paginationBean,
                                                        List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 用户与租户关联 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<UserTenantVo> dealQueryPageByDtos(UserAccount loginUser,MyCommonResult<UserTenantVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserTenantDto> paginationBean,
                                                           List<AntdvSortBean> sortBeans);


    /**
     * 用户与租户关联-新增
     * @param userTenantVo
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser,UserTenantVo userTenantVo) throws Exception;

    /**
     * 用户与租户关联-更新
     * @param userTenantVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser,UserTenantVo userTenantVo, boolean updateAll) throws Exception;

    /**
     * 用户与租户关联-删除
     * @param delIds 要删除的用户与租户关联id 集合
     * @throws Exception
     */
    Integer dealBatchDelete(UserAccount loginUser,String[] delIds) throws Exception ;

    /**
     * 用户与租户关联-删除
     * @param delId 要删除的用户与租户关联id
     * @throws Exception
     */
    Integer dealDeleteById(UserAccount loginUser,String delId) throws Exception ;
}
