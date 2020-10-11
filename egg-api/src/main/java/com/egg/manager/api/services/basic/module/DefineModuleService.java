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
import com.egg.manager.persistence.pojo.mysql.dto.module.DefineModuleDto;
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
     * @param loginUser 当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefineModuleVo> dealQueryPageByEntitys(UserAccount loginUser,MyCommonResult<DefineModuleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineModule> paginationBean,
                                                            List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 模块 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser 当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefineModuleVo> dealQueryPageByDtos(UserAccount loginUser,MyCommonResult<DefineModuleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineModuleDto> paginationBean,
                                                               List<AntdvSortBean> sortBeans);
    /**
     * 模块定义-新增
     * @param loginUser 当前登录用户
     * @param defineModuleVo
     * @throws Exception
     * @return
     */
    Integer dealCreate(UserAccount loginUser,DefineModuleVo defineModuleVo) throws Exception ;

    /**
     * 模块定义-更新
     * @param loginUser 当前登录用户
     * @param defineModuleVo
     * @throws Exception
     * @return
     */
    Integer dealUpdate(UserAccount loginUser,DefineModuleVo defineModuleVo) throws Exception ;

    /**
     * 模块定义-批量删除
     * @param loginUser 当前登录用户
     * @param delIds 要删除的模块id 集合
     * @throws Exception
     * @return
     */
    Integer dealBatchDelete(UserAccount loginUser,String[] delIds) throws Exception;

    /**
     * 模块定义-删除
     * @param loginUser 当前登录用户
     * @param delId 要删除的模块id
     * @throws Exception
     * @return
     */
    Integer dealDeleteById(UserAccount loginUser,String delId) throws Exception;
}
