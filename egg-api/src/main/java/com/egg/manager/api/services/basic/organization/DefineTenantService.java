package com.egg.manager.api.services.basic.organization;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.organization.DefineTenantMapper;
import com.egg.manager.persistence.pojo.mysql.vo.organization.DefineTenantVo;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface DefineTenantService extends IService<DefineTenant>,MyBaseMysqlService<DefineTenantMapper,DefineTenant,DefineTenantVo> {




    /**
     * 分页查询 租户
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<DefineTenantVo> dealGetDefineTenantDtoPages(UserAccount loginUser,MyCommonResult<DefineTenantVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                               List<AntdvSortBean> sortBeans);

    /**
     * 租户定义-新增
     * @param defineTenantVo
     * @throws Exception
     */
    Integer dealAddDefineTenant(DefineTenantVo defineTenantVo, UserAccount loginUser) throws Exception ;

    /**
     * 租户定义-更新
     * @param defineTenantVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateDefineTenant(DefineTenantVo defineTenantVo, UserAccount loginUser, boolean updateAll) throws Exception ;

    /**
     * 租户定义-批量删除
     * @param delIds 要删除的租户id 集合
     * @throws Exception
     */
    Integer dealDelDefineTenantByArr(String[] delIds, UserAccount loginUser) throws Exception;

    /**
     * 租户定义-删除
     * @param delId 要删除的租户id
     * @throws Exception
     */
    Integer dealDelDefineTenant(String delId, UserAccount loginUser) throws Exception;


    /**
     * 取得的结果 转为 枚举类型
     * @param result
     */
    MyCommonResult dealResultListSetToEntitySelect(MyCommonResult result);
}
