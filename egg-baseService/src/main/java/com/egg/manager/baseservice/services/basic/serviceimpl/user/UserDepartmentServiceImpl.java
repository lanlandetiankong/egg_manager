package com.egg.manager.baseservice.services.basic.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.user.UserDepartmentService;
import com.egg.manager.api.services.redis.service.RedisHelper;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseservice.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.redis.RedisShiroCacheEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserDepartment;
import com.egg.manager.persistence.db.mysql.mapper.user.UserDepartmentMapper;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserDepartmentDto;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserDepartmentTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserDepartmentVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = UserDepartmentService.class)
public class UserDepartmentServiceImpl extends MyBaseMysqlServiceImpl<UserDepartmentMapper, UserDepartment, UserDepartmentVo> implements UserDepartmentService {


    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private UserDepartmentMapper userDepartmentMapper;

    @Reference
    private RedisHelper redisHelper;


    @Override
    public List<UserDepartment> dealQueryListByAccount(UserAccount userAccount) {
        if (super.checkUserAccountIsBlank(userAccount) == true) {
            return null;
        }
        List<UserDepartment> userDepartment = dealGetAllByAccountFromRedis(userAccount);
        if (userDepartment == null || userDepartment.isEmpty()) {
            userDepartment = dealGetAllByAccountFromDb(userAccount);
        }
        return userDepartment;
    }


    @Override
    public List<UserDepartment> dealGetAllByAccountFromDb(UserAccount userAccount) {
        if (super.checkUserAccountIsBlank(userAccount) == true) {
            return null;
        }
        QueryWrapper<UserDepartment> userDepartmentEm = new QueryWrapper<UserDepartment>();
        userDepartmentEm.eq("state", BaseStateEnum.ENABLED.getValue())
                .eq("user_account_id", userAccount.getFid());
        userDepartmentEm.orderBy(true, false, "update_time");
        List<UserDepartment> userDepartment = userDepartmentMapper.selectList(userDepartmentEm);
        return userDepartment;
    }


    @Override
    public List<UserDepartment> dealGetAllByAccountFromRedis(UserAccount userAccount) {
        if (super.checkUserAccountIsBlank(userAccount) == true) {
            return null;
        }
        Object userDepartmentListObj = redisHelper.hashGet(RedisShiroCacheEnum.userDepartment.getKey(), userAccount.getFid());
        String userDepartmentListJson = JSONObject.toJSONString(userDepartmentListObj);
        List<UserDepartment> userDepartment = JSON.parseObject(userDepartmentListJson, new TypeReference<ArrayList<UserDepartment>>() {
        });
        return userDepartment;
    }


    @Override
    public MyCommonResult<UserDepartmentVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserDepartment> paginationBean,
                                                                   List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<UserDepartment> userDepartmentEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = userDepartmentMapper.selectCount(userDepartmentEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = userDepartmentMapper.selectPage(page, userDepartmentEntityWrapper);
        List<UserDepartment> userDepartments = iPage.getRecords();
        result.setResultList(UserDepartmentTransfer.transferEntityToVoList(userDepartments));
        return result;
    }


    @Override
    public MyCommonResult<UserDepartmentVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserDepartmentDto> paginationBean,
                                                                List<AntdvSortBean> sortBeans) {
        Page<UserDepartmentDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<UserDepartmentDto> userDepartmentDtoList = userDepartmentMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(UserDepartmentTransfer.transferDtoToVoList(userDepartmentDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(UserAccount loginUser, UserDepartmentVo userDepartmentVo) throws Exception {
        UserDepartment userDepartment = UserDepartmentTransfer.transferVoToEntity(userDepartmentVo);
        userDepartment = super.doBeforeCreate(loginUser, userDepartment, true);
        Integer addCount = userDepartmentMapper.insert(userDepartment);
        return addCount;
    }


    @Override
    public Integer dealUpdate(UserAccount loginUser, UserDepartmentVo userDepartmentVo) throws Exception {
        Integer changeCount = 0;
        UserDepartment userDepartment = UserDepartmentTransfer.transferVoToEntity(userDepartmentVo);
        userDepartment = super.doBeforeUpdate(loginUser, userDepartment);
        changeCount = userDepartmentMapper.updateById(userDepartment);
        return changeCount;
    }

    @Override
    public Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception {
        Integer delCount = userDepartmentMapper.deleteByIdWithModifyFill(delId,loginUser);
        return delCount;
    }


}
