package com.egg.manager.api.services.basic.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserJob;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserJobMapper;
import com.egg.manager.persistence.em.user.pojo.dto.UserJobDto;
import com.egg.manager.persistence.em.user.pojo.vo.UserJobVo;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface UserJobService extends IService<UserJob>, MyBaseMysqlService<UserJob, UserJobMapper, UserJobVo> {

    /**
     * 分页查询 用户职务列表
     * @param loginUser              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserJobVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserJob> paginationBean,
                                                     List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 用户职务 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<UserJobVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserJobDto> paginationBean,
                                                  List<AntdvSortBean> sortBeans);


    /**
     * 用户职务-新增
     * @param loginUser 当前登录用户
     * @param userJobVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser, UserJobVo userJobVo) throws Exception;

    /**
     * 用户职务-更新
     * @param loginUser 当前登录用户
     * @param userJobVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser, UserJobVo userJobVo) throws Exception;

}
