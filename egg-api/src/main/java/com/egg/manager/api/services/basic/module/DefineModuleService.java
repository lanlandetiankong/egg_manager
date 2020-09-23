package com.egg.manager.api.services.basic.module;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.module.DefineModule;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.module.DefineModuleMapper;
import com.egg.manager.persistence.pojo.mysql.vo.module.DefineModuleVo;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface DefineModuleService extends IService<DefineModule>,MyBaseMysqlService<DefineModuleMapper,DefineModule,DefineModuleVo> {


    /**
     * 分页查询 模块
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<DefineModuleVo> dealQueryPageByEntitys(UserAccount loginUser,MyCommonResult<DefineModuleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                            List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 模块 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<DefineModuleVo> dealQueryPageByDtos(UserAccount loginUser,MyCommonResult<DefineModuleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                               List<AntdvSortBean> sortBeans);
    /**
     * 模块定义-新增
     * @param defineModuleVo
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser,DefineModuleVo defineModuleVo) throws Exception ;

    /**
     * 模块定义-更新
     * @param defineModuleVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser,DefineModuleVo defineModuleVo,boolean updateAll) throws Exception ;

    /**
     * 模块定义-批量删除
     * @param delIds 要删除的模块id 集合
     * @throws Exception
     */
    Integer dealBatchDelete(UserAccount loginUser,String[] delIds) throws Exception;

    /**
     * 模块定义-删除
     * @param delId 要删除的模块id
     * @throws Exception
     */
    Integer dealDeleteById(UserAccount loginUser,String delId) throws Exception;
}
