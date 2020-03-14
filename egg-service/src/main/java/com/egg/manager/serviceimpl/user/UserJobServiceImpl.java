package com.egg.manager.serviceimpl.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.props.redis.shiro.RedisPropsOfShiroCache;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.AntdvSortBean;
import com.egg.manager.dto.define.DefineDepartmentDto;
import com.egg.manager.dto.user.UserJobDto;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserJob;
import com.egg.manager.mapper.user.UserJobMapper;
import com.egg.manager.service.CommonFuncService;
import com.egg.manager.service.redis.RedisHelper;
import com.egg.manager.service.user.UserJobService;
import com.egg.manager.vo.define.DefineDepartmentVo;
import com.egg.manager.vo.user.UserJobVo;
import com.egg.manager.common.base.query.QueryFormFieldBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service
public class UserJobServiceImpl extends ServiceImpl<UserJobMapper,UserJob> implements UserJobService {

    @Autowired
    private UserJobMapper userJobMapper ;
    @Autowired
    private CommonFuncService commonFuncService ;

    @Autowired
    private RedisHelper redisHelper ;

    @Autowired
    private RedisPropsOfShiroCache redisPropsOfShiroCache ;



    /**
     * 分页查询 用户职务列表
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetUserJobPages(MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                    List<AntdvSortBean> sortBeans){
        //解析 搜索条件
        EntityWrapper<UserJob> userJobEntityWrapper = new EntityWrapper<UserJob>();
        //取得 分页配置
        RowBounds rowBounds = commonFuncService.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到 userJobEntityWrapper
        commonFuncService.dealSetConditionsMapToEntityWrapper(userJobEntityWrapper,queryFormFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                userJobEntityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        //取得 总数
        Integer total = userJobMapper.selectCount(userJobEntityWrapper);
        result.myAntdvPaginationBeanSet(paginationBean,total);
        List<UserJob> userJobs = userJobMapper.selectPage(rowBounds,userJobEntityWrapper) ;
        result.setResultList(UserJobVo.transferEntityToVoList(userJobs));
    }

    /**
     * 分页查询 用户职务 Dto列表
     * (查询的是 dto，最终依然是转化为vo，包含了较多的信息，需要耗费sql的资源相对较多)
     * @param result
     * @param queryFieldBeanList
     * @param paginationBean
     */
    @Override
    public void dealGetUserJobDtoPages(MyCommonResult<UserJobVo> result, List<QueryFormFieldBean> queryFieldBeanList, AntdvPaginationBean paginationBean,
                                    List<AntdvSortBean> sortBeans){
        Pagination mpPagination = this.commonFuncService.dealAntvPageToPagination(paginationBean);
        List<UserJobDto> userJobDtoList = userJobMapper.selectQueryPage(mpPagination, queryFieldBeanList,sortBeans);
        result.myAntdvPaginationBeanSet(paginationBean,mpPagination.getTotal());
        result.setResultList(UserJobVo.transferDtoToVoList(userJobDtoList));
    }


    /**
     * 用户职务-新增
     * @param userJobVo
     * @throws Exception
     */
    @Override
    public Integer dealAddUserJob(UserJobVo userJobVo,UserAccount loginUser) throws Exception{
        Date now = new Date() ;
        UserJob userJob = UserJobVo.transferVoToEntity(userJobVo);
        userJob.setFid(MyUUIDUtil.renderSimpleUUID());
        userJob.setState(BaseStateEnum.ENABLED.getValue());
        userJob.setCreateTime(now);
        userJob.setUpdateTime(now);
        if(loginUser != null){
            userJob.setCreateUserId(loginUser.getFid());
            userJob.setLastModifyerId(loginUser.getFid());
        }
        Integer addCount = userJobMapper.insert(userJob) ;
        return addCount ;
    }


    /**
     * 用户职务-更新
     * @param userJobVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Override
    public Integer dealUpdateUserJob(UserJobVo userJobVo,UserAccount loginUser,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        userJobVo.setUpdateTime(now);
        UserJob userJob = UserJobVo.transferVoToEntity(userJobVo);
        if(loginUser != null){
            userJob.setLastModifyerId(loginUser.getFid());
        }
        if(updateAll){  //是否更新所有字段
            changeCount = userJobMapper.updateAllColumnById(userJob) ;
        }   else {
            changeCount = userJobMapper.updateById(userJob) ;
        }
        return changeCount ;
    }



    /**
     * 用户职务-删除
     * @param delIds 要删除的用户职务id 集合
     * @throws Exception
     */
    @Override
    public Integer dealDelUserJobByArr(String[] delIds, UserAccount loginUser) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = userJobMapper.batchFakeDelByIds(delIdList,loginUser);
        }
        return delCount ;
    }

    /**
     * 用户职务-删除
     * @param delId 要删除的用户职务id
     * @throws Exception
     */
    @Override
    public Integer dealDelUserJob(String delId,UserAccount loginUser) throws Exception{
        UserJob userJob = UserJob.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        if(loginUser != null){
            userJob.setLastModifyerId(loginUser.getFid());
        }
        Integer delCount = userJobMapper.updateById(userJob);
        return delCount ;
    }
}
