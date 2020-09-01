package com.egg.manager.api.services.basic.user;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserDepartment;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserDepartmentVo;

import java.util.List;

/**
 * 用户-部门 关联service
 * \* note:
 * \* User: zhouchengjie
 * \* Description:
 * \
 */
public interface UserDepartmentService extends IService<UserDepartment> {

    /**
     * 查询账号下的所有[用户-部门关联]
     * @param account
     * @return
     */
    List<UserDepartment> dealGetAllUserDepartmentByAccount(UserAccount account);

    List<UserDepartment> dealGetAllUserDepartmentByAccountFromDb(UserAccount userAccount);

    List<UserDepartment> dealGetAllUserDepartmentByAccountFromRedis(UserAccount userAccount);


    /**
     * 分页查询 用户与部门关联列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<UserDepartmentVo> dealGetUserDepartmentPages(MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                                                List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 用户与部门关联 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<UserDepartmentVo> dealGetUserDepartmentDtoPages(MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                   List<AntdvSortBean> sortBeans);


    /**
     * 用户与部门关联-新增
     * @param userDepartmentVo
     * @throws Exception
     */
    Integer dealAddUserDepartment(UserDepartmentVo userDepartmentVo, UserAccount loginUser) throws Exception;

    /**
     * 用户与部门关联-更新
     * @param userDepartmentVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateUserDepartment(UserDepartmentVo userDepartmentVo, UserAccount loginUser, boolean updateAll) throws Exception;

    /**
     * 用户与部门关联-删除
     * @param delIds 要删除的用户与部门关联id 集合
     * @throws Exception
     */
    Integer dealDelUserDepartmentByArr(String[] delIds, UserAccount loginUser) throws Exception ;

    /**
     * 用户与部门关联-删除
     * @param delId 要删除的用户与部门关联id
     * @throws Exception
     */
    Integer dealDelUserDepartment(String delId, UserAccount loginUser) throws Exception ;
}
