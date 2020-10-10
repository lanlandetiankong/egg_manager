package com.egg.manager.api.services.basic.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserDepartment;
import com.egg.manager.persistence.db.mysql.mapper.user.UserDepartmentMapper;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserAccountDto;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserDepartmentDto;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserDepartmentVo;

import java.util.List;

/**
 * 用户-部门 关联service
 * \* note:
 * \* User: zhouchengjie
 * \* Description:
 * \
 */
public interface UserDepartmentService extends IService<UserDepartment>,MyBaseMysqlService<UserDepartmentMapper,UserDepartment,UserDepartmentVo> {

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
     * @param loginUser 当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserDepartmentVo> dealQueryPageByEntitys(UserAccount loginUser,MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserDepartment> paginationBean,
                                                                List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 用户与部门关联 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser 当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserDepartmentVo> dealQueryPageByDtos(UserAccount loginUser,MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserDepartmentDto> paginationBean,
                                                                   List<AntdvSortBean> sortBeans);


    /**
     * 用户与部门关联-新增
     * @param loginUser 当前登录用户
     * @param userDepartmentVo
     * @throws Exception
     * @return
     */
    Integer dealCreate(UserAccount loginUser,UserDepartmentVo userDepartmentVo) throws Exception;

    /**
     * 用户与部门关联-更新
     * @param loginUser 当前登录用户
     * @param userDepartmentVo
     * @throws Exception
     * @return
     */
    Integer dealUpdate(UserAccount loginUser,UserDepartmentVo userDepartmentVo) throws Exception;

    /**
     * 用户与部门关联-删除
     * @param loginUser 当前登录用户
     * @param delIds 要删除的用户与部门关联id 集合
     * @throws Exception
     * @return
     */
    Integer dealBatchDelete(UserAccount loginUser,String[] delIds) throws Exception ;

    /**
     * 用户与部门关联-删除
     * @param loginUser 当前登录用户
     * @param delId 要删除的用户与部门关联id
     * @throws Exception
     * @return
     */
    Integer dealDeleteById(UserAccount loginUser,String delId) throws Exception ;
}
