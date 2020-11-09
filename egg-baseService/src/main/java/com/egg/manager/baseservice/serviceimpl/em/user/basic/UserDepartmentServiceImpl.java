package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.em.user.basic.UserDepartmentService;
import com.egg.manager.api.exchange.helper.redis.RedisHelper;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.commons.base.enums.base.BaseStateEnum;
import com.egg.manager.persistence.commons.base.enums.redis.RedisShiroCacheEnum;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserDepartmentEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserDepartmentMapper;
import com.egg.manager.persistence.em.user.pojo.dto.UserDepartmentDto;
import com.egg.manager.persistence.em.user.pojo.transfer.UserDepartmentTransfer;
import com.egg.manager.persistence.em.user.pojo.vo.UserDepartmentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = UserDepartmentService.class)
public class UserDepartmentServiceImpl extends MyBaseMysqlServiceImpl<UserDepartmentMapper, UserDepartmentEntity, UserDepartmentVo> implements UserDepartmentService {


    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private UserDepartmentMapper userDepartmentMapper;

    @Reference
    private RedisHelper redisHelper;


    @Override
    public List<UserDepartmentEntity> dealQueryListByAccount(UserAccountEntity userAccountEntity) {
        if (super.checkUserAccountIsBlank(userAccountEntity) == true) {
            return null;
        }
        List<UserDepartmentEntity> userDepartmentEntity = dealGetAllByAccountFromRedis(userAccountEntity);
        if (userDepartmentEntity == null || userDepartmentEntity.isEmpty()) {
            userDepartmentEntity = dealGetAllByAccountFromDb(userAccountEntity);
        }
        return userDepartmentEntity;
    }


    @Override
    public List<UserDepartmentEntity> dealGetAllByAccountFromDb(UserAccountEntity userAccountEntity) {
        if (super.checkUserAccountIsBlank(userAccountEntity) == true) {
            return null;
        }
        QueryWrapper<UserDepartmentEntity> userDepartmentEm = new QueryWrapper<UserDepartmentEntity>();
        userDepartmentEm.eq("state", BaseStateEnum.ENABLED.getValue())
                .eq("user_account_id", userAccountEntity.getFid());
        userDepartmentEm.orderBy(true, false, "update_time");
        List<UserDepartmentEntity> userDepartmentEntity = userDepartmentMapper.selectList(userDepartmentEm);
        return userDepartmentEntity;
    }


    @Override
    public List<UserDepartmentEntity> dealGetAllByAccountFromRedis(UserAccountEntity userAccountEntity) {
        if (super.checkUserAccountIsBlank(userAccountEntity) == true) {
            return null;
        }
        Object userDepartmentListObj = redisHelper.hashGet(RedisShiroCacheEnum.userDepartment.getKey(), userAccountEntity.getFid());
        String userDepartmentListJson = JSONObject.toJSONString(userDepartmentListObj);
        List<UserDepartmentEntity> userDepartmentEntity = JSON.parseObject(userDepartmentListJson, new TypeReference<ArrayList<UserDepartmentEntity>>() {
        });
        return userDepartmentEntity;
    }


    @Override
    public MyCommonResult<UserDepartmentVo> dealQueryPageByEntitys(UserAccountEntity loginUser, MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserDepartmentEntity> paginationBean,
                                                                   List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<UserDepartmentEntity> userDepartmentEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = userDepartmentMapper.selectCount(userDepartmentEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = userDepartmentMapper.selectPage(page, userDepartmentEntityWrapper);
        List<UserDepartmentEntity> userDepartmentEntities = iPage.getRecords();
        result.setResultList(UserDepartmentTransfer.transferEntityToVoList(userDepartmentEntities));
        return result;
    }


    @Override
    public MyCommonResult<UserDepartmentVo> dealQueryPageByDtos(UserAccountEntity loginUser, MyCommonResult<UserDepartmentVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserDepartmentDto> paginationBean,
                                                                List<AntdvSortBean> sortBeans) {
        Page<UserDepartmentDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<UserDepartmentDto> userDepartmentDtoList = userDepartmentMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(UserDepartmentTransfer.transferDtoToVoList(userDepartmentDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(UserAccountEntity loginUser, UserDepartmentVo userDepartmentVo) throws Exception {
        UserDepartmentEntity userDepartmentEntity = UserDepartmentTransfer.transferVoToEntity(userDepartmentVo);
        userDepartmentEntity = super.doBeforeCreate(loginUser, userDepartmentEntity, true);
        Integer addCount = userDepartmentMapper.insert(userDepartmentEntity);
        return addCount;
    }


    @Override
    public Integer dealUpdate(UserAccountEntity loginUser, UserDepartmentVo userDepartmentVo) throws Exception {
        Integer changeCount = 0;
        UserDepartmentEntity userDepartmentEntity = UserDepartmentTransfer.transferVoToEntity(userDepartmentVo);
        userDepartmentEntity = super.doBeforeUpdate(loginUser, userDepartmentEntity);
        changeCount = userDepartmentMapper.updateById(userDepartmentEntity);
        return changeCount;
    }


}
