package com.egg.manager.api.services.basic.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserDepartment;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserDepartmentMapper;
import com.egg.manager.persistence.em.user.pojo.dto.UserDepartmentDto;
import com.egg.manager.persistence.em.user.pojo.vo.UserDepartmentVo;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface UserDepartmentService extends IService<UserDepartment>, MyBaseMysqlService<UserDepartment, UserDepartmentMapper, UserDepartmentVo> {

    /**
     * 查询账号下的所有[用户-部门关联]
     * @param account
     * @return
     */
    List<UserDepartment> dealQueryListByAccount(UserAccount account);

    /**
     * 从数据库是中取得当前用户关联的 UserDepartment
     * @param userAccount
     * @return
     */
    List<UserDepartment> dealGetAllByAccountFromDb(UserAccount userAccount);

    /**
     * 从Redis中取得当前用户关联的 UserDepartment
     * @param userAccount
     * @return
     */
    List<UserDepartment> dealGetAllByAccountFromRedis(UserAccount userAccount);


    /**
     * 分页查询 用户与部门关联列表
     * @param loginUser              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserDepartmentVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserDepartment> paginationBean,
                                                            List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 用户与部门关联 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserDepartmentVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserDepartmentDto> paginationBean,
                                                         List<AntdvSortBean> sortBeans);


    /**
     * 用户与部门关联-新增
     * @param loginUser        当前登录用户
     * @param userDepartmentVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser, UserDepartmentVo userDepartmentVo) throws Exception;

    /**
     * 用户与部门关联-更新
     * @param loginUser        当前登录用户
     * @param userDepartmentVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser, UserDepartmentVo userDepartmentVo) throws Exception;
}
