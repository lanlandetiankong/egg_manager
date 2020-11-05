package com.egg.manager.api.services.basic.define;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineJob;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineJobMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineJobDto;
import com.egg.manager.persistence.em.define.pojo.vo.DefineJobVo;

import java.util.List;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface DefineJobService extends IService<DefineJob>, MyBaseMysqlService<DefineJob, DefineJobMapper, DefineJobVo> {


    /**
     * 分页查询 职务定义 列表
     * @param loginUser              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefineJobVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<DefineJob> paginationBean,
                                                       List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 职务定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser              当前登录用户
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefineJobVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<DefineJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<DefineJobDto> paginationBean,
                                                    List<AntdvSortBean> sortBeans);

    /**
     * 职务账号-新增
     * @param loginUser   当前登录用户
     * @param defineJobVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser, DefineJobVo defineJobVo) throws Exception;

    /**
     * 职务账号-更新
     * @param loginUser   当前登录用户
     * @param defineJobVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser, DefineJobVo defineJobVo) throws Exception;

}
