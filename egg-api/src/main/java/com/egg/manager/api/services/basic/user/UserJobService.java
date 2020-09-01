package com.egg.manager.api.services.basic.user;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserJob;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserJobVo;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface UserJobService extends IService<UserJob> {

    /**
     * 分页查询 用户职务列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<UserJobVo> dealGetUserJobPages(MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                                  List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 用户职务 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<UserJobVo> dealGetUserJobDtoPages(MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                                     List<AntdvSortBean> sortBeans);


    /**
     * 用户职务-新增
     * @param UserJobVo
     * @throws Exception
     */
    Integer dealAddUserJob(UserJobVo UserJobVo, UserAccount loginUser) throws Exception;

    /**
     * 用户职务-更新
     * @param UserJobVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateUserJob(UserJobVo UserJobVo, UserAccount loginUser, boolean updateAll) throws Exception;

    /**
     * 用户职务-删除
     * @param delIds 要删除的用户职务id 集合
     * @throws Exception
     */
    Integer dealDelUserJobByArr(String[] delIds, UserAccount loginUser) throws Exception ;

    /**
     * 用户职务-删除
     * @param delId 要删除的用户职务id
     * @throws Exception
     */
    Integer dealDelUserJob(String delId,UserAccount loginUser) throws Exception ;
}
