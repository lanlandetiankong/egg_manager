package com.egg.manager.api.services.basic.define;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefinePermissionMapper;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefinePermissionVo;

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
public interface DefinePermissionService extends IService<DefinePermission>,MyBaseMysqlService<DefinePermissionMapper,DefinePermission,DefinePermissionVo> {

    /**
     * 查询 所有[可用状态]的 [权限定义]
     * @param wrapper
     * @return
     */
    List<DefinePermission> getAllEnableDefinePermissions(UserAccount loginUser,EntityWrapper<DefinePermission> wrapper);

    /**
     * 分页查询 权限定义 列表
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<DefinePermissionVo> dealGetDefinePermissionPages(UserAccount loginUser,MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                    List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 权限定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    MyCommonResult<DefinePermissionVo> dealGetDefinePermissionDtoPages(UserAccount loginUser,MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                       List<AntdvSortBean> sortBeans);

    /**
     * 权限定义-新增
     * @param definePermissionVo
     * @throws Exception
     */
    Integer dealAddDefinePermission(UserAccount loginUser,DefinePermissionVo definePermissionVo) throws Exception ;

    /**
     * 权限定义-更新
     * @param definePermissionVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    Integer dealUpdateDefinePermission(UserAccount loginUser,DefinePermissionVo definePermissionVo,boolean updateAll) throws Exception ;

    /**
     * 权限定义-批量删除
     * @param delIds 要删除的权限id 集合
     * @throws Exception
     */
    Integer dealDelDefinePermissionByArr(UserAccount loginUser,String[] delIds) throws Exception;

    /**
     * 权限定义-删除
     * @param delId 要删除的权限id
     * @throws Exception
     */
    Integer dealDelDefinePermission(UserAccount loginUser,String delId) throws Exception;


    /**
     * 权限定义-启用
     * @param ensureIds 要启用的权限id 集合
     * @throws Exception
     */
    Integer dealEnsureDefinePermissionByArr(UserAccount loginUser,String[] ensureIds);

    /**
     * 取得用户 所拥有的 权限定义-List集合
     * @param userAccountId
     * @return
     */
    List<DefinePermission> dealGetPermissionsByAccountFromDb(UserAccount loginUser,String userAccountId) ;
    /**
     * 取得用户 所拥有的 权限code-Set集合
     * @param userAccountId
     * @return
     */
    Set<String> dealGetPermissionCodeSetByAccountFromDb(UserAccount loginUser,String userAccountId);


    /**
     * 验证 数据库 中的唯一冲突
     * @param definePermissionVo
     * @param definePermissionWrap
     * @return
     */
    MyVerifyDuplicateBean dealCheckDuplicateKey(DefinePermissionVo definePermissionVo, Wrapper<DefinePermission> definePermissionWrap);
}
