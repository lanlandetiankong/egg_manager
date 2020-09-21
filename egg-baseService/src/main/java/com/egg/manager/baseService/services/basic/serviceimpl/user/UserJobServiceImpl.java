package com.egg.manager.baseService.services.basic.serviceimpl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
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
import org.apache.ibatis.session.RowBounds;
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
@Service(interfaceClass = UserJobService.class)
public class UserJobServiceImpl extends MyBaseMysqlServiceImpl<UserJobMapper, UserJob, UserJobVo> implements UserJobService {
    @Autowired
    private RoutineCommonFunc routineCommonFunc;

    @Autowired
    private UserJobMapper userJobMapper;


    /**
     * 分页查询 用户职务列表
     *
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<UserJobVo> dealGetUserJobPages(UserAccount loginUser, MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                                         List<AntdvSortBean> sortBeans) {
        //解析 搜索条件
        EntityWrapper<UserJob> userJobEntityWrapper = super.doGetPageQueryWrapper(loginUser, result, queryFormFieldBeanList, paginationBean, sortBeans);
        //取得 分页配置
        RowBounds rowBounds = routineCommonFunc.parsePaginationToRowBounds(paginationBean);
        //取得 总数
        Integer total = userJobMapper.selectCount(userJobEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean, total);
        List<UserJob> userJobs = userJobMapper.selectPage(rowBounds, userJobEntityWrapper);
        result.setResultList(UserJobTransfer.transferEntityToVoList(userJobs));
        return result;
    }

    /**
     * 分页查询 用户职务 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     *
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public MyCommonResult<UserJobVo> dealGetUserJobDtoPages(UserAccount loginUser, MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                                            List<AntdvSortBean> sortBeans) {
        Pagination mpPagination = super.dealAntvPageToPagination(paginationBean);
        List<UserJobDto> userJobDtoList = userJobMapper.selectQueryPage(mpPagination, queryFieldBeanList, sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean, mpPagination.getTotal());
        result.setResultList(UserJobTransfer.transferDtoToVoList(userJobDtoList));
        return result;
    }


    /**
     * 用户职务-新增
     *
     * @param userJobVo
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealAddUserJob(UserAccount loginUser, UserJobVo userJobVo) throws Exception {
        UserJob userJob = UserJobTransfer.transferVoToEntity(userJobVo);
        userJob = super.doBeforeCreate(loginUser, userJob, true);
        Integer addCount = userJobMapper.insert(userJob);
        return addCount;
    }


    /**
     * 用户职务-更新
     *
     * @param userJobVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealUpdateUserJob(UserAccount loginUser, UserJobVo userJobVo, boolean updateAll) throws Exception {
        Integer changeCount = 0;
        UserJob userJob = UserJobTransfer.transferVoToEntity(userJobVo);
        userJob = super.doBeforeUpdate(loginUser, userJob);
        if (updateAll) {  //是否更新所有字段
            changeCount = userJobMapper.updateAllColumnById(userJob);
        } else {
            changeCount = userJobMapper.updateById(userJob);
        }
        return changeCount;
    }


    /**
     * 用户职务-删除
     *
     * @param delIds 要删除的用户职务id 集合
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDelUserJobByArr(UserAccount loginUser, String[] delIds) throws Exception {
        Integer delCount = 0;
        if (delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds);
            //批量伪删除
            delCount = userJobMapper.batchFakeDelByIds(delIdList, loginUser);
        }
        return delCount;
    }

    /**
     * 用户职务-删除
     *
     * @param delId 要删除的用户职务id
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer dealDelUserJob(UserAccount loginUser, String delId) throws Exception {
        UserJob userJob = super.doBeforeDeleteOneById(loginUser, UserJob.class, delId);
        Integer delCount = userJobMapper.updateById(userJob);
        return delCount;
    }
}
