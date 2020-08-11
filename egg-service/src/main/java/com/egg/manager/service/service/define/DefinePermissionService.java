package com.egg.manager.service.service.define;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.common.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.persistence.entity.define.DefinePermission;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.vo.define.DefinePermissionVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;

import java.util.List;
import java.util.Set;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface DefinePermissionService extends IService<DefinePermission> {

    /**
     * 查询 所有[可用状态]的 [权限定义]
     * @param wrapper
     * @return
     */
    List<DefinePermission> getAllEnableDefinePermissions(EntityWrapper<DefinePermission> wrapper);

    /**
     * 分页查询 权限定义 列表
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetDefinePermissionPages(MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                      List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 权限定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    void dealGetDefinePermissionDtoPages(MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
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


    /**
     * 权限定义-启用
     * @param ensureIds 要启用的权限id 集合
     * @throws Exception
     */
    Integer dealEnsureDefinePermissionByArr(String[] ensureIds,UserAccount loginUser);

    /**
     * 取得用户 所拥有的 权限定义-List集合
     * @param userAccountId
     * @return
     */
    List<DefinePermission> dealGetPermissionsByAccountFromDb(String userAccountId) ;
    /**
     * 取得用户 所拥有的 权限code-Set集合
     * @param userAccountId
     * @return
     */
    Set<String> dealGetPermissionCodeSetByAccountFromDb(String userAccountId);


    /**
     * 验证 数据库 中的唯一冲突
     * @param definePermissionVo
     * @param definePermissionWrap
     * @return
     */
    MyVerifyDuplicateBean dealCheckDuplicateKey(DefinePermissionVo definePermissionVo, Wrapper<DefinePermission> definePermissionWrap);
}
