package com.egg.manager.api.services.em.define.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefinePermissionEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.DefinePermissionMapper;
import com.egg.manager.persistence.em.define.pojo.dto.DefinePermissionDto;
import com.egg.manager.persistence.em.define.pojo.vo.DefinePermissionVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;

import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface DefinePermissionService extends IService<DefinePermissionEntity>, MyBaseMysqlService<DefinePermissionEntity, DefinePermissionMapper, DefinePermissionVo> {

    /**
     * 查询 所有[可用状态]的 [权限定义]
     * @param wrapper
     * @return
     */
    List<DefinePermissionEntity> getAllEnableList(QueryWrapper<DefinePermissionEntity> wrapper);

    /**
     * 分页查询 权限定义 列表
     * @param loginUserInfo          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefinePermissionVo> dealQueryPageByEntitys(CurrentLoginUserInfo loginUserInfo, MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefinePermissionEntity> paginationBean,
                                                              List<AntdvSortBean> sortBeans);

    /**
     * 分页查询 权限定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo          当前登录用户
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    MyCommonResult<DefinePermissionVo> dealQueryPageByDtos(CurrentLoginUserInfo loginUserInfo, MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<DefinePermissionDto> paginationBean,
                                                           List<AntdvSortBean> sortBeans);

    /**
     * 权限定义-新增
     * @param loginUserInfo          当前登录用户
     * @param definePermissionVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginUserInfo loginUserInfo, DefinePermissionVo definePermissionVo) throws Exception;

    /**
     * 权限定义-更新
     * @param loginUserInfo          当前登录用户
     * @param definePermissionVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginUserInfo loginUserInfo, DefinePermissionVo definePermissionVo) throws Exception;

    /**
     * 权限定义-启用
     * @param loginUserInfo 当前登录用户
     * @param ensureIds 要启用的权限id 集合
     * @return
     * @throws Exception
     */
    Integer dealBatchEnsure(CurrentLoginUserInfo loginUserInfo, Long[] ensureIds);

    /**
     * 取得用户 所拥有的 权限定义-List集合
     * @param userAccountId
     * @return
     */
    List<DefinePermissionEntity> dealGetListByAccountFromDb(Long userAccountId);

    /**
     * 取得用户 所拥有的 权限code-Set集合
     * @param userAccountId
     * @return
     */
    Set<String> queryDbToCacheable(Long userAccountId);


    /**
     * 验证 数据库 中的唯一冲突
     * @param definePermissionVo
     * @param definePermissionWrap
     * @return
     */
    MyVerifyDuplicateBean dealCheckDuplicateKey(DefinePermissionVo definePermissionVo, QueryWrapper<DefinePermissionEntity> definePermissionWrap);
}
