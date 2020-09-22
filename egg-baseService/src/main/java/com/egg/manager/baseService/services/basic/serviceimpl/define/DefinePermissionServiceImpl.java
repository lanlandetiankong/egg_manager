package com.egg.manager.baseService.services.basic.serviceimpl.define;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.define.DefinePermissionService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseService.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.beans.verify.MyVerifyDuplicateBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.base.exception.MyDbException;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.mapper.define.DefinePermissionMapper;
import com.egg.manager.persistence.db.mysql.mapper.user.UserAccountMapper;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefinePermissionDto;
import com.egg.manager.persistence.pojo.mysql.transfer.define.DefinePermissionTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefinePermissionVo;
import com.google.common.collect.Sets;
import javafx.scene.control.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
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
@Service(interfaceClass = DefinePermissionService.class)
public class DefinePermissionServiceImpl extends MyBaseMysqlServiceImpl<DefinePermissionMapper, DefinePermission, DefinePermissionVo> implements DefinePermissionService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private DefinePermissionMapper definePermissionMapper;
    @Autowired
    private UserAccountMapper userAccountMapper;

    /**
     * 查询 所有[可用状态]的 [权限定义]
     *
     * @param wrapper
     * @return
     */
    @Override
    public List<DefinePermission> getAllEnableDefinePermissions(UserAccount loginUser, QueryWrapper<DefinePermission> wrapper) {
        wrapper = wrapper != null ? wrapper : new QueryWrapper<DefinePermission>();
        //筛选与排序
        wrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        wrapper.orderBy(true,false,"create_time");
        return definePermissionMapper.selectList(wrapper);
    }

    /**
     * 分页查询 权限定义 列表
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<DefinePermissionVo> dealGetDefinePermissionPages(UserAccount loginUser, MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                           List<AntdvSortBean> sortBeans) {
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //解析 搜索条件
        QueryWrapper<DefinePermission> queryWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFieldBeanList, paginationBean, sortBeans);
        //取得 总数
        Integer total = definePermissionMapper.selectCount(queryWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = definePermissionMapper.selectPage(page, queryWrapper);
        List<DefinePermission> definePermissions = iPage.getRecords();
        result.setResultList(DefinePermissionTransfer.transferEntityToVoList(definePermissions));
        return result;
    }

    /**
     * 分页查询 权限定义 dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<DefinePermissionVo> dealGetDefinePermissionDtoPages(UserAccount loginUser, MyCommonResult<DefinePermissionVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                                              List<AntdvSortBean> sortBeans) {
        Page<DefinePermissionDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<DefinePermissionDto> definePermissionDtos = definePermissionMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(DefinePermissionTransfer.transferDtoToVoList(definePermissionDtos));
        return result;
    }


    /**
     * 权限定义-新增
     *
     * @param definePermissionVo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealAddDefinePermission(UserAccount loginUser, DefinePermissionVo definePermissionVo) throws Exception {
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(definePermissionVo, new QueryWrapper());
        if (verifyDuplicateBean.isSuccessFlag() == false) {    //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Date now = new Date();
        DefinePermission definePermission = DefinePermissionTransfer.transferVoToEntity(definePermissionVo);
        definePermission = super.doBeforeCreate(loginUser, definePermission, true);
        definePermission.setEnsure(BaseStateEnum.DISABLED.getValue());
        return definePermissionMapper.insert(definePermission);
    }


    /**
     * 权限定义-更新
     *
     * @param definePermissionVo
     * @param updateAll          是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealUpdateDefinePermission(UserAccount loginUser, DefinePermissionVo definePermissionVo, boolean updateAll) throws Exception {
        QueryWrapper<DefinePermission> uniWrapper = new QueryWrapper<DefinePermission>()
                .ne("fid", definePermissionVo.getFid());
        MyVerifyDuplicateBean verifyDuplicateBean = dealCheckDuplicateKey(definePermissionVo, uniWrapper);
        if (verifyDuplicateBean.isSuccessFlag() == false) {    //已有重复键值
            throw new MyDbException(verifyDuplicateBean.getErrorMsg());
        }
        Integer changeCount = 0;
        DefinePermission updateEntity = DefinePermissionTransfer.transferVoToEntity(definePermissionVo);
        updateEntity = super.doBeforeUpdate(loginUser, updateEntity);
        DefinePermission oldEntity = definePermissionMapper.selectById(definePermissionVo.getFid());
        if (SwitchStateEnum.Open.getValue().equals(oldEntity.getEnsure())) {    //如果已经启用
            DefinePermissionTransfer.handleSwitchOpenChangeFieldChange(updateEntity, oldEntity);
        }
        if (updateAll) {  //是否更新所有字段
            changeCount = definePermissionMapper.updateById(updateEntity);
        } else {
            changeCount = definePermissionMapper.updateById(updateEntity);
        }
        return changeCount;
    }


    /**
     * 权限定义-删除
     *
     * @param delIds 要删除的权限id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDelDefinePermissionByArr(UserAccount loginUser, String[] delIds) {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = definePermissionMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    /**
     * 权限定义-启用
     *
     * @param ensureIds 要启用的权限id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealEnsureDefinePermissionByArr(UserAccount loginUser, String[] ensureIds) {
        Integer delCount = 0;
        if (ensureIds != null && ensureIds.length > 0) {
            List<String> delIdList = Arrays.asList(ensureIds);
            //批量伪删除
            delCount = definePermissionMapper.batchEnsureByIds(delIdList, loginUser);
        }
        return delCount;
    }

    /**
     * 权限定义-删除
     *
     * @param delId 要删除的权限id
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDelDefinePermission(UserAccount loginUser, String delId) throws Exception {
        DefinePermission updateEntity = super.doBeforeDeleteOneById(loginUser, DefinePermission.class, delId);
        return definePermissionMapper.updateById(updateEntity);
    }


    /**
     * 取得用户 所拥有的 权限定义-List集合
     *
     * @param userAccountId
     * @return
     */
    @Override
    public List<DefinePermission> dealGetPermissionsByAccountFromDb(UserAccount loginUser, String userAccountId) {
        if (StringUtils.isBlank(userAccountId)) {
            return null;
        }
        UserAccount userAccount = userAccountMapper.selectById(userAccountId);
        if (UserAccountBaseTypeEnum.SuperRoot.getValue().equals(userAccount.getUserTypeNum())) {    //如果是[超级管理员]的话可以访问全部菜单
            return getAllEnableDefinePermissions(loginUser, null);
        } else {
            return definePermissionMapper.findAllPermissionByUserAcccountId(userAccountId);
        }
    }

    /**
     * 取得用户 所拥有的 权限code-Set集合
     *
     * @param userAccountId
     * @return
     */
    @Override
    public Set<String> dealGetPermissionCodeSetByAccountFromDb(UserAccount loginUser, String userAccountId) {
        Set<String> codeSet = Sets.newHashSet();
        List<DefinePermission> definePermissions = this.dealGetPermissionsByAccountFromDb(loginUser, userAccountId);
        if (definePermissions != null && definePermissions.isEmpty() == false) {
            for (DefinePermission definePermission : definePermissions) {
                String permissionCode = definePermission.getCode();
                if (StringUtils.isNotBlank(permissionCode)) {
                    codeSet.add(permissionCode);
                }
            }
        }
        return codeSet;
    }


    /**
     * 验证 数据库 中的唯一冲突
     *
     * @param definePermissionVo
     * @param wrapper
     * @return
     */
    @Override
    public MyVerifyDuplicateBean dealCheckDuplicateKey(DefinePermissionVo definePermissionVo, QueryWrapper<DefinePermission> wrapper) {
        MyVerifyDuplicateBean verifyBean = new MyVerifyDuplicateBean();
        wrapper = wrapper != null ? wrapper : new QueryWrapper<>();
        wrapper.eq("code", definePermissionVo.getCode());
        wrapper.eq("state", BaseStateEnum.ENABLED.getValue());
        boolean successFlag = definePermissionMapper.selectCount(wrapper) == 0;
        if (successFlag == false) {
            verifyBean.setErrorMsg("唯一键[编码]不允许重复！");
            verifyBean.dealAddColumn("code");
            verifyBean.dealAddFieldName("编码");
        }
        verifyBean.setSuccessFlag(successFlag);
        return verifyBean;
    }
}
