package com.egg.manager.service.define;

import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.web.pagination.AntdvPaginationBean;
import com.egg.manager.common.web.pagination.AntdvSortBean;
import com.egg.manager.entity.define.DefineGroup;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.define.DefinePermissionVo;
import com.egg.manager.webvo.query.QueryFormFieldBean;

import java.util.List;
import java.util.Map;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface DefinePermissionService extends IService<DefinePermission> {

    List<DefinePermission> dealGetAllPermissionByRoles(List<DefineRole> defineRoles) ;


    /**
     * 分页查询 权限
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetDefinePermissionPages(MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                      List<AntdvSortBean> sortBeans);

    /**
     * 权限定义-新增
     * @param definePermissionVo
     * @throws Exception
     */
    Integer dealAddDefinePermission(DefinePermissionVo definePermissionVo,UserAccount loginUser) throws Exception ;

    /**
     * 权限定义-更新
     * @param definePermissionVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateDefinePermission(DefinePermissionVo definePermissionVo,UserAccount loginUser,boolean updateAll) throws Exception ;

    /**
     * 权限定义-批量删除
     * @param delIds 要删除的权限id 集合
     * @throws Exception
     */
    Integer dealDelDefinePermissionByArr(String[] delIds,UserAccount loginUser) throws Exception;

    /**
     * 权限定义-删除
     * @param delId 要删除的权限id
     * @throws Exception
     */
    Integer dealDelDefinePermission(String delId,UserAccount loginUser) throws Exception;
}
