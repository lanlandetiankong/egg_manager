package com.egg.manager.api.services.em.define.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenuEntity;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineRoleEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefineRoleMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefineRoleDto;
import com.egg.manager.persistence.em.define.pojo.vo.DefineRoleVo;

import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface DefineRoleService extends IService<DefineRoleEntity>, MyBaseMysqlService<DefineRoleEntity, DefineRoleMapper, DefineRoleVo> {
    /**
     * 取得用户 所拥有的 角色定义-List集合
     * @param userAccountId
     * @param stateVal      状态值
     * @return
     */
    List<DefineRoleEntity> dealGetRolesByAccountFromDb(Long userAccountId, Short stateVal);

    /**
     * 取得用户 所拥有的 角色code-Set集合
     * @param userAccountId
     * @return
     */
    Set<String> queryDbToCacheable(Long userAccountId);

    /**
     * 取得角色 所拥有的 菜单定义-List集合
     * @param roleId
     * @param stateVal 状态值
     * @return
     */
    List<DefineMenuEntity> dealGetMenusByRoleIdFromDb(Long roleId, Short stateVal);

    /**
     * 取得角色 所拥有的 菜单定义id-Set集合
     * @param roleId
     * @param stateVal 状态值
     * @return
     */
    Set<Long> dealGetMenuIdSetByRoleIdFromDb(Long roleId, Short stateVal);

    /**
     * 查询 所有[可用状态]的 [角色定义]
     * @param wrapper
     * @return
     */
    List<DefineRoleEntity> queryAllEnableList(QueryWrapper<DefineRoleEntity> wrapper);

    /**
     * 根据 用户账号 取得所有角色
     * @param userAccountEntity
     * @return
     */
    List<DefineRoleEntity> dealGetListFormRedisByAccount(UserAccountEntity userAccountEntity);


    /**
     * 分页查询 角色定义 列表
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefineRoleVo> dealQueryPageByEntitys(UserAccountEntity loginUser, MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineRoleEntity> paginationBean,
                                                        List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 角色定义 列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUser          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefineRoleVo> dealQueryPageByDtos(UserAccountEntity loginUser, MyCommonResult<DefineRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefineRoleDto> paginationBean,
                                                     List<AntdvSortBean> sortBeans);

    /**
     * 角色定义-新增
     * @param loginUser    当前登录用户
     * @param defineRoleVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(UserAccountEntity loginUser, DefineRoleVo defineRoleVo) throws Exception;

    /**
     * 角色定义-更新
     * @param loginUser    当前登录用户
     * @param defineRoleVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(UserAccountEntity loginUser, DefineRoleVo defineRoleVo) throws Exception;

    /**
     * 角色授权
     * @param loginUser 当前登录用户
     * @param roleId    要授权的角色id
     * @param checkIds  权限id集合
     * @param loginUser 当前登录用户
     * @return
     * @throws Exception
     */
    Integer dealGrantPermissionToRole(UserAccountEntity loginUser, Long roleId, Long[] checkIds) throws Exception;

}