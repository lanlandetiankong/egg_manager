package com.egg.manager.api.service.service.module;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.persistence.entity.module.DefineModule;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.vo.module.DefineModuleVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface DefineModuleService extends IService<DefineModule> {


    /**
     * 分页查询 模块
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetDefineModulePages(MyCommonResult<DefineModuleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                      List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 模块 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetDefineModuleDtoPages(MyCommonResult<DefineModuleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                            List<AntdvSortBean> sortBeans);
    /**
     * 模块定义-新增
     * @param defineModuleVo
     * @throws Exception
     */
    Integer dealAddDefineModule(DefineModuleVo defineModuleVo, UserAccount loginUser) throws Exception ;

    /**
     * 模块定义-更新
     * @param defineModuleVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateDefineModule(DefineModuleVo defineModuleVo,UserAccount loginUser,boolean updateAll) throws Exception ;

    /**
     * 模块定义-批量删除
     * @param delIds 要删除的模块id 集合
     * @throws Exception
     */
    Integer dealDelDefineModuleByArr(String[] delIds,UserAccount loginUser) throws Exception;

    /**
     * 模块定义-删除
     * @param delId 要删除的模块id
     * @throws Exception
     */
    Integer dealDelDefineModule(String delId,UserAccount loginUser) throws Exception;
}
