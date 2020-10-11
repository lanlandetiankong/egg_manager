package com.egg.manager.api.services.basic.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserJob;
import com.egg.manager.persistence.db.mysql.mapper.user.UserJobMapper;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserJobDto;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserJobVo;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface UserJobService extends IService<UserJob>,MyBaseMysqlService<UserJobMapper,UserJob,UserJobVo> {

    /**
     * 分页查询 用户职务列表
     * @param loginUser 当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserJobVo> dealQueryPageByEntitys(UserAccount loginUser,MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserJob> paginationBean,
                                                  List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 用户职务 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser 当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserJobVo> dealQueryPageByDtos(UserAccount loginUser,MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserJobDto> paginationBean,
                                                     List<AntdvSortBean> sortBeans);


    /**
     * 用户职务-新增
     * @param loginUser 当前登录用户
     * @param UserJobVo
     * @throws Exception
     * @return
     */
    Integer dealCreate(UserAccount loginUser,UserJobVo UserJobVo) throws Exception;

    /**
     * 用户职务-更新
     * @param loginUser 当前登录用户
     * @param UserJobVo
     * @throws Exception
     * @return
     */
    Integer dealUpdate(UserAccount loginUser,UserJobVo UserJobVo) throws Exception;

    /**
     * 用户职务-删除
     * @param loginUser 当前登录用户
     * @param delIds 要删除的用户职务id 集合
     * @throws Exception
     * @return
     */
    Integer dealBatchDelete(UserAccount loginUser,String[] delIds) throws Exception ;

    /**
     * 用户职务-删除
     * @param loginUser 当前登录用户
     * @param delId 要删除的用户职务id
     * @throws Exception
     * @return
     */
    Integer dealDeleteById(UserAccount loginUser,String delId) throws Exception ;
}
