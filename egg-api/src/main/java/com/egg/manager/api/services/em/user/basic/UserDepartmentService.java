package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserDepartmentEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserDepartmentMapper;
import com.egg.manager.persistence.em.user.pojo.dto.UserDepartmentDto;
import com.egg.manager.persistence.em.user.pojo.vo.UserDepartmentVo;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface UserDepartmentService extends IService<UserDepartmentEntity>, MyBaseMysqlService<UserDepartmentEntity, UserDepartmentMapper, UserDepartmentVo> {

    /**
     * 查询账号下的所有[用户-部门关联]
     * @param account
     * @return
     */
    List<UserDepartmentEntity> dealQueryListByAccount(UserAccountEntity account);

    /**
     * 从数据库是中取得当前用户关联的 UserDepartment
     * @param userAccountEntity
     * @return
     */
    List<UserDepartmentEntity> dealGetAllByAccountFromDb(UserAccountEntity userAccountEntity);

    /**
     * 从Redis中取得当前用户关联的 UserDepartment
     * @param userAccountEntity
     * @return
     */
    List<UserDepartmentEntity> dealGetAllByAccountFromRedis(UserAccountEntity userAccountEntity);


    /**
     * 分页查询 用户与部门关联列表
     * @param loginUser              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserDepartmentVo> dealQueryPageByEntitys(UserAccountEntity loginUser, MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserDepartmentEntity> paginationBean,
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
    MyCommonResult<UserDepartmentVo> dealQueryPageByDtos(UserAccountEntity loginUser, MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserDepartmentDto> paginationBean,
                                                         List<AntdvSortBean> sortBeans);


    /**
     * 用户与部门关联-新增
     * @param loginUser        当前登录用户
     * @param userDepartmentVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccountEntity loginUser, UserDepartmentVo userDepartmentVo) throws Exception;

    /**
     * 用户与部门关联-更新
     * @param loginUser        当前登录用户
     * @param userDepartmentVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccountEntity loginUser, UserDepartmentVo userDepartmentVo) throws Exception;
}
