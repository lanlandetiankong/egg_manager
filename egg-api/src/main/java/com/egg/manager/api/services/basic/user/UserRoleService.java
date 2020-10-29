package com.egg.manager.api.services.basic.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.db.mysql.mapper.user.UserRoleMapper;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserRoleDto;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserRoleVo;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface UserRoleService extends IService<UserRole>, MyBaseMysqlService<UserRole, UserRoleMapper, UserRoleVo> {
    /**
     * 取得当前用户关联的 UserRole
     * @param account
     * @return
     */
    List<UserRole> dealGetAllByAccount(UserAccount account);

    /**
     * 从数据库是中取得当前用户关联的 UserRole
     * @param userAccount
     * @return
     */
    List<UserRole> dealGetAllByAccountFromDb(UserAccount userAccount);

    /**
     * 从Redis中取得当前用户关联的 UserRole
     * @param userAccount
     * @return
     */
    List<UserRole> dealGetAllByAccountFromRedis(UserAccount userAccount);


    /**
     * 分页查询 用户角色列表
     * @param loginUser              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserRoleVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<UserRoleVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserRole> paginationBean,
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
    MyCommonResult<UserRoleVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<UserRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserRoleDto> paginationBean,
                                                   List<AntdvSortBean> sortBeans);


    /**
     * 用户角色-新增
     * @param loginUser  当前登录用户
     * @param userRoleVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser, UserRoleVo userRoleVo) throws Exception;

    /**
     * 用户角色-更新
     * @param loginUser  当前登录用户
     * @param userRoleVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser, UserRoleVo userRoleVo) throws Exception;

}
