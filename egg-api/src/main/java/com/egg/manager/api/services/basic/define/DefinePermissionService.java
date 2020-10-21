package com.egg.manager.api.services.basic.define;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.common.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefinePermissionMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefinePermissionDto;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefinePermissionVo;

import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface DefinePermissionService extends IService<DefinePermission>, MyBaseMysqlService<DefinePermission, DefinePermissionMapper, DefinePermissionVo> {

    /**
     * 查询 所有[可用状态]的 [权限定义]
     * @param loginUser 当前登录用户
     * @param wrapper
     * @return
     */
    List<DefinePermission> getAllEnableList(UserAccount loginUser, QueryWrapper<DefinePermission> wrapper);

    /**
     * 分页查询 权限定义 列表
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefinePermissionVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefinePermission> paginationBean,
                                                              List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 权限定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefinePermissionVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefinePermissionDto> paginationBean,
                                                           List<AntdvSortBean> sortBeans);

    /**
     * 权限定义-新增
     * @param loginUser          当前登录用户
     * @param definePermissionVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccount loginUser, DefinePermissionVo definePermissionVo) throws Exception;

    /**
     * 权限定义-更新
     * @param loginUser          当前登录用户
     * @param definePermissionVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccount loginUser, DefinePermissionVo definePermissionVo) throws Exception;

    /**
     * 权限定义-批量删除
     * @param loginUser 当前登录用户
     * @param delIds    要删除的权限id 集合
     * @return
     * @throws Exception
     */
    Integer dealBatchDelete(UserAccount loginUser, String[] delIds) throws Exception;

    /**
     * 权限定义-删除
     * @param loginUser 当前登录用户
     * @param delId     要删除的权限id
     * @return
     * @throws Exception
     */
    Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception;


    /**
     * 权限定义-启用
     * @param loginUser 当前登录用户
     * @param ensureIds 要启用的权限id 集合
     * @return
     * @throws Exception
     */
    Integer dealBatchEnsure(UserAccount loginUser, String[] ensureIds);

    /**
     * 取得用户 所拥有的 权限定义-List集合
     * @param loginUser     当前登录用户
     * @param userAccountId
     * @return
     */
    List<DefinePermission> dealGetListByAccountFromDb(UserAccount loginUser, String userAccountId);

    /**
     * 取得用户 所拥有的 权限code-Set集合
     * @param loginUser     当前登录用户
     * @param userAccountId
     * @return
     */
    Set<String> dealGetPermissionCodeSetByAccountFromDb(UserAccount loginUser, String userAccountId);


    /**
     * 验证 数据库 中的唯一冲突
     * @param definePermissionVo
     * @param definePermissionWrap
     * @return
     */
    MyVerifyDuplicateBean dealCheckDuplicateKey(DefinePermissionVo definePermissionVo, QueryWrapper<DefinePermission> definePermissionWrap);
}
