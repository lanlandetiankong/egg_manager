package com.egg.manager.baseservice.serviceimpl.em.user.basic;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.em.user.basic.UserJobService;
import com.egg.manager.api.exchange.routine.RoutineCommonFunc;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserJob;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserJobMapper;
import com.egg.manager.persistence.em.user.pojo.dto.UserJobDto;
import com.egg.manager.persistence.em.user.pojo.transfer.UserJobTransfer;
import com.egg.manager.persistence.em.user.pojo.vo.UserJobVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = UserJobService.class)
public class UserJobServiceImpl extends MyBaseMysqlServiceImpl<UserJobMapper, UserJob, UserJobVo> implements UserJobService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private UserJobMapper userJobMapper;


    @Override
    public MyCommonResult<UserJobVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean<UserJob> paginationBean,
                                                            List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        QueryWrapper<UserJob> userJobEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        Page page = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = userJobMapper.selectCount(userJobEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, Long.valueOf(total));
        IPage iPage = userJobMapper.selectPage(page, userJobEntityWrapper);
        List<UserJob> userJobs = iPage.getRecords();
        result.setResultList(UserJobTransfer.transferEntityToVoList(userJobs));
        return result;
    }


    @Override
    public MyCommonResult<UserJobVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean<UserJobDto> paginationBean,
                                                         List<AntdvSortBean> sortBeans) {
        Page<UserJobDto> mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<UserJobDto> userJobDtoList = userJobMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(UserJobTransfer.transferDtoToVoList(userJobDtoList));
        return result;
    }


    @Override
    public Integer dealCreate(UserAccount loginUser, UserJobVo userJobVo) throws Exception {
        UserJob userJob = UserJobTransfer.transferVoToEntity(userJobVo);
        userJob = super.doBeforeCreate(loginUser, userJob, true);
        Integer addCount = userJobMapper.insert(userJob);
        return addCount;
    }

    @Override
    public Integer dealUpdate(UserAccount loginUser, UserJobVo userJobVo) throws Exception {
        Integer changeCount = 0;
        UserJob userJob = UserJobTransfer.transferVoToEntity(userJobVo);
        userJob = super.doBeforeUpdate(loginUser, userJob);
        changeCount = userJobMapper.updateById(userJob);
        return changeCount;
    }


}
