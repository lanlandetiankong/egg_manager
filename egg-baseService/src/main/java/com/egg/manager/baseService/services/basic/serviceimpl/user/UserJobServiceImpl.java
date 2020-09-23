package com.egg.manager.baseService.services.basic.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egg.manager.api.services.basic.user.UserJobService;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.baseService.services.basic.serviceimpl.MyBaseMysqlServiceImpl;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.db.mysql.entity.user.UserJob;
import com.egg.manager.persistence.db.mysql.mapper.user.UserJobMapper;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserJobDto;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserJobTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserJobVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = UserJobService.class)
public class UserJobServiceImpl extends MyBaseMysqlServiceImpl<UserJobMapper, UserJob, UserJobVo> implements UserJobService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private UserJobMapper userJobMapper;


    @Override
    public MyCommonResult<UserJobVo> dealQueryPageByEntitys(UserAccount loginUser, MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
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
    public MyCommonResult<UserJobVo> dealQueryPageByDtos(UserAccount loginUser, MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
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
    public Integer dealUpdate(UserAccount loginUser, UserJobVo userJobVo, boolean updateAll) throws Exception {
        Integer changeCount = 0;
        UserJob userJob = UserJobTransfer.transferVoToEntity(userJobVo);
        userJob = super.doBeforeUpdate(loginUser, userJob);
        if (updateAll) {  //是否更新所有字段
            changeCount = userJobMapper.updateById(userJob);
        } else {
            changeCount = userJobMapper.updateById(userJob);
        }
        return changeCount;
    }

    @Override
    public Integer dealBatchDelete(UserAccount loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = userJobMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    @Override
    public Integer dealDeleteById(UserAccount loginUser, String delId) throws Exception {
        UserJob userJob = super.doBeforeDeleteOneById(loginUser, UserJob.class, delId);
        Integer delCount = userJobMapper.updateById(userJob);
        return delCount;
    }
}
