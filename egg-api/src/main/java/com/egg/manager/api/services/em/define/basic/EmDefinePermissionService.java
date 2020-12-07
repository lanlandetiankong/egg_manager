package com.egg.manager.api.services.em.define.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefinePermissionEntity;
import com.egg.manager.persistence.em.define.db.mysql.mapper.EmDefinePermissionMapper;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefinePermissionVo;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;

import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmDefinePermissionService extends MyBaseMysqlService<EmDefinePermissionEntity, EmDefinePermissionMapper, EmDefinePermissionVo> {

    /**
     * 查询 所有[可用状态]的 [权限定义]
     * @param wrapper
     * @return
     */
    List<EmDefinePermissionEntity> getAllEnableList(QueryWrapper<EmDefinePermissionEntity> wrapper);

    /**
     * 分页查询 权限定义 列表
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 分页查询-配置
     * @return
     */
    WebResult dealQueryPageByEntitys(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean queryPageBean);

    /**
     * 分页查询 权限定义 dto列表
     * (查询的是 com.egg.manager.persistence.obl.article.pojo.dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPageBean 查询分页配置
     * @return
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean queryPageBean);

    /**
     * 权限定义-新增
     * @param loginUserInfo      当前登录用户
     * @param emDefinePermissionVo
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmDefinePermissionVo emDefinePermissionVo) throws Exception;

    /**
     * 权限定义-更新
     * @param loginUserInfo      当前登录用户
     * @param emDefinePermissionVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmDefinePermissionVo emDefinePermissionVo) throws Exception;

    /**
     * 权限定义-启用
     * @param loginUserInfo 当前登录用户
     * @param ensureIds     要启用的权限id 集合
     * @return
     * @throws Exception
     */
    Integer dealBatchEnsure(CurrentLoginEmUserInfo loginUserInfo, String[] ensureIds);

    /**
     * 取得用户 所拥有的 权限定义-List集合
     * @param userAccountId
     * @return
     */
    List<EmDefinePermissionEntity> dealGetListByAccountFromDb(String userAccountId);

    /**
     * 取得用户 所拥有的 权限code-Set集合
     * @param userAccountId
     * @return
     */
    Set<String> queryDbToCacheable(String userAccountId);


    /**
     * 验证 数据库 中的唯一冲突
     * @param emDefinePermissionVo
     * @param definePermissionWrap
     * @return
     */
    MyVerifyDuplicateBean dealCheckDuplicateKey(EmDefinePermissionVo emDefinePermissionVo, QueryWrapper<EmDefinePermissionEntity> definePermissionWrap);
}
