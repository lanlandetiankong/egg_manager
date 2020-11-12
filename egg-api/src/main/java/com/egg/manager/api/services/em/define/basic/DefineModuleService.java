package com.egg.manager.api.services.em.define.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineModuleEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineModuleMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineModuleDto;
import com.egg.manager.persistence.em.define.pojo.vo.DefineModuleVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface DefineModuleService extends IService<DefineModuleEntity>, MyBaseMysqlService<DefineModuleEntity, DefineModuleMapper, DefineModuleVo> {


    /**
     * 分页查询 模块
     * @param loginUserInfo          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    WebResult dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, WebResult result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineModuleEntity> paginationBean,
                                     List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 模块 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    WebResult dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, WebResult result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineModuleDto> paginationBean,
                                  List<AntdvSortBean> sortBeans);

    /**
     * 模块定义-新增
     * @param loginUserInfo      当前登录用户
     * @param defineModuleVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, DefineModuleVo defineModuleVo) throws Exception;

    /**
     * 模块定义-更新
     * @param loginUserInfo      当前登录用户
     * @param defineModuleVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, DefineModuleVo defineModuleVo) throws Exception;

}
