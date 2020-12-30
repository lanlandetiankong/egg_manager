package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.persistence.commons.base.query.pagination.QueryPageBean;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmUserAccountMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginEmUserInfo;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserAccountDto;
import com.egg.manager.persistence.em.user.pojo.dto.login.LoginAccountDTO;
import com.egg.manager.persistence.em.user.pojo.excel.export.user.EmUserAccountXlsOutModel;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserAccountVo;

import java.util.List;
import java.util.Set;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface EmUserAccountService extends MyBaseMysqlService<EmUserAccountEntity, EmUserAccountMapper, EmUserAccountVo> {

    String FOREIGN_NAME_OF_USER_TENANT = "userTenant";
    String FOREIGN_NAME_OF_USER_DEPARTMENT = "userDepartment";


    /**
     * 根据LoginAccountDTO查询对应的用户账号entity
     * @param loginAccountDTO
     * @return
     */
    EmUserAccountEntity dealGetEntityByDTO(LoginAccountDTO loginAccountDTO);


    /**
     * 分页查询 用户列表
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPage     查询分页配置
     * @return
     */
    WebResult dealQueryPageByEntitys(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmUserAccountEntity> queryPage);

    /**
     * 分页查询 用户 Dto列表
     * (查询的 Dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param loginUserInfo 当前登录用户
     * @param result
     * @param queryPage
     * @return
     */
    WebResult dealQueryPageByDtos(CurrentLoginEmUserInfo loginUserInfo, WebResult result, QueryPageBean<EmUserAccountDto> queryPage);

    /**
     * 用户账号-新增
     * @param loginUserInfo   当前登录用户
     * @param emUserAccountVo
     * @param loginUserInfo   当前登录用户
     * @return
     * @throws Exception
     */
    Integer dealCreate(CurrentLoginEmUserInfo loginUserInfo, EmUserAccountVo emUserAccountVo) throws Exception;

    /**
     * 用户账号-更新
     * @param loginUserInfo   当前登录用户
     * @param emUserAccountVo
     * @return
     * @throws Exception
     */
    Integer dealUpdate(CurrentLoginEmUserInfo loginUserInfo, EmUserAccountVo emUserAccountVo) throws Exception;

    /**
     * 用户账号-锁定
     * @param loginUserInfo 当前登录用户
     * @param lockIds       要锁定的用户账号id 集合
     * @param isLock        是否锁定
     * @return
     * @throws Exception
     */
    Integer dealBatchRenewLock(CurrentLoginEmUserInfo loginUserInfo, String[] lockIds, boolean isLock) throws Exception;

    /**
     * 用户账号-锁定
     * @param loginUserInfo 当前登录用户
     * @param lockId        要锁定的用户账号id
     * @param isLock        是否锁定
     * @param loginUserInfo 当前登录用户
     * @return
     * @throws Exception
     */
    Integer dealRenewLock(CurrentLoginEmUserInfo loginUserInfo, String lockId, boolean isLock) throws Exception;


    /**
     * 用户分配角色
     * @param loginUserInfo 当前登录用户
     * @param userAccountId 用户id
     * @param checkIds      角色id集合
     * @param loginUserInfo 当前登录用户
     * @return
     * @throws Exception
     */
    Integer dealGrantRoleToUser(CurrentLoginEmUserInfo loginUserInfo, String userAccountId, String[] checkIds) throws Exception;

    /**
     * 用户分配职务
     * @param loginUserInfo 当前登录用户
     * @param userAccountId 用户id
     * @param checkIds      职务id集合
     * @param loginUserInfo 当前登录用户
     * @return
     * @throws Exception
     */
    Integer dealGrantJobToUser(CurrentLoginEmUserInfo loginUserInfo, String userAccountId, String[] checkIds) throws Exception;


    /**
     * 用于保存前验证 数据库 中的唯一冲突
     * @param emUserAccountVo
     * @param wrapper
     * @return
     */
    boolean dealCheckDuplicateKey(EmUserAccountVo emUserAccountVo, QueryWrapper<EmUserAccountEntity> wrapper);


    /**
     * 查询要导出的[用户账号] xlsModel 集合
     * @param loginUserInfo 当前登录用户
     * @param checkIds
     * @param wrapper
     * @return
     */
    List<EmUserAccountXlsOutModel> dealGetExportXlsModelList(CurrentLoginEmUserInfo loginUserInfo, String[] checkIds, QueryWrapper<EmUserAccountEntity> wrapper);


    /**
     * 查询所有已存在的 用户账号account
     * @param loginUserInfo 当前登录用户
     * @param state
     * @param wrapper
     * @return
     */
    Set<String> dealGetExistAccountSet(CurrentLoginEmUserInfo loginUserInfo, Short state, QueryWrapper<EmUserAccountEntity> wrapper);

    /**
     * 配置缓存用-当前用户信息
     * @param userAccountId 用户id
     * @return
     */
    CurrentLoginEmUserInfo queryDbToCacheable(String userAccountId);

    /**
     * 更新所有用户的密码
     * @param loginUserInfo 当前登录用户
     * @return
     */
    boolean reflushSecurePwd(CurrentLoginEmUserInfo loginUserInfo);
}
