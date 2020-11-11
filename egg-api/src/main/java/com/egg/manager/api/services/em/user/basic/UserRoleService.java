package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserRoleEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserRoleMapper;
import com.egg.manager.persistence.em.user.pojo.dto.UserRoleDto;
import com.egg.manager.persistence.em.user.pojo.vo.UserRoleVo;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface UserRoleService extends IService<UserRoleEntity>, MyBaseMysqlService<UserRoleEntity, UserRoleMapper, UserRoleVo> {
    /**
     * 取得当前用户关联的 UserRole
     * @param account
     * @return
     */
    List<UserRoleEntity> dealGetAllByAccount(UserAccountEntity account);

    /**
     * 从数据库是中取得当前用户关联的 UserRole
     * @param userAccountEntity
     * @return
     */
    List<UserRoleEntity> dealGetAllByAccountFromDb(UserAccountEntity userAccountEntity);

    /**
     * 从Redis中取得当前用户关联的 UserRole
     * @param userAccountEntity
     * @return
     */
    List<UserRoleEntity> dealGetAllByAccountFromRedis(UserAccountEntity userAccountEntity);


    /**
     * 分页查询 用户角色列表
     * @param loginUser              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserRoleVo> dealQueryPageByEntitys(UserAccountEntity loginUser, MyCommonResult<UserRoleVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserRoleEntity> paginationBean,
                                                      List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 用户角色 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserRoleVo> dealQueryPageByDtos(UserAccountEntity loginUser, MyCommonResult<UserRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserRoleDto> paginationBean,
                                                   List<AntdvSortBean> sortBeans);


    /**
     * 用户角色-新增
     * @param loginUser  当前登录用户
     * @param userRoleVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccountEntity loginUser, UserRoleVo userRoleVo) throws Exception;

    /**
     * 用户角色-更新
     * @param loginUser  当前登录用户
     * @param userRoleVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccountEntity loginUser, UserRoleVo userRoleVo) throws Exception;

}