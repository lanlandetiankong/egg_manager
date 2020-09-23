package com.egg.manager.baseService.services.basic.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.user.UserRoleService;
import com.egg.manager.api.services.redis.service.RedisHelper;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseService.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.db.mysql.mapper.user.UserRoleMapper;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserRoleDto;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserRoleTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserRoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service(interfaceClass = UserRoleService.class)
public class UserRoleServiceImpl extends MyBaseMysqlServiceImpl<UserRoleMapper, UserRole, UserRoleVo> implements UserRoleService {
    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache;
    @Reference
    private RedisHelper redisHelper;
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 取得当前用户关联的 UserRole
     *
     * @return
     */
    @Override
    public List<UserRole> dealGetAllUserRoleByAccount(UserAccount userAccount) {
        if (checkUserAccountIsBlank(userAccount) == true) {
            return null;
        }
        List<UserRole> userRoleList = dealGetAllUserRoleByAccountFromRedis(userAccount);
        if (userRoleList == null || userRoleList.isEmpty()) {
            userRoleList = dealGetAllUserRoleByAccountFromDb(userAccount);
        }
        return userRoleList;
    }

    /**
     * 从数据库是中取得当前用户关联的 UserRole
     *
     * @return
     */
    @Override
    public List<UserRole> dealGetAllUserRoleByAccountFromDb(UserAccount userAccount) {
        if (checkUserAccountIsBlank(userAccount) == true) {
            return null;
        }
        QueryWrapper<UserRole> userRoleEm = new QueryWrapper<UserRole>();
        userRoleEm.eq("state", BaseStateEnum.ENABLED.getValue())
                .eq("user_account_id", userAccount.getFid());
        userRoleEm.orderBy(true,false,"update_time");
        List<UserRole> userRoleList = userRoleMapper.selectList(userRoleEm);
        return userRoleList;
    }

    /**
     * 从Redis中取得当前用户关联的 UserRole
     *
     * @return
     */
    @Override
    public List<UserRole> dealGetAllUserRoleByAccountFromRedis(UserAccount userAccount) {
        if (checkUserAccountIsBlank(userAccount) == true) {
            return null;
        }
        Object userRoleListObj = redisHelper.hashGet(redisPropsOfShiroCache.getUserRolesKey(), userAccount.getFid());
        String userRoleListJson = JSONObject.toJSONString(userRoleListObj);
        List<UserRole> userRoleList = JSON.parseObject(userRoleListJson, new TypeReference<ArrayList<UserRole>>() {
        });
        return userRoleList;
    }


    private boolean checkUserAccountIsBlank(UserAccount userAccount) {
        if (userAccount == null || StringUtils.isBlank(userAccount.getFid())) {
            return true;
        }
        return false;
    }


    /**
     * 分页查询 用户角色列表
     *
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<UserRoleVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<UserRoleVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                                           List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<UserRole> userRoleEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = userRoleMapper.selectCount(userRoleEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = userRoleMapper.selectPage(page, userRoleEntityWrapper);
        List<UserRole> userRoles = iPage.getRecords();
        result.setResultList(UserRoleTransfer.transferEntityToVoList(userRoles));
        return result;
    }


    /**
     * 分页查询 用户角色 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<UserRoleVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<UserRoleVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                              List<AntdvSortBean> sortBeans) {
        Page<UserRoleDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<UserRoleDto> userRoleDtoList = userRoleMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(UserRoleTransfer.transferDtoToVoList(userRoleDtoList));
        return result;
    }


    /**
     * 用户角色-新增
     *
     * @param userRoleVo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealCreate(UserAccount loginUser, UserRoleVo userRoleVo) throws Exception {
        UserRole userRole = UserRoleTransfer.transferVoToEntity(userRoleVo);
        userRole = super.doBeforeCreate(loginUser, userRole, true);
        Integer addCount = userRoleMapper.insert(userRole);
        return addCount;
    }


    /**
     * 用户角色-更新
     *
     * @param userRoleVo
     * @param updateAll  是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealUpdate(UserAccount loginUser, UserRoleVo userRoleVo, boolean updateAll) throws Exception {
        Integer changeCount = 0;
        UserRole userRole = UserRoleTransfer.transferVoToEntity(userRoleVo);
        userRole = super.doBeforeUpdate(loginUser, userRole);
        if (updateAll) {  //是否更新所有字段
            changeCount = userRoleMapper.updateById(userRole);
        } else {
            changeCount = userRoleMapper.updateById(userRole);
        }
        return changeCount;
    }


    /**
     * 用户角色-删除
     *
     * @param delIds 要删除的用户角色id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealBatchDelete(UserAccount loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = userRoleMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    /**
     * 用户角色-删除
     *
     * @param delId 要删除的用户角色id
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception {
        UserRole userRole = super.doBeforeDeleteOneById(loginUser, UserRole.class, delId);
        Integer delCount = userRoleMapper.updateById(userRole);
        return delCount;
    }
}
